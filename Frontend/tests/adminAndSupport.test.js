import { renderSupportReplyFields } from '../src/supportComposer.js';
import { bindCourierOfferActions } from '../src/deliveryBindings.js';
import { bindCatalogActions } from '../src/catalogBindings.js';
import test from 'node:test';
import assert from 'node:assert/strict';
import { runOnce } from '../src/actionGuard.js';
import { formatCpfMask, formatCnpjMask, formatCurrencyMask, parseCurrencyDecimal, formatCurrencyValue } from '../src/inputMasks.js';
import { mobileNavigation } from '../src/mobileNavigation.js';
import { catalogItemIcon, categoryMatches, customerOutsideStoreCity } from '../src/catalogPolicy.js';
import { dateTimeInZone, localDateTimeInZoneToIso, localDateTimeMinimum, storeOpenAt, storeScheduleAllowsLocalDateTime, storeHoursStatusRelevant } from '../src/storeTime.js';
import { coordinatesFromForm, storeMapUrl } from '../src/storeLocation.js';
import { adminAreaNames, adminStoreStatus, filterAdminStores, renderAdminAreas, renderAdminTeamArea, storeApprovalId, storeApprovalPath, supportDetailPath, supportMessagePath } from '../src/adminSections.js';
import { adminProfilePayload, applyAdminProfileResponse } from '../src/adminProfile.js';
import { postalCodeResponse } from '../src/postalCode.js';
import { groupCustomerReservations } from '../src/reservationList.js';

test('customer sees current service, nearest future bookings, then history', () => {
  const now = Date.parse('2026-11-05T18:00:00Z');
  const records = [
    {id:'distant',status:'CONFIRMED',startsAt:'2027-01-01T12:00:00Z'},
    {id:'cancelled',status:'CANCELLED',startsAt:'2026-11-07T12:00:00Z'},
    {id:'nearest',status:'CONFIRMED',startsAt:'2026-11-06T12:00:00Z'},
    {id:'current',status:'IN_SERVICE',startsAt:'2026-11-05T17:50:00Z'},
    {id:'past',status:'COMPLETED',startsAt:'2026-10-01T12:00:00Z'},
  ];
  const groups=groupCustomerReservations(records,now);
  assert.deepEqual(groups.map(g=>g.title),['Em atendimento','Próximas reservas','Histórico']);
  assert.deepEqual(groups[1].items.map(r=>r.id),['nearest','distant']);
  assert.deepEqual(groups[2].items.map(r=>r.id),['cancelled','past']);
  assert.equal(records[0].id,'distant');
});

test('overdue and invalid dates remain in history without hiding future bookings', () => {
  const groups=groupCustomerReservations([
    {id:'invalid',status:'CONFIRMED',startsAt:'invalid'},
    {id:'overdue',status:'CONFIRMED',startsAt:'2026-11-05T17:00:00Z'},
    {id:'pending',status:'PENDING_PAYMENT',startsAt:'2026-11-05T19:00:00Z'},
  ],Date.parse('2026-11-05T18:00:00Z'));
  assert.equal(groups[0].items[0].id,'pending');
  assert.deepEqual(groups[1].items.map(r=>r.id),['overdue','invalid']);
  assert.deepEqual(groupCustomerReservations([]),[]);
});

test('demo postal codes never call the external provider and reject unknown fixtures',async()=>{
  const oldDemo=globalThis.FOODDELIVERY_DEMO,oldFetch=globalThis.fetch;
  globalThis.FOODDELIVERY_DEMO=true;globalThis.fetch=()=>{throw new Error('External network forbidden');};
  try{assert.equal((await (await postalCodeResponse('19645000')).json()).localidade,'Nantes');assert.equal((await (await postalCodeResponse('00000000')).json()).erro,true);}
  finally{globalThis.FOODDELIVERY_DEMO=oldDemo;globalThis.fetch=oldFetch;}
});

test('the admin tickets view contains only support tickets', () => {
  const content = Object.fromEntries(adminAreaNames.map(name => [name, `<section>${name}</section>`]));
  assert.equal(renderAdminAreas(content, 'tickets'), '<section>tickets</section>');
});

test('pending stores are reachable from the store management view', () => {
  const content = Object.fromEntries(adminAreaNames.map(name => [name, `<section>${name}</section>`]));
  assert.equal(renderAdminAreas(content, 'stores'), '<section>stores</section>');
});

