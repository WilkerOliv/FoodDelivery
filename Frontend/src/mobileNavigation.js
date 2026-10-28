const priorities = {
  CUSTOMER: ['home', 'orders', 'reservations', 'notifications'],
  MERCHANT: ['home', 'merchant', 'merchantOrders', 'merchantReservations'],
  COURIER: ['courier', 'home', 'orders', 'notifications'],
  ADMIN: ['admin', 'notifications', 'account'],
};

export function mobileNavigation(links, role, limit = 4) {
  const byPage = new Map(links.map(link => [link[0], link]));
  const ordered = (priorities[role] || []).map(page => byPage.get(page)).filter(Boolean);
  for (const link of links) {
    if (!ordered.some(([page]) => page === link[0])) ordered.push(link);
  }
  return { visible: ordered.slice(0, limit), hasMore: ordered.length > limit };
}
