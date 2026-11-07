import test from 'node:test';
import assert from 'node:assert/strict';
import { createPageLoader, pageLoadFeedback } from '../src/pageLoading.js';
import { fetchPageData } from '../src/pageData.js';

function deferred() {
  let resolve, reject;
  const promise = new Promise((yes, no) => { resolve = yes; reject = no; });
  return { promise, resolve, reject };
}

function harness(load) {
  const commits = [], statuses = [];
  let context = { key: 'customer:orders', page: 'orders' };
  const loader = createPageLoader({ getContext: () => context, load,
    commit: result => commits.push(result), onStatus: status => statuses.push(status) });
  return { loader, commits, statuses, change: value => { context = value; } };
}

test('loading is published before data arrives and does not pretend the list is empty', async () => {
  const request = deferred(), h = harness(() => request.promise);
  const pending = h.loader.run();
  assert.equal(h.statuses.at(-1).phase, 'loading');
  assert.equal(h.commits.length, 0);
  const html = pageLoadFeedback(h.statuses.at(-1), 'Meus pedidos');
  assert.match(html, /Carregando informações/);
  assert.doesNotMatch(html, /Nenhum pedido|Seus pedidos vão aparecer/);
  request.resolve({ data: { orders: [] } });
  assert.equal(await pending, true);
  assert.equal(h.statuses.at(-1).phase, 'ready');
  assert.deepEqual(h.commits[0].data.orders, []);
});

test('a newer load aborts the previous signal and wins even when the old provider ignores abort', async () => {
  const first = deferred(), second = deferred(), signals = [];
  const h = harness((context, { signal }) => { signals.push(signal); return signals.length === 1 ? first.promise : second.promise; });
  const old = h.loader.run(), latest = h.loader.run();
  assert.equal(signals[0].aborted, true);
  second.resolve({ data: { orders: ['latest'] } });
  assert.equal(await latest, true);
  first.resolve({ data: { orders: ['old'] } });
  assert.equal(await old, false);
  assert.deepEqual(h.commits.map(result => result.data.orders), [['latest']]);
});

test('navigation or store context change rejects an old response even without a second load', async () => {
  const request = deferred(), h = harness(() => request.promise);
  const pending = h.loader.run();
  h.change({ key: 'merchant:store-b', page: 'merchant' });
  request.resolve({ data: { orders: ['customer-only'] } });
  assert.equal(await pending, false);
  assert.equal(h.commits.length, 0);
});

test('logout invalidates pending data and leaves an idle state', async () => {
  const request = deferred(), h = harness(() => request.promise);
  const pending = h.loader.run();
  h.loader.invalidate();
  request.resolve({ data: { orders: ['private'] } });
  assert.equal(await pending, false);
  assert.equal(h.commits.length, 0);
  assert.equal(h.statuses.at(-1).phase, 'idle');
});

test('failure offers retry and successful retry commits real data', async () => {
  let fail = true;
  const h = harness(async () => { if (fail) throw new Error('offline'); return { data: { orders: ['saved'] } }; });
  assert.equal(await h.loader.run(), false);
  assert.equal(h.statuses.at(-1).phase, 'error');
  assert.match(pageLoadFeedback(h.statuses.at(-1), 'Pedidos'), /data-retry-page/);
  fail = false;
  assert.equal(await h.loader.run(), true);
  assert.equal(h.statuses.at(-1).phase, 'ready');
  assert.deepEqual(h.commits[0].data.orders, ['saved']);
});

test('an obsolete failure cannot replace a newer successful screen with an error', async () => {
  const first = deferred(); let count = 0;
  const h = harness(() => ++count === 1 ? first.promise : Promise.resolve({ data: {} }));
  const old = h.loader.run();
  await h.loader.run();
  first.reject(new Error('late failure'));
  assert.equal(await old, false);
  assert.equal(h.statuses.at(-1).phase, 'ready');
});

test('a normalized store selection updates the key of the ready screen', async () => {
  let key = 'store:unset', status;
  const loader = createPageLoader({ getContext: () => ({ key }), load: async () => ({ data: {} }),
    commit: () => { key = 'store:first'; }, onStatus: value => { status = value; } });
  assert.equal(await loader.run(), true);
  assert.deepEqual(status, { phase: 'ready', key: 'store:first', warning: '' });
});

