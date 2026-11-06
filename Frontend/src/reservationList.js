const futureStatuses = new Set(['PENDING_PAYMENT', 'CONFIRMED']);
const start = reservation => {
  const value = Date.parse(reservation.startsAt);
  return Number.isFinite(value) ? value : null;
};
const chronological = (a, b) => {
  const left = start(a), right = start(b);
  if (left === null || right === null) return left === right ? 0 : left === null ? 1 : -1;
  return left - right;
};

export function groupCustomerReservations(reservations, now = Date.now()) {
  const groups = [
    { title: 'Em atendimento', items: [] },
    { title: 'Próximas reservas', items: [] },
    { title: 'Histórico', items: [] },
  ];
  for (const reservation of reservations) {
    const at = start(reservation);
    const index = reservation.status === 'IN_SERVICE' ? 0
      : futureStatuses.has(reservation.status) && at !== null && at >= now ? 1 : 2;
    groups[index].items.push(reservation);
  }
  groups[0].items.sort(chronological);
  groups[1].items.sort(chronological);
  groups[2].items.sort((a, b) => {
    if (start(a) === null || start(b) === null) return chronological(a, b);
    return chronological(b, a);
  });
  return groups.filter(group => group.items.length);
}
