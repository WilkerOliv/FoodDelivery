export const adminAreasBySection = {
  overview: ['stats'],
  applications: ['applications', 'application-history'],
  stores: ['stores'],
  team: [],
  finance: ['refunds'],
  tickets: ['tickets'],
};

export const adminTeamAreas = {
  profiles: ['operator-profiles'],
  accounts: ['accounts'],
  create: ['create-operator'],
};

export const adminAreaNames = [...new Set([...Object.values(adminAreasBySection).flat(), ...Object.values(adminTeamAreas).flat()])];

export function renderAdminAreas(content, section) {
  return (adminAreasBySection[section] || adminAreasBySection.overview)
    .map(name => content[name] || '')
    .join('');
}

export function renderAdminTeamArea(content, subsection) {
  return (adminTeamAreas[subsection] || adminTeamAreas.profiles)
    .map(name => content[name] || '')
    .join('');
}

export function supportDetailPath(isAdmin, id) {
  const ticketId = encodeURIComponent(id);
  return isAdmin ? `/admin/support-tickets/${ticketId}` : `/support/tickets/${ticketId}`;
}

export function supportMessagePath(isAdmin, id) {
  const ticketId = encodeURIComponent(id);
  return isAdmin
    ? `/admin/support-tickets/${ticketId}/messages`
    : `/support/tickets/${ticketId}/messages`;
}

export function storeApprovalPath(storeId, platformRequestId) {
  if (platformRequestId) return `/admin/platform-requests/${encodeURIComponent(platformRequestId)}/review`;
  return `/admin/stores/${encodeURIComponent(storeId)}/approval`;
}

export function adminStoreStatus(store) {
  if (!store.active) return 'STORE_SUSPENDED';
  return store.approved ? 'CONFIRMED' : 'PENDING_REVIEW';
}

export function filterAdminStores(stores = [], status = 'ALL') {
  return stores.filter(store => status === 'ALL' || adminStoreStatus(store) === status);
}

export function storeApprovalId(button) {
  return button?.dataset?.approveStore;
}
