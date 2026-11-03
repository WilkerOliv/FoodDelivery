const clean = value => String(value ?? '').trim() || null;

export function adminProfilePayload(values, account) {
  const clearCpf = values.get('clearCpf') === 'on';
  const cpf = clearCpf ? null : clean(values.get('cpf'))?.replace(/\D/g, '') || null;
  return {
    name: String(values.get('name') || '').trim(),
    email: String(values.get('email') || '').trim(),
    phone: clean(values.get('phone')),
    cpf,
    clearCpf,
    addressLine: account.addressLine || null,
    addressComplement: account.addressComplement || null,
    neighborhood: account.neighborhood || null,
    city: account.city || null,
    state: account.state || null,
    postalCode: account.postalCode || null,
  };
}

export function applyAdminProfileResponse(account, response, payload) {
  const next = { ...account, name: response.name, email: response.email, phone: response.phone };
  if (payload.clearCpf) delete next.cpf;
  else if (payload.cpf) next.cpf = payload.cpf;
  return next;
}
