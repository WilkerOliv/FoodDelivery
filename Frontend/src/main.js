import { api, apiAssetUrl, getToken, money, setToken, shortDate } from './api.js';
import { adminAreaNames, adminStoreStatus, renderAdminAreas, renderAdminTeamArea, storeApprovalId, storeApprovalPath, supportDetailPath, supportMessagePath } from './adminSections.js';
import { runOnce } from './actionGuard.js';
document.title = 'FoodDelivery — pedidos e reservas perto de você';

const $ = (selector, root = document) => root.querySelector(selector);
const app = $('#app');
const roleNames = { CUSTOMER: 'Cliente', MERCHANT: 'Vendedor', COURIER: 'Entregador', ADMIN: 'Administrador' };
const pageNames = { home: 'Descobrir', orders: 'Meus pedidos', reservations: 'Minhas reservas', account: 'Minha conta', notifications: 'Notificações', merchant: 'Minha loja', merchantOrders: 'Pedidos da loja', merchantReservations: 'Reservas da loja', courier: 'Entregas', admin: 'Administração', checkout: 'Finalizar pedido' };
const navByRole = {
  CUSTOMER: [['home','home','Descobrir'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  MERCHANT: [['home','home','Comprar'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['merchant','store','Minha loja'],['merchantOrders','receipt','Pedidos da loja'],['merchantReservations','calendar','Agenda'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  COURIER: [['home','home','Comprar'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['courier','truck','Entregas'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  ADMIN: [['admin','grid','Visão geral'],['notifications','bell','Notificações'],['account','user','Minha conta']],
};
const categoryStyles = { Restaurantes:'orange', Restaurante:'orange', Mercados:'green', 'Conveniências':'gold', Lanchonetes:'rose', Pizzarias:'gold', Pizzaria:'gold', Cafeterias:'rose', Cafeteria:'rose', Farmácias:'green', Beleza:'purple', Barbearias:'blue', Barbearia:'blue', Serviços:'green' };
const categoryEmoji = { Restaurantes:'🍽️', Restaurante:'🍽️', Mercados:'🛒', 'Conveniências':'🧃', Lanchonetes:'🍔', Pizzarias:'🍕', Pizzaria:'🍕', Cafeterias:'☕', Cafeteria:'☕', Farmácias:'💊', Beleza:'💇', Barbearias:'💈', Barbearia:'💈', Serviços:'✂️' };
const statusNames = { AWAITING_PAYMENT:'Aguardando pagamento', AWAITING_ACCEPTANCE:'Aguardando a loja', ACCEPTED:'Pedido aceito', PREPARING:'Em preparo', READY:'Pronto', COMPLETED:'Concluído', REJECTED:'Não aceito', CANCELLED:'Cancelado', PENDING_PAYMENT:'Aguardando pagamento', PENDING_REVIEW:'Aguardando análise', STORE_SUSPENDED:'Loja suspensa', CONFIRMED:'Confirmada', IN_SERVICE:'Em atendimento', NO_SHOW:'Não compareceu', OFFERED:'Disponível', ASSIGNED:'Com você', PICKED_UP:'Coletado', IN_TRANSIT:'A caminho', DELIVERED:'Entregue', REFUND_PENDING:'Estorno em processamento', REFUNDED:'Estornado', REFUND_REVIEW:'Estorno precisa de atenção', OPEN:'Aberto', IN_PROGRESS:'Em atendimento', CLOSED:'Encerrado' };
const statusClass = s => ['COMPLETED','DELIVERED','CONFIRMED','REFUNDED','CLOSED'].includes(s) ? 'done' : ['REJECTED','CANCELLED','NO_SHOW','REFUND_REVIEW'].includes(s) ? 'cancel' : ['ACCEPTED','PREPARING','READY','IN_SERVICE','ASSIGNED','PICKED_UP','IN_TRANSIT','IN_PROGRESS'].includes(s) ? 'progress' : 'pending';
const esc = value => String(value ?? '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
function icon(name,size=18){
  const paths={home:'M3 10.5 12 3l9 7.5M5.5 9v11h13V9M9 20v-6h6v6',receipt:'M6 3h12v18l-3-2-3 2-3-2-3 2zM9 8h6M9 12h6M9 16h3',calendar:'M5 4v3m14-3v3M4 8h16M5 5h14a1 1 0 0 1 1 1v14H4V6a1 1 0 0 1 1-1zM8 12h3v3H8z',bell:'M18 9a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9M10 21h4',user:'M20 21a8 8 0 0 0-16 0M12 13a5 5 0 1 0 0-10 5 5 0 0 0 0 10',store:'M3 10h18M5 10v10h14V10M4 10l1-6h14l1 6M9 20v-6h6v6',truck:'M3 5h11v12H3zM14 9h4l3 4v4h-7M7 20a2 2 0 1 0 0-4 2 2 0 0 0 0 4M18 20a2 2 0 1 0 0-4 2 2 0 0 0 0 4',grid:'M4 4h7v7H4zM15 4h5v5h-5zM15 13h5v7h-5zM4 15h7v5H4z',pin:'M20 10c0 5-8 11-8 11S4 15 4 10a8 8 0 1 1 16 0zM12 10a2 2 0 1 0 0-4 2 2 0 0 0 0 4',shield:'M12 22s8-4 8-11V5l-8-3-8 3v6c0 7 8 11 8 11zM9 12l2 2 4-4',key:'M21 2l-2 2m-7.6 7.6a5 5 0 1 1-7.1 7.1 5 5 0 0 1 7.1-7.1zm0 0L15 8l3 3 3-3-3-3',search:'m20 20-4.5-4.5M18 10.5a7.5 7.5 0 1 1-15 0 7.5 7.5 0 0 1 15 0'};
  return `<svg aria-hidden="true" width="${size}" height="${size}" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="${paths[name]||paths.grid}"/></svg>`;
}

const savedAccount = (() => { try { return JSON.parse(localStorage.getItem('pronto_account') || 'null'); } catch { return null; } })();
const savedLocation = (() => { try { return JSON.parse(localStorage.getItem('fooddelivery_location') || 'null'); } catch { return null; } })();
const knownPage = page => Object.hasOwn(pageNames, page);
const notificationKey = accountId => `fooddelivery_notifications:${accountId || 'guest'}`;
const readLocalNotifications = () => [];
let notificationPollHandle = null;
const state = {
  account: getToken() ? savedAccount : null, activeRole: savedAccount?.activeRole || savedAccount?.role || null, mustChangePassword: !!savedAccount?.passwordChangeRequired, adminTemporaryPassword: '',
  page: 'home', authMode: 'login', recoveryMode: false, recoveryStep: 'request', recoveryEmail: '', recoveryChannel: 'EMAIL', phoneVerificationSent: false, loading: false, mobileMenu: false, sidebarCollapsed: false,
  merchantSection: 'overview', merchantStoreId: '', adminSection: 'overview', adminTeamSection: 'profiles', accountSection: 'profile',
  browseLocation: savedLocation, locationCities: [], adminTickets: [], supportTickets: [], activeTicket: null, supportAdminView: false,
  stores: [], filteredStores: [], category: 'Todas', search: '', selectedStore: null,
  storeItems: [], cart: [], cartMode: 'PICKUP', deliveryAddress: '', cartOpen: false,
  addresses: [], selectedAddressId: '', addressFormOpen: false,
  orders: [], reservations: [], deliveries: [], offers: [], ownStores: [],
  checkoutNote: '', openChat: null, chatThreads: {}, chatDrafts: {}, chatPollHandle: null,
  notifications: readLocalNotifications(savedAccount?.id),
  checkout: null, profile: null, platformRequests: [], adminPlatformRequests: [], adminStores: [], adminRefunds: [], adminOperators: [], adminAccounts: [], adminApplications: [], adminLoadError: '', backendStatus: 'checking',
  paymentConnection: null,
  paymentStarting: false, aiGeneratedCover: null, aiGenerating: false, aiPrompt: '',
  locationWatchId: null, sharingDeliveryId: '', lastLocationSentAt: 0, trackingMapOrderId: '',
};

function notify(title, message, kind = 'success', persist = false) {
  const safeMessage=kind==='error'&&/cannot read properties|typeerror|referenceerror|nullpointerexception|sql exception|stack trace/i.test(String(message))
    ? 'Ocorreu um problema inesperado. Tente novamente em instantes. Se continuar, fale com o atendimento.'
    : message;
  const entry = { id: crypto.randomUUID(), title, message:safeMessage, kind, date: new Date().toISOString(), read: false };
  if (persist) {
    state.notifications = [entry, ...state.notifications].slice(0, 40);
    localStorage.setItem(notificationKey(state.account?.id), JSON.stringify(state.notifications.filter(n => n.localOnly !== false).slice(0,40)));
  }
  const region = $('#toast-region');
  if (region) {
    const toast = document.createElement('div');
    toast.className = `toast ${kind}`;
    toast.innerHTML = `<span class="toast-icon">${kind === 'error' ? '⚠' : kind === 'info' ? 'ℹ' : '✓'}</span><div><strong>${esc(title)}</strong><p>${esc(safeMessage)}</p></div>`;
    region.append(toast);
    setTimeout(() => toast.remove(), 4800);
  }
}

window.addEventListener('session-expired', () => {
  resetChatState();
  stopNotificationPolling();
  stopCourierLocation();
  setToken(null);
  localStorage.removeItem('pronto_account');
  state.account = null;
  state.profile = null;
  state.cart = [];
  state.addresses = [];
  state.selectedAddressId = '';
  state.addressFormOpen = false;
  state.orders = [];
  state.reservations = [];
  state.deliveries = [];
  state.offers = [];
  state.ownStores = [];
  state.checkout = null;
  state.paymentConnection = null;
  state.notifications = readLocalNotifications(null);
  notify('Sua sessão terminou', 'Entre novamente para continuar. Seus pedidos continuam salvos.', 'info');
  render();
});

const role = () => {
  const granted=state.account?.grantedRoles;
  if(Array.isArray(granted)&&granted.length){if(state.activeRole&&granted.includes(state.activeRole))return state.activeRole;if(granted.includes(state.account.role))return state.account.role;return granted[0];}
  return state.account?.role || 'CUSTOMER';
};
const canShop = () => role()==='CUSTOMER' || state.account?.grantedRoles?.includes('CUSTOMER');
const initials = name => (name || 'Você').trim().split(/\s+/).slice(0,2).map(n => n[0]).join('').toUpperCase();
const fullAddress = account => [account?.addressLine,account?.addressComplement,account?.neighborhood,account?.city,account?.state,account?.postalCode].filter(Boolean).join(', ');
const displayName = () => (state.account?.name || 'Olá').split(' ')[0];
const formatStatus = s => statusNames[s] || String(s || '').replaceAll('_',' ').toLowerCase();
const pill = s => `<span class="status-pill ${statusClass(s)}"><i>●</i>${esc(formatStatus(s))}</span>`;
const refundNotice = status => status==='REFUND_PENDING'?'<div class="alert-strip info">O Mercado Pago está processando o estorno. O prazo para o valor aparecer depende do meio de pagamento.</div>':status==='REFUND_REVIEW'?'<div class="alert-strip warn">O estorno está em análise pela equipe. Se precisar, fale com o atendimento informando o número do pedido.</div>':status==='REFUNDED'?'<div class="alert-strip info">Estorno confirmado pelo Mercado Pago.</div>':'';
const categoryOptions = ['Todas','Restaurantes','Mercados','Conveniências','Lanchonetes','Pizzarias','Cafeterias','Farmácias','Beleza','Barbearias','Serviços'];

async function boot() {
  const initialPage=location.hash.slice(1);
  if(knownPage(initialPage)) state.page=initialPage;
  render();
  try { await api('/status'); state.backendStatus = 'online'; } catch { state.backendStatus = 'offline'; }
  try { const categories=await api('/store-categories'); if(Array.isArray(categories)&&categories.length){categoryOptions.splice(1,categoryOptions.length-1,...categories.map(category=>category.name));categories.forEach(category=>{categoryEmoji[category.name]=category.emoji;});} } catch { /* categorias locais mantêm a navegação disponível durante indisponibilidade da API */ }
  if (state.account) {
    if(state.account.passwordChangeRequired){state.mustChangePassword=true;state.profile=state.account;state.page='account';render();return;}
    try { const preferredRole=state.activeRole;state.profile = await api('/account/me'); state.account = state.profile; state.mustChangePassword=!!state.account.passwordChangeRequired; state.activeRole=state.account.grantedRoles?.includes(preferredRole)?preferredRole:state.account.role; localStorage.setItem('pronto_account', JSON.stringify({...state.account,activeRole:state.activeRole})); const mpResult=new URLSearchParams(location.search).get('mp');if(mpResult==='connected'||mpResult==='error'){notify(mpResult==='connected'?'Conta Mercado Pago conectada':'Não foi possível conectar o Mercado Pago',mpResult==='connected'?'Esta loja já pode receber pagamentos pelo checkout.':'A autorização foi cancelada ou expirou. Tente conectar novamente.',mpResult==='connected'?'success':'error');const url=new URL(location.href);url.searchParams.delete('mp');history.replaceState({page:'merchant'},'',url.pathname+url.search+'#merchant');} }
    catch { state.account = null; setToken(null); }
    if (state.account) { const params=new URLSearchParams(location.search),paymentReturn=params.get('mp'),paymentId=params.get('app_payment_id'),providerPaymentId=params.get('collection_id')||params.get('payment_id');if(paymentId&&['success','pending','failure'].includes(paymentReturn))await handlePaymentReturn(paymentId,paymentReturn,providerPaymentId);if(state.mustChangePassword)state.page='account';else if(!navByRole[role()].some(([page])=>page===state.page))state.page=role()==='CUSTOMER'?'home':role()==='MERCHANT'?'merchant':role()==='COURIER'?'courier':'admin';state.notifications = readLocalNotifications(state.account.id); await loadNotifications().catch(()=>{}); startNotificationPolling(); if(state.account.grantedRoles?.includes('CUSTOMER'))await loadAddresses(); await loadCurrentPage(); }
  }
  render();
}

async function loadCurrentPage() {
  try {
    if (state.page === 'home') await loadStores();
    if (state.page === 'orders') state.orders = await api('/orders/mine');
    if (state.page === 'reservations') state.reservations = await api('/reservations/mine');
    if (state.page === 'notifications') await loadNotifications();
    if (state.page === 'account') { state.platformRequests=await api('/account/platform-requests'); }
    if (state.page === 'merchant') { state.ownStores = await api('/merchant/stores/mine');state.platformRequests=await api('/account/platform-requests'); if(!state.ownStores.some(s=>String(s.id)===String(state.merchantStoreId)))state.merchantStoreId=state.ownStores[0]?.id||''; const active=activeMerchantStore();state.storeItems=active?await api(`/merchant/stores/${active.id}/items`):[];state.paymentConnection=active?await api(`/merchant/stores/${active.id}/mercadopago/status`):null;state.orders=await api('/merchant/orders'); }
    if (state.page === 'merchantOrders') state.orders = await api('/merchant/orders');
    if (state.page === 'merchantReservations') state.reservations = await api('/merchant/reservations');
    if (state.page === 'courier') { state.offers = await api('/courier/offers'); state.deliveries = await api('/courier/deliveries'); }
    if (state.page === 'admin') await loadAdminData();
  } catch (error) { if(state.page==='admin')state.adminLoadError=error.message||'Verifique se o servidor está iniciado.'; notify('Não foi possível carregar esta área', error.message, 'error'); }
}

async function loadAdminData(){
  const jobs=[['adminStores','/admin/stores'],['adminRefunds','/admin/refunds'],['adminOperators','/admin/operators'],['adminApplications','/admin/operator-applications'],['adminPlatformRequests','/admin/platform-requests'],['adminAccounts','/admin/accounts'],['adminTickets','/admin/support-tickets']];
  const results=await Promise.allSettled(jobs.map(([,url])=>api(url)));
  const failed=[];results.forEach((result,index)=>{const [key]=jobs[index];if(result.status==='fulfilled')state[key]=result.value;else failed.push(jobs[index][1]);});
  state.adminLoadError=failed.length===jobs.length? 'Não foi possível conectar ao FoodDelivery. Inicie o backend e verifique o banco de dados.':failed.length?`Algumas áreas não responderam (${failed.length}). As demais continuam disponíveis.`:'';
}

async function loadNotifications() {
  const local = readLocalNotifications(state.account?.id);
  const remote = await api('/account/notifications');
  state.notifications = [...local, ...remote.map(n => ({...n, date:n.createdAt, read:!!n.readAt, localOnly:false}))]
    .sort((a,b)=>new Date(b.date)-new Date(a.date)).slice(0,100);
}

const addressText = address => [address.addressLine, address.addressComplement, address.neighborhood,
  `${address.city}${address.state ? `/${address.state}` : ''}`, `CEP ${address.postalCode}`].filter(Boolean).join(', ');

async function loadAddresses() {
  try {
    state.addresses = await api('/account/addresses');
    if (!state.addresses.length && state.account.addressLine && state.account.neighborhood && state.account.city && state.account.state && state.account.postalCode) {
      const imported = await api('/account/addresses', {method:'POST', body:JSON.stringify({label:'Casa',addressLine:state.account.addressLine,addressComplement:state.account.addressComplement,neighborhood:state.account.neighborhood,city:state.account.city,state:state.account.state,postalCode:state.account.postalCode,defaultAddress:true})});
      state.addresses = [imported];
    }
    state.selectedAddressId = state.addresses.find(a=>a.defaultAddress)?.id || state.addresses[0]?.id || '';
  } catch (error) { notify('Não foi possível carregar seus endereços', error.message, 'error'); }
}

function startNotificationPolling() {
  stopNotificationPolling();
  notificationPollHandle = setInterval(refreshNotifications, 30000);
}

function stopNotificationPolling() {
  if (notificationPollHandle) clearInterval(notificationPollHandle);
  notificationPollHandle = null;
}

function isTypingInPage(){const el=document.activeElement;return !!el&&el.matches?.('#page-content textarea, #page-content input, #page-content select');}

async function refreshNotifications() {
  if (!state.account || document.visibilityState !== 'visible') return;
  const accountId = state.account.id;
  try {
    const remote = await api('/account/notifications');
    if (!state.account || String(state.account.id) !== String(accountId)) return;
    const existing = new Set(state.notifications.filter(n => n.localOnly === false).map(n => String(n.id)));
    const fresh = remote.filter(n => !existing.has(String(n.id)));
    state.notifications = [...readLocalNotifications(state.account.id), ...remote.map(n => ({...n, date:n.createdAt, read:!!n.readAt, localOnly:false}))]
      .sort((a,b)=>new Date(b.date)-new Date(a.date)).slice(0,100);
    fresh.slice(0,3).reverse().forEach(n => notify(n.title, n.message, n.kind || 'info', false));
    if (state.page === 'notifications' && fresh.length) renderPage();
  } catch { /* a próxima rodada tenta novamente sem interromper a tela */ }
  if (state.page === 'orders') {
    try { state.orders = await api('/orders/mine'); if(!isTypingInPage())renderPage(); } catch { /* o próximo ciclo atualiza o acompanhamento */ }
  }
}

async function handlePaymentReturn(paymentId,result,providerPaymentId){
  try{
    let payment=await api(`/payments/${encodeURIComponent(paymentId)}`);
    for(let attempt=0;payment.status==='PENDING'&&attempt<5;attempt++){await new Promise(resolve=>setTimeout(resolve,1600));payment=providerPaymentId?await api(`/payments/${encodeURIComponent(paymentId)}/sync`,{method:'POST',body:JSON.stringify({providerPaymentId})}):await api(`/payments/${encodeURIComponent(paymentId)}`);}
    const target=payment.reservationId?'reservations':'orders';state.page=target;
    const message=payment.status==='PAID'?'Pagamento aprovado. Sua operação já foi confirmada.':payment.status==='PENDING'?'O Mercado Pago ainda está processando. Você pode acompanhar o status nesta tela.':'O pagamento não foi aprovado. Nenhuma confirmação foi enviada à loja.';
    notify(payment.status==='PAID'?'Pagamento confirmado':payment.status==='PENDING'?'Pagamento em análise':'Pagamento não concluído',message,payment.status==='PAID'?'success':'info');
    const url=new URL(location.href);for(const key of ['mp','app_payment_id','payment_id','collection_id','collection_status','status','external_reference'])url.searchParams.delete(key);history.replaceState({page:target},'',url.pathname+url.search+`#${target}`);
  }catch(error){notify('Não conseguimos atualizar o pagamento',error.message,'error');}
}

async function loadStores() {
  const city=String(state.browseLocation?.city||state.account?.city||'').trim(),stateCode=String(state.browseLocation?.state||state.account?.state||'').trim();
  const query=city?`?city=${encodeURIComponent(city)}&state=${encodeURIComponent(stateCode)}`:'';
  state.stores = await api(`/stores${query}`);
  filterStores();
}

function filterStores() {
  const query = state.search.trim().toLocaleLowerCase('pt-BR');
  state.filteredStores = state.stores.filter(store => {
    const categoryMatch = state.category === 'Todas' || categoryMatches(store.category, state.category);
    const searchMatch = !query || `${store.name} ${store.description || ''} ${store.category || ''} ${store.city||''} ${store.state||''}`.toLocaleLowerCase('pt-BR').includes(query);
    return categoryMatch && searchMatch;
  });
}

function categoryMatches(storeCategory, selectedCategory) {
  const aliases = { Restaurantes:['restaurante','restaurantes'], Mercados:['mercado','mercados'], 'Conveniências':['conveniencia','conveniencias'], Lanchonetes:['lanchonete','lanchonetes'], Pizzarias:['pizzaria','pizzarias'], Cafeterias:['cafeteria','cafeterias'], Farmácias:['farmacia','farmacias'], Beleza:['beleza'], Barbearias:['barbearia','barbearias'], Serviços:['servico','servicos'] };
  const normalize = value => String(value||'').normalize('NFD').replace(/[\u0300-\u036f]/g,'').toLocaleLowerCase('pt-BR');
  return (aliases[selectedCategory]||[selectedCategory]).some(alias=>normalize(storeCategory)===normalize(alias));
}

function render() {
  $('#cart-backdrop')?.remove();
  $('#store-modal')?.remove();
  if (!state.account) { renderAuth(); return; }
  renderShell();
  renderPage();
  if (state.cartOpen) renderCartDrawer();
  if (state.selectedStore) renderStoreModal();
}

function renderAuth() {
  const recovery = state.recoveryMode;
  app.innerHTML = `<main class="auth-screen">
    <section class="auth-art"><div class="brand"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">pedidos e reservas perto de você</small></div></div>
      <div class="auth-pitch"><span class="eyebrow">A cidade cabe no seu pedido</span><h1>O que você quer viver hoje?</h1><p>Sabores, serviços e bons momentos por perto. Descubra o comércio local e resolva tudo em poucos passos.</p></div>
      <div class="auth-quote">Feito para conectar pessoas e negócios do seu bairro.</div>
    </section>
    <section class="auth-form-wrap"><form class="auth-form" id="auth-form">
      <div class="mobile-auth-brand"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">pedidos e reservas perto de você</small></div></div>
      ${state.backendStatus==='offline'?'<div class="alert-strip warn"><span>!</span><span>Não conseguimos acessar o servidor agora. Você pode entrar, mas pedidos e reservas só serão enviados quando a conexão voltar.</span></div>':''}
      ${recovery ? `<h2>${state.recoveryStep==='request'?'Recupere seu acesso':'Crie uma nova senha'}</h2>
      <p>${state.recoveryStep==='request'?'Escolha onde quer receber um código de segurança.':'Digite o código de 6 números enviado e escolha uma senha nova.'}</p>
      ${state.recoveryStep==='request'?`<div class="field"><label for="recovery-email">E-mail da conta</label><input id="recovery-email" type="email" autocomplete="email" value="${esc(state.recoveryEmail)}" required /></div>
      <div class="field"><label for="recovery-channel">Enviar código por</label><select id="recovery-channel"><option value="EMAIL" ${state.recoveryChannel==='EMAIL'?'selected':''}>E-mail</option><option value="WHATSAPP" ${state.recoveryChannel==='WHATSAPP'?'selected':''}>WhatsApp confirmado</option></select></div>
      <button class="btn block" type="submit">Enviar código <span>→</span></button>`:`<div class="field"><label for="recovery-code">Código de segurança</label><input id="recovery-code" inputmode="numeric" autocomplete="one-time-code" pattern="[0-9]{6}" maxlength="6" placeholder="000000" required /></div>
      <div class="field"><label for="recovery-new-password">Nova senha</label><input id="recovery-new-password" type="password" autocomplete="new-password" minlength="12" maxlength="72" required /></div>
      <div class="field"><label for="recovery-confirm-password">Repita a nova senha</label><input id="recovery-confirm-password" type="password" autocomplete="new-password" minlength="12" maxlength="72" required /></div>
      <button class="btn block" type="submit">Redefinir senha <span>→</span></button>`}
      <div class="auth-foot"><button type="button" class="text-button" data-recovery-back>Voltar para entrar</button></div>` : `<h2>${state.authMode === 'login' ? 'Que bom ter você por aqui' : 'Vamos começar?'}</h2>
      <p>${state.authMode === 'login' ? 'Entre na sua conta para continuar.' : 'Crie sua conta de cliente. É rapidinho.'}</p>
      <div class="auth-tabs"><button type="button" data-auth-mode="login" class="${state.authMode==='login'?'active':''}">Entrar</button><button type="button" data-auth-mode="register" class="${state.authMode==='register'?'active':''}">Criar conta</button></div>
      ${state.authMode==='register'?`<div class="field"><label for="auth-name">Seu nome</label><input id="auth-name" name="name" autocomplete="name" placeholder="Como podemos chamar você?" required maxlength="120" /></div>`:''}
      ${state.authMode==='register'?`<div class="field"><label for="auth-cpf">CPF</label><input id="auth-cpf" name="cpf" autocomplete="off" inputmode="numeric" pattern="[0-9.\\-]{11,14}" placeholder="000.000.000-00" required /></div>`:''}
      <div class="field"><label for="auth-email">E-mail</label><input id="auth-email" name="email" type="email" autocomplete="email" placeholder="voce@email.com" required /></div>
      <div class="field"><label for="auth-password">Senha</label><input id="auth-password" name="password" type="password" autocomplete="${state.authMode==='login'?'current-password':'new-password'}" placeholder="${state.authMode==='login'?'Sua senha':'Pelo menos 12 caracteres'}" required minlength="${state.authMode==='register'?12:1}" maxlength="72" /></div>
      ${state.authMode==='register'?'<p class="warning-small">Ao criar a conta, você concorda com os termos e com o uso dos dados necessários para processar pedidos e reservas.</p>':''}
      <button class="btn block" type="submit" style="margin-top:15px">${state.authMode==='login'?'Entrar na minha conta':'Criar conta gratuita'} <span>→</span></button>
      <div class="auth-foot">${state.authMode==='login'?'<button type="button" class="text-button" data-forgot-password>Esqueceu sua senha?</button>':'Ao continuar, seus dados são usados para autenticar você e atender seus pedidos.'}</div>`}
    </form></section>
  </main>`;
  $('#auth-form').addEventListener('submit', recovery ? submitRecovery : submitAuth);
  document.querySelectorAll('[data-auth-mode]').forEach(button => button.addEventListener('click', () => { state.authMode = button.dataset.authMode; renderAuth(); }));
  $('[data-forgot-password]')?.addEventListener('click',()=>{state.recoveryMode=true;state.recoveryStep='request';state.recoveryEmail=$('#auth-email')?.value||'';renderAuth();});
  $('[data-recovery-channel]')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
  $('#recovery-channel')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
  $('[data-recovery-back]')?.addEventListener('click',()=>{state.recoveryMode=false;state.recoveryStep='request';renderAuth();});
}

async function submitRecovery(event) {
  event.preventDefault();
  try {
    if (state.recoveryStep === 'request') {
      state.recoveryEmail = $('#recovery-email').value.trim().toLowerCase();
      state.recoveryChannel = $('#recovery-channel').value;
      await api('/auth/password-recovery',{method:'POST',body:JSON.stringify({email:state.recoveryEmail,channel:state.recoveryChannel})});
      state.recoveryStep='reset';
      notify('Confira seu canal','Se os dados forem elegíveis, enviaremos um código. O código vale por 10 minutos.','success');
      renderAuth();
      return;
    }
    const newPassword=$('#recovery-new-password').value;
    if(newPassword!==$('#recovery-confirm-password').value){notify('As senhas não conferem','Digite a mesma senha nos dois campos.','error');return;}
    await api('/auth/password-recovery/reset',{method:'POST',body:JSON.stringify({email:state.recoveryEmail,channel:state.recoveryChannel,code:$('#recovery-code').value.trim(),newPassword})});
    state.recoveryMode=false;state.recoveryStep='request';state.authMode='login';
    notify('Senha alterada','Agora entre com a nova senha.');renderAuth();$('#auth-email').value=state.recoveryEmail;
  } catch(error) { notify('Não foi possível concluir',error.message,'error'); }
}

async function submitAuth(event) {
  event.preventDefault();
  const form = new FormData(event.currentTarget);
  const email = String(form.get('email')).trim().toLowerCase();
  const password = String(form.get('password'));
  try {
    if (state.authMode === 'register') {
      await api('/auth/register', { method:'POST', body:JSON.stringify({ name:String(form.get('name')).trim(), email, password, cpf:String(form.get('cpf')).replace(/\D/g,'') }) });
      notify('Conta criada', 'Agora entre e solicite outros perfis em Minha conta > Acessos. Seus dados e CPF ficam em uma única conta.', 'success');
      state.authMode = 'login'; renderAuth();
      $('#auth-email').value = email;
      return;
    }
    const response = await api('/auth/login', { method:'POST', body:JSON.stringify({ email, password }) });
    setToken(response.accessToken);
    state.account = response.account;
    localStorage.setItem('pronto_account', JSON.stringify(state.account));
    state.activeRole=state.account.role;
    state.mustChangePassword=!!state.account.passwordChangeRequired;
    if(!state.mustChangePassword){state.profile = await api('/account/me');state.account = state.profile;localStorage.setItem('pronto_account', JSON.stringify(state.account));}
    state.page = state.mustChangePassword?'account':role() === 'CUSTOMER' ? 'home' : role() === 'MERCHANT' ? 'merchant' : role() === 'COURIER' ? 'courier' : 'admin';
    state.cart=[]; state.orders=[]; state.reservations=[]; state.deliveries=[]; state.offers=[]; state.ownStores=[];
    state.notifications=readLocalNotifications(state.account.id);
    if(!state.mustChangePassword){await loadNotifications().catch(()=>{});if(canShop())await loadAddresses();startNotificationPolling();await loadCurrentPage();}
    render();
    notify(`Olá, ${displayName()}!`, 'Seu espaço está pronto.', 'success');
  } catch (error) { notify('Não conseguimos entrar', error.message, 'error'); }
}

function renderShell() {
  const links = state.mustChangePassword ? navByRole[role()].filter(([page])=>page==='account') : navByRole[role()] || navByRole.CUSTOMER;
  const mobileLinks=role()==='CUSTOMER'?links:links.filter(([page])=>page!=='account');
  app.innerHTML = `<div class="app-shell ${state.sidebarCollapsed?'sidebar-collapsed':''}">
    <div class="mobile-nav-backdrop ${state.mobileMenu?'show':''}" id="nav-backdrop"></div>
    <aside class="sidebar ${state.mobileMenu?'open':''}">
      <a href="#" class="brand" data-page="${role()==='CUSTOMER'?'home':role()==='MERCHANT'?'merchant':role()==='COURIER'?'courier':'admin'}"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">${esc(roleNames[role()])}</small></div></a>
      <div class="nav-label">Menu principal</div><nav class="nav-list">${links.map(([page,iconName,label])=>`<button class="nav-item ${state.page===page?'active':''}" data-page="${page}"><span class="nav-icon">${icon(iconName)}</span>${label}${page==='notifications'&&state.notifications.some(n=>!n.read)?'<span class="nav-count">novo</span>':''}</button>`).join('')}</nav>
      <div class="side-bottom"><div class="help-card"><strong>${role()==='ADMIN'?'Central de atendimento':'Precisa de uma mão?'}</strong><p>${role()==='ADMIN'?'Acompanhe e responda as solicitações de suporte.':'Estamos aqui para ajudar você a aproveitar melhor o FoodDelivery.'}</p><button data-help>${role()==='ADMIN'?'Ver chamados →':'Falar com atendimento →'}</button></div>
      <div class="profile-mini"><div class="avatar">${esc(initials(state.account.name))}</div><div><strong>${esc(state.account.name)}</strong><small>${esc(roleNames[role()])}</small></div><button class="profile-more" data-page="account" aria-label="Abrir minha conta">Minha conta</button></div></div>
    </aside>
    <nav class="mobile-bottom-nav" aria-label="Navegação principal" style="--mobile-nav-count:${mobileLinks.length}">${mobileLinks.map(([page,iconName,label])=>`<button class="mobile-nav-item ${state.page===page?'active':''}" data-page="${page}" aria-label="${label}" title="${label}"><span>${icon(iconName)}</span><small>${label}</small>${page==='notifications'&&state.notifications.some(n=>!n.read)?'<i class="mobile-nav-dot"></i>':''}</button>`).join('')}</nav>
    <div class="main-column"><header class="topbar">
      <button class="icon-button mobile-menu" id="mobile-menu" aria-label="Recolher menu" aria-expanded="${!state.sidebarCollapsed}">☰</button>
      <button class="page-location location-button" data-location-picker aria-label="Alterar cidade de entrega"><span class="location-pin">${icon('pin')}</span><span><small>Lojas em destaque na cidade</small><strong>${esc((state.browseLocation?.city||state.account.city) ? `${state.browseLocation?.city||state.account.city}${(state.browseLocation?.state||state.account.state)?`, ${state.browseLocation?.state||state.account.state}`:''}` : 'Escolher minha cidade')} <span aria-hidden="true">⌄</span></strong></span></button>
      <label class="top-search"><span>⌕</span><input id="global-search" type="search" placeholder="Buscar lojas, pratos ou serviços" value="${esc(state.search)}" /></label>
      <div class="top-actions"><button class="icon-button" data-page="notifications" aria-label="Notificações">${icon('bell')}${state.notifications.some(n=>!n.read)?'<i class="dot"></i>':''}</button>${canShop()?`<button class="cart-top" id="open-cart">Sacola <b>${state.cart.reduce((n,i)=>n+i.quantity,0)}</b></button>`:''}<button class="top-account" data-page="account" title="Minha conta">${esc(initials(state.account.name))}</button><button class="header-logout" data-logout aria-label="Sair da conta">Sair</button></div>
    </header><main class="content" id="page-content"></main></div>
  </div>`;
  $('#mobile-menu')?.addEventListener('click',()=>{
    if(window.matchMedia('(max-width: 820px)').matches){state.mobileMenu=!state.mobileMenu;render();}
    else {state.sidebarCollapsed=!state.sidebarCollapsed;$('#mobile-menu').setAttribute('aria-expanded',String(!state.sidebarCollapsed));$('#mobile-menu').setAttribute('aria-label',state.sidebarCollapsed?'Expandir menu':'Recolher menu');app.querySelector('.app-shell')?.classList.toggle('sidebar-collapsed',state.sidebarCollapsed);}
  });
  $('#nav-backdrop')?.addEventListener('click',()=>{state.mobileMenu=false;render();});
  $('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
  $('#global-search')?.addEventListener('input',event=>{state.search=event.target.value;if(canShop()&&state.page==='home'){filterStores();renderPage();}});
  $('[data-location-picker]')?.addEventListener('click',openLocationPicker);
  $('[data-help]')?.addEventListener('click',openSupportCenter);
}

async function navigate(page) {
  if(state.mustChangePassword&&page!=='account')return;
  if(!navByRole[role()]?.some(([allowed])=>allowed===page) && page!=='checkout')return;
  if(state.page!==page)stopChat();
  state.page=page;state.mobileMenu=false;state.cartOpen=false;state.selectedStore=null;
  if(location.hash!==`#${page}`)history.pushState({page},'',`#${page}`);
  render();
  await loadCurrentPage();
  if(page==='notifications'){
    const unread=state.notifications.filter(n=>!n.read&&!n.localOnly);
    await Promise.all(unread.map(n=>api(`/account/notifications/${n.id}/read`,{method:'PATCH'}).catch(()=>{})));
    state.notifications=state.notifications.map(n=>({...n,read:true}));
    localStorage.setItem(notificationKey(state.account.id),JSON.stringify(state.notifications.filter(n=>n.localOnly!==false).slice(0,40)));
  }
  render();
}

function renderPage() {
  const content=$('#page-content');if(!content)return;
  const pages={home:renderHome,orders:renderOrders,reservations:renderReservations,account:renderAccount,notifications:renderNotifications,merchant:renderMerchant,merchantOrders:renderMerchantOrders,merchantReservations:renderMerchantReservations,courier:renderCourier,admin:renderAdmin,checkout:renderCheckout};
  const fn=pages[state.page]||renderHome;
  content.innerHTML=fn();
  bindPage();
}

async function openLocationPicker(){
  $('#location-modal')?.remove();
  const modal=document.createElement('div');modal.id='location-modal';modal.className='modal-backdrop';
  modal.innerHTML=`<section class="modal location-modal" role="dialog" aria-modal="true" aria-labelledby="location-title"><div class="modal-head"><div><span class="eyebrow">Sua região</span><h2 id="location-title">Onde você quer pedir?</h2><p>Escolha uma cidade ou informe seu CEP para encontrar lojas próximas.</p></div><button class="close-button" data-close-location aria-label="Fechar">×</button></div><div class="location-picker-grid"><section class="location-city-panel"><h3>Cidades com lojas cadastradas</h3><div class="location-city-list" data-city-list><p class="store-desc">Carregando cidades…</p></div></section><form class="location-cep-panel" data-location-cep><h3>Encontrar pelo CEP</h3><label class="field"><span>CEP</span><div class="cep-control"><input name="postalCode" inputmode="numeric" autocomplete="postal-code" maxlength="9" placeholder="00000-000" required/><button class="btn small" type="submit">Buscar</button></div></label><p class="store-desc" data-cep-feedback>Usamos o CEP para sugerir a cidade. Você pode revisar antes de aplicar.</p><div class="location-map-preview" data-location-map>Escolha uma cidade para visualizar a região.</div></form></div><div class="location-modal-foot"><button class="btn outline small" data-location-clear>Remover filtro de cidade</button><span>As lojas exibidas dependem da disponibilidade na região.</span></div></section>`;
  document.body.append(modal);
  modal.querySelector('[data-close-location]').onclick=()=>modal.remove();modal.onclick=e=>{if(e.target===modal)modal.remove();};
  modal.querySelector('[data-location-clear]').onclick=async()=>{state.browseLocation=null;localStorage.removeItem('fooddelivery_location');modal.remove();if(state.page==='home')await loadStores().catch(()=>{});render();};
  modal.querySelector('[data-location-cep]').onsubmit=async e=>{e.preventDefault();const input=e.currentTarget.elements.postalCode,feedback=modal.querySelector('[data-cep-feedback]'),cep=input.value.replace(/\D/g,'');if(cep.length!==8){feedback.textContent='Digite os oito números do CEP.';return;}feedback.textContent='Consultando CEP…';try{const response=await fetch(`https://viacep.com.br/ws/${cep}/json/`);if(!response.ok)throw new Error();const data=await response.json();if(data.erro)throw new Error();await selectBrowseLocation({city:data.localidade,state:data.uf},modal);feedback.textContent=`Região encontrada: ${data.localidade} · ${data.uf}.`; }catch{feedback.textContent='Não encontramos esse CEP. Confira os números ou escolha uma cidade da lista.';}};
  try{state.locationCities=await api('/store-locations');}catch{state.locationCities=[];}
  const cities=[...state.locationCities];if(state.account?.city&&state.account?.state&&!cities.some(c=>c.city.toLowerCase()===state.account.city.toLowerCase()&&c.state===state.account.state))cities.push({city:state.account.city,state:state.account.state});
  const list=modal.querySelector('[data-city-list]');list.innerHTML=cities.length?cities.map(c=>`<button type="button" class="location-city-option" data-city="${esc(c.city)}" data-uf="${esc(c.state)}"><span class="location-option-pin">${icon('pin',17)}</span><span><strong>${esc(c.city)}</strong><small>${esc(c.state)}</small></span><b>›</b></button>`).join(''):'<p class="store-desc">Ainda não há lojas aprovadas em outras cidades. Você também pode buscar qualquer CEP.</p>';
  list.querySelectorAll('[data-city]').forEach(b=>b.onclick=()=>selectBrowseLocation({city:b.dataset.city,state:b.dataset.uf},modal));
  const active=state.browseLocation||{city:state.account?.city,state:state.account?.state};if(active?.city)showLocationMap(modal,active);
}
function showLocationMap(modal,location){const box=modal.querySelector('[data-location-map]');if(!box)return;const query=encodeURIComponent(`${location.city}, ${location.state}, Brasil`);box.innerHTML=`<iframe title="Mapa da região de ${esc(location.city)}" loading="lazy" src="https://maps.google.com/maps?q=${query}&output=embed"></iframe><small>${esc(location.city)}, ${esc(location.state)}</small>`;}
async function selectBrowseLocation(location,modal){state.browseLocation={city:location.city,state:location.state};localStorage.setItem('fooddelivery_location',JSON.stringify(state.browseLocation));showLocationMap(modal,location);notify('Região atualizada',`Agora você está vendo lojas de ${location.city}, ${location.state}.`,'success');setTimeout(async()=>{modal.remove();if(state.page==='home')await loadStores().catch(()=>{});render();},450);}

async function openSupportCenter(){
  if(role()==='ADMIN'){state.adminSection='tickets';await loadAdminData();render();return;}
  const modal=document.createElement('div');modal.id='support-modal';modal.className='modal-backdrop';modal.innerHTML='<section class="modal support-modal" role="dialog" aria-modal="true" aria-labelledby="support-title"></section>';document.body.append(modal);modal.onclick=e=>{if(e.target===modal)modal.remove();};await loadSupportTicketList();
}
async function loadSupportTicketList(){try{state.supportAdminView=false;state.supportTickets=await api('/support/tickets/mine');state.activeTicket=null;renderSupportModal();}catch(error){notify('Não foi possível carregar seus chamados',error.message,'error');}}
function renderSupportModal(){
  const root=$('#support-modal .support-modal');if(!root)return;
  root.innerHTML=`<div class="modal-head"><div><span class="eyebrow">${state.supportAdminView?'Central de chamados':'Atendimento FoodDelivery'}</span><h2 id="support-title">${state.activeTicket?esc(state.activeTicket.ticket.subject):'Como podemos ajudar?'}</h2><p>${state.activeTicket?'Acompanhe as respostas e envie novas mensagens.':'Crie um chamado e acompanhe a resposta nesta conversa.'}</p></div><button class="close-button" data-close-support aria-label="Fechar">×</button></div>${state.activeTicket?ticketConversationHtml(state.activeTicket,state.supportAdminView):`<div class="support-columns"><form class="panel support-new" data-ticket-create><h3>Novo chamado</h3><label class="field"><span>Assunto</span><input name="subject" maxlength="140" required placeholder="Ex.: dúvida sobre meu pedido"/></label><label class="field"><span>Categoria</span><select name="category"><option value="GENERAL">Dúvida geral</option><option value="ORDER">Pedido</option><option value="RESERVATION">Reserva</option><option value="PAYMENT">Pagamento</option><option value="ACCOUNT">Minha conta</option></select></label><label class="field"><span>Mensagem</span><textarea name="body" maxlength="2000" required placeholder="Conte para a gente como podemos ajudar."></textarea></label><button class="btn" type="submit">Enviar ao atendimento</button></form><section class="panel support-list"><div class="section-head"><div><h3>Meus chamados</h3><p class="store-desc">Consulte atualizações anteriores.</p></div></div>${state.supportTickets.length?state.supportTickets.map(t=>`<button class="support-ticket-row" type="button" data-user-ticket="${esc(t.id)}"><span><strong>${esc(t.subject)}</strong><small>${esc(t.lastMessage||'Sem mensagens')} · ${shortDate(t.updatedAt)}</small></span>${pill(t.status)}</button>`).join(''):'<p class="store-desc">Você ainda não abriu um chamado.</p>'}</section></div>`}`;
  root.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();root.querySelector('[data-ticket-create]')?.addEventListener('submit',createSupportTicket);root.querySelectorAll('[data-user-ticket]').forEach(b=>b.onclick=()=>openTicket(b.dataset.userTicket,false));root.querySelector('[data-ticket-back]')?.addEventListener('click',loadSupportTicketList);root.querySelector('[data-ticket-compose]')?.addEventListener('submit',sendTicketMessage);
}
function ticketConversationHtml(detail,isAdmin){const t=detail.ticket;const ticketCategories={GENERAL:'Dúvida geral',ORDER:'Pedido',RESERVATION:'Reserva',PAYMENT:'Pagamento',ACCOUNT:'Minha conta'};return `<div class="ticket-detail-top"><button class="btn outline small" data-ticket-back>← ${isAdmin?'Chamados':'Meus chamados'}</button>${pill(t.status)}<span>${esc(ticketCategories[t.category]||t.category)}</span></div><div class="ticket-message-list">${detail.messages.map(m=>`<article class="ticket-message ${m.senderRole==='ADMIN'?'staff':''}"><div><strong>${esc(m.senderName)} ${m.senderRole==='ADMIN'?'<small>Atendimento</small>':''}</strong><time>${shortDate(m.createdAt)}</time></div><p>${esc(m.body)}</p></article>`).join('')}</div>${t.status==='CLOSED'?'<div class="alert-strip info">Este chamado foi encerrado. Se ainda precisar de ajuda, abra um novo.</div>':`<form class="ticket-compose" data-ticket-compose ${isAdmin?'data-admin-ticket="true"':''} data-ticket-id="${esc(t.id)}"><textarea name="body" maxlength="2000" required placeholder="${isAdmin?'Escreva uma resposta para o cliente…':'Digite sua mensagem…'}"></textarea><button class="btn small" type="submit">Enviar resposta</button></form>`}`;}
async function openTicket(id,isAdmin){try{if(!$('#support-modal')){const dialog=document.createElement('div');dialog.id='support-modal';dialog.className='modal-backdrop';dialog.innerHTML='<section class="modal support-modal" role="dialog" aria-modal="true" aria-labelledby="ticket-loading-title"></section>';document.body.append(dialog);dialog.onclick=e=>{if(e.target===dialog)dialog.remove();};}const root=$('#support-modal .support-modal');root.setAttribute('aria-busy','true');root.innerHTML='<div class="ticket-loading" role="status"><span class="loading-spinner" aria-hidden="true"></span><h2 id="ticket-loading-title">Abrindo chamado…</h2><p>Estamos carregando as mensagens.</p><button type="button" class="btn outline small" data-close-support>Fechar</button></div>';root.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();state.supportAdminView=isAdmin;state.activeTicket=await api(supportDetailPath(isAdmin,id));if(!$('#support-modal'))return;renderSupportModal(isAdmin);const currentRoot=$('#support-modal .support-modal');currentRoot.removeAttribute('aria-busy');currentRoot.innerHTML=`<div class="modal-head"><div><span class="eyebrow">${isAdmin?'Central de chamados':'Atendimento FoodDelivery'}</span><h2 id="support-title">${esc(state.activeTicket.ticket.subject)}</h2><p>${isAdmin?`${esc(state.activeTicket.ticket.accountName)} · ${esc(state.activeTicket.ticket.accountEmail)}`:'Acompanhe as respostas e envie novas mensagens.'}</p></div><button class="close-button" data-close-support aria-label="Fechar">×</button></div>${ticketConversationHtml(state.activeTicket,isAdmin)}`;currentRoot.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();currentRoot.querySelector('[data-ticket-back]')?.addEventListener('click',isAdmin?async()=>{state.adminTickets=await api('/admin/support-tickets');state.adminSection='tickets';render();$('#support-modal')?.remove();}:loadSupportTicketList);currentRoot.querySelector('[data-ticket-compose]')?.addEventListener('submit',sendTicketMessage);}catch(error){$('#support-modal')?.remove();state.activeTicket=null;notify('Não foi possível abrir o chamado',error.message,'error');}}
async function createSupportTicket(event){event.preventDefault();const form=event.currentTarget,data=new FormData(form);try{const detail=await api('/support/tickets',{method:'POST',body:JSON.stringify({subject:String(data.get('subject')).trim(),category:String(data.get('category')),body:String(data.get('body')).trim()})});state.supportTickets=await api('/support/tickets/mine');state.activeTicket=detail;notify('Chamado enviado','A equipe de atendimento recebeu sua mensagem.','success');renderSupportModal();}catch(error){notify('Não conseguimos enviar o chamado',error.message,'error');}}
async function sendTicketMessage(event){event.preventDefault();const form=event.currentTarget,body=String(new FormData(form).get('body')||'').trim(),id=form.dataset.ticketId,isAdmin=form.dataset.adminTicket==='true';if(!body)return;try{state.activeTicket=await api(supportMessagePath(isAdmin,id),{method:'POST',body:JSON.stringify({body})});if(isAdmin)state.adminTickets=await api('/admin/support-tickets');else state.supportTickets=await api('/support/tickets/mine');renderSupportModal(isAdmin);}catch(error){notify('Mensagem não enviada',error.message,'error');}}
async function updateTicketStatus(id,status){try{await api(`/admin/support-tickets/${encodeURIComponent(id)}/status`,{method:'PATCH',body:JSON.stringify({status})});state.adminTickets=await api('/admin/support-tickets');renderPage();notify(status==='CLOSED'?'Chamado encerrado':'Chamado reaberto','O status foi atualizado.','success');}catch(error){notify('Não foi possível atualizar o chamado',error.message,'error');}}

function renderHome() {
  const count=state.filteredStores.length;
  const sectionCategories=categoryOptions.slice(1).map(category=>({category,stores:state.filteredStores.filter(store=>categoryMatches(store.category,category))})).filter(section=>section.stores.length);
  const storeSections=state.category==='Todas'&&!state.search.trim()
    ? sectionCategories.map(section=>`<section class="store-category-section"><div class="section-head"><div><span class="eyebrow">${esc(section.category)}</span><h2>${esc(section.category)}</h2></div><button data-category="${esc(section.category)}">Ver todas →</button></div><div class="store-grid">${section.stores.slice(0,4).map((store,index)=>storeCard(store,index)).join('')}</div></section>`).join('')
    : state.filteredStores.length?`<div class="store-grid">${state.filteredStores.map((store,index)=>storeCard(store,index)).join('')}</div>`:'';
  const activeCity=state.browseLocation?.city||state.account?.city,activeUf=state.browseLocation?.state||state.account?.state;const city=activeCity?`${activeCity}${activeUf?`, ${activeUf}`:''}`:'';
  return `<section class="home-location"><span class="home-location-icon">${icon('pin',20)}</span><span><strong>${city?`Lojas e serviços em ${esc(city)}`:'Escolha sua cidade para encontrar lojas próximas'}</strong><small>${city?'Os resultados foram filtrados pela cidade do seu endereço.':'Informe seu endereço para ver estabelecimentos da sua região.'}</small></span><button class="btn outline small" data-location-picker>${city?'Alterar cidade':'Escolher cidade'}</button></section><section class="hero"><div class="hero-copy"><span class="eyebrow">Um bairro inteiro para descobrir</span><h1>Seu dia fica melhor<br/>quando é por perto.</h1><p>Peça seu favorito, marque um horário e apoie quem faz parte da sua rotina.</p><button data-scroll-stores>Explorar agora <span>→</span></button></div><div class="hero-art">🥗</div><div class="hero-note">Hoje é dia de <b>pedir algo gostoso</b> ✨</div></section>
    <section class="discovery-panel" aria-label="Categorias de lugares"><div class="discovery-heading"><div><span class="eyebrow">Escolha o seu momento</span><h2>O que combina com hoje?</h2><p>Explore os comércios da sua cidade por tipo.</p></div><span class="home-results-count"><strong>${count}</strong><span>${count===1?'lugar encontrado':'lugares encontrados'}</span></span></div>
    <div class="category-row" role="group" aria-label="Filtrar por categoria">${categoryOptions.map(c=>{const categoryCount=c==='Todas'?state.stores.length:state.stores.filter(store=>categoryMatches(store.category,c)).length;return `<button class="category-chip ${state.category===c?'active':''}" data-category="${esc(c)}" aria-pressed="${state.category===c}"><span class="category-icon">${c==='Todas'?'✦':categoryEmoji[c]||'✦'}</span><span>${esc(c)}</span><span class="category-count">${categoryCount}</span></button>`}).join('')}</div></section>
    <div class="section-head discovery-results-heading" id="stores-heading"><div><span class="eyebrow">Perto de você</span><h2>${state.category==='Todas'?'Descubra por perto':`Lugares em ${esc(state.category)}`}</h2></div><button data-category="Todas">Ver todos <span aria-hidden="true">→</span></button></div>
    ${storeSections||emptyState('⌕','Ainda não encontramos lugares por aqui',state.stores.length?'Tente outra busca ou escolha uma categoria diferente.':'Quando os comerciantes cadastrarem suas lojas, elas aparecerão aqui.','Atualizar lista','refresh-stores')}`;
}

function storeCard(store,index=0) {
  const style=categoryStyles[store.category]||['orange','green','purple','blue','gold','rose'][index%6];
  const emoji=categoryEmoji[store.category]||'🥘';
  const cover=store.coverImageUrl?`style="background-image:linear-gradient(180deg,rgba(20,25,22,.02),rgba(20,25,22,.42)),url('${esc(apiAssetUrl(store.coverImageUrl))}')"`:'';
  return `<article class="store-card" data-store="${esc(store.id)}" tabindex="0" role="button" aria-label="Abrir ${esc(store.name)}"><div class="store-cover ${style} ${store.coverImageUrl?'has-cover':''}" ${cover}><span class="store-badge">${esc(store.category||'Comércio local')}</span>${store.coverImageUrl?'':`<span>${emoji}</span>`}</div><div class="store-card-body"><div class="store-title-row"><h3>${esc(store.name)}</h3><span class="status-pill">Local</span></div><p class="store-desc">${esc(store.description||'Uma boa escolha pertinho de você.')}</p><div class="store-meta"><span>⌖ ${esc(store.address||'Na sua região')}</span></div><div class="store-card-foot"><span>Consulte opções e valores</span><strong>Ver opções →</strong></div></div></article>`;
}

function emptyState(icon,title,message,button,label) {return `<div class="empty-state"><div class="empty-icon">${icon}</div><h3>${esc(title)}</h3><p>${esc(message)}</p>${button?`<button class="btn light small" data-action="${label}">${esc(button)}</button>`:''}</div>`;}

function bindPage() {
  document.querySelectorAll('[data-category]').forEach(b=>b.addEventListener('click',()=>{state.category=b.dataset.category;filterStores();renderPage();}));
  document.querySelectorAll('[data-store]').forEach(card=>{const open=()=>openStore(card.dataset.store);card.addEventListener('click',open);card.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();open();}});});
  document.querySelectorAll('[data-action]').forEach(b=>b.addEventListener('click',()=>runAction(b.dataset.action)));
  document.querySelectorAll('[data-checkout]').forEach(b=>b.addEventListener('click',checkout));
  document.querySelectorAll('[data-profile-save]').forEach(f=>f.addEventListener('submit',saveProfile));
  document.querySelectorAll('[data-password-change]').forEach(f=>f.addEventListener('submit',changePassword));
  $('[data-phone-verify-request]')?.addEventListener('click',requestPhoneVerification);
  $('[data-phone-verify-confirm]')?.addEventListener('submit',confirmPhoneVerification);
  document.querySelectorAll('[data-account-section]').forEach(b=>b.addEventListener('click',async()=>{state.accountSection=b.dataset.accountSection;if(state.accountSection==='addresses')await loadAddresses();renderPage();}));
  document.querySelectorAll('[data-ticket-open]').forEach(b=>b.addEventListener('click',()=>openTicket(b.dataset.ticketOpen,b.dataset.ticketAdmin==='true')));
  document.querySelectorAll('[data-ticket-status]').forEach(b=>b.addEventListener('click',()=>updateTicketStatus(b.dataset.ticketStatus,b.dataset.status)));
  document.querySelectorAll('[data-ticket-compose]').forEach(f=>f.addEventListener('submit',sendTicketMessage));
  document.querySelectorAll('[data-ticket-create]').forEach(f=>f.addEventListener('submit',createSupportTicket));
  document.querySelectorAll('[data-support-list]').forEach(b=>b.addEventListener('click',loadSupportTicketList));
  document.querySelectorAll('[data-switch-role]').forEach(b=>b.addEventListener('click',()=>switchRole(b.dataset.switchRole)));
  document.querySelectorAll('[data-operator-request]').forEach(f=>f.addEventListener('submit',requestOperatorAccess));
  document.querySelectorAll('[data-city-request]').forEach(f=>f.addEventListener('submit',requestCityAccess));
  document.querySelectorAll('[data-platform-review]').forEach(b=>b.addEventListener('click',()=>reviewPlatformRequest(b.dataset.platformReview,b.dataset.decision)));
  document.querySelectorAll('[data-create-store]').forEach(f=>f.addEventListener('submit',createStore));
  document.querySelectorAll('[data-resubmit-store]').forEach(b=>b.addEventListener('click',()=>resubmitStore(b.dataset.resubmitStore)));
  $('[data-merchant-store-select]')?.addEventListener('change',selectMerchantStore);
  document.querySelectorAll('[data-create-item]').forEach(f=>f.addEventListener('submit',createItem));
  document.querySelectorAll('[data-update-store]').forEach(f=>f.addEventListener('submit',updateStore));
  document.querySelectorAll('[data-create-operator]').forEach(f=>f.addEventListener('submit',createOperator));
  document.querySelectorAll('[data-operator-application]').forEach(b=>b.addEventListener('click',()=>reviewOperatorApplication(b.dataset.operatorApplication,b.dataset.decision)));
  document.querySelectorAll('[data-operator-active]').forEach(b=>b.addEventListener('click',()=>setOperatorActive(b.dataset.operatorActive,b.dataset.active==='true')));
  document.querySelectorAll('[data-account-active]').forEach(b=>b.addEventListener('click',()=>setAccountActive(b.dataset.accountActive,b.dataset.active==='true')));
  document.querySelectorAll('[data-account-role]').forEach(b=>b.addEventListener('click',()=>setAccountRole(b.dataset.accountRole,b.dataset.role,b.dataset.active==='true')));
  document.querySelectorAll('[data-reset-password]').forEach(b=>b.addEventListener('click',()=>resetAccountPassword(b.dataset.resetPassword,b.dataset.name)));
  $('[data-dismiss-temp-password]')?.addEventListener('click',()=>{state.adminTemporaryPassword='';renderPage();});
  $('[data-ai-generate]')?.addEventListener('click',generateStoreCover);
  $('#ai-cover-prompt')?.addEventListener('input',event=>{state.aiPrompt=event.target.value;});
  $('[data-ai-save-cover]')?.addEventListener('click',saveGeneratedStoreCover);
  $('[data-ai-discard-cover]')?.addEventListener('click',()=>{state.aiGeneratedCover=null;renderPage();});
  document.querySelectorAll('[data-order-status]').forEach(b=>b.addEventListener('click',()=>updateOrder(b.dataset.id,b.dataset.orderStatus)));
  document.querySelectorAll('[data-offer-delivery]').forEach(b=>b.addEventListener('click',()=>offerDelivery(b.dataset.offerDelivery)));
  document.querySelectorAll('[data-reservation-status]').forEach(b=>b.addEventListener('click',()=>updateReservation(b.dataset.id,b.dataset.reservationStatus)));
  document.querySelectorAll('[data-approve-store]').forEach(b=>b.addEventListener('click',()=>approveStore(storeApprovalId(b))));
  document.querySelectorAll('[data-store-active]').forEach(b=>b.addEventListener('click',()=>setStoreActive(b.dataset.storeActive,b.dataset.active==='true')));
  document.querySelectorAll('[data-accept-delivery]').forEach(b=>b.addEventListener('click',()=>acceptDelivery(b.dataset.id)));
  document.querySelectorAll('[data-delivery-status]').forEach(b=>b.addEventListener('click',()=>updateDelivery(b.dataset.id,b.dataset.deliveryStatus)));
  document.querySelectorAll('[data-share-location]').forEach(b=>b.addEventListener('click',()=>shareCourierLocation(b.dataset.shareLocation)));
  document.querySelectorAll('[data-reopen-store]').forEach(b=>b.addEventListener('click',()=>openStore(b.dataset.reopenStore)));
  $('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
  $('[data-scroll-stores]')?.addEventListener('click',()=>$('#stores-heading')?.scrollIntoView({behavior:'smooth'}));
  $('[data-checkout-pay]')?.addEventListener('click',approvePayment);
  document.querySelectorAll('[data-section]').forEach(button=>button.addEventListener('click',()=>{
    if(role()==='ADMIN')state.adminSection=button.dataset.section;
    else if(role()==='MERCHANT')state.merchantSection=button.dataset.section;
    renderPage();
  }));
  document.querySelectorAll('[data-admin-team-section]').forEach(button=>button.addEventListener('click',()=>{
    state.adminSection='team';state.adminTeamSection=button.dataset.adminTeamSection;renderPage();
  }));
  document.querySelectorAll('[data-tracking-map]').forEach(button=>button.addEventListener('click',()=>{
    state.trackingMapOrderId=state.trackingMapOrderId===button.dataset.trackingMap?'':button.dataset.trackingMap;
    renderPage();
  }));
  document.querySelectorAll('[data-open-chat-type]').forEach(button=>button.addEventListener('click',()=>openChat(button.dataset.openChatType,button.dataset.openChatId)));
  document.querySelectorAll('[data-chat-form]').forEach(form=>form.addEventListener('submit',sendChatMessage));
  document.querySelectorAll('[data-chat-draft]').forEach(area=>area.addEventListener('input',()=>{state.chatDrafts[area.dataset.chatDraft]=area.value;}));
  document.querySelectorAll('[data-close-chat]').forEach(button=>button.addEventListener('click',()=>closeChat(button.dataset.closeChat)));
}

async function runAction(action) {
  if(action==='refresh-stores'){try{await loadStores();renderPage();}catch(e){notify('Não conseguimos atualizar',e.message,'error');}}
  if(action==='reload-admin'){await loadAdminData();renderPage();if(state.adminLoadError)notify('Atualização parcial',state.adminLoadError,'error');else notify('Dados atualizados','As informações administrativas estão atualizadas.','success');}
}

async function openStore(id) {
  try { state.selectedStore=state.stores.find(s=>String(s.id)===String(id))||null;state.storeItems=await api(`/stores/${encodeURIComponent(id)}/items`);render(); }
  catch(error){notify('Não conseguimos abrir essa loja',error.message,'error');}
}

function renderStoreModal() {
  const store=state.selectedStore;if(!store)return;
  const modal=document.createElement('div');modal.className='modal-backdrop';modal.id='store-modal';
  modal.innerHTML=`<section class="modal" role="dialog" aria-modal="true" aria-labelledby="store-modal-title"><div class="modal-head"><div><span class="eyebrow">${esc(store.category||'Comércio local')}</span><h2 id="store-modal-title">${esc(store.name)}</h2><p>${esc(store.description||store.address||'Escolha uma opção para continuar.')}</p></div><button class="close-button" data-close-modal aria-label="Fechar">×</button></div>
    <div class="alert-strip info"><span>✦</span><span>Os valores e a disponibilidade são confirmados pela loja. Você será direcionado ao Mercado Pago para concluir o pagamento.</span></div>
    ${state.storeItems.length?`<div class="menu-list">${state.storeItems.map((item,i)=>`<div class="menu-row"><div class="menu-row-icon">${item.serviceItem?'✂️':['🥗','🍕','🥪','🍜'][i%4]}</div><div class="menu-row-copy"><strong>${esc(item.name)}</strong><small>${esc(item.description|| (item.serviceItem?'Serviço presencial':item.stock>0?`Disponível: ${item.stock}`:'Preparado com carinho'))}</small></div><span class="menu-row-price">${money(item.priceCents)}</span>${item.serviceItem?`<button class="btn small light" data-reserve="${esc(item.id)}">Reservar</button>`:item.stock<1?'<span class="warning-small">Indisponível</span>':`<button class="btn small" data-add-item="${esc(item.id)}">Adicionar</button>`}</div>`).join('')}</div>`:emptyState('☕','Esta loja está preparando o cardápio','Volte daqui a pouco para conferir as novidades.')}
    </section>`;
  document.body.append(modal);
  $('[data-close-modal]',modal).addEventListener('click',()=>{state.selectedStore=null;modal.remove();});
  modal.addEventListener('click',e=>{if(e.target===modal){state.selectedStore=null;modal.remove();}});
  modal.querySelectorAll('[data-add-item]').forEach(b=>b.addEventListener('click',()=>addToCart(b.dataset.addItem)));
  modal.querySelectorAll('[data-reserve]').forEach(b=>b.addEventListener('click',()=>showReserveForm(b.dataset.reserve)));
}

function addToCart(itemId) {
  const item=state.storeItems.find(i=>String(i.id)===String(itemId));if(!item)return;
  if(state.cart.length&&String(state.cart[0].storeId)!==String(state.selectedStore.id)){notify('Sua sacola tem outra loja','Finalize ou esvazie a sacola antes de pedir de outro estabelecimento.','info');return;}
  const line=state.cart.find(i=>String(i.id)===String(item.id));
  if(line&&line.quantity>=item.stock){notify('Limite de estoque','A loja não tem mais unidades disponíveis deste item.','info');return;}
  if(line)line.quantity++;else state.cart.push({...item,storeId:state.selectedStore.id,storeName:state.selectedStore.name,deliveryFeeCents:state.selectedStore.deliveryFeeCents||0,quantity:1});
  state.selectedStore=null;state.cartOpen=true;notify('Adicionado à sacola',`${item.name} está esperando por você.`);render();
}

function showReserveForm(itemId) {
  const item=state.storeItems.find(i=>String(i.id)===String(itemId));const backdrop=$('#store-modal');if(!item||!backdrop)return;
  const modal=$('.modal',backdrop);
  modal.innerHTML=`<div class="modal-head"><div><span class="eyebrow">Reserva presencial</span><h2>Escolha um horário</h2><p>${esc(item.name)} · ${money(item.priceCents)}</p></div><button class="close-button" data-close-modal aria-label="Fechar">×</button></div>
    <div class="alert-strip info"><span>✓</span><span>Seu horário será confirmado quando o Mercado Pago aprovar o pagamento.</span></div>
    <form id="reserve-form"><div class="field"><label for="reserve-date">Data e horário</label><input id="reserve-date" name="startsAt" type="datetime-local" required min="${new Date(Date.now()+31*60*1000).toISOString().slice(0,16)}" /></div><div class="field"><label for="reserve-note">Comentário para a loja <small>(opcional)</small></label><textarea id="reserve-note" name="customerNote" maxlength="500" rows="3" placeholder="Conte algo que ajude a preparar seu atendimento"></textarea></div><p class="warning-small">Escolha um horário com pelo menos 30 minutos de antecedência.</p><button class="btn block" type="submit">Continuar para pagamento · ${money(item.priceCents)}</button></form>`;
  $('[data-close-modal]',modal).addEventListener('click',()=>{state.selectedStore=null;backdrop.remove();});
  $('#reserve-form',modal).addEventListener('submit',async e=>{e.preventDefault();const fields=new FormData(e.currentTarget),local=fields.get('startsAt');if(!local)return;try{const created=await api('/reservations',{method:'POST',body:JSON.stringify({storeId:state.selectedStore.id,serviceItemId:item.id,startsAt:new Date(local).toISOString(),customerNote:String(fields.get('customerNote')||'').trim()||null})});state.selectedStore=null;backdrop.remove();state.checkout={type:'RESERVATION',paymentId:created.paymentId,reservation:created.reservation};state.page='checkout';render();}catch(error){notify('Não foi possível iniciar a reserva',error.message,'error');}});
}

function renderCartDrawer() {
  const backdrop=document.createElement('div');backdrop.className='drawer-backdrop';backdrop.id='cart-backdrop';
  const subtotal=state.cart.reduce((sum,line)=>sum+line.priceCents*line.quantity,0);
  const deliveryFee=state.cartMode==='DELIVERY'?(state.cart[0]?.deliveryFeeCents||0):0;
  const total=subtotal+deliveryFee;
  backdrop.innerHTML=`<aside class="drawer" aria-label="Sacola"><div class="drawer-head"><div><span class="eyebrow">Seu pedido</span><h2>Sua sacola</h2></div><button class="close-button" data-close-cart aria-label="Fechar sacola">×</button></div>
    ${state.cart.length?`<p class="store-meta">${esc(state.cart[0].storeName)}</p><div>${state.cart.map(line=>`<div class="cart-line"><div class="cart-food-icon">🥘</div><div><strong>${esc(line.name)}</strong><small>${money(line.priceCents)} cada</small></div><div class="qty-control"><button data-qty="-1" data-id="${esc(line.id)}" aria-label="Diminuir">−</button><span>${line.quantity}</span><button data-qty="1" data-id="${esc(line.id)}" aria-label="Aumentar">+</button></div></div>`).join('')}</div>
      <div class="fulfillment-toggle"><button data-cart-mode="PICKUP" class="${state.cartMode==='PICKUP'?'active':''}">Retirar no local</button><button data-cart-mode="DELIVERY" class="${state.cartMode==='DELIVERY'?'active':''}">Receber em casa</button></div>
      ${state.cartMode==='DELIVERY'?`<section class="checkout-address"><div class="address-heading"><div><strong>Onde devemos entregar?</strong><small>Escolha um endereço salvo ou cadastre outro.</small></div><button class="text-button" type="button" data-add-address>+ Novo endereço</button></div>
        ${state.addresses.length?state.addresses.map(a=>`<label class="address-option ${String(state.selectedAddressId)===String(a.id)?'selected':''}"><input type="radio" name="selected-address" value="${esc(a.id)}" ${String(state.selectedAddressId)===String(a.id)?'checked':''}/><span><strong>${esc(a.label||'Meu endereço')} ${a.defaultAddress?'<i>Principal</i>':''}</strong><small>${esc(addressText(a))}</small></span></label>`).join(''):'<div class="alert-strip warn">Cadastre um endereço para receber este pedido.</div>'}
        ${state.addressFormOpen?`<form class="address-form" data-address-form><div class="form-grid"><div class="field"><label>Como quer identificar?</label><input name="label" required maxlength="40" placeholder="Casa, trabalho…" value="Casa"/></div><div class="field"><label>CEP</label><div class="cep-control"><input name="postalCode" data-cep autocomplete="postal-code" inputmode="numeric" required maxlength="9" placeholder="00000-000"/><button class="btn outline small" type="button" data-lookup-cep>Buscar</button></div></div><div class="field span-2"><label>Rua e número</label><input name="addressLine" required maxlength="250" autocomplete="street-address" placeholder="Rua das Flores, 123"/></div><div class="field"><label>Complemento <small>(opcional)</small></label><input name="addressComplement" maxlength="120" placeholder="Apto, bloco, referência"/></div><div class="field"><label>Bairro</label><input name="neighborhood" required maxlength="120" autocomplete="address-level3"/></div><div class="field"><label>Cidade</label><input name="city" required maxlength="100" autocomplete="address-level2"/></div><div class="field"><label>Estado</label><input name="state" required minlength="2" maxlength="2" autocomplete="address-level1" placeholder="SP"/></div></div><label class="check-row"><input name="defaultAddress" type="checkbox" ${state.addresses.length?'':'checked'}/> Usar como endereço principal</label><div class="action-row"><button class="btn small" type="submit">Salvar endereço</button><button class="btn outline small" type="button" data-cancel-address>Agora não</button></div></form>`:''}
        <div class="alert-strip info"><span>⌖</span><span>Taxa de entrega definida pela loja e incluída no total.</span></div></section>`:'<div class="alert-strip info"><span>⌖</span><span>Você retira seu pedido diretamente na loja. O pagamento é feito no checkout.</span></div>'}
      <div class="field checkout-note-field"><label for="checkout-note">Observação para a loja ou entregador <small>(opcional)</small></label><textarea id="checkout-note" data-checkout-note maxlength="500" rows="3" placeholder="Ex.: sem cebola, interfone quebrado, ponto de referência">${esc(state.checkoutNote)}</textarea><small>Até 500 caracteres. Você também poderá conversar com a loja depois de confirmar.</small></div>
      ${!state.account.phone?`<div class="alert-strip warn"><span>!</span><span>Antes do primeiro pedido, precisamos do seu telefone. <button class="text-button" data-page="account">Completar cadastro</button></span></div>`:''}
      <div class="cart-total"><span>Itens</span><span>${money(subtotal)}</span></div><div class="cart-total"><span>Entrega</span><span>${state.cartMode==='DELIVERY'?money(deliveryFee):'Grátis'}</span></div><div class="cart-total grand"><span>Total</span><span>${money(total)}</span></div>
      <button class="btn block" data-checkout ${!state.account.phone?'disabled':''}>Continuar para pagamento <span>→</span></button><p class="warning-small" style="text-align:center;margin-top:10px">Pagamento processado com segurança pelo Mercado Pago.</p>
    `:emptyState('🛍️','Sua sacola está vazia','Explore as lojas e adicione algo que você goste.','Explorar lojas','close-empty-cart')}
    </aside>`;
  document.body.append(backdrop);
  $('[data-close-cart]',backdrop).addEventListener('click',closeCart);
  backdrop.addEventListener('click',e=>{if(e.target===backdrop)closeCart();});
  backdrop.querySelectorAll('[data-qty]').forEach(b=>b.addEventListener('click',()=>{const item=state.cart.find(i=>String(i.id)===String(b.dataset.id));if(!item)return;const change=Number(b.dataset.qty);if(change>0&&item.quantity>=item.stock){notify('Limite de estoque','A loja não tem mais unidades disponíveis deste item.','info');return;}item.quantity+=change;if(item.quantity<1)state.cart=state.cart.filter(i=>i!==item);render();}));
  backdrop.querySelectorAll('[data-cart-mode]').forEach(b=>b.addEventListener('click',()=>{state.cartMode=b.dataset.cartMode;render();}));
  $('[data-checkout]',backdrop)?.addEventListener('click',checkout);
  $('[data-action="close-empty-cart"]',backdrop)?.addEventListener('click',closeCart);
  $('[data-page="account"]',backdrop)?.addEventListener('click',()=>navigate('account'));
  backdrop.querySelectorAll('[name="selected-address"]').forEach(radio=>radio.addEventListener('change',()=>{state.selectedAddressId=radio.value;render();}));
  $('[data-checkout-note]',backdrop)?.addEventListener('input',event=>{state.checkoutNote=event.target.value;});
  $('[data-add-address]',backdrop)?.addEventListener('click',()=>{state.addressFormOpen=true;render();});
  $('[data-cancel-address]',backdrop)?.addEventListener('click',()=>{state.addressFormOpen=false;render();});
  $('[data-lookup-cep]',backdrop)?.addEventListener('click',e=>lookupCep(e.currentTarget.closest('form')));
  $('[data-cep]',backdrop)?.addEventListener('blur',e=>{if(e.target.value.replace(/\D/g,'').length===8)lookupCep(e.target.closest('form'));});
  $('[data-address-form]',backdrop)?.addEventListener('submit',saveCheckoutAddress);
}

function closeCart(){state.cartOpen=false;render();}

async function checkout() {
  if(!state.cart.length)return;
  const selected=state.addresses.find(a=>String(a.id)===String(state.selectedAddressId));
  const address=selected?addressText(selected):'';
  if(state.cartMode==='DELIVERY'&&!address.trim()){notify('Falta o endereço','Informe onde devemos entregar seu pedido.','info');return;}
  try {
    const response=await api('/orders',{method:'POST',body:JSON.stringify({storeId:state.cart[0].storeId,fulfillmentType:state.cartMode,deliveryAddress:state.cartMode==='DELIVERY'?address:null,customerNote:state.checkoutNote.trim()||null,items:state.cart.map(line=>({itemId:line.id,quantity:line.quantity}))})});
    state.checkout={...response,type:'ORDER'};state.cart=[];state.checkoutNote='';state.cartOpen=false;state.deliveryAddress=address;state.page='checkout';render();
  } catch(error){notify('O pedido não foi enviado',error.message,'error');}
}

async function saveCheckoutAddress(event) {
  event.preventDefault(); const form=event.currentTarget; const fields=new FormData(form);
  try {
    const address=await api('/account/addresses',{method:'POST',body:JSON.stringify({label:String(fields.get('label')).trim(),addressLine:String(fields.get('addressLine')).trim(),addressComplement:String(fields.get('addressComplement')||'').trim()||null,neighborhood:String(fields.get('neighborhood')).trim(),city:String(fields.get('city')).trim(),state:String(fields.get('state')).trim().toUpperCase(),postalCode:String(fields.get('postalCode')).trim(),defaultAddress:fields.get('defaultAddress')==='on'})});
    state.addresses=await api('/account/addresses'); state.selectedAddressId=address.id; state.addressFormOpen=false;
    state.profile=await api('/account/me');state.account=state.profile;localStorage.setItem('pronto_account',JSON.stringify(state.account));
    notify('Endereço salvo','Você pode usá-lo neste e nos próximos pedidos.');render();
  } catch(error) { notify('Não foi possível salvar o endereço',error.message,'error'); }
}

async function lookupCep(form) {
  const input=form?.querySelector('[data-cep]'); if(!input)return;
  const cep=input.value.replace(/\D/g,''); if(cep.length!==8){notify('Confira o CEP','Digite os 8 números do CEP para buscar o endereço.','info');return;}
  if(form.dataset.cepLookup===cep)return;
  form.dataset.cepLookup=cep;
  const button=form.querySelector('[data-lookup-cep]'); if(button){button.disabled=true;button.textContent='Buscando…';}
  try {
    const response=await fetch(`https://viacep.com.br/ws/${cep}/json/`,{headers:{Accept:'application/json'}});
    if(!response.ok)throw new Error('O serviço de CEP está indisponível. Você ainda pode preencher o endereço manualmente.');
    const data=await response.json(); if(data.erro)throw new Error('Não encontramos esse CEP. Confira os números ou preencha o endereço manualmente.');
    const fill=(name,value)=>{const field=form.querySelector(`[name="${name}"]`);if(field&&value&&!field.value)field.value=value;};
    fill('addressLine',data.logradouro);fill('neighborhood',data.bairro);fill('city',data.localidade);fill('state',data.uf);
    input.value=data.cep||input.value;notify('CEP encontrado','Confira o número e o complemento do endereço.','success');
  } catch(error) {notify('Não foi possível consultar o CEP',error.message,'error');}
  finally {delete form.dataset.cepLookup;if(button){button.disabled=false;button.textContent='Buscar';}}
}

function renderCheckout() {
  if(!state.checkout)return `<div class="page-head"><div><span class="eyebrow">Pagamento</span><h1>Finalizar pedido</h1><p>Não encontramos uma compra para continuar.</p></div></div>${emptyState('🛍️','Sua sacola está vazia','Volte às lojas para escolher seus itens.','Ver lojas','go-home')}`;
  if(state.checkout.type==='RESERVATION'){
    const r=state.checkout.reservation;
    return `<div class="page-head"><div><span class="eyebrow">Pagamento da reserva</span><h1>Confirme seu horário</h1><p>O horário fica reservado após a aprovação do pagamento.</p></div></div><div class="split-layout"><section class="panel"><span class="eyebrow">Atendimento</span><h3 style="margin-top:8px">${esc(r.serviceName)}</h3><p class="store-desc">${esc(r.store.name)} · ${shortDate(r.startsAt)}</p><div class="alert-strip info"><span>✓</span><span>Pagamento realizado no ambiente seguro do Mercado Pago. O estabelecimento só confirma sua reserva após a aprovação.</span></div></section><aside class="panel"><span class="eyebrow">Resumo</span><div class="cart-total grand"><span>Total</span><span>${money(r.amountCents)}</span></div><button class="btn block" data-checkout-pay ${state.paymentStarting?'disabled':''}>${state.paymentStarting?'Abrindo pagamento…':'Pagar e confirmar horário'}</button><button class="btn outline block" style="margin-top:8px" data-page="reservations">Voltar às reservas</button></aside></div>`;
  }
  const order=state.checkout.order;
  return `<div class="page-head"><div><span class="eyebrow">Quase lá</span><h1>Revise seu pedido</h1><p>Confira os detalhes antes de confirmar o pagamento.</p></div></div>
    <div class="split-layout"><section><div class="panel"><h3>Resumo da compra</h3><div class="store-title-row"><strong>${esc(order.store.name)}</strong>${pill(order.status)}</div><p class="store-desc">${order.fulfillmentType==='DELIVERY'?'Entrega em':'Retirada em'} ${esc(order.fulfillmentType==='DELIVERY'?order.deliveryAddress:order.store.address)}</p><div class="order-items">${order.lines.map(line=>`<div>${line.quantity} × ${esc(line.name)} <span style="float:right">${money(line.unitPriceCents*line.quantity)}</span></div>`).join('')}${order.deliveryFeeCents?`<div>Taxa de entrega <span style="float:right">${money(order.deliveryFeeCents)}</span></div>`:''}</div><div class="cart-total"><span>Itens</span><span>${money(order.subtotalCents)}</span></div><div class="cart-total"><span>Entrega</span><span>${order.fulfillmentType==='DELIVERY'?money(order.deliveryFeeCents):'Grátis'}</span></div><div class="cart-total grand"><span>Total</span><span>${money(order.totalCents)}</span></div></div>
      <div class="alert-strip info"><span>✓</span><span>Você continuará para o checkout seguro do Mercado Pago. O pedido só será confirmado depois que o provedor aprovar o pagamento.</span></div></section>
      <aside class="panel"><span class="eyebrow">Checkout seguro</span><h3 style="margin-top:7px">Finalize o pagamento</h3><p class="store-desc">Total a pagar · <strong>${money(order.totalCents)}</strong></p><div class="alert-strip info"><span>✓</span><span>Os dados do cartão são processados pelo Mercado Pago e não são armazenados pelo FoodDelivery.</span></div><button class="btn block" data-checkout-pay ${state.paymentStarting?'disabled':''}>${state.paymentStarting?'Abrindo pagamento…':'Pagar com Mercado Pago'}</button><button class="btn outline block" style="margin-top:8px" data-page="orders">Deixar para depois</button></aside></div>`;
}

async function approvePayment() {
  if(!state.checkout)return;
  state.paymentStarting=true;render();
  try{const checkout=await api(`/payments/${state.checkout.paymentId}/checkout`,{method:'POST'});window.location.assign(checkout.checkoutUrl);}
  catch(error){state.paymentStarting=false;render();notify('Não foi possível abrir o pagamento',error.message,'error');}
}

function renderOrders() {
  const orders=state.orders||[];
  return `<div class="page-head"><div><span class="eyebrow">Acompanhe de perto</span><h1>Meus pedidos</h1><p>Veja o andamento e os detalhes das suas compras.</p></div><button class="btn light" data-page="home">+ Fazer um pedido</button></div>
    ${orders.length?orders.map(order=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(order.store.name)} · ${order.fulfillmentType==='DELIVERY'?'Entrega':'Retirada'}</h3><p>Pedido ${esc(String(order.id).slice(0,8).toUpperCase())} · ${shortDate(order.createdAt)}</p></div>${pill(order.tracking?.status||order.status)}</div>
      ${order.fulfillmentType==='DELIVERY'?`<div class="tracking-panel"><span class="tracking-mark">⌖</span><div><strong>${esc(trackingMessage(order))}</strong><small>As atualizações aparecem automaticamente nesta tela.</small></div></div>${order.tracking?.latitude!=null&&order.tracking?.longitude!=null?`<div class="tracking-map-actions"><button class="btn outline small" data-tracking-map="${esc(order.id)}">${state.trackingMapOrderId===String(order.id)?'Fechar mapa':'Ver mapa do entregador'}</button><small>O mapa externo recebe a posição compartilhada para mostrar o trajeto.</small></div>${state.trackingMapOrderId===String(order.id)?`<iframe class="tracking-map" title="Localização aproximada do entregador" loading="lazy" referrerpolicy="no-referrer" src="https://www.openstreetmap.org/export/embed.html?bbox=${Number(order.tracking.longitude)-0.006}%2C${Number(order.tracking.latitude)-0.006}%2C${Number(order.tracking.longitude)+0.006}%2C${Number(order.tracking.latitude)+0.006}&layer=mapnik&marker=${Number(order.tracking.latitude)}%2C${Number(order.tracking.longitude)}"></iframe>`:''}`:''}`:''}
      <div class="order-items">${order.lines.map(line=>`${line.quantity} × ${esc(line.name)}`).join('<br/>')}</div>${order.customerNote?`<div class="customer-note"><strong>Observação enviada à loja</strong><p>${esc(order.customerNote)}</p></div>`:''}<div class="order-bottom"><span>${money(order.totalCents)}</span><span>${order.fulfillmentType==='DELIVERY'?'⌖ '+esc(order.deliveryAddress||'Endereço informado'):'Retirada na loja'}</span></div>
      <div class="action-row">${order.fulfillmentType==='DELIVERY'&&order.deliveryAddress?`<a class="btn outline small" href="https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(order.deliveryAddress)}" target="_blank" rel="noopener noreferrer">Ver endereço no mapa</a>`:''}${order.tracking?.deliveryId&&order.tracking.status!=='OFFERED'?`<button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(order.tracking.deliveryId)}">${order.tracking.status==='ASSIGNED'?'Conversar com entregador':'Histórico do chat de entrega'}</button>`:''}${order.tracking?.latitude!=null&&order.tracking?.longitude!=null?`<a class="btn small" href="https://maps.google.com/?q=${encodeURIComponent(`${order.tracking.latitude},${order.tracking.longitude}`)}" target="_blank" rel="noopener noreferrer">Ver localização do entregador</a><small class="location-updated">Atualizado ${shortDate(order.tracking.locationUpdatedAt)}</small>`:''}${order.status!=='AWAITING_PAYMENT'?`<button class="btn outline small" data-open-chat-type="orders" data-open-chat-id="${esc(order.id)}">Mensagens com a loja</button>`:''}${order.status==='AWAITING_PAYMENT'&&order.paymentId?'<button class="btn small" data-resume-order="'+esc(order.id)+'">Continuar pagamento</button><button class="btn outline small" data-cancel-order="'+esc(order.id)+'">Cancelar pedido</button>':''}</div>${chatPanel('orders',order.id)}${order.tracking?.deliveryId?chatPanel('deliveries',order.tracking.deliveryId):''}${refundNotice(order.paymentStatus)}</article>`).join(''):emptyState('🧾','Seus pedidos vão aparecer aqui','Quando fizer seu primeiro pedido, você acompanha cada etapa nesta tela.','Encontrar uma loja','go-home')}`;
}

function trackingMessage(order) {
  const delivery={OFFERED:'A loja está procurando um entregador',ASSIGNED:'Um entregador aceitou e vai buscar seu pedido',PICKED_UP:'O pedido foi coletado na loja',IN_TRANSIT:'Seu pedido está a caminho',DELIVERED:'Pedido entregue'};
  if(order.tracking?.status)return delivery[order.tracking.status]||formatStatus(order.tracking.status);
  const status={AWAITING_PAYMENT:'Aguardando a confirmação do pagamento',AWAITING_ACCEPTANCE:'A loja recebeu seu pedido',ACCEPTED:'A loja aceitou seu pedido',PREPARING:'A loja está preparando seu pedido',READY:'Seu pedido está pronto; aguardando um entregador',COMPLETED:'Pedido concluído',REJECTED:'A loja não pôde aceitar este pedido',CANCELLED:'Pedido cancelado'};
  return status[order.status]||formatStatus(order.status);
}

function renderReservations() {
  const list=state.reservations||[];
  return `<div class="page-head"><div><span class="eyebrow">Um tempo reservado para você</span><h1>Minhas reservas</h1><p>Seus próximos momentos e atendimentos.</p></div><button class="btn light" data-page="home">Descobrir serviços</button></div>
    ${list.length?list.map(r=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(r.serviceName)}</h3><p>${esc(r.store.name)} · ${shortDate(r.startsAt)}</p></div>${pill(r.status)}</div>${r.customerNote?`<div class="customer-note"><strong>Comentário enviado à loja</strong><p>${esc(r.customerNote)}</p></div>`:''}<div class="order-bottom"><span>${money(r.amountCents)}</span><span>Reserva ${esc(String(r.id).slice(0,8).toUpperCase())}</span></div><div class="action-row">${r.status!=='PENDING_PAYMENT'?`<button class="btn outline small" data-open-chat-type="reservations" data-open-chat-id="${esc(r.id)}">Mensagens com a loja</button>`:''}${r.status==='PENDING_PAYMENT'&&r.paymentId?'<button class="btn small" data-resume-reservation="'+esc(r.id)+'">Continuar pagamento</button><button class="btn outline small" data-cancel-reservation="'+esc(r.id)+'">Cancelar reserva</button>':''}</div>${chatPanel('reservations',r.id)}${refundNotice(r.paymentStatus)}</article>`).join(''):emptyState('🗓️','Ainda não há reservas','Escolha um serviço perto de você e marque um horário que combine com a sua agenda.','Explorar serviços','go-home')}`;
}

function chatKey(type,id){return `${type}:${id}`;}
function chatMessagesHtml(thread){return thread.messages.length?thread.messages.map(m=>`<article class="chat-message ${String(m.senderId)===String(state.account?.id)?'mine':''}"><strong>${esc(m.senderName)}</strong><p>${esc(m.body)}</p><time>${shortDate(m.createdAt)}</time></article>`).join(''):'<p class="chat-empty">Ainda não há mensagens. Escreva se precisar combinar algum detalhe.</p>';}
function stopChat(){if(state.chatPollHandle)clearInterval(state.chatPollHandle);state.chatPollHandle=null;state.openChat=null;}
function resetChatState(){stopChat();state.chatThreads={};state.chatDrafts={};state.checkoutNote='';}
function applyChatUpdate(key,next){
  const previous=state.chatThreads[key];state.chatThreads[key]=next;
  const panel=document.querySelector(`[data-chat-panel="${CSS.escape(key)}"]`),box=panel?.querySelector('.chat-messages');
  if(!panel||!box||!previous||previous.open!==next.open){renderPage();return;}
  if(previous.messages.length===next.messages.length&&previous.messages.at(-1)?.id===next.messages.at(-1)?.id)return;
  const atBottom=box.scrollHeight-box.scrollTop-box.clientHeight<48;
  box.innerHTML=chatMessagesHtml(next);
  if(atBottom)box.scrollTop=box.scrollHeight;
}
function chatPanel(type,id){const key=chatKey(type,id);if(state.openChat!==key)return '';const thread=state.chatThreads[key];if(!thread)return '<section class="chat-panel"><p>Carregando conversa…</p></section>';return `<section class="chat-panel" data-chat-panel="${esc(key)}"><div class="chat-heading"><div><strong>${type==='deliveries'?'Chat com o entregador':type==='reservations'?'Conversa sobre a reserva':'Conversa sobre o pedido'}</strong><small>${thread.open?'Mensagens visíveis para as pessoas envolvidas nesta operação.':esc(thread.closedReason||'Conversa encerrada; o histórico continua disponível.')}</small></div><button class="btn outline small" data-close-chat="${esc(key)}">Fechar</button></div><div class="chat-messages">${chatMessagesHtml(thread)}</div>${thread.open?`<form class="chat-compose" data-chat-form data-chat-type="${type}" data-chat-id="${esc(id)}"><label class="sr-only" for="message-${esc(key)}">Escreva uma mensagem</label><textarea id="message-${esc(key)}" name="body" maxlength="1000" rows="2" required placeholder="Escreva sua mensagem…" data-chat-draft="${esc(key)}">${esc(state.chatDrafts[key]||'')}</textarea><button class="btn small" type="submit">Enviar</button></form>`:'<div class="chat-closed">Este chat foi encerrado conforme o andamento da operação. Você ainda pode consultar as mensagens anteriores.</div>'}</section>`;}

async function openChat(type,id){const key=chatKey(type,id);stopChat();state.openChat=key;state.chatThreads[key]=null;renderPage();try{state.chatThreads[key]=await api(`/${type}/${encodeURIComponent(id)}/messages`);renderPage();if(state.chatPollHandle)clearInterval(state.chatPollHandle);state.chatPollHandle=setInterval(()=>refreshOpenChat(key),5000);}catch(error){state.openChat=null;notify('Não foi possível abrir a conversa',error.message,'error');renderPage();}}
async function refreshOpenChat(key){if(state.openChat!==key||document.visibilityState!=='visible')return;const [type,id]=key.split(':');try{const next=await api(`/${type}/${encodeURIComponent(id)}/messages`);if(state.openChat===key)applyChatUpdate(key,next);}catch{/* a próxima atualização tenta novamente sem interromper o atendimento */}}
function closeChat(key){if(state.openChat!==key)return;stopChat();renderPage();}
async function sendChatMessage(event){event.preventDefault();const form=event.currentTarget;const type=form.dataset.chatType,id=form.dataset.chatId,body=String(new FormData(form).get('body')||'').trim();if(!body)return;const button=form.querySelector('button[type="submit"]');if(button)button.disabled=true;try{state.chatThreads[chatKey(type,id)]=await api(`/${type}/${encodeURIComponent(id)}/messages`,{method:'POST',body:JSON.stringify({body})});delete state.chatDrafts[chatKey(type,id)];renderPage();}catch(error){if(button)button.disabled=false;notify('Mensagem não enviada',error.message,'error');}}

function renderAccount() {
  const profile=state.profile||state.account,roles=profile.grantedRoles||[profile.role||'CUSTOMER'];
  const sections=[['profile','Dados pessoais'],['addresses','Endereços'],['security','Segurança'],['access','Acessos']];
  const tabs=sections.filter(([key])=>key!=='addresses'||roles.includes('CUSTOMER')).filter(([key])=>key!=='access'||profile.role!=='ADMIN');
  const selected=state.accountSection;
  const profileForm=`<form class="panel account-form" data-profile-save><h3>Dados pessoais</h3><p class="store-desc">Atualize as informações usadas nos pedidos e na sua conta.</p><div class="form-grid">
    <div class="field"><label for="profile-name">Nome completo</label><input id="profile-name" name="name" value="${esc(profile.name)}" required maxlength="120" autocomplete="name" /></div>
    <div class="field"><label for="profile-email">E-mail</label><input id="profile-email" type="email" value="${esc(profile.email)}" disabled /><small>Para alterar o e-mail, fale com o atendimento.</small></div>
    <div class="field"><label for="profile-cpf">CPF</label><input id="profile-cpf" name="cpf" value="${esc(profile.cpf||'')}" ${profile.cpf?'readonly':''} inputmode="numeric" placeholder="000.000.000-00" required /><small>${profile.cpf?'O CPF fica fixo para proteger sua conta.':'Depois de salvo, o CPF não poderá ser alterado.'}</small></div>
    <div class="field"><label for="profile-phone">Telefone</label><input id="profile-phone" name="phone" value="${esc(profile.phone||'')}" placeholder="(11) 99999-9999" required maxlength="30" autocomplete="tel" /></div>
    <div class="field"><label for="profile-cep">CEP</label><div class="cep-control"><input id="profile-cep" name="postalCode" data-cep value="${esc(profile.postalCode||'')}" placeholder="00000-000" maxlength="9" autocomplete="postal-code" inputmode="numeric" /><button class="btn outline small" type="button" data-lookup-cep>Buscar CEP</button></div></div>
    <div class="field span-2"><label for="profile-address">Rua e número</label><input id="profile-address" name="addressLine" value="${esc(profile.addressLine||'')}" placeholder="Rua das Flores, 123" maxlength="250" autocomplete="street-address" /></div>
    <div class="field"><label for="profile-complement">Complemento</label><input id="profile-complement" name="addressComplement" value="${esc(profile.addressComplement||'')}" maxlength="120" /></div><div class="field"><label for="profile-neighborhood">Bairro</label><input id="profile-neighborhood" name="neighborhood" value="${esc(profile.neighborhood||'')}" maxlength="120" /></div>
    <div class="field"><label for="profile-city">Cidade</label><input id="profile-city" name="city" value="${esc(profile.city||'')}" maxlength="100" /></div><div class="field"><label for="profile-state">Estado</label><select id="profile-state" name="state"><option value="">Selecione</option>${['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'].map(uf=>`<option value="${uf}" ${profile.state===uf?'selected':''}>${uf}</option>`).join('')}</select></div>
    </div><button class="btn" type="submit">Salvar alterações</button></form>`;
  const addressSection=`<section class="panel address-book"><div class="section-head"><div><h3>Endereços salvos</h3><p class="store-desc">Escolha um endereço principal ou cadastre outro local.</p></div><button class="btn light small" type="button" data-add-address>+ Adicionar endereço</button></div>${state.addresses.length?state.addresses.map(a=>`<article class="saved-address"><div><strong>${esc(a.label||'Meu endereço')} ${a.defaultAddress?'<i>Principal</i>':''}</strong><p>${esc(addressText(a))}</p></div><div class="action-row">${a.defaultAddress?'':`<button class="btn outline small" data-default-address="${esc(a.id)}">Tornar principal</button>`}<button class="btn danger small" data-delete-address="${esc(a.id)}">Remover</button></div></article>`).join(''):'<p class="store-desc">Nenhum endereço salvo. Adicione um para agilizar suas próximas compras.</p>'}${state.addressFormOpen?`<form class="address-form" data-address-form><div class="form-grid"><div class="field"><label>Identificação</label><input name="label" required maxlength="40" placeholder="Casa, trabalho…" value="Casa"/></div><div class="field"><label>CEP</label><div class="cep-control"><input name="postalCode" data-cep autocomplete="postal-code" inputmode="numeric" required maxlength="9" placeholder="00000-000"/><button class="btn outline small" type="button" data-lookup-cep>Buscar</button></div></div><div class="field span-2"><label>Rua e número</label><input name="addressLine" required maxlength="250" autocomplete="street-address"/></div><div class="field"><label>Complemento</label><input name="addressComplement" maxlength="120"/></div><div class="field"><label>Bairro</label><input name="neighborhood" required maxlength="120"/></div><div class="field"><label>Cidade</label><input name="city" required maxlength="100"/></div><div class="field"><label>Estado</label><input name="state" required minlength="2" maxlength="2" placeholder="SP"/></div></div><label class="check-row"><input name="defaultAddress" type="checkbox" ${state.addresses.length?'':'checked'}/> Usar como endereço principal</label><div class="action-row"><button class="btn small" type="submit">Salvar endereço</button><button class="btn outline small" type="button" data-cancel-address>Cancelar</button></div></form>`:''}</section>`;
  const security=`<section class="panel security-panel"><span class="eyebrow">Proteção da conta</span><h3>Alterar senha</h3><p class="store-desc">Use uma senha exclusiva com pelo menos 12 caracteres.</p><form data-password-change><div class="field"><label>Senha atual</label><input name="currentPassword" type="password" autocomplete="current-password" required maxlength="72" /></div><div class="field"><label>Nova senha</label><input name="newPassword" type="password" autocomplete="new-password" required minlength="12" maxlength="72" /></div><div class="field"><label>Confirme a nova senha</label><input name="confirmPassword" type="password" autocomplete="new-password" required minlength="12" maxlength="72" /></div><button class="btn" type="submit">Atualizar senha</button></form><hr class="divider"><h3>Confirmar telefone para recuperação por WhatsApp</h3><p class="store-desc">${profile.phoneVerified?'Telefone confirmado.':'Confirme o telefone cadastrado para habilitar a recuperação de senha pelo WhatsApp.'}</p>${profile.phoneVerified?'<span class="status-pill done">Telefone confirmado</span>':`<button class="btn outline small" type="button" data-phone-verify-request ${profile.phone?'':'disabled'}>Enviar código pelo WhatsApp</button>${state.phoneVerificationSent?'<form data-phone-verify-confirm class="action-row" style="margin-top:12px"><input name="code" inputmode="numeric" autocomplete="one-time-code" pattern="[0-9]{6}" maxlength="6" placeholder="Código de 6 dígitos" required/><button class="btn small" type="submit">Confirmar telefone</button></form>':''}`}<hr class="divider"><p class="store-desc">Acesso atual: <strong>${esc(roleNames[role()])}</strong> · ${esc(profile.email)}</p></section>`;
  const registered=profile.registeredRoles||roles;
  const pendingRoles=(state.platformRequests||[]).filter(r=>r.requestType==='ROLE'&&r.status==='PENDING').map(r=>r.requestedRole);
  const access=`<section class="panel"><h3>Seus acessos</h3><p class="store-desc">Todos os perfis ficam vinculados a esta conta e ao mesmo CPF. Não crie outro cadastro.</p>${roles.length>1?`<div class="action-row account-role-list">${roles.map(r=>`<button class="btn ${role()===r?'':'outline'} small" data-switch-role="${r}">${esc(roleNames[r]||r)}${role()===r?' · ativo':''}</button>`).join('')}</div>`:'<p class="store-desc">Perfil ativo: <strong>'+esc(roleNames[role()])+'</strong></p>'}${['MERCHANT','COURIER'].some(r=>!registered.includes(r)&&!pendingRoles.includes(r))?`<form data-operator-request class="action-row access-request"><select name="role" aria-label="Perfil solicitado">${['MERCHANT','COURIER'].filter(r=>!registered.includes(r)&&!pendingRoles.includes(r)).map(r=>`<option value="${r}">${esc(roleNames[r])}</option>`).join('')}</select><button class="btn small" type="submit">Solicitar novo perfil</button></form>`:''}</section>
    <section class="panel" style="margin-top:16px"><h3>Solicitar atendimento na minha cidade</h3><p class="store-desc">Peça para o FoodDelivery começar a operar em uma cidade. A administração analisará o pedido.</p><form data-city-request class="form-grid"><div class="field"><label>Cidade</label><input name="city" maxlength="100" required value="${esc(profile.city||'')}" placeholder="Ex.: Campinas"/></div><div class="field"><label>Estado</label><input name="state" minlength="2" maxlength="2" required value="${esc(profile.state||'')}" placeholder="SP"/></div><div class="field"><label>CEP de referência</label><input name="postalCode" inputmode="numeric" maxlength="9" value="${esc(profile.postalCode||'')}" placeholder="00000-000"/></div><div class="field" style="align-self:end"><button class="btn small" type="submit">Solicitar cidade</button></div></form></section>
    <section class="panel" style="margin-top:16px"><h3>Minhas solicitações</h3>${state.platformRequests?.length?state.platformRequests.map(r=>`<article class="saved-address"><div><strong>${esc(r.requestType==='ROLE'?`Perfil de ${roleNames[r.requestedRole]||r.requestedRole}`:r.requestType==='CITY'?`Cidade: ${r.city}/${r.state}`:`Loja: ${r.storeName||'Nova loja'}`)}</strong><p>${r.requestType==='STORE'?`CNPJ ${esc(r.storeCnpj||'—')} · `:''}${esc(r.reviewNote||({PENDING:'Aguardando análise da administração',APPROVED:'Solicitação aprovada',REJECTED:'Solicitação não aprovada'}[r.status]||r.status))}</p></div>${pill(r.status==='PENDING'?'PENDING_REVIEW':r.status==='APPROVED'?'CONFIRMED':'REJECTED')}</article>`).join(''):'<p class="store-desc">Suas solicitações de acesso, cidade e novas lojas ficarão listadas aqui.</p>'}</section>`;
  const content={profile:profileForm,addresses:addressSection,security,access}[selected]||profileForm;
  return `${state.mustChangePassword?'<div class="alert-strip warn"><span>!</span><span>Altere a senha temporária antes de continuar.</span></div>':''}<div class="page-head"><div><span class="eyebrow">Seu perfil</span><h1>Minha conta</h1><p>Encontre cada configuração no seu próprio espaço.</p></div></div><nav class="section-tabs account-tabs" aria-label="Seções da conta">${tabs.map(([id,label])=>`<button class="section-tab ${selected===id?'active':''}" data-account-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>`).join('')}</nav><div class="account-section-view">${content}</div><button class="btn danger account-logout" data-logout>Sair da conta</button>`;
}

async function requestOperatorAccess(event){event.preventDefault();const roleName=String(new FormData(event.currentTarget).get('role'));try{await api('/account/platform-requests/roles',{method:'POST',body:JSON.stringify({role:roleName})});state.platformRequests=await api('/account/platform-requests');notify('Solicitação enviada','O administrador analisará o novo perfil vinculado à sua conta.','info');render();}catch(error){notify('Não foi possível solicitar o acesso',error.message,'error');}}
async function requestCityAccess(event){event.preventDefault();const form=new FormData(event.currentTarget);try{await api('/account/platform-requests/cities',{method:'POST',body:JSON.stringify({city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),postalCode:String(form.get('postalCode')||'').trim()||null})});state.platformRequests=await api('/account/platform-requests');notify('Cidade solicitada','A administração recebeu o pedido e fará a análise.','info');render();}catch(error){notify('Não foi possível solicitar a cidade',error.message,'error');}}

async function saveProfile(event) {
  event.preventDefault();const form=new FormData(event.currentTarget);
  try{state.profile=await api('/account/profile',{method:'PUT',body:JSON.stringify(Object.fromEntries(['name','cpf','phone','addressLine','addressComplement','neighborhood','city','state','postalCode'].map(k=>[k,String(form.get(k)||'').trim()||null]))) });state.account={...state.account,...state.profile,activeRole:state.activeRole};localStorage.setItem('pronto_account',JSON.stringify(state.account));if(state.account.grantedRoles?.includes('CUSTOMER')&&state.profile.addressLine&&state.profile.neighborhood&&state.profile.city&&state.profile.state&&state.profile.postalCode){const current=state.addresses.find(a=>a.defaultAddress);const body={label:current?.label||'Casa',addressLine:state.profile.addressLine,addressComplement:state.profile.addressComplement,neighborhood:state.profile.neighborhood,city:state.profile.city,state:state.profile.state,postalCode:state.profile.postalCode,defaultAddress:true};if(current)await api(`/account/addresses/${current.id}`,{method:'PUT',body:JSON.stringify(body)});else await api('/account/addresses',{method:'POST',body:JSON.stringify(body)});await loadAddresses();}if(state.page==='account'&&state.account.grantedRoles?.includes('CUSTOMER'))await loadStores().catch(()=>{});notify('Perfil atualizado','Seus dados foram salvos com sucesso.');render();}
  catch(error){notify('Não conseguimos salvar seus dados',error.message,'error');}
}

async function changePassword(event){event.preventDefault();const form=new FormData(event.currentTarget);const currentPassword=String(form.get('currentPassword')),newPassword=String(form.get('newPassword'));if(newPassword!==String(form.get('confirmPassword'))){notify('As senhas não conferem','Digite a mesma senha nos dois campos.','error');return;}try{const response=await api('/account/password',{method:'POST',body:JSON.stringify({currentPassword,newPassword})});setToken(response.accessToken);state.account={...state.account,...response.account,activeRole:state.activeRole};state.profile=state.account;state.mustChangePassword=false;localStorage.setItem('pronto_account',JSON.stringify(state.account));notify('Senha alterada','Sua conta está protegida com a nova senha.');render();}catch(error){notify('Não foi possível alterar a senha',error.message,'error');}}

async function requestPhoneVerification(){try{await api('/account/phone-verification',{method:'POST'});state.phoneVerificationSent=true;notify('Código solicitado','Se o WhatsApp estiver configurado, você receberá um código no telefone cadastrado.');renderPage();}catch(error){notify('Não foi possível solicitar o código',error.message,'error');}}
async function confirmPhoneVerification(event){event.preventDefault();const code=String(new FormData(event.currentTarget).get('code')||'').trim();try{await api('/account/phone-verification/confirm',{method:'POST',body:JSON.stringify({code})});state.profile={...state.profile,phoneVerified:true};state.account={...state.account,phoneVerified:true};state.phoneVerificationSent=false;localStorage.setItem('pronto_account',JSON.stringify(state.account));notify('Telefone confirmado','A recuperação por WhatsApp está habilitada para esta conta.');renderPage();}catch(error){notify('Código não confirmado',error.message,'error');}}

async function switchRole(nextRole){if(!(state.account?.grantedRoles||[]).includes(nextRole))return;stopChat();state.activeRole=nextRole;state.account={...state.account,activeRole:nextRole};localStorage.setItem('pronto_account',JSON.stringify(state.account));state.page=nextRole==='ADMIN'?'admin':nextRole==='MERCHANT'?'merchant':nextRole==='COURIER'?'courier':'home';await loadCurrentPage();render();}

function renderNotifications() {
  const list=state.notifications;
  return `<div class="page-head"><div><span class="eyebrow">Atualizações da sua conta</span><h1>Notificações</h1><p>Pedidos, reservas e avisos vinculados somente a este perfil.</p></div>${list.length?'<button class="btn outline small" data-clear-notifications>Limpar avisos</button>':''}</div>
    ${list.length?`<div class="notification-list">${list.map(n=>`<article class="notification-item ${n.read?'read':''}"><span class="notification-symbol">${n.kind==='error'?'⚠':n.kind==='info'?'ℹ':'✓'}</span><div><strong>${esc(n.title)}</strong><p>${esc(n.message)}</p><time>${shortDate(n.date)}</time></div>${n.read?'':'<span class="notification-new">Nova</span>'}</article>`).join('')}</div>`:emptyState('♧','Tudo em dia','Quando houver novidade sobre seus pedidos ou reservas, ela aparecerá aqui.')}`;
}

function renderMerchant() {
  const store=activeMerchantStore();
  if(!store)return renderMerchantAll();
  const source=document.createElement('template');source.innerHTML=renderMerchantAll();
  const parts=[...source.content.children];
  const alert=parts[1]?.classList.contains('alert-strip')?parts[1].outerHTML:'';
  const stats=parts.find(node=>node.classList.contains('stat-grid'))?.outerHTML||'';
  const catalog=parts.find(node=>node.classList.contains('split-layout'))?.outerHTML||'';
  const settings=parts.find(node=>node.matches('form.panel[data-update-store]'))?.outerHTML||'';
  const branding=parts.find(node=>node.classList.contains('ai-branding-panel'))?.outerHTML||'';
  const payment=parts.find(node=>node.classList.contains('payment-connect-panel'))?.outerHTML||'';
  const selected=state.merchantSection;
  const tabs=[['overview','Resumo'],['catalog','Catálogo'],['settings','Dados da loja'],['branding','Aparência e pagamentos']];
  const headerNode=parts[0];
  const merchantHeadings={catalog:['Seu catálogo','Inclua e organize os produtos e serviços da sua loja.'],settings:['Dados da loja','Mantenha atualizados o endereço, a categoria e as informações que os clientes veem.'],branding:['Aparência e pagamentos','Personalize a página da loja e configure como receber.']};
  if(merchantHeadings[selected]&&headerNode){headerNode.querySelector('h1').textContent=merchantHeadings[selected][0];headerNode.querySelector('p').textContent=merchantHeadings[selected][1];}
  const header=headerNode?.outerHTML||'';
  let body=selected==='catalog'?catalog:selected==='settings'?`${settings}<section class="panel" style="margin-top:16px"><h3>Adicionar outra loja</h3><p class="store-desc">Cada loja é revisada separadamente. Use o CNPJ correspondente a este estabelecimento.</p><form data-create-store class="form-grid">${storeFields()}<div class="span-2"><button class="btn" type="submit">Enviar nova loja para aprovação</button></div></form></section>`:selected==='branding'?`${branding}${payment}`:`${alert}${stats}<section class="panel merchant-next"><h3>Próximos passos</h3><p class="store-desc">Acompanhe sua operação, mantenha o catálogo atualizado e confira os dados da loja.</p><div class="action-row"><button class="btn light" data-page="merchantOrders">Ver pedidos (${state.orders.filter(o=>!['COMPLETED','CANCELLED','REJECTED'].includes(o.status)).length})</button><button class="btn outline" data-section="catalog">Gerenciar catálogo</button></div></section>`;
  const rejectedRequest=state.platformRequests.find(r=>r.requestType==='STORE'&&String(r.storeId)===String(store.id)&&r.status==='REJECTED');
  return `${header}${rejectedRequest?`<div class="alert-strip warn"><span>!</span><span>A loja não foi aprovada. ${esc(rejectedRequest.reviewNote||'Revise os dados e envie novamente para análise.')} <button class="btn outline small" data-resubmit-store="${esc(store.id)}">Reenviar para análise</button></span></div>`:''}${state.ownStores.length>1?`<div class="panel merchant-store-picker"><label for="merchant-store-select">Loja em edição</label><select id="merchant-store-select" data-merchant-store-select>${state.ownStores.map(s=>`<option value="${esc(s.id)}" ${String(s.id)===String(store.id)?'selected':''}>${esc(s.name)} · ${esc(s.city||'')}</option>`).join('')}</select></div>`:''}<nav class="section-tabs" aria-label="Seções da loja">${tabs.map(([id,label])=>`<button class="section-tab ${selected===id?'active':''}" data-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>`).join('')}</nav><div class="section-view">${body}</div>`;
}

function activeMerchantStore(){return state.ownStores.find(s=>String(s.id)===String(state.merchantStoreId))||state.ownStores[0]||null;}
async function selectMerchantStore(event){state.merchantStoreId=event.target.value;const store=activeMerchantStore();try{state.storeItems=store?await api(`/merchant/stores/${store.id}/items`):[];state.paymentConnection=store?await api(`/merchant/stores/${store.id}/mercadopago/status`):null;state.aiGeneratedCover=null;renderPage();}catch(e){notify('Não foi possível abrir esta loja',e.message,'error');}}

function renderMerchantAll() {
  const store=activeMerchantStore();
  if(!store)return `<div class="page-head"><div><span class="eyebrow">Seu negócio no FoodDelivery</span><h1>Vamos abrir sua loja?</h1><p>Cadastre o perfil para começar a incluir produtos e serviços.</p></div></div>
    <div class="split-layout"><form class="panel" data-create-store><h3>Dados da loja</h3>${storeFields()}<button class="btn" type="submit">Cadastrar loja</button><p class="warning-small">Sua loja ficará oculta para clientes até a aprovação do administrador.</p></form><aside class="panel"><h3>Próximos passos</h3><p class="store-desc">Depois do cadastro, adicione itens ao catálogo. O administrador revisará e publicará a loja para que os clientes possam encontrá-la.</p><div class="alert-strip info"><span>✓</span><span>Você controla os pedidos, as reservas e o catálogo por este painel.</span></div></aside></div>`;
  return `<div class="page-head"><div><span class="eyebrow">Painel do vendedor</span><h1>Olá, ${esc(displayName())}.</h1><p>Cuide da sua loja e deixe o resto com a gente.</p></div>${pill(!store.active?'STORE_SUSPENDED':store.approved?'CONFIRMED':'PENDING_REVIEW')}</div>
    ${!store.active?'<div class="alert-strip warn"><span>!</span><span>A loja está suspensa pela administração. Ela não aparece para clientes; entre em contato com o suporte para solicitar uma revisão.</span></div>':!store.approved?`<div class="alert-strip warn"><span>◷</span><span>${state.account.operatorApplicationStatus==='APPROVED'?'Seu acesso de vendedor já foi aprovado. Agora a loja aguarda uma revisão própria antes de aparecer para os clientes.':'A loja aguarda a aprovação da administração antes de aparecer para os clientes.'}</span></div>`:''}
    <div class="stat-grid"><div class="stat-card"><small>Loja</small><strong>${esc(store.name)}</strong><span>${esc(store.category)}</span></div><div class="stat-card"><small>Pedidos para atender</small><strong>${state.orders.filter(o=>!['COMPLETED','CANCELLED','REJECTED'].includes(o.status)).length||'—'}</strong><span>Acompanhe na aba Pedidos</span></div><div class="stat-card"><small>Como encontrar</small><strong>${!store.active?'Suspensa':store.approved?'Publicada':'Em revisão'}</strong><span>${esc(store.address)}</span></div></div>
    <div class="split-layout"><section class="panel"><div class="section-head" style="margin-top:0"><h3>Seu catálogo</h3></div>${(state.storeItems||[]).length?`<div class="menu-list">${state.storeItems.map((item,i)=>`<div class="menu-row"><div class="menu-row-icon">${item.serviceItem?'✂️':['🥗','🍕','🥪'][i%3]}</div><div class="menu-row-copy"><strong>${esc(item.name)}</strong><small>${item.serviceItem?'Serviço':'Produto'} · ${item.stock} disponíveis</small></div><span class="menu-row-price">${money(item.priceCents)}</span></div>`).join('')}</div>`:emptyState('☕','Seu catálogo começa aqui','Inclua produtos ou serviços para que a loja possa receber pedidos.')}</section>
      <form class="panel" data-create-item><h3>Adicionar ao catálogo</h3><div class="field"><label>Nome do item</label><input name="name" required maxlength="140" placeholder="Ex.: Almoço da casa" /></div><div class="field"><label>Descrição</label><textarea name="description" maxlength="1000" placeholder="Conte um pouco sobre esta opção"></textarea></div><div class="form-grid"><div class="field"><label>Preço (R$)</label><input name="price" type="number" min="0.01" step="0.01" required placeholder="29,90" /></div><div class="field"><label>Tipo</label><select name="serviceItem"><option value="false">Produto / pedido</option><option value="true">Serviço com reserva</option></select></div><div class="field"><label>Estoque inicial</label><input name="stock" type="number" min="0" value="20" required /></div></div><button class="btn block" type="submit">Adicionar item</button></form></div>
    <div class="panel" style="margin-top:18px"><h3>Editar perfil da loja</h3><form data-update-store="${esc(store.id)}" class="form-grid"><div class="field"><label>Nome</label><input name="name" value="${esc(store.name)}" required maxlength="140" /></div><div class="field"><label>Categoria</label><select name="category" required>${storeCategoryOptions(store.category)}</select></div><div class="field span-2"><label>Endereço</label><input name="address" value="${esc(store.address)}" required maxlength="250" /></div><div class="field"><label>Cidade</label><input name="city" value="${esc(store.city||state.account?.city||'')}" required maxlength="100" /></div><div class="field"><label>Estado</label><input name="state" value="${esc(store.state||state.account?.state||'')}" required minlength="2" maxlength="2" /></div><div class="field span-2"><label>Descrição</label><textarea name="description" maxlength="1000">${esc(store.description||'')}</textarea></div><div class="field"><label>Taxa fixa de entrega (R$)</label><input name="deliveryFee" type="number" min="0" max="1000" step="0.01" value="${(store.deliveryFeeCents||0)/100}" required /><small>Este valor será somado ao checkout dos pedidos com delivery.</small></div><div class="span-2"><button class="btn light" type="submit">Salvar perfil</button></div></form></div>
    <section class="panel ai-branding-panel" style="margin-top:18px"><div><span class="eyebrow">Personalização da loja</span><h3>Crie uma capa com IA</h3><p class="store-desc">Descreva o clima e os elementos da imagem. Revise a prévia antes de aplicar à sua loja.</p></div><label class="field"><span>Como deve ser a imagem?</span><textarea id="ai-cover-prompt" maxlength="1200" placeholder="Ex.: mesa de madeira clara, pão artesanal recém-assado e luz natural da manhã">${esc(state.aiPrompt)}</textarea></label><div class="ai-cover-actions"><button class="btn" data-ai-generate ${state.aiGenerating?'disabled':''}>${state.aiGenerating?'Gerando imagem…':'✦ Gerar imagem'}</button>${state.aiGeneratedCover?'<button class="btn light" data-ai-save-cover>Aplicar à minha loja</button><button class="btn outline small" data-ai-discard-cover>Descartar prévia</button>':''}</div>${state.aiGeneratedCover?`<div class="ai-cover-preview"><img src="${esc(state.aiGeneratedCover)}" alt="Prévia gerada por IA para a capa da loja"/><p>Esta imagem ainda não foi publicada. Aplique para substituir a capa atual.</p></div>`:store.coverImageUrl?`<div class="ai-cover-preview"><img src="${esc(apiAssetUrl(store.coverImageUrl))}" alt="Capa atual da loja"/><p>Capa atual visível na página de descoberta.</p></div>`:''}<small class="store-meta">A geração requer configuração da chave de IA pela administração e pode gerar custos de uso.</small></section>
    <div class="panel payment-connect-panel" style="margin-top:18px"><div><span class="eyebrow">Receba pelos seus pedidos</span><h3>Conta Mercado Pago</h3><p class="store-desc">${state.paymentConnection?.connected?'Conta conectada. Os pagamentos são direcionados ao seu Mercado Pago.':'Conecte a conta Mercado Pago da loja para receber pagamentos e habilitar checkout.'}</p></div><button class="btn ${state.paymentConnection?.connected?'outline':'primary'}" data-connect-mercadopago="${esc(store.id)}">${state.paymentConnection?.connected?'Reconectar conta':'Conectar Mercado Pago'}</button></div>`;
}

function storeCategoryOptions(selected=''){return categoryOptions.slice(1).map(c=>`<option value="${esc(c)}" ${c===selected?'selected':''}>${esc(c)}</option>`).join('');}
function storeFields(){return `<div class="field"><label>Nome da loja</label><input name="name" required maxlength="140" placeholder="Ex.: Cantina da Praça" /></div><div class="field"><label>CNPJ</label><input name="cnpj" required inputmode="numeric" maxlength="18" placeholder="00.000.000/0000-00" /><small>Vinculado ao cadastro e não poderá ser alterado.</small></div><div class="field"><label>Categoria</label><select name="category" required>${storeCategoryOptions()}</select></div><div class="field"><label>Endereço</label><input name="address" required maxlength="250" placeholder="Rua, número e bairro" /></div><div class="field"><label>Cidade</label><input name="city" required maxlength="100" value="${esc(state.account?.city||'')}" placeholder="Sua cidade" /></div><div class="field"><label>Estado</label><input name="state" required minlength="2" maxlength="2" value="${esc(state.account?.state||'')}" placeholder="SP" /></div><div class="field"><label>Taxa fixa de entrega (R$)</label><input name="deliveryFee" type="number" min="0" max="1000" step="0.01" value="0" required /></div><div class="field"><label>Descrição</label><textarea name="description" maxlength="1000" placeholder="O que torna sua loja especial?"></textarea></div>`;}

function renderMerchantOrders(){
  const orders=state.orders||[];
  return `<div class="page-head"><div><span class="eyebrow">Sua operação</span><h1>Pedidos da loja</h1><p>Novos pedidos e andamento em um só lugar.</p></div><button class="btn light" data-action="reload-merchant-orders">↻ Atualizar</button></div>
    ${orders.length?`<div class="panel table-wrap"><table class="data-table"><thead><tr><th>Pedido</th><th>Modalidade</th><th>Itens e observação</th><th>Valor</th><th>Status</th><th>Ação</th></tr></thead><tbody>${orders.map(o=>`<tr><td><strong>${esc(String(o.id).slice(0,8).toUpperCase())}</strong><br><small>${shortDate(o.createdAt)}</small></td><td>${o.fulfillmentType==='DELIVERY'?'Entrega':'Retirada'}</td><td>${o.lines.map(i=>`${i.quantity} × ${esc(i.name)}`).join(', ')}${o.customerNote?`<div class="customer-note"><strong>Observação</strong><p>${esc(o.customerNote)}</p></div>`:''}${o.status!=='AWAITING_PAYMENT'?`<button class="btn outline small" data-open-chat-type="orders" data-open-chat-id="${esc(o.id)}">Mensagens</button>${chatPanel('orders',o.id)}`:''}</td><td>${money(o.totalCents)}</td><td>${pill(o.status)}</td><td>${merchantOrderAction(o)}</td></tr>`).join('')}</tbody></table></div>`:emptyState('🧾','Nenhum pedido por enquanto','Assim que um cliente confirmar o pagamento, o pedido aparecerá aqui.')}`;
}

function merchantOrderAction(order){
  const next={AWAITING_ACCEPTANCE:['ACCEPTED','Aceitar'],ACCEPTED:['PREPARING','Começar preparo'],PREPARING:['READY','Marcar como pronto'],READY:['COMPLETED','Concluir'],};
  if(order.status==='READY'&&order.fulfillmentType==='DELIVERY')return `<button class="btn small" data-offer-delivery="${esc(order.id)}">Enviar para entregadores</button>`;
  const action=next[order.status];const progress=action?`<button class="btn small" data-order-status="${action[0]}" data-id="${esc(order.id)}">${action[1]}</button>`:'';
  const canCancel=['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING'].includes(order.status);const cancellation=canCancel?`<button class="btn small danger" data-order-status="${order.status==='AWAITING_ACCEPTANCE'?'REJECTED':'CANCELLED'}" data-id="${esc(order.id)}">${order.status==='AWAITING_ACCEPTANCE'?'Recusar pedido':'Cancelar pedido'}</button>`:'';
  return progress||cancellation?`<div class="action-row">${progress}${cancellation}</div>`:'—';
}

function renderMerchantReservations(){
  const list=state.reservations||[];
  return `<div class="page-head"><div><span class="eyebrow">Agenda do seu negócio</span><h1>Reservas</h1><p>Organize os próximos atendimentos.</p></div><button class="btn light" data-action="reload-merchant-reservations">↻ Atualizar</button></div>
    ${list.length?list.map(r=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(r.serviceName)}</h3><p>${shortDate(r.startsAt)} · Reserva ${esc(String(r.id).slice(0,8).toUpperCase())}</p></div>${pill(r.status)}</div>${r.customerNote?`<div class="customer-note"><strong>Comentário do cliente</strong><p>${esc(r.customerNote)}</p></div>`:''}<div class="order-bottom"><span>${money(r.amountCents)}</span><span>${reservationActions(r)}</span></div><div class="action-row"><button class="btn outline small" data-open-chat-type="reservations" data-open-chat-id="${esc(r.id)}">Mensagens</button></div>${chatPanel('reservations',r.id)}</article>`).join(''):emptyState('🗓️','Sua agenda está livre','Reservas confirmadas aparecerão aqui depois do pagamento.')}`;
}

function reservationActions(r){const action=r.status==='CONFIRMED'?['IN_SERVICE','Iniciar atendimento']:r.status==='IN_SERVICE'?['COMPLETED','Concluir atendimento']:null;const progress=action?`<button class="btn small" data-reservation-status="${action[0]}" data-id="${esc(r.id)}">${action[1]}</button>`:'';const cancel=r.status==='CONFIRMED'?`<button class="btn small danger" data-reservation-status="CANCELLED" data-id="${esc(r.id)}">Cancelar e solicitar estorno</button>`:'';return progress||cancel?`<div class="action-row">${progress}${cancel}</div>`:'—';}

function renderCourier(){
  return `<div class="page-head"><div><span class="eyebrow">Área do entregador</span><h1>Suas entregas</h1><p>Escolha uma oferta e acompanhe o trajeto até a conclusão.</p></div><button class="btn light" data-action="reload-courier">↻ Atualizar</button></div>
    <div class="section-head"><h2>Ofertas disponíveis</h2><span class="store-meta">${state.offers.length} aguardando aceite</span></div>
    ${state.offers.length?`<div class="store-grid">${state.offers.map(o=>`<article class="panel"><span class="eyebrow">Nova entrega</span><h3 style="margin-top:8px">${esc(o.storeName)}</h3><p class="store-desc">Pedido ${esc(String(o.orderId).slice(0,8).toUpperCase())} · valor ${money(o.orderTotalCents)}</p><div class="alert-strip info">⌖ Endereço e dados do cliente ficam visíveis depois que você aceitar.</div><button class="btn block" data-accept-delivery="${esc(o.deliveryId)}">Aceitar entrega</button></article>`).join('')}</div>`:emptyState('🚲','Nenhuma oferta por enquanto','Fique de olho. As novas entregas aparecerão nesta tela.')}
    <div class="section-head"><h2>Em andamento</h2></div>
    ${state.deliveries.length?state.deliveries.map(d=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(d.storeName)} · ${money(d.orderTotalCents)}</h3><p>Retirar na loja e entregar em: ${esc(d.deliveryAddress||'Endereço não informado')}</p></div>${pill(d.status)}</div>${d.customerNote?`<div class="customer-note"><strong>Observação do cliente</strong><p>${esc(d.customerNote)}</p></div>`:''}<div class="order-bottom"><span>Pedido ${esc(String(d.orderId).slice(0,8).toUpperCase())}</span><span>${courierNext(d)}</span></div>${d.status==='ASSIGNED'?`<div class="action-row"><button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(d.deliveryId)}">Conversar com cliente</button></div>`:d.status==='PICKED_UP'||d.status==='IN_TRANSIT'||d.status==='DELIVERED'?`<div class="action-row"><button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(d.deliveryId)}">Ver histórico do chat</button></div>`:''}${chatPanel('deliveries',d.deliveryId)}</article>`).join(''):''}`;
}

function courierNext(d){const action={ASSIGNED:['PICKED_UP','Marcar como coletado'],PICKED_UP:['IN_TRANSIT','Iniciar trajeto'],IN_TRANSIT:['DELIVERED','Marcar como entregue']}[d.status];return `<div class="action-row">${action?`<button class="btn small" data-delivery-status="${action[0]}" data-id="${esc(d.deliveryId)}">${action[1]}</button>`:''}${d.status==='IN_TRANSIT'?`<button class="btn outline small" data-share-location="${esc(d.deliveryId)}">${state.sharingDeliveryId===String(d.deliveryId)?'Localização compartilhada':'Compartilhar localização'}</button>`:''}</div>`;}

function renderAdmin(){
  const source=document.createElement('template');source.innerHTML=renderAdminAll();
  const areaHtml=Object.fromEntries(adminAreaNames.map(name=>[name,[...source.content.querySelectorAll(`[data-admin-area="${name}"]`)].map(node=>node.outerHTML).join('')]));
  const selected=state.adminSection;
  const sections=[['overview','Visão geral'],['tickets',`Chamados${state.adminTickets.filter(t=>t.status!=='CLOSED').length?` · ${state.adminTickets.filter(t=>t.status!=='CLOSED').length}`:''}`],['applications',`Solicitações${state.adminPlatformRequests.filter(r=>r.status==='PENDING').length+state.adminApplications.length||''}`],['stores','Lojas'],['team','Equipe'],['finance','Financeiro']];
  areaHtml.stats+=`<div class="admin-shortcuts"><button class="panel shortcut-card" data-section="applications"><strong>${state.adminApplications.length+state.adminPlatformRequests.filter(r=>r.status==='PENDING').length} solicitações</strong><span>Revisar perfis, cidades e novas lojas →</span></button><button class="panel shortcut-card" data-section="stores"><strong>${state.adminStores.length} lojas · ${state.adminStores.filter(store=>store.active&&!store.approved).length} pendentes</strong><span>Ver cadastros e aprovar estabelecimentos →</span></button><button class="panel shortcut-card" data-section="tickets"><strong>${state.adminTickets.filter(t=>t.status!=='CLOSED').length} chamados</strong><span>Responder quem precisa de atendimento →</span></button></div>`;
  const headerNode=source.content.querySelector('.page-head');
  const adminHeadings={overview:['Visão geral','Acompanhe a operação e acesse rapidamente cada área administrativa.'],applications:['Solicitações de acesso','Revise cadastros de vendedores e entregadores.'],stores:['Gestão de lojas','Aprove perfis, acompanhe o funcionamento e configure comissões.'],team:['Equipe e acessos','Gerencie os operadores e crie novos acessos.'],finance:['Financeiro','Acompanhe os estornos que precisam de atenção.'],tickets:['Chamados','Responda clientes e acompanhe solicitações de suporte.']};
  const teamHeadings={profiles:['Perfis operacionais','Consulte vendedores e entregadores vinculados à plataforma.'],accounts:['Contas e segurança','Ative, suspenda ou redefina o acesso de cada pessoa.'],create:['Novo acesso','Vincule um perfil profissional à conta correta.']};
  const heading=selected==='team'?teamHeadings[state.adminTeamSection]||teamHeadings.profiles:adminHeadings[selected];
  if(heading&&headerNode){headerNode.querySelector('h1').textContent=heading[0];headerNode.querySelector('p').textContent=heading[1];}
  const header=headerNode?.outerHTML||'';
  const teamNav=selected==='team'?`<nav class="section-tabs admin-team-tabs" aria-label="Seções de equipe">${[['profiles','Perfis operacionais'],['accounts','Contas e segurança'],['create','Criar acesso']].map(([id,label])=>`<button class="section-tab ${state.adminTeamSection===id?'active':''}" data-admin-team-section="${id}" aria-current="${state.adminTeamSection===id?'page':'false'}">${label}</button>`).join('')}</nav>`:'';
  const body=selected==='team'?teamNav+renderAdminTeamArea(areaHtml,state.adminTeamSection):renderAdminAreas(areaHtml,selected);
  const errorMessage=state.adminLoadError?`<div class="alert-strip warn"><span>!</span><span>Não foi possível carregar os dados administrativos. ${esc(state.adminLoadError)}. Confirme que o FoodDelivery está iniciado e tente atualizar.</span></div>`:'';
  return `${header}${errorMessage}<nav class="section-tabs" aria-label="Áreas administrativas">${sections.map(([id,label])=>`<button class="section-tab ${selected===id?'active':''}" data-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>`).join('')}</nav><div class="section-view">${body}</div>`;
}

function renderAdminAll(){
  const pendingStores=state.adminStores.filter(store=>store.active&&!store.approved);
  const pendingRequests=state.adminPlatformRequests.filter(r=>r.status==='PENDING');
  const pendingOperators=pendingRequests.filter(r=>r.requestType==='ROLE').length+state.adminApplications.length;
  return `<div class="page-head"><div><span class="eyebrow">Administração da plataforma</span><h1>Visão geral</h1><p>Controle solicitações, vendedores, entregadores, lojas e pagamentos.</p></div><button class="btn light" data-action="reload-admin">↻ Atualizar dados</button></div>
    ${state.adminLoadError?'<div class="alert-strip warn admin-load-error"><span>!</span><span>Falha temporária ao carregar informações do servidor.</span></div>':''}
    <div class="stat-grid" data-admin-area="stats"><button class="stat-card stat-card-action" data-section="stores"><small>Lojas cadastradas</small><strong>${state.adminStores.length}</strong><span>${pendingStores.length} aguardando aprovação</span><span class="stat-action-hint">Ver lojas e analisar →</span></button><button class="stat-card stat-card-action" data-section="applications"><small>Solicitações de acesso</small><strong>${pendingOperators}</strong><span>Vendedores e entregadores</span><span class="stat-action-hint">Revisar solicitações →</span></button><button class="stat-card stat-card-action" data-section="team"><small>Contas operacionais</small><strong>${state.adminOperators.length}</strong><span>Comerciantes e entregadores</span><span class="stat-action-hint">Gerenciar contas →</span></button></div>
    <section class="panel" data-admin-area="applications"><div class="section-head"><div><h3>Solicitações da plataforma</h3><p class="store-desc">Analise novos perfis, cidades e lojas adicionais antes de liberar cada recurso.</p></div></div>
      ${pendingRequests.length?pendingRequests.map(r=>{const title=r.requestType==='ROLE'?`Novo perfil: ${roleNames[r.requestedRole]||r.requestedRole}`:r.requestType==='CITY'?`Atender em ${r.city}/${r.state}`:`Nova loja: ${r.storeName||'Estabelecimento'}`;const detail=r.requestType==='STORE'?`CNPJ ${r.storeCnpj||'—'} · `:'';return `<article class="order-card"><div class="order-card-top"><div><h3>${esc(title)}</h3><p>${esc(r.accountName)} · ${esc(r.accountEmail)}<br/>${detail}${shortDate(r.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-platform-review="${esc(r.id)}" data-decision="true">Aprovar</button><button class="btn outline small" data-platform-review="${esc(r.id)}" data-decision="false">Reprovar</button></div></article>`}).join(''):emptyState('✓','Nenhuma solicitação pendente','Novos perfis, cidades e lojas serão exibidos aqui.')}
      ${state.adminApplications.length?`<h3>Cadastros antigos</h3>${state.adminApplications.map(operator=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(operator.name)}</h3><p>${esc(operator.email)} · ${operator.role==='MERCHANT'?'Vendedor':'Entregador'}<br/>Solicitado em ${shortDate(operator.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-operator-application="${esc(operator.id)}" data-decision="approve">Aprovar acesso</button><button class="btn outline small" data-operator-application="${esc(operator.id)}" data-decision="reject">Recusar</button></div></article>`).join('')}`:''}
      </section>
    <section class="panel" data-admin-area="application-history" style="margin-top:16px"><h3>Histórico das solicitações</h3>${state.adminPlatformRequests.filter(r=>r.status!=='PENDING').length?state.adminPlatformRequests.filter(r=>r.status!=='PENDING').map(r=>`<article class="saved-address"><div><strong>${esc(r.requestType==='ROLE'?`Perfil ${roleNames[r.requestedRole]||r.requestedRole}`:r.requestType==='CITY'?`${r.city}/${r.state}`:`Loja ${r.storeName||''}`)}</strong><p>${esc(r.accountName)} · ${esc(r.accountEmail)}${r.reviewNote?' · '+esc(r.reviewNote):''}</p></div>${pill(r.status==='APPROVED'?'CONFIRMED':'REJECTED')}</article>`).join(''):'<p class="store-desc">As decisões tomadas aparecerão neste histórico.</p>'}</section>

    <section class="panel" data-admin-area="operator-profiles" style="margin-top:18px"><div class="section-head"><div><h3>Perfis operacionais</h3><p class="store-desc">Cada pessoa usa uma única conta. Consulte e altere acessos individuais na seção Contas e segurança, sem suspender por engano os outros perfis vinculados.</p></div><button class="btn outline small" data-admin-team-section="accounts">Abrir contas e segurança →</button></div>${state.adminOperators.length?`<div class="account-admin-list">${state.adminOperators.map(operator=>`<article class="saved-address"><div><strong>${esc(operator.name)}</strong><p>${esc(operator.email)} · ${operator.role==='MERCHANT'?'Vendedor':'Entregador'} · ${operator.active?'Conta ativa':'Conta suspensa'}</p></div>${pill(operator.active?'CONFIRMED':'CANCELLED')}</article>`).join('')}</div>`:emptyState('♙','Nenhuma conta operacional','Os perfis aprovados aparecerão aqui.')}</section>
    <section class="panel admin-refunds" data-admin-area="refunds" style="margin-top:18px"><div class="section-head"><div><h3>Estornos para acompanhar</h3><p class="store-desc">Casos em análise precisam de verificação no Mercado Pago.</p></div></div>
      ${state.adminRefunds.length?state.adminRefunds.map(r=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(r.storeName)} · ${r.operationType==='ORDER'?'Pedido':'Reserva'}</h3><p>${esc(String(r.operationId).slice(0,8).toUpperCase())} · Pagamento ${money(r.amountCents)}<br/>Tentativas: ${r.attempts}${r.providerPaymentId?' · Mercado Pago: '+esc(r.providerPaymentId):''}</p></div>${pill(r.status)}</div>${r.attentionMessage?`<p class="warning-small">${esc(r.attentionMessage)}</p>`:''}${r.lastAttemptAt?`<small class="store-meta">Última tentativa: ${shortDate(r.lastAttemptAt)}</small>`:''}</article>`).join(''):emptyState('✓','Nenhum estorno pendente','Solicitações pendentes ou que precisem de análise aparecerão aqui.')}</section>
    <section class="panel" data-admin-area="stores" style="margin-top:18px"><h3>Revisar estabelecimentos</h3>${state.adminStores.length?state.adminStores.map(s=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(s.name)}</h3><p>${esc(s.category)} · ${esc(s.address)}<br/>Responsável: ${esc(s.ownerName)} · ${esc(s.ownerEmail)}</p></div>${pill(adminStoreStatus(s))}</div><form class="commission-form" data-store-commission="${esc(s.id)}"><label>Comissão do marketplace (%)</label><input name="percent" type="number" min="0" max="50" step="0.01" value="${Number(s.commissionPercent||0).toFixed(2)}" required /><button class="btn outline small" type="submit">Salvar comissão</button></form><div class="action-row">${!s.approved&&s.active?`<button class="btn small" data-approve-store="${esc(s.id)}">Aprovar loja</button>`:''}${s.active?`<button class="btn outline small" data-store-active="${esc(s.id)}" data-active="false">Suspender loja</button>`:`<button class="btn small" data-store-active="${esc(s.id)}" data-active="true">Reativar loja</button>`}</div></article>`).join(''):emptyState('🏪','Nenhuma loja cadastrada','Quando vendedores enviarem seus perfis, você poderá revisá-los aqui.')}</section>
    <section class="panel" data-admin-area="accounts" style="margin-top:18px"><div class="section-head"><div><h3>Contas e segurança</h3><p class="store-desc">Ative ou suspenda perfis. Ao suspender o perfil profissional, a área de cliente continua disponível.</p></div></div>${state.adminTemporaryPassword?`<div class="alert-strip warn"><span>!</span><span>Senha temporária de ${esc(state.adminTemporaryPassword.name)}: <strong>${esc(state.adminTemporaryPassword.password)}</strong> · informe por um canal seguro. Será solicitada a troca no próximo acesso. <button type="button" class="text-button" data-dismiss-temp-password>Ocultar</button></span></div>`:''}${state.adminAccounts.length?`<div class="account-admin-list">${state.adminAccounts.map(a=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(a.name)}</h3><p>${esc(a.email)} · CPF ${esc(a.maskedCpf)}<br/>Perfis: ${esc((a.registeredRoles||[]).map(r=>roleNames[r]||r).join(', ')||roleNames[a.role])}</p></div>${pill(a.active?'CONFIRMED':'CANCELLED')}</div><details class="admin-action-menu"><summary aria-label="Mais ações para ${esc(a.name)}" title="Mais ações">⋯</summary><div class="admin-action-menu-panel">${a.role!=='ADMIN'?`<button class="btn outline small" data-account-active="${esc(a.id)}" data-active="${!a.active}">${a.active?'Suspender conta':'Reativar conta'}</button>${a.active?(a.registeredRoles||[]).map(r=>{const enabled=(a.grantedRoles||[]).includes(r);return `<button class="btn outline small" data-account-role="${esc(a.id)}" data-role="${r}" data-active="${!enabled}">${enabled?'Suspender':'Ativar'} ${esc(roleNames[r]||r)}</button>`}).join(''):''}<button class="btn light small" data-reset-password="${esc(a.id)}" data-name="${esc(a.name)}">Redefinir senha</button>`:'<span class="store-meta">Conta administrativa protegida</span>'}</div></details></article>`).join('')}</div>`:emptyState('♙','Nenhuma conta cadastrada','As contas de clientes e operadores aparecerão aqui.')}</section>
    <form class="panel" data-admin-area="create-operator" style="margin-top:18px" data-create-operator><h3>Criar acesso de vendedor ou entregador</h3><p class="store-desc">O novo perfil será vinculado à conta criada para essa pessoa.</p><div class="form-grid"><div class="field"><label>Nome</label><input name="name" required maxlength="120" /></div><div class="field"><label>E-mail</label><input name="email" type="email" required /></div><div class="field"><label>CPF</label><input name="cpf" required inputmode="numeric" placeholder="000.000.000-00" /></div><div class="field"><label>Senha inicial</label><input name="password" type="password" required minlength="12" maxlength="72" /></div><div class="field"><label>Perfil</label><select name="role"><option value="MERCHANT">Vendedor</option><option value="COURIER">Entregador</option></select></div></div><button class="btn" type="submit">Criar acesso</button></form><section class="panel admin-ticket-panel" data-admin-area="tickets" style="margin-top:18px"><h3>Chamados de atendimento</h3>${state.adminTickets.length?state.adminTickets.map(t=>`<article class="order-card"><div class="order-card-top"><div><h3>${esc(t.subject)}</h3><p>${esc(t.accountName)} · ${esc(t.accountEmail)}<br/>${esc(t.lastMessage||'Sem mensagens')}</p></div>${pill(t.status)}</div><div class="action-row"><button class="btn outline small" data-ticket-open="${esc(t.id)}" data-ticket-admin="true">Abrir chamado</button>${t.status==='OPEN'?`<button class="btn light small" data-ticket-status="${esc(t.id)}" data-status="IN_PROGRESS">Assumir</button>`:''}${t.status!=='CLOSED'?`<button class="btn outline small" data-ticket-status="${esc(t.id)}" data-status="CLOSED">Encerrar</button>`:`<button class="btn light small" data-ticket-status="${esc(t.id)}" data-status="OPEN">Reabrir</button>`}</div></article>`).join(''):emptyState('✉','Nenhum chamado aberto','Quando alguém falar com o atendimento, a conversa aparecerá aqui.')}</section>`;
}

async function createStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const fee=Number(form.get('deliveryFee')||0);const body={name:String(form.get('name')).trim(),cnpj:String(form.get('cnpj')||'').replace(/\D/g,''),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:true,deliveryFeeCents:Math.round(fee*100)};try{const created=await api('/merchant/stores',{method:'POST',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');state.merchantStoreId=created.id;state.aiGeneratedCover=null;state.platformRequests=await api('/account/platform-requests');notify('Loja enviada para revisão','O administrador analisará os dados e o CNPJ antes de publicar a loja.');render();}catch(e){notify('Não conseguimos cadastrar a loja',e.message,'error');}}
async function resubmitStore(id){try{await api(`/merchant/stores/${id}/resubmit`,{method:'POST'});state.platformRequests=await api('/account/platform-requests');notify('Loja reenviada','A administração receberá uma nova solicitação para análise.');render();}catch(e){notify('Não foi possível reenviar a loja',e.message,'error');}}


async function updateStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const store=activeMerchantStore();if(!store)return;const body={cnpj:store.cnpj,name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:store.active,deliveryFeeCents:Math.round(Number(form.get('deliveryFee')||0)*100)};try{await api(`/merchant/stores/${store.id}`,{method:'PUT',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');notify('Perfil atualizado','As informações da loja foram salvas.');render();}catch(e){notify('Não conseguimos atualizar a loja',e.message,'error');}}

async function createItem(event){event.preventDefault();const store=activeMerchantStore();if(!store)return;const form=new FormData(event.currentTarget);const price=Number(form.get('price'));if(!Number.isFinite(price)||price<=0){notify('Confira o preço','Informe um valor maior que zero.','info');return;}const serviceItem=form.get('serviceItem')==='true';const body={name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),priceCents:Math.round(price*100),stock:serviceItem?0:Number(form.get('stock')),serviceItem,active:true};try{await api(`/merchant/stores/${store.id}/items`,{method:'POST',body:JSON.stringify(body)});state.storeItems=await api(`/merchant/stores/${store.id}/items`);notify('Item adicionado','O catálogo da sua loja foi atualizado.');render();}catch(e){notify('Não conseguimos adicionar o item',e.message,'error');}}

async function createOperator(event){event.preventDefault();const element=event.currentTarget;return runOnce(`admin:create-operator:${state.account?.id}`,async()=>{const form=new FormData(element),submit=element.querySelector('[type="submit"]');if(submit){submit.disabled=true;submit.textContent='Criando acesso…';}try{await api('/admin/operators',{method:'POST',body:JSON.stringify({name:String(form.get('name')).trim(),email:String(form.get('email')).trim(),cpf:String(form.get('cpf')).replace(/\D/g,''),password:String(form.get('password')),role:String(form.get('role'))})});element.reset();notify('Acesso criado','O novo perfil foi vinculado a uma conta.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Acesso criado, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos criar o acesso',e.message,'error');}finally{if(submit?.isConnected){submit.disabled=false;submit.textContent='Criar acesso';}}});}

async function setAccountActive(id,active){try{await api(`/admin/accounts/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.adminAccounts=await api('/admin/accounts');notify(active?'Conta reativada':'Conta suspensa',active?'A pessoa pode voltar a acessar os perfis ativos.':'A conta não poderá entrar enquanto estiver suspensa.','info');renderPage();}catch(e){notify('Não foi possível alterar a conta',e.message,'error');}}
async function setAccountRole(id,roleName,active){try{await api(`/admin/accounts/${id}/roles/${roleName}`,{method:'PATCH',body:JSON.stringify({active})});state.adminAccounts=await api('/admin/accounts');state.adminOperators=await api('/admin/operators');notify(active?'Acesso ativado':'Acesso suspenso',roleName==='MERCHANT'&&!active?'A área de cliente continua disponível; a loja foi suspensa.':'As permissões da conta foram atualizadas.','info');renderPage();}catch(e){notify('Não foi possível alterar esse acesso',e.message,'error');}}
async function resetAccountPassword(id,name){if(!window.confirm(`Gerar uma senha temporária para ${name}? A pessoa deverá alterá-la no próximo acesso.`))return;try{const result=await api(`/admin/accounts/${id}/password-reset`,{method:'POST'});state.adminTemporaryPassword={name,password:result.temporaryPassword};notify('Senha temporária gerada','Compartilhe a senha diretamente com a pessoa por um canal seguro.','info');renderPage();}catch(e){notify('Não foi possível redefinir a senha',e.message,'error');}}

