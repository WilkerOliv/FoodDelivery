import { adminLists, fetchAdminList } from './adminPagination.js';
/** Fetch screen data into a detached patch, never into shared application state. */
export async function fetchPageData(context, { api, signal }) {
  const get = path => api(path, { signal });
  const data = {};
  let warning = '';
  const { page, role, merchantStoreId } = context;
  if (['home', 'categories', 'categoryStores'].includes(page)) {
    data.stores = await fetchStoreListings(context, get);
  } else if (page === 'orders') data.orders = await get('/orders/mine');
  else if (page === 'reservations') data.reservations = await get('/reservations/mine');
  else if (page === 'notifications') data.notifications = notificationRecords(await get('/account/notifications'));
  else if (page === 'account' && role === 'ADMIN') {
    const jobs = [['adminServiceCities', '/admin/service-cities'], ['adminIntegrations', '/admin/integrations'],
      ['adminStoreCategories', '/admin/store-categories'], ['adminAuditEvents', '/admin/audit-events?page=0&size=50']];
    const failed = await collect(jobs, get, data);
    if (failed === jobs.length) throw new Error('Definitions unavailable');
    for (const key of ['adminServiceCities', 'adminIntegrations', 'adminStoreCategories']) data[key] ||= [];
    data.adminAuditEvents = data.adminAuditEvents?.content || [];
    if (failed) warning = 'Algumas definições não responderam. Tente atualizar antes de fazer alterações nessa área.';
  } else if (page === 'account') data.platformRequests = await get('/account/platform-requests');
  else if (page === 'merchant') {
    // A missing store list must never be mistaken for permission to register a new store.
    data.ownStores = await get('/merchant/stores/mine');
    data.merchantStoreId = selectedStore(data.ownStores, merchantStoreId)?.id || '';
    const jobs = [['platformRequests', '/account/platform-requests'], ['orders', '/merchant/orders']];
    let failed = await collect(jobs, get, data);
    data.platformRequests ||= []; data.orders ||= [];
    const store = selectedStore(data.ownStores, data.merchantStoreId);
    data.storeItems = []; data.paymentConnection = null; data.merchantHours = null;
    if (store) {
      failed += await collect([['storeItems', `/merchant/stores/${store.id}/items`],
        ['paymentConnection', `/merchant/stores/${store.id}/mercadopago/status`],
        ['merchantHours', `/merchant/stores/${store.id}/hours`]], get, data);
    }
    // A catalog load failure needs a retry screen, not an apparently empty catalog.
    if (store && !Array.isArray(data.storeItems)) throw new Error('Catalog unavailable');
    data.merchantLoadError = failed ? 'Parte do painel não respondeu. Atualize antes de usar informações dessa área.' : '';
    warning = data.merchantLoadError;
  } else if (page === 'merchantOrders') data.orders = await get('/merchant/orders');
  else if (page === 'merchantReservations') {
    data.ownStores = context.ownStores.length ? context.ownStores : await get('/merchant/stores/mine');
    const store = selectedStore(data.ownStores, merchantStoreId);
    data.merchantStoreId = store?.id || '';
    [data.reservations, data.merchantProfessionals] = await Promise.all([
      get('/merchant/reservations'), store ? get(`/merchant/stores/${store.id}/professionals`) : []]);
  } else if (page === 'courier') {
    [data.offers, data.deliveries] = await Promise.all([get('/courier/offers'), get('/courier/deliveries')]);
  } else if (page === 'finance' && role === 'MERCHANT') {
    data.ownStores = context.ownStores.length ? context.ownStores : await get('/merchant/stores/mine');
    const store = selectedStore(data.ownStores, merchantStoreId);
    data.merchantStoreId = store?.id || '';
    data.paymentConnection = store ? await get(`/merchant/stores/${store.id}/mercadopago/status`) : null;
  } else if (page === 'finance' && role === 'COURIER') data.deliveries = await get('/courier/deliveries');
  else if (page === 'admin') {
    Object.assign(data, await fetchAdminData(get, context.adminPages, context.adminStoreFilter));
    warning = data.adminLoadError;
  }
  return { data, warning };
}

async function collect(jobs, get, data) {
  const results = await Promise.allSettled(jobs.map(([, path]) => get(path)));
  let failed = 0;
  results.forEach((result, index) => {
    const key = jobs[index][0];
    if (result.status === 'fulfilled') data[key] = result.value;
    else { data[key] = null; failed += 1; }
  });
  return failed;
}

export async function fetchAdminData(get, pages = {}, status = 'ALL') {
  const jobs = [['adminRefunds', '/admin/refunds'], ['adminApplications', '/admin/operator-applications'],
    ['adminPlatformRequests', '/admin/platform-requests'], ['adminSummary', '/admin/list-summary']];
  const data = { adminPageMeta: {} };
  let failed = await collect(jobs, get, data);
  const keys = Object.keys(adminLists);
  const results = await Promise.allSettled(keys.map(key => fetchAdminList(key, get, pages, status)));
  results.forEach((result, index) => {
    const key = keys[index];
    if (result.status === 'fulfilled') { data[key] = result.value.content; data.adminPageMeta[key] = result.value; }
    else { data[key] = []; failed += 1; }
  });
  if (failed === jobs.length + keys.length) throw new Error('Administration unavailable');
  jobs.forEach(([key]) => { data[key] ||= key === 'adminSummary' ? {} : []; });
  data.adminLoadError = failed ? 'Algumas áreas não responderam. As demais continuam disponíveis.' : '';
  return data;
}

export function selectedStore(stores, id) {
  return stores.find(store => String(store.id) === String(id)) || stores[0] || null;
}

export async function fetchStoreListings(context, get) {
  const city = String(context.browseLocation?.city || context.city || '').trim();
  const state = String(context.browseLocation?.state || context.state || '').trim();
  return get(`/stores${city ? `?city=${encodeURIComponent(city)}&state=${encodeURIComponent(state)}` : ''}`);
}

export function notificationRecords(records) {
  return records.map(record => ({ ...record, date: record.createdAt, read: !!record.readAt, localOnly: false }))
    .sort((a, b) => new Date(b.date) - new Date(a.date)).slice(0, 100);
}
