export function normalizeCity(value) {
  return String(value || '').normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim().toLocaleLowerCase('pt-BR');
}

export function customerOutsideStoreCity(customer, store) {
  if (!customer?.city || !customer?.state || !store?.city || !store?.state) return true;
  return normalizeCity(customer.city) !== normalizeCity(store.city)
    || String(customer.state).trim().toUpperCase() !== String(store.state).trim().toUpperCase();
}

export function catalogItemIcon(serviceItem) {
  return serviceItem ? '🗓️' : '📦';
}