async function reviewPlatformRequest(id,decision){return runOnce(`admin:review-request:${id}`,async()=>{const button=document.querySelector(`[data-platform-review="${CSS.escape(String(id))}"][data-decision="${decision}"]`);if(button){button.disabled=true;button.textContent=decision==='true'?'Aprovando…':'Reprovando…';}try{await api(`/admin/platform-requests/${id}/review`,{method:'PATCH',body:JSON.stringify({approved:decision==='true'})});notify(decision==='true'?'Solicitação aprovada':'Solicitação reprovada','A decisão foi registrada e a pessoa será notificada.',decision==='true'?'success':'info');await loadAdminData();renderPage();if(state.adminLoadError)notify('Decisão registrada, atualização parcial',state.adminLoadError,'info');}catch(error){notify('Não foi possível analisar a solicitação',error.message,'error');if(button?.isConnected)button.disabled=false;}});}

async function reviewOperatorApplication(id,decision){
  const verb=decision==='approve'?'approve':'reject';
  try{await api(`/admin/operator-applications/${id}/${verb}`,{method:'PATCH'});[state.adminApplications,state.adminOperators]=await Promise.all([api('/admin/operator-applications'),api('/admin/operators')]);notify(decision==='approve'?'Acesso aprovado':'Solicitação recusada',decision==='approve'?'A conta já pode entrar na área de vendedor ou entregador.':'A conta não poderá entrar no sistema.','info');render();}
  catch(error){notify('Não foi possível revisar a solicitação',error.message,'error');}
}

