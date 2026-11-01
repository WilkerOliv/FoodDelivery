import test from 'node:test';
import assert from 'node:assert/strict';
import { runOnce } from '../src/actionGuard.js';
import { formatCpfMask, formatCnpjMask, formatCurrencyMask, parseCurrencyDecimal, formatCurrencyValue } from '../src/inputMasks.js';
import { mobileNavigation } from '../src/mobileNavigation.js';
import { catalogItemIcon, customerOutsideStoreCity } from '../src/catalogPolicy.js';
import { dateTimeInZone, localDateTimeInZoneToIso, localDateTimeMinimum, storeOpenAt, storeScheduleAllowsLocalDateTime, storeHoursStatusRelevant } from '../src/storeTime.js';
import { coordinatesFromForm, storeMapUrl } from '../src/storeLocation.js';
import { adminAreaNames, adminStoreStatus, filterAdminStores, renderAdminAreas, renderAdminTeamArea, storeApprovalId, storeApprovalPath, supportDetailPath, supportMessagePath } from '../src/adminSections.js';

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

class MapForm {
  constructor(values) { this.values = values; }
  get(name) { return this.values[name]; }
}