test('operator creation stays out of the support tickets view', () => {
  const content = Object.fromEntries(adminAreaNames.map(name => [name, `<section>${name}</section>`]));
  assert.doesNotMatch(renderAdminAreas(content, 'tickets'), /create-operator/);
});

test('team subsections show one destination at a time', () => {
  const content = Object.fromEntries(adminAreaNames.concat(['operator-profiles', 'accounts', 'create-operator']).map(name => [name, `<section>${name}</section>`]));
  assert.equal(renderAdminTeamArea(content, 'accounts'), '<section>accounts</section>');
  assert.equal(renderAdminTeamArea(content, 'profiles'), '<section>operator-profiles</section>');
  assert.equal(renderAdminTeamArea(content, 'create'), '<section>create-operator</section>');
});

test('admin ticket detail uses the implemented support endpoint', () => {
  assert.equal(supportDetailPath(true, 'ticket-42'), '/admin/support-tickets/ticket-42');
});

test('customer support replies use the customer ticket messages endpoint', () => {
  assert.equal(supportMessagePath(false, 'ticket/42'), '/support/tickets/ticket%2F42/messages');
});

test('category filtering handles accents and singular/plural built-in names', () => {
  assert.equal(categoryMatches('Barbearia', 'Barbearias'), true);
  assert.equal(categoryMatches('conveniencias', 'Conveniência'), true);
  assert.equal(categoryMatches('Farmácia', 'Farmacias'), true);
  assert.equal(categoryMatches('Pizzarias', 'Mercados'), false);
});

test('custom categories match without changing their configured name', () => {
  assert.equal(categoryMatches('Pet shop', 'Pet shop'), true);
  assert.equal(categoryMatches('Pet shop', 'Pet Shop'), true);
  assert.equal(categoryMatches('Pet shop', 'Banho e tosa'), false);
});

test('admin support replies use the admin ticket messages endpoint', () => {
  assert.equal(supportMessagePath(true, 'ticket-42'), '/admin/support-tickets/ticket-42/messages');
});

test('store approval prefers the pending request and falls back to the store endpoint', () => {
  assert.equal(storeApprovalPath('store-42', 'request-12'), '/admin/platform-requests/request-12/review');
  assert.equal(storeApprovalPath('store-42'), '/admin/stores/store-42/approval');
});

test('repeated clicks do not run the same administrative mutation twice', async () => {
  let calls = 0;
  let release;
  const action = () => { calls += 1; return new Promise(resolve => { release = resolve; }); };
  const first = runOnce('approve:store-42', action);
  const second = await runOnce('approve:store-42', action);
  assert.equal(second, undefined);
  assert.equal(calls, 1);
  release();
  await first;
});

test('an active unapproved store is labeled as awaiting review, not awaiting payment', () => {
  assert.equal(adminStoreStatus({ active: true, approved: false }), 'PENDING_REVIEW');
  assert.equal(adminStoreStatus({ active: true, approved: true }), 'CONFIRMED');
  assert.equal(adminStoreStatus({ active: false, approved: false }), 'STORE_SUSPENDED');
});

test('store approval reads the identifier from the button data attribute', () => {
  assert.equal(storeApprovalId({ dataset: { approveStore: 'store-42' } }), 'store-42');
});

test('store filters separate awaiting approval, active and suspended stores', () => {
  const stores=[{id:'pending',active:true,approved:false},{id:'active',active:true,approved:true},{id:'suspended',active:false,approved:false}];
  assert.deepEqual(filterAdminStores(stores,'PENDING_REVIEW').map(s=>s.id),['pending']);
  assert.deepEqual(filterAdminStores(stores,'CONFIRMED').map(s=>s.id),['active']);
  assert.deepEqual(filterAdminStores(stores,'STORE_SUSPENDED').map(s=>s.id),['suspended']);
});

test('CPF and CNPJ masks format only valid-length document digits', () => {
  assert.equal(formatCpfMask('12345678901'), '123.456.789-01');
  assert.equal(formatCpfMask('123456789012'), '123.456.789-01');
  assert.equal(formatCnpjMask('12345678000199'), '12.345.678/0001-99');
});

