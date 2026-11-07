/** Only the latest request for the same screen/session may publish its result. */
export function createPageLoader({ getContext, load, commit, onStatus }) {
  let revision = 0;
  let controller;

  function invalidate() {
    revision += 1;
    controller?.abort();
    controller = undefined;
    onStatus({ phase: 'idle' });
  }

  async function run() {
    controller?.abort();
    const requestController = new AbortController();
    controller = requestController;
    const currentRevision = ++revision;
    const context = getContext();
    const current = () => revision === currentRevision && !requestController.signal.aborted
      && getContext().key === context.key;
    onStatus({ phase: 'loading', key: context.key });
    try {
      const result = await load(context, { signal: requestController.signal });
      if (!current()) return false;
      commit(result);
      onStatus({ phase: 'ready', key: getContext().key, warning: result.warning || '' });
      return true;
    } catch (error) {
      if (!current()) return false;
      onStatus({ phase: 'error', key: context.key });
      return false;
    } finally {
      if (controller === requestController) controller = undefined;
    }
  }

  return { run, invalidate, get revision() { return revision; } };
}

const escape = value => String(value ?? '').replace(/[&<>"']/g,
  character => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[character]));

/** Loading and failure have their own presentation; neither means an empty list. */
export function pageLoadFeedback(status, pageName) {
  if (status.phase === 'loading') return `<section class="panel page-load-feedback" role="status" aria-live="polite"><span class="loading-spinner" aria-hidden="true"></span><h1>${escape(pageName)}</h1><h2>Carregando informações…</h2><p>Buscando dados atualizados. Aguarde um instante.</p></section>`;
  if (status.phase === 'error') return `<section class="panel page-load-feedback" role="alert"><h1>${escape(pageName)}</h1><h2>Não foi possível carregar esta área</h2><p>Não conseguimos consultar seus registros agora. Confira sua conexão e tente carregar novamente.</p><button type="button" class="btn" data-retry-page>Tentar novamente</button></section>`;
  return status.warning ? `<div class="alert-strip warn" role="status">${escape(status.warning)}</div>` : '';
}
