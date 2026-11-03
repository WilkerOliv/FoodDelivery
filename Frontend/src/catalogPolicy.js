export function normalizeCity(value) {
  return String(value || '').normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim().toLocaleLowerCase('pt-BR');
}

const categoryAliases = {
  restaurante: ['restaurante', 'restaurantes'],
  mercado: ['mercado', 'mercados'],
  conveniencia: ['conveniencia', 'conveniencias'],
  lanchonete: ['lanchonete', 'lanchonetes'],
  pizzaria: ['pizzaria', 'pizzarias'],
  cafeteria: ['cafeteria', 'cafeterias'],
  farmacia: ['farmacia', 'farmacias'],
  beleza: ['beleza'],
  barbearia: ['barbearia', 'barbearias'],
  servico: ['servico', 'servicos'],
};

export function categoryMatches(storeCategory, selectedCategory) {
  const normalize = value => normalizeCity(value).replace(/[^a-z0-9]+/g, '');
  const selected = normalize(selectedCategory);
  const store = normalize(storeCategory);
  if (!selected || !store) return false;
  const canonical = Object.entries(categoryAliases).find(([, aliases]) => aliases.some(alias => normalize(alias) === selected))?.[0];
  if (canonical) return categoryAliases[canonical].some(alias => normalize(alias) === store);
  return selected === store;
}

export function customerOutsideStoreCity(customer, store) {
  if (!customer?.city || !customer?.state || !store?.city || !store?.state) return true;
  return normalizeCity(customer.city) !== normalizeCity(store.city)
    || String(customer.state).trim().toUpperCase() !== String(store.state).trim().toUpperCase();
}

export function catalogItemIcon(serviceItem) {
  return serviceItem ? '🗓️' : '📦';
}
