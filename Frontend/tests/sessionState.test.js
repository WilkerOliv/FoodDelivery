import test from 'node:test';
import assert from 'node:assert/strict';
import { clearAccountState } from '../src/sessionState.js';

test('expiration removes another account’s dialog, draft, checkout and administrative data', () => {
  const state = { account: { id: 'old-admin' }, selectedStore: { id: 'old-store' },
    adminTemporaryPassword: 'temporary-secret', activeTicket: { messages: ['private'] },
    checkout: { id: 'old-payment' }, chatDrafts: { old: 'private draft' },
    adminAccounts: [{ email: 'private@example.test' }], merchantStoreId: 'old-store',
    cartOpen: true, browseLocation: { city: 'Nantes', state: 'SP' }, sessionEpoch: 8 };
  clearAccountState(state);
  assert.equal(state.account, null);
  assert.equal(state.selectedStore, null);
  assert.equal(state.checkout, null);
  assert.equal(state.activeTicket, null);
  assert.equal(state.adminTemporaryPassword, '');
  assert.equal(state.merchantStoreId, '');
  assert.equal(state.cartOpen, false);
  assert.deepEqual(state.chatDrafts, {});
  assert.deepEqual(state.adminAccounts, []);
  assert.deepEqual(state.browseLocation, { city: 'Nantes', state: 'SP' });
  assert.equal(state.sessionEpoch, 8, 'the navigation controller owns the request generation');
});

test('session resets do not share mutable arrays between identities', () => {
  const first = {}, second = {};
  clearAccountState(first); clearAccountState(second);
  first.orders.push({ id: 'first' });
  first.adminPages.accounts = 2;
  assert.deepEqual(second.orders, []);
  assert.deepEqual(second.adminPages, {});
});
