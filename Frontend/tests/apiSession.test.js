import test from 'node:test';
import assert from 'node:assert/strict';

test('API uses HttpOnly browser cookies and removes legacy bearer tokens', async () => {
  const stored = new Map([['pronto_token', 'legacy-secret']]);
  globalThis.localStorage = {
    getItem: key => stored.get(key) ?? null,
    setItem: (key, value) => stored.set(key, value),
    removeItem: key => stored.delete(key),
  };
  globalThis.location = { hostname: 'localhost' };
  let captured;
  globalThis.fetch = async (url, options) => {
    captured = { url, options };
    return { ok: true, status: 200, headers: { get: () => 'application/json' }, json: async () => ({ ok: true }) };
  };
  const { api } = await import(`../src/api.js?session-test=${Date.now()}`);
  assert.equal(stored.has('pronto_token'), false);
  await api('/account/me');
  assert.equal(captured.url, 'http://localhost:8080/api/v1/account/me');
  assert.equal(captured.options.credentials, 'include');
  assert.equal(Object.hasOwn(captured.options.headers, 'Authorization'), false);
});

test('guest session probe does not emit session-expired event', async () => {
  let expired = false;
  globalThis.window = { dispatchEvent: () => { expired = true; } };
  globalThis.CustomEvent = class { constructor(type) { this.type = type; } };
  globalThis.fetch = async () => ({ ok: false, status: 401, headers: { get: () => 'application/json' }, json: async () => ({}) });
  const { api } = await import(`../src/api.js?guest-test=${Date.now()}`);
  await assert.rejects(api('/account/me'));
  assert.equal(expired, false);
});

test('published site uses its HTTPS origin without a development API port',async()=>{
  globalThis.location={hostname:'lojas.example.test',origin:'https://lojas.example.test'};
  let called;globalThis.fetch=async url=>{called=url;return {ok:true,status:200,headers:{get:()=> 'application/json'},json:async()=>({})};};
  const {api}=await import(`../src/api.js?production=${Date.now()}`);await api('/stores');assert.equal(called,'https://lojas.example.test/api/v1/stores');
});

test('rate limit explains the waiting time without expiring the session', async () => {
  let expired = false;
  globalThis.window = { dispatchEvent: () => { expired = true; } };
  globalThis.fetch = async () => ({ ok: false, status: 429,
    headers: { get: name => name === 'Retry-After' ? '121' : 'application/json' },
    json: async () => ({ message: 'Muitas tentativas.' }) });
  const { api } = await import(`../src/api.js?retry-test=${Date.now()}`);
  await assert.rejects(api('/auth/login'), /Muitas tentativas.*3 minutos/);
  assert.equal(expired, false);
});

test('invalid retry header is not rendered as an instruction', async () => {
  globalThis.fetch = async () => ({ ok: false, status: 429,
    headers: { get: name => name === 'Retry-After' ? '<script>alert(1)</script>' : 'application/json' },
    json: async () => ({ message: 'Muitas tentativas.' }) });
  const { api } = await import(`../src/api.js?retry-invalid=${Date.now()}`);
  await assert.rejects(api('/auth/login'), error => error.message === 'Muitas tentativas.');
});
