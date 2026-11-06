const localHost = globalThis.location?.hostname || '127.0.0.1';
const API_BASE = globalThis.FOODDELIVERY_DEMO ? `${globalThis.location.origin}/api/v1` : import.meta.env?.VITE_API_BASE || (['localhost','127.0.0.1','[::1]'].includes(localHost) ? `http://${localHost}:8080/api/v1` : `${globalThis.location.origin}/api/v1`);
export const apiAssetUrl = (path) => path ? new URL(path, API_BASE).href : '';

// Remove tokens issued by older builds; browser sessions now live in HttpOnly cookies.
try { localStorage.removeItem('pronto_token'); } catch { /* storage can be disabled */ }

export async function api(path, options = {}) {
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  let response;
  try {
    response = await fetch(`${API_BASE}${path}`, { ...options, headers, credentials: 'include' });
  } catch {
    throw new Error('A conexão com o site foi interrompida. Confira sua conexão e consulte o andamento da operação antes de tentar novamente.');
  }
  if (response.status === 401 && !['/auth/login', '/auth/register', '/auth/logout', '/account/me'].includes(path)) {
    localStorage.removeItem('pronto_account');
    window.dispatchEvent(new CustomEvent('session-expired'));
  }
  if (!response.ok) {
    let body = {};
    try { body = await response.json(); } catch { /* resposta sem JSON */ }
    let message = body.error || body.message || friendlyStatus(response.status);
    if (response.status === 429) {
      const retry = response.headers.get('Retry-After');
      if (retry && /^\d{1,6}$/.test(retry)) {
        const minutes = Math.ceil(Number(retry) / 60);
        if (minutes > 0 && minutes <= 1440) message += ` Tente novamente em cerca de ${minutes} ${minutes === 1 ? 'minuto' : 'minutos'}.`;
      }
    }
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
  if (status >= 500) return 'Não foi possível confirmar o resultado agora. Consulte o andamento da operação antes de tentar novamente.';
  return 'Não foi possível concluir agora. Tente novamente.';
}

export const money = (cents = 0) => new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(cents / 100);
export const shortDate = (date) => new Intl.DateTimeFormat('pt-BR', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(date));
