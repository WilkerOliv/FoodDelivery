const digitsOnly = value => String(value ?? '').replace(/\D/g, '');

export function formatCpfMask(value) {
  const digits = digitsOnly(value).slice(0, 11);
  return digits
    .replace(/^(\d{3})(\d)/, '$1.$2')
    .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3')
    .replace(/\.(\d{3})(\d{1,2})$/, '.$1-$2');
}

export function formatCnpjMask(value) {
  const digits = digitsOnly(value).slice(0, 14);
  return digits
    .replace(/^(\d{2})(\d)/, '$1.$2')
    .replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3')
    .replace(/\.(\d{3})(\d)/, '.$1/$2')
    .replace(/(\d{4})(\d{1,2})$/, '$1-$2');
}

const currencyFormatter = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });

export function formatCurrencyMask(value) {
  const cents = Number(digitsOnly(value) || 0);
  return currencyFormatter.format(cents / 100);
}

export function formatCurrencyValue(decimalValue) {
  const value = Number(decimalValue || 0);
  return currencyFormatter.format(Number.isFinite(value) ? value : 0);
}

export function parseCurrencyDecimal(value) {
  let normalized = String(value ?? '').trim().replace(/[^\d,.-]/g, '');
  if (normalized.includes(',')) normalized = normalized.replace(/\./g, '').replace(',', '.');
  const parsed = Number(normalized);
  return Number.isFinite(parsed) ? parsed : NaN;
}