test('currency mask treats typed digits as cents and parses Brazilian values', () => {
  assert.equal(formatCurrencyMask('123456'), 'R$ 1.234,56');
  assert.equal(parseCurrencyDecimal('R$ 1.234,56'), 1234.56);
  assert.equal(parseCurrencyDecimal('29,90'), 29.9);
  assert.equal(formatCurrencyValue(29.9), 'R$ 29,90');
});

test('mobile navigation keeps four role-specific destinations and sends the rest to the menu', () => {
  const merchant = [['home','home','Comprar'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['merchant','store','Minha loja'],['merchantOrders','receipt','Pedidos da loja'],['merchantReservations','calendar','Agenda'],['notifications','bell','Notificações'],['account','user','Minha conta']];
  const result = mobileNavigation(merchant, 'MERCHANT');
  assert.deepEqual(result.visible.map(([page]) => page), ['home','merchant','merchantOrders','merchantReservations']);
  assert.equal(result.hasMore, true);
});

test('short mobile navigation does not show an unnecessary menu action', () => {
  const links = [['admin','grid','Visão geral'],['notifications','bell','Notificações'],['account','user','Minha conta']];
  assert.deepEqual(mobileNavigation(links, 'ADMIN'), { visible: links, hasMore: false });
});

test('store reservation input is converted using the store timezone', () => {
  assert.equal(localDateTimeInZoneToIso('2026-01-15T09:00', 'America/Sao_Paulo'), '2026-01-15T12:00:00.000Z');
  assert.equal(dateTimeInZone('2026-01-15T12:00:00.000Z', 'America/Sao_Paulo'), '15 de jan. de 2026, 09:00');
});

test('reservation minimum uses store local time and rejects impossible DST times', () => {
  assert.equal(localDateTimeMinimum('America/Sao_Paulo', 31, new Date('2026-01-15T12:00:00Z')), '2026-01-15T09:31');
  assert.throws(() => localDateTimeInZoneToIso('2026-03-08T02:30', 'America/New_York'), /não existe no fuso/);
});

test('out-of-city reservations require prepayment even when the seller allows payment after service', () => {
  assert.equal(customerOutsideStoreCity({city:'Nantes',state:'SP'},{city:'Iepê',state:'SP'}),true);
  assert.equal(customerOutsideStoreCity({city:'Iepê',state:'SP'},{city:'Iepê',state:'SP'}),false);
  assert.equal(customerOutsideStoreCity({city:'Iepê',state:'SP'},{city:'IEPE',state:'sp'}),false);
  assert.equal(customerOutsideStoreCity({city:'Nantes',state:'SP'},{}),true);
});

test('catalog icons describe the offered item type', () => {
  assert.equal(catalogItemIcon(true),'🗓️');
  assert.equal(catalogItemIcon(false),'📦');
});

test('weekly store hours allow selected days and treat the closing time as exclusive', () => {
  const config={enabled:true,weekly:{MONDAY:{opensAt:'09:00',closesAt:'20:00'},SUNDAY:{opensAt:'09:00',closesAt:'12:00'}},exceptions:[]};
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-02T09:00'),true);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-02T20:00'),false);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-03T10:00'),false);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-02T19:59:00Z')),true);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-02T20:00:00Z')),false);
});

test('dated closures and special hours override the regular schedule even without weekly limits', () => {
  const config={enabled:false,weekly:{},exceptions:[{date:'2026-11-01',closed:true},{date:'2026-11-02',closed:false,opensAt:'08:00',closesAt:'12:00'}]};
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-01T10:00'),false);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-02T11:59'),true);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-02T12:00'),false);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-01T10:00:00Z')),false);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-02T12:00:00Z')),false);
});

test('the store modal shows status for dated exceptions even when weekly limits are disabled', () => {
  const config={enabled:false,weekly:{},exceptions:[{date:'2026-11-01',closed:true}]};
  assert.equal(storeHoursStatusRelevant(config,'UTC',new Date('2026-11-01T10:00:00Z')),true);
  assert.equal(storeHoursStatusRelevant(config,'UTC',new Date('2026-11-02T01:00:00Z')),true);
  assert.equal(storeHoursStatusRelevant({enabled:false,weekly:{},exceptions:[]},'UTC',new Date('2026-11-01T10:00:00Z')),false);
});