test('feedback escapes labels and warnings and does not include raw server failures', () => {
  assert.match(pageLoadFeedback({ phase: 'loading' }, '<img onerror=alert(1)>'), /&lt;img/);
  assert.match(pageLoadFeedback({ phase: 'ready', warning: '<script>bad</script>' }, ''), /&lt;script/);
  const error = pageLoadFeedback({ phase: 'error', error: 'SQL password=secret' }, 'Pedidos');
  assert.doesNotMatch(error, /SQL|password|secret/);
  assert.equal(pageLoadFeedback({ phase: 'ready' }, 'Pedidos'), '');
});

const base = page => ({ page, role: 'CUSTOMER', ownStores: [], merchantStoreId: '', city: 'Nantes', state: 'SP', browseLocation: null });

test('screen requests receive the cancellation signal and produce a detached patch', async () => {
  const context = base('orders'), original = structuredClone(context), controller = new AbortController();
  const result = await fetchPageData(context, { signal: controller.signal, api: async (path, options) => {
    assert.equal(path, '/orders/mine'); assert.equal(options.signal, controller.signal); return ['one'];
  } });
  assert.deepEqual(result.data, { orders: ['one'] });
  assert.deepEqual(context, original);
});

test('courier offers and deliveries start concurrently and commit as one result', async () => {
  const offers = deferred(), deliveries = deferred(), paths = [];
  const pending = fetchPageData(base('courier'), { api: path => {
    paths.push(path); return path === '/courier/offers' ? offers.promise : deliveries.promise;
  } });
  assert.deepEqual(paths, ['/courier/offers', '/courier/deliveries']);
  deliveries.resolve(['delivery']); offers.resolve(['offer']);
  assert.deepEqual((await pending).data, { offers: ['offer'], deliveries: ['delivery'] });
});

test('store list failure never uses a cached store or shows a registration form as a successful load', async () => {
  const context = { ...base('merchant'), role: 'MERCHANT', ownStores: [{ id: 'cached' }] };
  const paths = [];
  await assert.rejects(fetchPageData(context, { api: async path => { paths.push(path); throw new Error('offline'); } }));
  assert.deepEqual(paths, ['/merchant/stores/mine']);
});

test('merchant catalog failure is a failed load rather than an empty successful catalog', async () => {
  await assert.rejects(fetchPageData({ ...base('merchant'), role: 'MERCHANT' }, { api: async path => {
    if (path === '/merchant/stores/mine') return [{ id: 'store-a' }];
    if (path.endsWith('/items')) throw new Error('catalog offline');
    return [];
  } }), /Catalog unavailable/);
});

test('merchant loads the selected store and leaves the original snapshot untouched', async () => {
  const context = { ...base('merchant'), role: 'MERCHANT', merchantStoreId: 'b' }, paths = [];
  const original = structuredClone(context);
  const result = await fetchPageData(context, { api: async path => {
    paths.push(path);
    if (path === '/merchant/stores/mine') return [{ id: 'a' }, { id: 'b' }];
    if (path.endsWith('/items')) return [{ id: 'item-b' }];
    return [];
  } });
  assert.equal(result.data.merchantStoreId, 'b');
  assert.ok(paths.includes('/merchant/stores/b/items'));
  assert.ok(paths.includes('/merchant/stores/b/hours'));
  assert.equal(paths.some(path => path.startsWith('/merchant/stores/a/')), false);
  assert.deepEqual(context, original);
});

test('direct navigation to merchant agenda loads stores before professionals', async () => {
  const context = { ...base('merchantReservations'), role: 'MERCHANT' }, paths = [];
  const result = await fetchPageData(context, { api: async path => {
    paths.push(path); return path === '/merchant/stores/mine' ? [{ id: 'a' }] : [];
  } });
  assert.equal(result.data.merchantStoreId, 'a');
  assert.ok(paths.includes('/merchant/stores/a/professionals'));
});

test('partial administrator definitions are safe arrays with an explicit warning', async () => {
  const result = await fetchPageData({ ...base('account'), role: 'ADMIN' }, { api: async path => {
    if (path.includes('audit-events')) throw new Error('audit offline');
    return [];
  } });
  assert.deepEqual(result.data.adminAuditEvents, []);
  assert.match(result.warning, /Algumas definições/);
});

test('total administrator failure is not a successful empty dashboard', async () => {
  await assert.rejects(fetchPageData({ ...base('admin'), role: 'ADMIN' }, { api: async () => { throw new Error('offline'); } }), /Administration unavailable/);
});

test('store query follows the selected city and encodes user input', async () => {
  let path;
  await fetchPageData({ ...base('home'), browseLocation: { city: 'Iepê & Centro', state: 'SP' } }, { api: async value => { path = value; return []; } });
  assert.equal(path, '/stores?city=Iep%C3%AA%20%26%20Centro&state=SP');
});
