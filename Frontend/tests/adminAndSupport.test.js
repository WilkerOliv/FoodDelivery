import test from 'node:test';
import assert from 'node:assert/strict';
import { runOnce } from '../src/actionGuard.js';
import { adminAreaNames, adminStoreStatus, renderAdminAreas, renderAdminTeamArea, storeApprovalId, storeApprovalPath, supportDetailPath, supportMessagePath } from '../src/adminSections.js';

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
  assert.equal(adminStoreStatus({ active: false, approved: false }), 'CANCELLED');
});

test('store approval reads the identifier from the button data attribute', () => {
  assert.equal(storeApprovalId({ dataset: { approveStore: 'store-42' } }), 'store-42');
});