test('overnight weekly hours remain available after midnight until closing', () => {
  const config={enabled:true,weekly:{FRIDAY:{opensAt:'22:00',closesAt:'02:00'}},exceptions:[]};
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-06T01:30'),false);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-06T01:30:00Z')),false);
  assert.equal(storeOpenAt(config,'UTC',new Date('2026-11-07T01:30:00Z')),true);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-07T01:30'),true);
  assert.equal(storeScheduleAllowsLocalDateTime(config,'2026-11-07T02:00'),false);
});

test('store coordinates are optional but must be a valid pair', () => {
  assert.deepEqual(coordinatesFromForm(new MapForm({latitude:'',longitude:''})), {latitude:null,longitude:null});
  assert.deepEqual(coordinatesFromForm(new MapForm({latitude:'-23.55',longitude:'-46.63'})), {latitude:-23.55,longitude:-46.63});
  assert.throws(() => coordinatesFromForm(new MapForm({latitude:'-23.55',longitude:''})), /juntas/);
  assert.throws(() => coordinatesFromForm(new MapForm({latitude:'91',longitude:'0'})), /limites válidos/);
});

test('store map links prefer saved coordinates and otherwise use its address', () => {
  assert.match(storeMapUrl({latitude:-23.55,longitude:-46.63}), /-23\.55%2C-46\.63/);
  assert.match(storeMapUrl({address:'Rua A',city:'Nantes',state:'SP'}), /Rua%20A/);
});

test('administrator can explicitly remove an optional CPF without retaining it in browser state', () => {
  const account = { id: 'admin-1', cpf: '47329438880', addressLine: 'Rua A' };
  const values = new Map([['name', 'Administrador'], ['email', 'admin@fooddelivery.local'], ['phone', ''], ['cpf', '473.294.388-80'], ['clearCpf', 'on']]);
  const payload = adminProfilePayload(values, account);
  const next = applyAdminProfileResponse(account, { name: 'Administrador', email: 'admin@fooddelivery.local', phone: null }, payload);
  assert.equal(payload.cpf, null);
  assert.equal(payload.clearCpf, true);
  assert.equal(Object.hasOwn(next, 'cpf'), false);
});

class MapForm {
  constructor(values) { this.values = values; }
  get(name) { return this.values[name]; }
}

 test('catalog forms submit through the application and editing opens the selected item', () => {
  const listeners = {};
  const form = { addEventListener: (event, handler) => { listeners.submit = handler; } };
  const button = { dataset: { itemEdit: 'item-42' }, addEventListener: (event, handler) => { listeners.click = handler; } };
  let submitted = false, edited;
  const create = event => { event.preventDefault(); submitted = true; };
  bindCatalogActions({ querySelectorAll: selector => selector === '[data-create-item]' ? [form] : [button] }, { create, edit: id => { edited = id; } });
  let prevented = false;
  listeners.submit({ preventDefault: () => { prevented = true; } });
  listeners.click();
  assert.equal(submitted, true);
  assert.equal(prevented, true);
  assert.equal(edited, 'item-42');
});

test('customer support reply has an explicit visible label and input limits', () => {
  const html = renderSupportReplyFields(false);
  assert.match(html, /<label for="support-reply-body">Mensagem para o atendimento<\/label>/);
  assert.match(html, /<textarea id="support-reply-body" name="body" maxlength="2000" required aria-describedby="support-reply-help"/);
  assert.match(html, /id="support-reply-help"/);
  assert.match(html, /Não envie senhas nem códigos/);
});

test('administrator support reply names the customer destination', () => {
  const html = renderSupportReplyFields(true);
  assert.match(html, /Resposta ao cliente/);
  assert.match(html, /Escreva uma resposta para o cliente/);
  assert.match(html, /type="submit">Enviar resposta/);
});

test('accepting an offer reads its delivery attribute instead of a missing generic id', () => {
  const handlers = [];
  const buttons = ['delivery-42', 'delivery-84'].map(acceptDelivery => ({
    dataset: { acceptDelivery },
    addEventListener: (event, handler) => { assert.equal(event, 'click'); handlers.push(handler); },
  }));
  const accepted = [];
  bindCourierOfferActions({ querySelectorAll: selector => {
    assert.equal(selector, '[data-accept-delivery]'); return buttons;
  } }, id => accepted.push(id));
  handlers.forEach(click => click());
  assert.deepEqual(accepted, ['delivery-42', 'delivery-84']);
});