async function setOperatorActive(id,active){
  try{const operator=state.adminOperators.find(account=>String(account.id)===String(id));await api(`/admin/operators/${id}/${active?'activate':'deactivate'}`,{method:'PATCH'});state.adminOperators=await api('/admin/operators');if(role()==='ADMIN'&&state.page==='admin')state.adminStores=await api('/admin/stores');notify(active?'Acesso reativado':'Acesso suspenso',active?'A conta pode entrar novamente.':operator?.role==='MERCHANT'?'A loja também foi retirada do ar e precisará de nova aprovação.':'A sessão da conta foi bloqueada.','info');render();}
  catch(error){notify('Não foi possível alterar o acesso',error.message,'error');}
}

async function generateStoreCover(){
  const store=activeMerchantStore(),prompt=($('#ai-cover-prompt')?.value||state.aiPrompt).trim();
  if(!store)return;
  if(!prompt){notify('Descreva a imagem','Inclua alguns detalhes para orientar a criação.','info');return;}
  state.aiGenerating=true;renderPage();
  try{const response=await api(`/merchant/stores/${store.id}/ai/cover`,{method:'POST',body:JSON.stringify({prompt})});state.aiGeneratedCover=response.dataUrl;notify('Prévia pronta','Revise a capa antes de aplicá-la à loja.','success');}
  catch(error){notify('Não foi possível gerar a imagem',error.message,'error');}
  finally{state.aiGenerating=false;renderPage();}
}

