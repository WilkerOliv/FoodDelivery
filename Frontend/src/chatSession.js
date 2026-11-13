/** Owns one conversation opening. Late responses cannot affect a newer opening. */
export function createChatSession({ request, onLoad, onUpdate, onError, visible = () => true,
  schedule = setTimeout, cancel = clearTimeout, delay = 5000 }) {
  let current = null;
  function stop() {
    const previous = current;
    current = null;
    if (previous) { previous.controller.abort(); cancel(previous.timer); }
  }
  const active = session => current === session && !session.controller.signal.aborted;
  function queue(session) {
    if (active(session)) session.timer = schedule(() => refresh(session), delay);
  }
  async function refresh(session) {
    if (!active(session)) return;
    if (!visible() || session.sending) { queue(session); return; }
    const revision = session.revision;
    try {
      const thread = await request(session.path, { signal: session.controller.signal });
      if (active(session) && revision === session.revision) onUpdate(session.key, thread);
    } catch { /* A later refresh retries without disturbing the conversation. */ }
    finally { queue(session); }
  }
  async function open(type, id) {
    stop();
    const session = { key: `${type}:${id}`, path: `/${type}/${encodeURIComponent(id)}/messages`,
      controller: new AbortController(), timer: null, sending: false, revision: 0 };
    current = session;
    try {
      const thread = await request(session.path, { signal: session.controller.signal });
      if (!active(session)) return;
      onLoad(session.key, thread);
      queue(session);
    } catch (error) {
      if (!active(session)) return;
      stop();
      onError(error);
    }
  }
  async function send(key, body, { onSent, onFailure }) {
    const session = current;
    if (!session || session.key !== key || session.sending) return;
    session.sending = true;
    session.revision += 1;
    try {
      const thread = await request(session.path, { method: 'POST', body: JSON.stringify({ body }),
        signal: session.controller.signal });
      if (active(session)) onSent(thread);
    } catch (error) {
      if (active(session)) onFailure(error);
    } finally { session.sending = false; session.revision += 1; }
  }
  return { open, stop, send };
}
