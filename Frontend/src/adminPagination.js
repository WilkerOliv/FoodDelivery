export const adminLists = {
  adminStores: '/admin/stores', adminOperators: '/admin/operators',
  adminAccounts: '/admin/accounts', adminTickets: '/admin/support-tickets',
};
export async function fetchAdminList(key, get, pages = {}, status = 'ALL') {
  if (!Object.hasOwn(adminLists, key)) throw new Error('Lista administrativa inválida.');
  const query = new URLSearchParams({ page: String(pages[key] || 0), size: '25' });
  if (key === 'adminStores') query.set('status', status);
  let result = await get(`${adminLists[key]}/page?${query}`);
  // A concurrent deletion/status change may remove the last page.
  if (result.number > 0 && result.number >= result.totalPages) {
    query.set('page', String(Math.max(0, result.totalPages - 1)));
    result = await get(`${adminLists[key]}/page?${query}`);
  }
  if (!Array.isArray(result.content)) throw new Error('A lista não respondeu corretamente.');
  return result;
}
export function adminPagination(key, meta) {
  if (!meta) return '<p role="status">Lista indisponível. Use Atualizar dados para tentar novamente.</p>';
  const page = meta.number, total = meta.totalElements;
  return `<nav class="admin-pagination" aria-label="Páginas da lista"><span role="status">${total} registro${total === 1 ? '' : 's'} · Página ${page + 1} de ${Math.max(1, meta.totalPages)}</span><div class="action-row"><button type="button" class="btn outline small" data-admin-page="${key}" data-page="${page - 1}" ${page === 0 ? 'disabled' : ''}>← Anterior</button><button type="button" class="btn outline small" data-admin-page="${key}" data-page="${page + 1}" ${page + 1 >= meta.totalPages ? 'disabled' : ''}>Próxima →</button></div></nav>`;
}