async function saveGeneratedStoreCover(){
  const store=activeMerchantStore();if(!store||!state.aiGeneratedCover)return;
  try{await api(`/merchant/stores/${store.id}/cover`,{method:'POST',body:JSON.stringify({dataUrl:state.aiGeneratedCover})});state.ownStores=await api('/merchant/stores/mine');state.aiGeneratedCover=null;await loadStores();notify('Capa atualizada','A imagem foi salva no perfil da sua loja.');render();}
  catch(error){notify('Não foi possível salvar a capa',error.message,'error');}
}

async function approveStore(id){return runOnce(`admin:approve-store:${id}`,async()=>{const button=document.querySelector(`[data-approve-store="${CSS.escape(String(id))}"]`);if(button){button.disabled=true;button.textContent='Aprovando…';}try{const request=state.adminPlatformRequests.find(r=>r.requestType==='STORE'&&String(r.storeId)===String(id)&&r.status==='PENDING');await api(storeApprovalPath(id,request?.id),{method:'PATCH',body:JSON.stringify({approved:true})});notify('Loja aprovada','Ela já pode aparecer para os clientes.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Loja aprovada, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos aprovar a loja',e.message,'error');if(button?.isConnected)button.disabled=false;}});}

async function setStoreActive(id,active){try{await api(`/admin/stores/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.adminStores=await api('/admin/stores');notify(active?'Loja reativada':'Loja suspensa',active?'A loja voltou a ficar disponível para revisão.':'A loja foi retirada da descoberta até nova ação administrativa.','info');render();}catch(error){notify('Não foi possível atualizar a loja',error.message,'error');}}

async function updateOrder(id,status){if(['CANCELLED','REJECTED'].includes(status)&&!window.confirm('Este pedido será encerrado. Se o pagamento foi aprovado, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))return;try{await api(`/merchant/orders/${id}/status`,{method:'PATCH',body:JSON.stringify({status})});state.orders=await api('/merchant/orders');notify('Pedido atualizado',`Novo status: ${formatStatus(status)}. ${['CANCELLED','REJECTED'].includes(status)?'Confira o acompanhamento de estorno na conta do cliente.':''}`);render();}catch(e){notify('Não conseguimos atualizar o pedido',e.message,'error');}}

async function offerDelivery(id){try{await api(`/merchant/orders/${id}/delivery`,{method:'POST'});state.orders=await api('/merchant/orders');notify('Entrega oferecida','Os entregadores disponíveis já podem aceitar esta corrida.');render();}catch(e){notify('Não conseguimos enviar para entrega',e.message,'error');}}

async function updateReservation(id,status){if(status==='CANCELLED'&&!window.confirm('A reserva será cancelada. Se já foi paga, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))return;try{await api(`/merchant/reservations/${id}`,{method:'PATCH',body:JSON.stringify({status})});state.reservations=await api('/merchant/reservations');notify('Reserva atualizada',`Novo status: ${formatStatus(status)}.${status==='CANCELLED'?' A solicitação de estorno está sendo processada.':''}`);render();}catch(e){notify('Não conseguimos atualizar a reserva',e.message,'error');}}

async function acceptDelivery(id){try{await api(`/courier/offers/${id}/accept`,{method:'POST'});state.offers=await api('/courier/offers');state.deliveries=await api('/courier/deliveries');notify('Entrega atribuída','Agora você pode consultar o endereço e atualizar o trajeto.');render();}catch(e){notify('Essa oferta já não está disponível',e.message,'info');await loadCurrentPage();render();}}

async function updateDelivery(id,status){try{await api(`/courier/deliveries/${id}`,{method:'PATCH',body:JSON.stringify({status})});if(status==='DELIVERED'&&state.sharingDeliveryId===String(id))stopCourierLocation();state.deliveries=await api('/courier/deliveries');notify('Etapa atualizada',`Entrega marcada como ${formatStatus(status).toLowerCase()}.`);render();}catch(e){notify('Não conseguimos atualizar a entrega',e.message,'error');}}

function stopCourierLocation(){if(state.locationWatchId!==null&&navigator.geolocation)navigator.geolocation.clearWatch(state.locationWatchId);state.locationWatchId=null;state.sharingDeliveryId='';state.lastLocationSentAt=0;}

function shareCourierLocation(id){
  if(!navigator.geolocation){notify('Localização indisponível','Este aparelho ou navegador não permite compartilhar a localização.','error');return;}
  if(state.sharingDeliveryId===String(id)&&state.locationWatchId!==null){notify('Localização ativa','Sua posição é compartilhada durante esta entrega.','info');return;}
  stopCourierLocation();state.sharingDeliveryId=String(id);
  state.locationWatchId=navigator.geolocation.watchPosition(async position=>{
    const now=Date.now();if(now-state.lastLocationSentAt<15000)return;state.lastLocationSentAt=now;
    try{await api(`/courier/deliveries/${encodeURIComponent(id)}/location`,{method:'PATCH',body:JSON.stringify({latitude:position.coords.latitude,longitude:position.coords.longitude})});}
    catch(error){notify('Não conseguimos atualizar a localização',error.message,'error');stopCourierLocation();}
  },error=>{stopCourierLocation();const message=error.code===1?'Permita o acesso à localização nas configurações do aparelho.':'Confira o sinal de localização e tente novamente.';notify('Não foi possível compartilhar a localização',message,'error');},{enableHighAccuracy:true,maximumAge:10000,timeout:20000});
  notify('Compartilhamento iniciado','O cliente verá sua posição somente durante esta entrega.','info');
}

async function cancelUnpaidOrder(id){if(!window.confirm('Cancelar este pedido não pago? O estoque reservado será liberado.'))return;try{await api(`/orders/${id}/cancel`,{method:'POST'});state.orders=await api('/orders/mine');notify('Pedido cancelado','O estoque foi liberado e a loja recebeu um aviso.','info');render();}catch(e){notify('Não foi possível cancelar o pedido',e.message,'error');}}
async function cancelUnpaidReservation(id){if(!window.confirm('Cancelar esta reserva não paga? O horário será liberado para outra pessoa.'))return;try{await api(`/reservations/${id}/cancel`,{method:'POST'});state.reservations=await api('/reservations/mine');notify('Reserva cancelada','O horário foi liberado e a loja recebeu um aviso.','info');render();}catch(e){notify('Não foi possível cancelar a reserva',e.message,'error');}}

async function bindPageActions(){
  document.querySelectorAll('#page-content [data-add-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=true;renderPage();}));
  document.querySelectorAll('#page-content [data-cancel-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=false;renderPage();}));
  document.querySelectorAll('#page-content [data-address-form]').forEach(form=>form.addEventListener('submit',saveCheckoutAddress));
  $('[data-clear-notifications]')?.addEventListener('click',async()=>{try{await api('/account/notifications',{method:'DELETE'});localStorage.removeItem(notificationKey(state.account.id));localStorage.removeItem(`pronto_notifications:${state.account.id}`);state.notifications=[];render();notify('Avisos limpos','Você está em dia.','info',false);}catch(e){notify('Não foi possível limpar os avisos',e.message,'error');}});
  $('[data-action="reload-merchant-orders"]')?.addEventListener('click',async()=>{try{state.orders=await api('/merchant/orders');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
  $('[data-action="reload-merchant-reservations"]')?.addEventListener('click',async()=>{try{state.reservations=await api('/merchant/reservations');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
  $('[data-action="reload-courier"]')?.addEventListener('click',async()=>{try{state.offers=await api('/courier/offers');state.deliveries=await api('/courier/deliveries');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
  $('[data-action="go-home"]')?.addEventListener('click',()=>navigate('home'));
  $('[data-action="reload-stores"]')?.addEventListener('click',async()=>{try{await loadStores();render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
  document.querySelectorAll('[data-store-commission]').forEach(form=>form.addEventListener('submit',saveCommission));
  document.querySelectorAll('[data-connect-mercadopago]').forEach(button=>button.addEventListener('click',connectMercadoPago));
  document.querySelectorAll('[data-resume-order]').forEach(button=>button.addEventListener('click',()=>resumeOrderPayment(button.dataset.resumeOrder)));
  document.querySelectorAll('[data-resume-reservation]').forEach(button=>button.addEventListener('click',()=>resumeReservationPayment(button.dataset.resumeReservation)));
  document.querySelectorAll('[data-cancel-order]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidOrder(button.dataset.cancelOrder)));
  document.querySelectorAll('[data-cancel-reservation]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidReservation(button.dataset.cancelReservation)));
  document.querySelectorAll('[data-default-address]').forEach(button=>button.addEventListener('click',()=>setDefaultAddress(button.dataset.defaultAddress)));
  document.querySelectorAll('[data-delete-address]').forEach(button=>button.addEventListener('click',()=>deleteAddress(button.dataset.deleteAddress)));
  document.querySelectorAll('[data-lookup-cep]').forEach(button=>button.addEventListener('click',()=>lookupCep(button.closest('form'))));
  document.querySelectorAll('[data-cep]').forEach(input=>input.addEventListener('blur',()=>{if(input.value.replace(/\D/g,'').length===8)lookupCep(input.closest('form'));}));
}

async function setDefaultAddress(id) {
  try { await api(`/account/addresses/${id}/default`,{method:'PATCH'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;localStorage.setItem('pronto_account',JSON.stringify(state.account));notify('Endereço principal atualizado','Este será o endereço sugerido nos próximos pedidos.');render(); }
  catch(error){notify('Não foi possível atualizar o endereço',error.message,'error');}
}

async function deleteAddress(id) {
  if(!window.confirm('Remover este endereço salvo?'))return;
  try { await api(`/account/addresses/${id}`,{method:'DELETE'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;localStorage.setItem('pronto_account',JSON.stringify(state.account));notify('Endereço removido','O endereço foi retirado da sua lista.','info');render(); }
  catch(error){notify('Não foi possível remover o endereço',error.message,'error');}
}

async function saveCommission(event){event.preventDefault();const id=event.currentTarget.dataset.storeCommission;const percent=Number(new FormData(event.currentTarget).get('percent'));if(!Number.isFinite(percent)||percent<0||percent>50){notify('Comissão inválida','Informe um percentual entre 0% e 50%.','error');return;}try{await api(`/admin/stores/${id}/commission`,{method:'PATCH',body:JSON.stringify({percent})});state.adminStores=await api('/admin/stores');notify('Comissão atualizada','A nova comissão será aplicada aos próximos pagamentos da loja.');render();}catch(e){notify('Não foi possível salvar a comissão',e.message,'error');}}

async function connectMercadoPago(event){const button=event.currentTarget;button.disabled=true;try{const result=await api(`/merchant/stores/${button.dataset.connectMercadopago}/mercadopago/connect`,{method:'POST'});window.location.assign(result.authorizationUrl);}catch(e){button.disabled=false;notify('Não foi possível conectar a conta',e.message,'error');}}

function resumeOrderPayment(id){const order=state.orders.find(item=>String(item.id)===String(id));if(!order)return;state.checkout={type:'ORDER',paymentId:order.paymentId,order};navigate('checkout');}
function resumeReservationPayment(id){const reservation=state.reservations.find(item=>String(item.id)===String(id));if(!reservation)return;state.checkout={type:'RESERVATION',paymentId:reservation.paymentId,reservation};navigate('checkout');}

function bindEvents() {
  app.addEventListener('click', event => {
    const logout=event.target.closest('[data-logout]');if(logout){event.preventDefault();showLogoutConfirmation();return;}
    const page=event.target.closest('[data-page]');if(page){event.preventDefault();navigate(page.dataset.page);}
  });
}

window.addEventListener('popstate',async()=>{const page=location.hash.slice(1);if(knownPage(page)&&navByRole[role()]?.some(([allowed])=>allowed===page)){if(state.page!==page)stopChat();state.page=page;await loadCurrentPage();render();}});

function showLogoutConfirmation(){
  $('#logout-confirm')?.remove();
  const dialog=document.createElement('div');dialog.id='logout-confirm';dialog.className='modal-backdrop confirm-backdrop';
  dialog.innerHTML='<section class="confirm-dialog" role="dialog" aria-modal="true" aria-labelledby="logout-title"><span class="confirm-mark">↗</span><h2 id="logout-title">Quer sair da sua conta?</h2><p>Você precisará entrar novamente para acessar seus pedidos e dados.</p><div class="confirm-actions"><button type="button" class="btn outline" data-cancel-logout>Continuar conectado</button><button type="button" class="btn danger" data-confirm-logout>Sair da conta</button></div></section>';
  document.body.append(dialog);
  dialog.querySelector('[data-cancel-logout]').addEventListener('click',()=>dialog.remove());
  dialog.addEventListener('click',event=>{if(event.target===dialog)dialog.remove();});
  dialog.querySelector('[data-confirm-logout]').addEventListener('click',()=>{dialog.remove();performLogout();});
}

function performLogout(){
  resetChatState();stopNotificationPolling();stopCourierLocation();setToken(null);localStorage.removeItem('pronto_account');state.account=null;state.profile=null;state.cart=[];state.addresses=[];state.selectedAddressId='';state.addressFormOpen=false;state.orders=[];state.reservations=[];state.deliveries=[];state.offers=[];state.ownStores=[];state.checkout=null;state.selectedStore=null;state.storeItems=[];state.paymentConnection=null;state.notifications=readLocalNotifications(null);state.authMode='login';state.page='home';if(location.hash)history.replaceState({page:'home'},'',location.pathname+location.search);render();notify('Você saiu da conta','Até a próxima!','info',false);
}

bindEvents();

// Rebind page-specific events after each render. The app shell remains delegated.
const originalRenderPage = renderPage;
renderPage = function(){
  const el=document.activeElement,keep=el&&el.id&&el.matches?.('#page-content textarea, #page-content input')?{id:el.id,value:el.value,start:el.selectionStart,end:el.selectionEnd}:null;
  originalRenderPage();bindPageActions();
  if(keep){const next=document.getElementById(keep.id);if(next){if(next.value!==keep.value)next.value=keep.value;next.focus({preventScroll:true});try{next.setSelectionRange(keep.start,keep.end);}catch{/* tipos sem seleção */}}}
};

boot();
