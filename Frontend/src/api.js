const API_BASE = import.meta.env?.VITE_API_BASE || 'http://127.0.0.1:8080/api/v1';
export const apiAssetUrl = (path) => path ? new URL(path, API_BASE).href : '';

export function setToken(token) {
  if (token) localStorage.setItem('pronto_token', token);
  else localStorage.removeItem('pronto_token');
}

export function getToken() {
  return localStorage.getItem('pronto_token');
}

export async function api(path, options = {}) {
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  const token = getToken();
  if (token) headers.Authorization = `Bearer ${token}`;
  let response;
  try {
    response = await fetch(`${API_BASE}${path}`, { ...options, headers });
  } catch {
    throw new Error('Não conseguimos falar com o servidor. Confira se o banco e o backend estão iniciados e tente novamente.');
  }
  if (response.status === 401 && path !== '/auth/login' && path !== '/auth/register') {
    setToken(null);
    localStorage.removeItem('pronto_account');
    window.dispatchEvent(new CustomEvent('session-expired'));
  }
  if (!response.ok) {
    let body = {};
    try { body = await response.json(); } catch { /* resposta sem JSON */ }
    const message = body.error || body.message || friendlyStatus(response.status);
    throw new Error(message);
  }
  if (response.status === 204) return null;
  const type = response.headers.get('content-type') || '';
  return type.includes('application/json') ? response.json() : null;
}

function friendlyStatus(status) {
  if (status === 400) return 'Alguns dados precisam de atenção. Confira o formulário e tente novamente.';
  if (status === 401) return 'Seus dados de acesso não foram aceitos. Confira o e-mail e a senha.';
  if (status === 403) return 'Esta área não está disponível para o seu perfil.';
  if (status === 404) return 'Não encontramos esse registro. Atualize a página e tente novamente.';
  if (status === 409) return 'Essa ação entrou em conflito com uma atualização recente. Recarregue e tente de novo.';
  if (status >= 500) return 'Tivemos um problema no servidor. Seus dados foram preservados; tente novamente em instantes.';
  return 'Não foi possível concluir agora. Tente novamente.';
}

export const money = (cents = 0) => new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(cents / 100);
export const shortDate = (date) => new Intl.DateTimeFormat('pt-BR', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(date));
