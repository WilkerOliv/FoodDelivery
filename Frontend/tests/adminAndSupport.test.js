import test from 'node:test';
import assert from 'node:assert/strict';
import { runOnce } from '../src/actionGuard.js';
import { formatCpfMask, formatCnpjMask, formatCurrencyMask, parseCurrencyDecimal, formatCurrencyValue } from '../src/inputMasks.js';
import { mobileNavigation } from '../src/mobileNavigation.js';
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
