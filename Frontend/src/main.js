import { clearAccountState } from './sessionState.js';
import { createAccountActions } from './features/accountActions.js';
import { createAdminActions } from './features/adminActions.js';
import { createOrderActions } from './features/orderActions.js';
import { createSupportActions } from './features/supportActions.js';
import { createShoppingViews } from './features/shoppingViews.js';
import { createDiscoveryViews } from './views/discoveryViews.js';
import { createCustomerViews } from './views/customerViews.js';
import { createMerchantViews } from './views/merchantViews.js';
import { createCourierViews } from './views/courierViews.js';
import { createAdminViews } from './views/adminViews.js';
import { createAccountViews } from './views/accountViews.js';
import { createAuthViews } from './views/authViews.js';
import { adminLists, fetchAdminList, adminPagination } from './adminPagination.js';
import { createChatSession } from './chatSession.js';
import { createPageLoader, pageLoadFeedback } from './pageLoading.js';
import { fetchPageData, fetchAdminData, fetchStoreListings, notificationRecords } from './pageData.js';
import { renderSupportReplyFields } from './supportComposer.js';
import { bindCourierOfferActions } from './deliveryBindings.js';
import { bindCatalogActions } from './catalogBindings.js';
import {mountAvailableSlots,mountBookingPolicy,openProfessionalHours} from './bookingAvailability.js';
import {mountPrivateIntegrations,mountAdminMfa} from './adminSecurity.js';
import {groupCustomerReservations} from './reservationList.js';
import {mountCourierFinance} from './courierFinance.js';
import { api, apiAssetUrl, money, shortDate, invalidateApiSession } from './api.js';
import { postalCodeResponse } from './postalCode.js';
import { initializeAccessibility } from './accessibility.js';
import { editStoreDialog } from './adminStoreEditor.js';
import { loadLeaflet, cleanupStoreMaps, storeMapFields, mountLocationPicker, mountStoreDirectoryMap } from './storeMaps.js';
import { fulfillmentFields, fulfillmentPayload, fulfillmentBadges } from './storeOptions.js';
import { mountOperatingHours, renderPublicHours } from './storeHours.js';
import { mountStoreCover, mountCoverReview } from './storeCovers.js';
import { adminProfilePayload, applyAdminProfileResponse } from './adminProfile.js?v=20261103-admin-cpf-1';
import { adminAreaNames, adminStoreStatus, filterAdminStores, renderAdminAreas, renderAdminTeamArea, storeApprovalId, storeApprovalPath, supportDetailPath, supportMessagePath } from './adminSections.js';
import { runOnce } from './actionGuard.js';
import { formatCpfMask, formatCnpjMask, formatCurrencyMask, formatCurrencyValue, parseCurrencyDecimal } from './inputMasks.js';
import { shellMarkup } from './views/shellView.js';
import { catalogItemIcon, categoryMatches, customerOutsideStoreCity } from './catalogPolicy.js?v=20261102-category-matcher-1';
import { dateTimeInZone, localDateTimeInZoneToIso, localDateTimeMinimum, storeOpenAt, storeScheduleAllowsAppointment, storeHoursStatusRelevant } from './storeTime.js?v=20261105-multi-shifts-1';
import { coordinatesFromForm, storeMapUrl } from './storeLocation.js?v=20261101-map-coordinates-1';
document.title = 'FoodDelivery — pedidos e reservas perto de você';

const $ = (selector, root = document) => root.querySelector(selector);
const app = $('#app');
const roleNames = { CUSTOMER: 'Cliente', MERCHANT: 'Vendedor', COURIER: 'Entregador', ADMIN: 'Administrador' };
const pageNames = { home: 'Descobrir', categories: 'Categorias', categoryStores: 'Estabelecimentos por categoria', orders: 'Meus pedidos', reservations: 'Reservas', account: 'Minha conta', notifications: 'Notificações', merchant: 'Minha loja', merchantOrders: 'Pedidos da loja', merchantReservations: 'Agenda', courier: 'Entregas', finance: 'Financeiro', admin: 'Administração', checkout: 'Finalizar pedido' };
const navByRole = {
  CUSTOMER: [['home','home','Descobrir'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  MERCHANT: [['home','home','Comprar'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['merchant','store','Minha loja'],['merchantOrders','receipt','Pedidos da loja'],['merchantReservations','calendar','Agenda'],['finance','receipt','Financeiro'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  COURIER: [['home','home','Comprar'],['orders','receipt','Meus pedidos'],['reservations','calendar','Reservas'],['courier','truck','Entregas'],['finance','receipt','Financeiro'],['notifications','bell','Notificações'],['account','user','Minha conta']],
  ADMIN: [['admin','grid','Visão geral'],['notifications','bell','Notificações'],['account','shield','Definições']],
};
const categoryStyles = { Restaurantes:'orange', Restaurante:'orange', Mercados:'green', 'Conveniências':'gold', Lanchonetes:'rose', Pizzarias:'gold', Pizzaria:'gold', Cafeterias:'rose', Cafeteria:'rose', Farmácias:'green', Beleza:'purple', Barbearias:'blue', Barbearia:'blue', Serviços:'green' };
const categoryEmoji = { Restaurantes:'🍽️', Restaurante:'🍽️', Mercados:'🛒', 'Conveniências':'🧃', Lanchonetes:'🍔', Pizzarias:'🍕', Pizzaria:'🍕', Cafeterias:'☕', Cafeteria:'☕', Farmácias:'💊', Beleza:'💇', Barbearias:'💈', Barbearia:'💈', Serviços:'🧰' };
const statusNames = { AWAITING_PAYMENT:'Aguardando pagamento', AWAITING_ACCEPTANCE:'Aguardando a loja', ACCEPTED:'Pedido aceito', PREPARING:'Em preparo', READY:'Pronto', COMPLETED:'Concluído', REJECTED:'Não aceito', CANCELLED:'Cancelado', PENDING_PAYMENT:'Aguardando pagamento', PENDING_REVIEW:'Aguardando análise', STORE_SUSPENDED:'Loja suspensa', CONFIRMED:'Confirmada', IN_SERVICE:'Em atendimento', NO_SHOW:'Não compareceu', OFFERED:'Disponível', ASSIGNED:'Com você', PICKED_UP:'Coletado', IN_TRANSIT:'A caminho', DELIVERED:'Entregue', REFUND_PENDING:'Estorno em processamento', REFUNDED:'Estornado', REFUND_REVIEW:'Estorno precisa de atenção', OPEN:'Aberto', IN_PROGRESS:'Em atendimento', CLOSED:'Encerrado' };
const statusClass = s => ['COMPLETED','DELIVERED','CONFIRMED','REFUNDED','CLOSED'].includes(s) ? 'done' : ['REJECTED','CANCELLED','NO_SHOW','REFUND_REVIEW','STORE_SUSPENDED'].includes(s) ? 'cancel' : ['ACCEPTED','PREPARING','READY','IN_SERVICE','ASSIGNED','PICKED_UP','IN_TRANSIT','IN_PROGRESS'].includes(s) ? 'progress' : 'pending';
const esc = value => String(value ?? '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
function icon(name,size=18){
  const paths={home:'M3 10.5 12 3l9 7.5M5.5 9v11h13V9M9 20v-6h6v6',receipt:'M6 3h12v18l-3-2-3 2-3-2-3 2zM9 8h6M9 12h6M9 16h3',calendar:'M5 4v3m14-3v3M4 8h16M5 5h14a1 1 0 0 1 1 1v14H4V6a1 1 0 0 1 1-1zM8 12h3v3H8z',bell:'M18 9a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9M10 21h4',user:'M20 21a8 8 0 0 0-16 0M12 13a5 5 0 1 0 0-10 5 5 0 0 0 0 10',store:'M3 10h18M5 10v10h14V10M4 10l1-6h14l1 6M9 20v-6h6v6',truck:'M3 5h11v12H3zM14 9h4l3 4v4h-7M7 20a2 2 0 1 0 0-4 2 2 0 0 0 0 4M18 20a2 2 0 1 0 0-4 2 2 0 0 0 0 4',grid:'M4 4h7v7H4zM15 4h5v5h-5zM15 13h5v7h-5zM4 15h7v5H4z',pin:'M20 10c0 5-8 11-8 11S4 15 4 10a8 8 0 1 1 16 0zM12 10a2 2 0 1 0 0-4 2 2 0 0 0 0 4',shield:'M12 22s8-4 8-11V5l-8-3-8 3v6c0 7 8 11 8 11zM9 12l2 2 4-4',key:'M21 2l-2 2m-7.6 7.6a5 5 0 1 1-7.1 7.1 5 5 0 0 1 7.1-7.1zm0 0L15 8l3 3 3-3-3-3',search:'m20 20-4.5-4.5M18 10.5a7.5 7.5 0 1 1-15 0 7.5 7.5 0 0 1 15 0'};
  return `<svg aria-hidden="true" width="${size}" height="${size}" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="${paths[name]||paths.grid}"/></svg>`;
}

const savedAccount = (() => {
  try {
    const raw=JSON.parse(localStorage.getItem('pronto_account') || 'null');
    if(!raw)return null;
    const hint={activeRole:raw.activeRole};
    localStorage.setItem('pronto_account',JSON.stringify(hint));
    return hint;
  } catch { return null; }
})();
function persistAccountHint(account) {
  if (!account) { localStorage.removeItem('pronto_account'); return; }
  const hint = { activeRole: state.activeRole || account.activeRole || account.role };
  localStorage.setItem('pronto_account', JSON.stringify(hint));
}
const savedLocation = (() => { try { return JSON.parse(localStorage.getItem('fooddelivery_location') || 'null'); } catch { return null; } })();
const knownPage = page => Object.hasOwn(pageNames, page);
const notificationKey = accountId => `fooddelivery_notifications:${accountId || 'guest'}`;
const readLocalNotifications = () => [];
let notificationPollHandle = null;
const state = {
  booting: true, sessionEpoch: 0, pageLoad: { phase: 'idle' },
  account: null, activeRole: savedAccount?.activeRole || savedAccount?.role || null, mustChangePassword: false, adminTemporaryPassword: '',
  page: 'home', authMode: 'login', recoveryMode: false, recoveryStep: 'request', recoveryEmail: '', recoveryChannel: 'EMAIL', phoneVerificationSent: false, loading: false, mobileMenu: false, sidebarCollapsed: false,
  merchantSection: 'overview', merchantStoreId: '', adminSection: 'overview', adminTeamSection: 'profiles', accountSection: 'profile', adminDefinitionSection: 'cities',
  browseLocation: savedLocation, locationCities: [], adminTickets: [], supportTickets: [], activeTicket: null, supportAdminView: false,
  stores: [], filteredStores: [], category: 'Todas', search: '', selectedStore: null,
  storeItems: [], storeProfessionals: [], storeHours: null, merchantHours: null, merchantProfessionals: [], editingCatalogItem: '', cart: [], cartMode: 'PICKUP', deliveryAddress: '', cartOpen: false,
  addresses: [], selectedAddressId: '', addressFormOpen: false, adminServiceCities: [], adminIntegrations: [], adminStoreCategories: [], adminAuditEvents: [],
  orders: [], reservations: [], deliveries: [], offers: [], ownStores: [],
  checkoutNote: '', openChat: null, chatThreads: {}, chatDrafts: {},
  notifications: readLocalNotifications(savedAccount?.id),
  checkout: null, profile: null, platformRequests: [], adminPlatformRequests: [], adminStores: [], adminPages: {}, adminPageMeta: {}, adminSummary: {}, adminStoreFilter: 'ALL', adminRefunds: [], adminOperators: [], adminAccounts: [], adminApplications: [], adminLoadError: '', merchantLoadError: '', backendStatus: 'checking',
  paymentConnection: null,
  paymentStarting: false,
  locationWatchId: null, sharingDeliveryId: '', lastLocationSentAt: 0, trackingMapOrderId: '',
};
function pageContext() {
  const context = { page: state.page, role: role(), adminPages: {...state.adminPages}, adminStoreFilter: state.adminStoreFilter, ownStores: [...state.ownStores],
    merchantStoreId: state.merchantStoreId, browseLocation: state.browseLocation ? { ...state.browseLocation } : null,
    city: state.account?.city || '', state: state.account?.state || '' };
  context.key = JSON.stringify([state.sessionEpoch, state.account?.id || '', context.role, context.page,
    context.adminPages, context.adminStoreFilter, context.merchantStoreId, context.browseLocation?.city || context.city, context.browseLocation?.state || context.state]);
  return context;
}
const pageLoader = createPageLoader({
  getContext: pageContext,
  load: (context, options) => fetchPageData(context, { api, ...options }),
  commit: result => { Object.assign(state, result.data); if (Object.hasOwn(result.data, 'stores')) filterStores(); },
  onStatus: status => { state.pageLoad = status; if (status.phase === 'loading') render(); else if (status.phase !== 'idle') renderPage(); },
});
function invalidatePageLoads() { state.sessionEpoch += 1; invalidateApiSession(); pageLoader.invalidate(); }
let locationMap = null;
let locationMarker = null;
let locationSelected = null;
let lastGeocodeRequestAt = 0;
const locationGeocodeCache = new Map();

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
    const signature=`${kind}:${title}:${safeMessage}`;
    if([...region.querySelectorAll('.toast')].some(item=>item.dataset.signature===signature))return;
    const toast = document.createElement('div');
    toast.dataset.signature=signature;
    toast.className = `toast ${kind}`;
    toast.setAttribute('role',kind==='error'?'alert':'status');
    toast.setAttribute('aria-live',kind==='error'?'assertive':'polite');
    toast.innerHTML = `<span class="toast-icon" aria-hidden="true">${kind === 'error' ? '⚠' : kind === 'info' ? 'ℹ' : '✓'}</span><div class="toast-copy"><strong>${esc(title)}</strong><p>${esc(safeMessage)}</p></div><button class="toast-dismiss" type="button" aria-label="Dispensar aviso">×</button>`;
    toast.querySelector('.toast-dismiss').addEventListener('click',()=>toast.remove());
    region.append(toast);
    while(region.querySelectorAll('.toast').length>3)region.querySelector('.toast')?.remove();
    setTimeout(()=>toast.classList.add('toast-leaving'),4400);
    setTimeout(() => toast.remove(), 4700);
  }
}

function clearSession() {
  invalidatePageLoads();
  resetChatState();
  stopNotificationPolling();
  stopCourierLocation();
  clearAccountState(state);
  persistAccountHint(null);
  document.querySelectorAll('.modal-backdrop').forEach(dialog => dialog.remove());
  if (location.hash) history.replaceState({page:'home'}, '', location.pathname + location.search);
}

window.addEventListener('session-expired', () => {
  clearSession();
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
const refundNotice = status => status==='REFUND_PENDING'?'<div class="alert-strip info">O Mercado Pago está processando o estorno. O prazo para o valor aparecer depende do meio de pagamento.</div>':status==='REFUND_REVIEW'?'<div class="alert-strip warn">O estorno está em análise pela equipe. Se precisar, fale com o atendimento informando o número do pedido.</div>':status==='REFUNDED'?`<div class="alert-strip info">${globalThis.FOODDELIVERY_DEMO?'Estorno simulado confirmado. Nenhum dinheiro foi movimentado.':'Estorno confirmado pelo Mercado Pago.'}</div>`:'';
const categoryOptions = ['Todas','Restaurantes','Mercados','Conveniências','Lanchonetes','Pizzarias','Cafeterias','Farmácias','Beleza','Barbearias','Serviços'];
function applyPublicCategories(categories){if(!Array.isArray(categories)||!categories.length)return;categoryOptions.splice(1,categoryOptions.length-1,...categories.map(category=>category.name));categories.forEach(category=>{categoryEmoji[category.name]=category.emoji;});}

async function boot() {
  try {
  if(globalThis.FOODDELIVERY_DEMO){const notice=document.createElement('aside');notice.className='demo-environment-banner';notice.innerHTML='HOMOLOGAÇÃO · Cadastros fictícios · Sem dinheiro ou mensagens reais <a href="/demo.html" target="_blank" rel="noopener">Abrir caixa de mensagens de teste ↗</a>';document.body.classList.add('demo-mode');document.body.prepend(notice);new ResizeObserver(()=>document.body.style.setProperty('--demo-banner-height',`${notice.offsetHeight}px`)).observe(notice);}
  const initialPage=location.hash.slice(1);
  if(knownPage(initialPage)) state.page=initialPage;
  render();
  try { await api('/status'); state.backendStatus = 'online'; } catch { state.backendStatus = 'offline'; }
  try { applyPublicCategories(await api('/store-categories')); } catch { /* categorias locais mantêm a navegação disponível durante indisponibilidade da API */ }
  const preferredRole=state.activeRole;
  try {
    state.profile=await api('/account/me');
    state.account=state.profile;
    state.mustChangePassword=!!state.account.passwordChangeRequired;
    state.activeRole=state.account.grantedRoles?.includes(preferredRole)?preferredRole:state.account.role;
    persistAccountHint(state.account);
    const mpResult=new URLSearchParams(location.search).get('mp');
    if(mpResult==='connected'||mpResult==='error'){notify(mpResult==='connected'?'Conta Mercado Pago conectada':'Não foi possível conectar o Mercado Pago',mpResult==='connected'?'Esta loja já pode receber pagamentos pelo checkout.':'A autorização foi cancelada ou expirou. Tente conectar novamente.',mpResult==='connected'?'success':'error');const url=new URL(location.href);url.searchParams.delete('mp');history.replaceState({page:'merchant'},'',url.pathname+url.search+'#merchant');}
  } catch { state.account=null;state.profile=null;state.mustChangePassword=false;persistAccountHint(null); }
  if (state.account) { const params=new URLSearchParams(location.search),paymentReturn=params.get('mp'),paymentId=params.get('app_payment_id'),providerPaymentId=params.get('collection_id')||params.get('payment_id');if(paymentId&&['success','pending','failure'].includes(paymentReturn))await handlePaymentReturn(paymentId,paymentReturn,providerPaymentId);if(state.mustChangePassword)state.page='account';else if(!navByRole[role()].some(([page])=>page===state.page)&&!(['categories','categoryStores'].includes(state.page)&&canShop()))state.page=role()==='CUSTOMER'?'home':role()==='MERCHANT'?'merchant':role()==='COURIER'?'courier':'admin';state.notifications = readLocalNotifications(state.account.id); await loadNotifications().catch(()=>{}); startNotificationPolling(); if(state.account.grantedRoles?.includes('CUSTOMER'))await loadAddresses(); await loadCurrentPage(); }
  } catch {
    state.account = null; state.profile = null; persistAccountHint(null);
    notify('Não foi possível recuperar sua sessão', 'Entre novamente para continuar.', 'info');
  } finally {
    state.booting = false;
    render();
  }
}

async function loadCurrentPage() { return pageLoader.run(); }

async function loadAdminData() {
  const context = pageContext();
  try {
    const data = await fetchAdminData(path => api(path), context.adminPages, context.adminStoreFilter);
    if (pageContext().key !== context.key) return false;
    Object.assign(state, data);
    return true;
  } catch {
    if (pageContext().key === context.key) state.adminLoadError = 'Não foi possível atualizar a administração. Confira sua conexão e tente novamente.';
    return false;
  }
}

async function loadNotifications() {
  const accountId = state.account?.id, epoch = state.sessionEpoch;
  const remote = await api('/account/notifications');
  if (state.account?.id !== accountId || state.sessionEpoch !== epoch) return;
  state.notifications = notificationRecords(remote);
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
  const accountId = state.account.id, epoch = state.sessionEpoch;
  try {
    const remote = await api('/account/notifications');
    if (!state.account || String(state.account.id) !== String(accountId) || state.sessionEpoch !== epoch) return;
    const existing = new Set(state.notifications.filter(n => n.localOnly === false).map(n => String(n.id)));
    const fresh = remote.filter(n => !existing.has(String(n.id)));
    state.notifications = [...readLocalNotifications(state.account.id), ...remote.map(n => ({...n, date:n.createdAt, read:!!n.readAt, localOnly:false}))]
      .sort((a,b)=>new Date(b.date)-new Date(a.date)).slice(0,100);
    fresh.slice(0,3).reverse().forEach(n => notify(n.title, n.message, n.kind || 'info', false));
    if (state.page === 'notifications' && fresh.length) renderPage();
  } catch { /* a próxima rodada tenta novamente sem interromper a tela */ }
  if (state.page === 'orders' && state.pageLoad.phase !== 'loading') {
    const key = pageContext().key, revision = pageLoader.revision;
    try {
      const orders = await api('/orders/mine');
      if (pageContext().key !== key || pageLoader.revision !== revision) return;
      state.orders = orders;
      if (!isTypingInPage()) renderPage();
    } catch { /* o próximo ciclo atualiza o acompanhamento */ }
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
  const context = pageContext();
  const stores = await fetchStoreListings(context, path => api(path));
  if (pageContext().key !== context.key) return false;
  state.stores = stores;
  filterStores();
  return true;
}

function filterStores() {
  const query = state.search.trim().toLocaleLowerCase('pt-BR');
  state.filteredStores = state.stores.filter(store => {
    const categoryMatch = state.category === 'Todas' || categoryMatches(store.category, state.category);
    const searchMatch = !query || `${store.name} ${store.description || ''} ${store.category || ''} ${store.city||''} ${store.state||''}`.toLocaleLowerCase('pt-BR').includes(query);
    return categoryMatch && searchMatch;
  });
}

function isCustomerOutsideStoreCity(store){return customerOutsideStoreCity(state.profile||state.account,store);}

function render() {
  if (state.booting) { app.innerHTML = '<div class="boot-screen" role="status" aria-live="polite"><div class="brand-mark" aria-hidden="true">FD</div><p>Preparando seu acesso…</p></div>'; return; }
  $('#cart-backdrop')?.remove();
  $('#store-modal')?.remove();
  if (!state.account) { renderAuth(); return; }
  renderShell();
  renderPage();
  if (state.cartOpen) renderCartDrawer();
  if (state.selectedStore) renderStoreModal();
}

function renderAuth(...args){return authViews.renderAuth(...args);}

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
    const response = await api('/auth/login', { method:'POST', body:JSON.stringify({ email, password,code:$('#auth-mfa-code')?.value.trim()||null }) });
    clearSession();
    state.account = response.account;
    persistAccountHint(state.account);
    state.activeRole=state.account.role;
    state.mustChangePassword=!!state.account.passwordChangeRequired;
    if(!state.mustChangePassword){state.profile = await api('/account/me');state.account = state.profile;persistAccountHint(state.account);}
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
  app.innerHTML = shellMarkup({
    state, links, role: role(), roleLabel: roleNames[role()], canShop: canShop(),
    compact: window.matchMedia('(max-width: 820px)').matches, esc, icon, initials,
  });
  $('#mobile-menu')?.addEventListener('click',()=>{
    if(window.matchMedia('(max-width: 820px)').matches){state.mobileMenu=!state.mobileMenu;render();}
    else {state.sidebarCollapsed=!state.sidebarCollapsed;$('#mobile-menu').setAttribute('aria-expanded',String(!state.sidebarCollapsed));$('#mobile-menu').setAttribute('aria-label',state.sidebarCollapsed?'Expandir menu':'Recolher menu');app.querySelector('.app-shell')?.classList.toggle('sidebar-collapsed',state.sidebarCollapsed);}
  });
  $('#nav-backdrop')?.addEventListener('click',()=>{state.mobileMenu=false;render();});
  $('[data-mobile-more]')?.addEventListener('click',()=>{state.mobileMenu=!state.mobileMenu;render();});
  $('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
  $('#global-search')?.addEventListener('input',event=>{state.search=event.target.value;if(canShop()&&state.page==='home'){filterStores();renderPage();}});
  $('[data-location-picker]')?.addEventListener('click',openLocationPicker);
  $('[data-help]')?.addEventListener('click',openSupportCenter);
}

async function navigate(page) {
  if(state.mustChangePassword&&page!=='account')return;
  const categoryPage=['categories','categoryStores'].includes(page)&&canShop();
  if(!navByRole[role()]?.some(([allowed])=>allowed===page) && page!=='checkout' && !categoryPage)return;
  if(state.page!==page)stopChat();
  state.page=page;state.mobileMenu=false;state.cartOpen=false;state.selectedStore=null;
  if(location.hash!==`#${page}`)history.pushState({page},'',`#${page}`);
  const applied = await loadCurrentPage();
  if (!applied || state.page !== page || !state.account) return;
  const contextKey = pageContext().key;
  if(page==='notifications'){
    const unread=state.notifications.filter(n=>!n.read&&!n.localOnly);
    await Promise.all(unread.map(n=>api(`/account/notifications/${n.id}/read`,{method:'PATCH'}).catch(()=>{})));
    if (pageContext().key !== contextKey) return;
    state.notifications=state.notifications.map(n=>({...n,read:true}));
    localStorage.setItem(notificationKey(state.account.id),JSON.stringify(state.notifications.filter(n=>n.localOnly!==false).slice(0,40)));
  }
  render();
  window.scrollTo({top:0,behavior:'instant'});
}

function renderPage() {
  const content=$('#page-content');if(!content)return;
  const status = state.pageLoad.key === pageContext().key ? state.pageLoad : { phase: 'idle' };
  content.setAttribute('aria-busy', String(status.phase === 'loading'));
  const feedback = pageLoadFeedback(status, state.page === 'account' && role() === 'ADMIN' ? 'Definições' : pageNames[state.page] || 'FoodDelivery');
  if (['loading', 'error'].includes(status.phase)) {
    content.innerHTML = feedback;
    content.querySelector('[data-retry-page]')?.addEventListener('click', () => loadCurrentPage());
    return;
  }
  const pages={home:renderHome,categories:renderAllCategories,categoryStores:renderCategoryStores,orders:renderOrders,reservations:renderReservations,account:renderAccount,notifications:renderNotifications,merchant:renderMerchant,merchantOrders:renderMerchantOrders,merchantReservations:renderMerchantReservations,courier:renderCourier,finance:renderFinance,admin:renderAdmin,checkout:renderCheckout};
  const fn=pages[state.page]||renderHome;
  content.innerHTML=feedback+fn();
  bindPage();
}

async function openLocationPicker(){
  $('#location-modal')?.remove();
  const modal=document.createElement('div');modal.id='location-modal';modal.className='modal-backdrop';
  modal.innerHTML=`<section class="modal location-modal" role="dialog" aria-modal="true" aria-labelledby="location-title"><div class="modal-head"><div><span class="eyebrow">Sua região</span><h2 id="location-title">Onde você quer pedir?</h2><p>Busque uma cidade, use sua localização ou escolha um ponto no mapa.</p></div><button class="close-button" data-close-location aria-label="Fechar">×</button></div><div class="location-search-row"><form data-location-search class="location-search-form"><label class="field"><span>Nome da cidade</span><div class="location-search-control"><input name="query" autocomplete="address-level2" placeholder="Ex.: Iepê, SP" required/><button class="btn small" type="submit">Buscar cidade</button></div></label></form><button type="button" class="btn outline location-gps" data-location-gps>${icon('pin',17)} Usar minha localização</button></div><p class="location-privacy-note">O GPS só é consultado quando você toca no botão. Usamos a posição para identificar cidade e CEP; não salvamos suas coordenadas precisas.</p><div class="location-results" data-location-results aria-live="polite"></div><div class="location-picker-grid"><section class="location-map-column"><div class="location-map-heading"><div><h3>Escolha no mapa</h3><p class="store-desc">Toque no mapa para identificar a cidade daquele ponto.</p></div><span class="map-badge">Mapa interativo</span></div><div class="location-map-preview" data-location-map><div class="location-map-loading">Carregando mapa…</div></div><small class="map-attribution">© OpenStreetMap contributors</small><a class="map-issue-link" href="https://www.openstreetmap.org/fixthemap" target="_blank" rel="noopener noreferrer">Informar problema no mapa</a></section><aside class="location-side-column"><section class="location-cep-panel"><h3>Buscar por CEP</h3><form data-location-cep><label class="field"><span>CEP</span><div class="cep-control"><input name="postalCode" inputmode="numeric" autocomplete="postal-code" maxlength="9" placeholder="00000-000" required/><button class="btn small" type="submit">Buscar CEP</button></div></label></form><p class="store-desc" data-cep-feedback>Um CEP identifica uma área/endereço; a cidade pode ter vários CEPs.</p></section><section class="location-city-panel"><div class="location-city-heading"><h3>Cidades disponíveis</h3><span data-city-count></span></div><div class="location-city-list" data-city-list><p class="store-desc">Carregando cidades…</p></div></section></aside></div><div class="location-selection" data-location-selection><span class="location-selection-icon">${icon('pin',18)}</span><span data-location-selection-label>Selecione uma cidade ou ponto no mapa.</span></div><div class="location-modal-foot"><button class="btn outline small" data-location-clear>Remover filtro de cidade</button><button class="btn" data-location-confirm disabled>Usar esta região</button></div></section>`;
  document.body.append(modal);
  locationSelected=null;locationMap=null;locationMarker=null;
  modal.querySelector('[data-close-location]').onclick=()=>{modal.remove();locationMap=null;};modal.onclick=e=>{if(e.target===modal){modal.remove();locationMap=null;}};
  modal.querySelector('[data-location-clear]').onclick=async()=>{state.browseLocation=null;localStorage.removeItem('fooddelivery_location');modal.remove();locationMap=null;if(canShop()&&['home','categories','categoryStores'].includes(state.page))await loadStores().catch(()=>{});render();};
  modal.querySelector('[data-location-confirm]').onclick=()=>selectBrowseLocation(locationSelected,modal);
  modal.querySelector('[data-location-search]').onsubmit=async e=>{e.preventDefault();const query=String(new FormData(e.currentTarget).get('query')||'').trim();if(query.length<2)return;const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Buscando cidade…</p>';try{const places=await searchBrazilPlaces(query);renderLocationResults(modal,places);}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível buscar essa cidade agora.')}</p>`;}};
  modal.querySelector('[data-location-gps]').onclick=()=>detectCurrentCity(modal);
  modal.querySelector('[data-location-cep]').onsubmit=async e=>{e.preventDefault();const input=e.currentTarget.elements.postalCode,feedback=modal.querySelector('[data-cep-feedback]'),cep=input.value.replace(/\D/g,'');if(cep.length!==8){feedback.textContent='Digite os oito números do CEP.';return;}feedback.textContent='Consultando CEP…';let viaCepUnavailable=false,place=null;try{const response=await postalCodeResponse(cep,{signal:AbortSignal.timeout(8000)});if(!response.ok)throw new Error('service');const data=await response.json();if(data.erro)throw new Error('not-found');place={city:data.localidade,state:data.uf,postalCode:String(data.cep||cep).replace(/\D/g,''),address:[data.logradouro,data.bairro].filter(Boolean).join(', ')};try{const places=await searchBrazilPlaces(`${data.localidade}, ${data.uf}`);if(places[0])place={...places[0],...place};}catch{}}catch(error){viaCepUnavailable=error.message!=='not-found';try{const places=await searchBrazilPlaces(cep);place=places.find(item=>String(item.postalCode||'').replace(/\D/g,'')===cep)||null;}catch{if(viaCepUnavailable){feedback.textContent='A consulta de CEP está temporariamente indisponível. Tente pelo nome da cidade ou pelo mapa.';return;}}}if(!place){feedback.textContent=viaCepUnavailable?'Não foi possível validar este CEP agora. Tente pelo nome da cidade ou pelo mapa.':'Não encontramos esse CEP. Confira os números ou escolha uma cidade no mapa.';return;}await chooseLocationPoint(modal,place);feedback.textContent=`CEP encontrado: ${cep.slice(0,5)}-${cep.slice(5)} · ${place.city}, ${place.state}. Confirme a região para aplicar.`;};
  try{state.locationCities=await api('/store-locations');}catch{state.locationCities=[];}
  const cities=[...state.locationCities];if(state.account?.city&&state.account?.state&&!cities.some(c=>c.city.toLowerCase()===state.account.city.toLowerCase()&&c.state===state.account.state))cities.push({city:state.account.city,state:state.account.state});
  const list=modal.querySelector('[data-city-list]');modal.querySelector('[data-city-count]').textContent=String(cities.length);list.innerHTML=cities.length?cities.map(c=>`<button type="button" class="location-city-option" data-city="${esc(c.city)}" data-uf="${esc(c.state)}"><span class="location-option-pin">${icon('pin',17)}</span><span><strong>${esc(c.city)}</strong><small>${esc(c.state)}</small></span><b>›</b></button>`).join(''):'<p class="store-desc">Ainda não há cidades cadastradas. Busque qualquer cidade ou use o mapa.</p>';
  list.querySelectorAll('[data-city]').forEach(b=>b.onclick=()=>selectDirectoryCity(modal,{city:b.dataset.city,state:b.dataset.uf}));
  try{await initializeLocationMap(modal);const active=state.browseLocation||{city:state.account?.city,state:state.account?.state};if(active?.city){const match=cities.find(c=>c.city.toLowerCase()===active.city.toLowerCase()&&c.state===active.state);if(match)selectDirectoryCity(modal,match);}}catch{modal.querySelector('[data-location-map]').innerHTML='<div class="location-map-error">O mapa não carregou. Você ainda pode buscar a cidade, usar o CEP ou selecionar uma cidade cadastrada.</div>';}
}
async function initializeLocationMap(modal){const L=await loadLeaflet(),box=modal.querySelector('[data-location-map]');if(!box?.isConnected)return;box.innerHTML='<div id="location-leaflet-map" aria-label="Mapa interativo; clique para selecionar uma cidade"></div>';locationMap=L.map('location-leaflet-map',{scrollWheelZoom:false}).setView([-14.235,-51.925],4);L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png',{maxZoom:19,attribution:'&copy; <a href="https://www.openstreetmap.org/copyright" target="_blank" rel="noopener noreferrer">OpenStreetMap contributors</a>'}).addTo(locationMap);locationMap.on('click',async event=>{const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Identificando o local…</p>';try{const place=await reverseBrazilPlace(event.latlng.lat,event.latlng.lng);await chooseLocationPoint(modal,{...place,latitude:event.latlng.lat,longitude:event.latlng.lng});result.innerHTML='';}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar a cidade deste ponto.')}</p>`;}});setTimeout(()=>locationMap?.invalidateSize(),80);}
async function waitForGeocodeSlot(){const wait=Math.max(0,1100-(Date.now()-lastGeocodeRequestAt));if(wait)await new Promise(resolve=>setTimeout(resolve,wait));lastGeocodeRequestAt=Date.now();}
async function geocodeJson(path){const cacheKey=path;if(locationGeocodeCache.has(cacheKey))return locationGeocodeCache.get(cacheKey);await waitForGeocodeSlot();const data=await api(path);locationGeocodeCache.set(cacheKey,data);if(locationGeocodeCache.size>50)locationGeocodeCache.delete(locationGeocodeCache.keys().next().value);return data;}
async function searchBrazilPlaces(query){const records=await geocodeJson(`/geography/search?query=${encodeURIComponent(query)}`),places=records.map(record=>placeFromGeocode(record)).filter(place=>place.city&&place.state),unique=new Map();for(const place of places){const key=`${place.city.normalize('NFD').replace(/[\u0300-\u036f]/g,'').toLocaleLowerCase('pt-BR')}|${place.state}|${place.postalCode}`;if(!unique.has(key))unique.set(key,place);}return [...unique.values()];}
async function selectDirectoryCity(modal,city){try{const places=await searchBrazilPlaces(`${city.city}, ${city.state}`);await chooseLocationPoint(modal,{...(places[0]||{}),...city});}catch{await chooseLocationPoint(modal,city);}}
function placeFromGeocode(record){const a=record.address||{},stateName=a['ISO3166-2-lvl4']||'',uf=/^BR-([A-Z]{2})$/.exec(stateName)?.[1]||a.state_code||'';return {city:a.city||a.town||a.village||a.municipality||a.county||'',state:String(uf).toUpperCase(),postalCode:a.postcode||'',address:[a.road,a.house_number,a.suburb].filter(Boolean).join(', '),latitude:Number(record.lat),longitude:Number(record.lon),label:record.display_name||''};}
async function reverseBrazilPlace(latitude,longitude){const record=await geocodeJson(`/geography/reverse?latitude=${encodeURIComponent(latitude)}&longitude=${encodeURIComponent(longitude)}`),place=placeFromGeocode(record);if(!place.city||!place.state)throw new Error('Não encontramos uma cidade nesse ponto. Aproxime o mapa de uma área urbana.');return place;}
async function chooseLocationPoint(modal,place){if(!place?.city||!place?.state)return;locationSelected={city:String(place.city).trim(),state:String(place.state).trim().toUpperCase(),postalCode:String(place.postalCode||'').trim(),latitude:Number.isFinite(Number(place.latitude))?Number(place.latitude):null,longitude:Number.isFinite(Number(place.longitude))?Number(place.longitude):null};const result=modal.querySelector('[data-location-results]');if(result&&place.label)result.innerHTML=`<p class="location-result-context">Local selecionado: ${esc(place.label)}</p>`;if(locationMap&&locationSelected.latitude!==null&&locationSelected.longitude!==null){const point=[locationSelected.latitude,locationSelected.longitude];locationMap.setView(point,Math.max(locationMap.getZoom(),11));if(locationMarker)locationMarker.setLatLng(point);else locationMarker=window.L.marker(point).addTo(locationMap);locationMarker.bindPopup(`${esc(locationSelected.city)}, ${esc(locationSelected.state)}`).openPopup();}const cep=modal.querySelector('[data-location-cep] [name="postalCode"]');if(cep&&locationSelected.postalCode)cep.value=locationSelected.postalCode;const label=modal.querySelector('[data-location-selection-label]');if(label)label.innerHTML=`<strong>${esc(locationSelected.city)}, ${esc(locationSelected.state)}</strong><small>${locationSelected.postalCode?`CEP ${esc(locationSelected.postalCode)} · `:''}confirme para ver os estabelecimentos desta cidade</small>`;modal.querySelector('[data-location-confirm]').disabled=false;}
function renderLocationResults(modal,places){const box=modal.querySelector('[data-location-results]');if(!places.length){box.innerHTML='<p class="location-error">Nenhuma cidade brasileira encontrada. Confira o nome e a UF.</p>';return;}box.innerHTML=`<div class="location-result-list">${places.map((place,index)=>`<button type="button" class="location-result-option" data-location-result="${index}"><span>${icon('pin',17)}</span><strong>${esc(place.city)}, ${esc(place.state)}</strong><small>${place.postalCode?`CEP ${esc(place.postalCode)}`:'CEP não informado pelo mapa'}</small></button>`).join('')}</div>`;box.querySelectorAll('[data-location-result]').forEach(button=>button.onclick=()=>chooseLocationPoint(modal,places[Number(button.dataset.locationResult)]));}
async function detectCurrentCity(modal){const button=modal.querySelector('[data-location-gps]'),result=modal.querySelector('[data-location-results]');if(!navigator.geolocation){result.innerHTML='<p class="location-error">Este navegador não oferece localização. Busque a cidade ou informe o CEP.</p>';return;}button.disabled=true;button.innerHTML=`${icon('pin',17)} Identificando…`;result.innerHTML='<p class="store-desc">Aguardando a permissão de localização do navegador…</p>';navigator.geolocation.getCurrentPosition(async position=>{try{const {latitude,longitude}=position.coords,place=await reverseBrazilPlace(latitude,longitude);await chooseLocationPoint(modal,{...place,latitude,longitude});}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar sua cidade. Use a busca ou o CEP.')}</p>`;}finally{button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`; }},error=>{const message=error.code===1?'Permita a localização no navegador ou escolha sua cidade manualmente.':error.code===3?'A localização demorou demais. Tente novamente ou escolha sua cidade.':'Não foi possível acessar sua localização. Busque a cidade ou o CEP.';result.innerHTML=`<p class="location-error">${message}</p>`;button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`;},{enableHighAccuracy:false,timeout:12000,maximumAge:300000});}
async function selectBrowseLocation(location,modal){if(!location?.city||!location?.state)return;state.browseLocation={city:location.city,state:location.state,postalCode:location.postalCode||''};localStorage.setItem('fooddelivery_location',JSON.stringify(state.browseLocation));notify('Região atualizada',`Agora você está vendo lojas de ${location.city}, ${location.state}.`,'success');modal.remove();locationMap=null;locationMarker=null;if(canShop()&&['home','categories','categoryStores'].includes(state.page))await loadStores().catch(()=>{});render();}

function openSupportCenter(...args){return supportActions.openSupportCenter(...args);}
function loadSupportTicketList(...args){return supportActions.loadSupportTicketList(...args);}
function renderSupportModal(...args){return supportActions.renderSupportModal(...args);}
async function loadAdminList(key){
  const context=pageContext();
  const result=await fetchAdminList(key,api,context.adminPages,context.adminStoreFilter);
  if(pageContext().key!==context.key)throw new DOMException('A tela mudou.','AbortError');
  state.adminPageMeta[key]=result;
  try{const summary=await api('/admin/list-summary');if(pageContext().key===context.key)state.adminSummary=summary;}catch{/* the list remains usable when the summary is unavailable */}
  if(pageContext().key!==context.key)throw new DOMException('A tela mudou.','AbortError');
  return result.content;
}
function returnToAdminTickets(...args){return supportActions.returnToAdminTickets(...args);}
function ticketConversationHtml(...args){return supportActions.ticketConversationHtml(...args);}
function openTicket(...args){return supportActions.openTicket(...args);}
function createSupportTicket(...args){return supportActions.createSupportTicket(...args);}
function sendTicketMessage(...args){return supportActions.sendTicketMessage(...args);}
function updateTicketStatus(...args){return supportActions.updateTicketStatus(...args);}

function renderHome(...args){return discoveryViews.renderHome(...args);}

function categoryTile(...args){return discoveryViews.categoryTile(...args);}

function renderAllCategories(...args){return discoveryViews.renderAllCategories(...args);}

function renderCategoryStores(...args){return discoveryViews.renderCategoryStores(...args);}

function storeCard(...args){return discoveryViews.storeCard(...args);}

function emptyState(icon,title,message,button,label) {return `<div class="empty-state"><div class="empty-icon">${icon}</div><h3>${esc(title)}</h3><p>${esc(message)}</p>${button?`<button class="btn light small" data-action="${label}">${esc(button)}</button>`:''}</div>`;}

function bindPage() {
  bindCatalogActions(document, { create: createItem, edit: openCatalogItemEditor });
  cleanupStoreMaps();
  document.querySelectorAll('[data-booking-policy]').forEach(root=>mountBookingPolicy(root,activeMerchantStore()));
  document.querySelectorAll('[data-private-integrations]').forEach(mountPrivateIntegrations);
  document.querySelectorAll('[data-admin-mfa]').forEach(root=>mountAdminMfa(root,{onSessionRevoked:()=>{clearSession();render();}}));
  document.querySelectorAll('[data-courier-finance]').forEach(root=>mountCourierFinance(root,{admin:root.dataset.courierFinance==='admin',onError:error=>notify('Financeiro',error.message,'error')}));
  document.querySelectorAll('[data-store-map-picker]').forEach(root=>mountLocationPicker(root));
  if($('[data-store-directory-map]'))mountStoreDirectoryMap($('[data-store-directory-map]'),state.page==='categoryStores'?state.stores.filter(s=>categoryMatches(s.category,state.category)):state.filteredStores,{onOpen:openStore});

  document.querySelectorAll('[data-mask]').forEach(input=>{
    const formatter={cpf:formatCpfMask,cnpj:formatCnpjMask,currency:formatCurrencyMask}[input.dataset.mask];
    if(!formatter)return;
    if(input.dataset.mask==='cpf'&&input.value)input.value=formatCpfMask(input.value);
    if(input.dataset.mask==='cnpj'&&input.value)input.value=formatCnpjMask(input.value);
    input.addEventListener('focus',()=>{if(input.dataset.mask==='currency')input.select();});
    input.addEventListener('input',()=>{input.value=formatter(input.value);});
  });
  document.querySelectorAll('[data-category-select]').forEach(b=>b.addEventListener('click',()=>{state.category=b.dataset.categorySelect;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}));
  document.querySelectorAll('[data-category]').forEach(b=>b.addEventListener('click',()=>{state.category=b.dataset.category;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}));
  $('[data-action="go-categories"]')?.addEventListener('click',()=>navigate('categories'));
  document.querySelectorAll('[data-store]').forEach(card=>{const open=()=>openStore(card.dataset.store);card.addEventListener('click',open);card.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();open();}});});
  document.querySelectorAll('[data-action]').forEach(b=>b.addEventListener('click',()=>runAction(b.dataset.action)));
  document.querySelectorAll('[data-checkout]').forEach(b=>b.addEventListener('click',checkout));
  document.querySelectorAll('[data-profile-save]').forEach(f=>f.addEventListener('submit',saveProfile));
  $('[data-admin-profile-save]')?.addEventListener('submit',saveAdminProfile);
  $('[data-service-city-create]')?.addEventListener('submit',createServiceCity);
  $('[data-store-category-create]')?.addEventListener('submit',createStoreCategory);
  document.querySelectorAll('[data-service-city-update]').forEach(form=>form.addEventListener('submit',updateServiceCity));
  document.querySelectorAll('[data-service-city-toggle]').forEach(button=>button.addEventListener('click',()=>toggleServiceCity(button.dataset.serviceCityToggle,button.dataset.enabled==='true')));
  document.querySelectorAll('[data-store-category-delete]').forEach(button=>button.addEventListener('click',()=>deleteStoreCategory(button.dataset.storeCategoryDelete)));
  $('[data-admin-account-edit]')?.addEventListener('submit',saveAdminAccount);
  document.querySelectorAll('[data-admin-edit-store]').forEach(button=>button.addEventListener('click',()=>openAdminStoreEditor(button.dataset.adminEditStore)));
  document.querySelectorAll('[data-password-change]').forEach(f=>f.addEventListener('submit',changePassword));
  $('[data-phone-verify-request]')?.addEventListener('click',requestPhoneVerification);
  $('[data-phone-verify-confirm]')?.addEventListener('submit',confirmPhoneVerification);
  document.querySelectorAll('[data-account-section]').forEach(b=>b.addEventListener('click',async()=>{state.accountSection=b.dataset.accountSection;if(state.accountSection==='addresses')await loadAddresses();renderPage();}));
  document.querySelectorAll('[data-admin-definition-section]').forEach(button=>button.addEventListener('click',async()=>{state.adminDefinitionSection=button.dataset.adminDefinitionSection;if(state.adminDefinitionSection==='audit')await loadAdminAuditEvents();renderPage();}));
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
  if($('[data-operating-hours]'))mountOperatingHours($('[data-operating-hours]'),activeMerchantStore(),{configuration:state.merchantHours,onSaved:(config,summary)=>{state.merchantHours=config;notify('Funcionamento salvo',summary?.notified?`${summary.notified} aviso(s) enviado(s), ${summary.cancelled} reserva(s) cancelada(s) e ${summary.kept} mantida(s).`:'Os dias e turnos da loja foram atualizados.');renderPage();},onError:error=>notify('Confira o funcionamento',error.message,'error')});
  document.querySelectorAll('[data-item-deactivate]').forEach(button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemDeactivate,false)));
  document.querySelectorAll('[data-item-activate]').forEach(button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemActivate,true)));
  document.querySelectorAll('[data-professional-create]').forEach(form=>form.addEventListener('submit',createProfessional));
  document.querySelectorAll('[data-professional-hours]').forEach(button=>button.addEventListener('click',()=>{const person=state.merchantProfessionals.find(p=>String(p.id)===button.dataset.professionalHours);if(person)openProfessionalHours(activeMerchantStore(),person,{onError:error=>notify('Agenda do profissional',error.message,'error')});}));
  document.querySelectorAll('[data-professional-active]').forEach(button=>button.addEventListener('click',()=>setProfessionalActive(button.dataset.professionalActive,button.dataset.active==='true')));
  document.querySelectorAll('[data-item-kind]').forEach(select=>select.addEventListener('change',()=>syncCatalogItemFields(select.form)));
  document.querySelectorAll('[data-pay-after-service]').forEach(select=>select.addEventListener('change',()=>syncCatalogItemRisk(select.form)));
  document.querySelectorAll('[data-admin-page]').forEach(button=>button.addEventListener('click',async()=>{
    const key=button.dataset.adminPage,page=Number(button.dataset.page);
    if(!Object.hasOwn(adminLists,key)||!Number.isInteger(page)||page<0)return;
    state.adminPages[key]=page;
    await loadCurrentPage();renderPage();
  }));
  document.querySelectorAll('[data-store-filter]').forEach(button=>button.addEventListener('click',async()=>{state.adminStoreFilter=button.dataset.storeFilter;state.adminPages.adminStores=0;await loadCurrentPage();renderPage();}));
  document.querySelectorAll('[data-update-store]').forEach(f=>f.addEventListener('submit',updateStore));
  document.querySelectorAll('[data-create-operator]').forEach(f=>f.addEventListener('submit',createOperator));
  document.querySelectorAll('[data-operator-application]').forEach(b=>b.addEventListener('click',()=>reviewOperatorApplication(b.dataset.operatorApplication,b.dataset.decision)));
  document.querySelectorAll('[data-operator-active]').forEach(b=>b.addEventListener('click',()=>setOperatorActive(b.dataset.operatorActive,b.dataset.active==='true')));
  document.querySelectorAll('[data-account-active]').forEach(b=>b.addEventListener('click',()=>setAccountActive(b.dataset.accountActive,b.dataset.active==='true')));
  document.querySelectorAll('[data-admin-edit-account]').forEach(b=>b.addEventListener('click',()=>openAdminAccountEditor(b.dataset.adminEditAccount)));
  document.querySelectorAll('[data-account-role]').forEach(b=>b.addEventListener('click',()=>setAccountRole(b.dataset.accountRole,b.dataset.role,b.dataset.active==='true')));
  document.querySelectorAll('[data-reset-password]').forEach(b=>b.addEventListener('click',()=>resetAccountPassword(b.dataset.resetPassword,b.dataset.name)));
  $('[data-dismiss-temp-password]')?.addEventListener('click',()=>{state.adminTemporaryPassword='';renderPage();});
  $('[data-refresh-integrations]')?.addEventListener('click',async()=>{try{state.adminIntegrations=await api('/admin/integrations');renderPage();notify('Status atualizado','A API informou a presença das configurações do servidor.','info');}catch(error){notify('Não foi possível atualizar os serviços',error.message,'error');}});
  $('[data-refresh-audit]')?.addEventListener('click',async()=>{await loadAdminAuditEvents();renderPage();});
  if($('[data-store-cover]'))mountStoreCover($('[data-store-cover]'),activeMerchantStore(),{onSaved:()=>notify('Imagem enviada','Aguarde a aprovação do administrador. A capa atual foi preservada.'),onError:e=>notify('Capa da loja',e.message,'error')});
  if($('[data-cover-review]'))mountCoverReview($('[data-cover-review]'),{onSaved:approved=>notify(approved?'Capa aprovada':'Imagem recusada','O vendedor será avisado do resultado.')});
  document.querySelectorAll('[data-order-status]').forEach(b=>b.addEventListener('click',()=>updateOrder(b.dataset.id,b.dataset.orderStatus)));
  document.querySelectorAll('[data-offer-delivery]').forEach(b=>b.addEventListener('click',()=>offerDelivery(b.dataset.offerDelivery)));
  document.querySelectorAll('[data-reservation-status]').forEach(b=>b.addEventListener('click',()=>updateReservation(b.dataset.id,b.dataset.reservationStatus)));
  document.querySelectorAll('[data-approve-store]').forEach(b=>b.addEventListener('click',()=>approveStore(storeApprovalId(b))));
  document.querySelectorAll('[data-store-active]').forEach(b=>b.addEventListener('click',()=>setStoreActive(b.dataset.storeActive,b.dataset.active==='true')));
  bindCourierOfferActions(document, acceptDelivery);
  document.querySelectorAll('[data-delivery-status]').forEach(b=>b.addEventListener('click',()=>updateDelivery(b.dataset.id,b.dataset.deliveryStatus)));
  document.querySelectorAll('[data-share-location]').forEach(b=>b.addEventListener('click',()=>shareCourierLocation(b.dataset.shareLocation)));
  document.querySelectorAll('[data-reopen-store]').forEach(b=>b.addEventListener('click',()=>openStore(b.dataset.reopenStore)));
  $('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
  $('[data-scroll-stores]')?.addEventListener('click',()=>$('#stores-heading')?.scrollIntoView({behavior:'smooth'}));
  $('[data-checkout-pay]')?.addEventListener('click',approvePayment);
  if(globalThis.FOODDELIVERY_DEMO&&$('[data-checkout-pay]')){const pay=$('[data-checkout-pay]');pay.textContent='Simular pagamento aprovado';const decline=document.createElement('button');decline.className='btn danger block';decline.textContent='Simular pagamento recusado';decline.type='button';decline.addEventListener('click',()=>simulatePayment('decline'));pay.after(decline);}
  document.querySelectorAll('[data-section]').forEach(button=>button.addEventListener('click',()=>{
    if(role()==='ADMIN')state.adminSection=button.dataset.section;
    else if(role()==='MERCHANT')state.merchantSection=button.dataset.section;
    renderPage();
    window.scrollTo({top:0,behavior:'instant'});
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
  if(action==='refresh-stores')await loadCurrentPage();
  if(action==='choose-city')openLocationPicker();
  if(action==='reload-admin')await loadCurrentPage();
}

async function openStore(id) {
  try { state.selectedStore=state.stores.find(s=>String(s.id)===String(id))||null;[state.storeItems,state.storeProfessionals,state.storeHours]=await Promise.all([api(`/stores/${encodeURIComponent(id)}/items`),api(`/stores/${encodeURIComponent(id)}/professionals`).catch(()=>[]),api(`/stores/${encodeURIComponent(id)}/hours`)]);render(); }
  catch(error){state.selectedStore=null;notify('Não conseguimos abrir essa loja',error.message,'error');}
}

function currentStoreOpen(store, schedule=state.storeHours) { return storeOpenAt(schedule, store?.timezone || 'America/Sao_Paulo'); }
function operatingHoursPanel(...args){return merchantViews.operatingHoursPanel(...args);}

function renderStoreModal(...args){return shoppingViews.renderStoreModal(...args);}

function addToCart(itemId) {
  const item=state.storeItems.find(i=>String(i.id)===String(itemId));if(!item)return;
  if(!currentStoreOpen(state.selectedStore)){notify('Loja fechada','Você poderá comprar quando o estabelecimento abrir novamente.','info');return;}
  if(state.cart.length&&String(state.cart[0].storeId)!==String(state.selectedStore.id)){notify('Sua sacola tem outra loja','Finalize ou esvazie a sacola antes de pedir de outro estabelecimento.','info');return;}
  if(state.selectedStore.deliveryEnabled===false&&state.selectedStore.pickupEnabled===false){notify('Modalidade indisponível','Esta loja não recebe pedidos de produtos online.','info');return;}
  if(!state.cart.length)state.cartMode=state.selectedStore.pickupEnabled!==false?'PICKUP':'DELIVERY';
  const line=state.cart.find(i=>String(i.id)===String(item.id));
  if(line&&line.quantity>=item.stock){notify('Limite de estoque','A loja não tem mais unidades disponíveis deste item.','info');return;}
  if(line)line.quantity++;else state.cart.push({...item,storeId:state.selectedStore.id,storeName:state.selectedStore.name,storeCity:state.selectedStore.city,storeState:state.selectedStore.state,storeTimezone:state.selectedStore.timezone,storeOperatingHours:state.storeHours,deliveryFeeCents:state.selectedStore.deliveryFeeCents||0,deliveryEnabled:state.selectedStore.deliveryEnabled,pickupEnabled:state.selectedStore.pickupEnabled,quantity:1});
  state.selectedStore=null;state.cartOpen=true;notify('Adicionado à sacola',`${item.name} está esperando por você.`);render();
}

function showReserveForm(...args){return shoppingViews.showReserveForm(...args);}

function renderCartDrawer(...args){return shoppingViews.renderCartDrawer(...args);}

function closeCart(){state.cartOpen=false;render();}

async function checkout() {
  if(!state.cart.length)return;
  if(!storeOpenAt(state.cart[0].storeOperatingHours,state.cart[0].storeTimezone)){notify('Loja fechada','O pedido poderá ser enviado quando o estabelecimento abrir novamente.','info');render();return;}
  if(!state.account?.cpf||!state.account?.phone){state.cartOpen=false;state.accountSection='profile';navigate('account');notify('Complete seu cadastro','CPF válido e telefone são necessários para comprar.','info');return;}
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
    state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);
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
    const response=await postalCodeResponse(cep,{headers:{Accept:'application/json'}});
    if(!response.ok)throw new Error('O serviço de CEP está indisponível. Você ainda pode preencher o endereço manualmente.');
    const data=await response.json(); if(data.erro)throw new Error('Não encontramos esse CEP. Confira os números ou preencha o endereço manualmente.');
    const fill=(name,value)=>{const field=form.querySelector(`[name="${name}"]`);if(field&&value&&!field.value)field.value=value;};
    fill('addressLine',data.logradouro);fill('neighborhood',data.bairro);fill('city',data.localidade);fill('state',data.uf);
    input.value=data.cep||input.value;notify('CEP encontrado','Confira o número e o complemento do endereço.','success');
  } catch(error) {notify('Não foi possível consultar o CEP',error.message,'error');}
  finally {delete form.dataset.cepLookup;if(button){button.disabled=false;button.textContent='Buscar';}}
}

function renderCheckout(...args){return customerViews.renderCheckout(...args);}

async function approvePayment() {
  if(!state.checkout)return;
  if(globalThis.FOODDELIVERY_DEMO)return simulatePayment('approve');
  state.paymentStarting=true;render();
  try{const checkout=await api(`/payments/${state.checkout.paymentId}/checkout`,{method:'POST'});window.location.assign(checkout.checkoutUrl);}
  catch(error){state.paymentStarting=false;render();notify('Não foi possível abrir o pagamento',error.message,'error');}
}

async function simulatePayment(outcome){
  if(!state.checkout||state.paymentStarting)return;
  state.paymentStarting=true;render();
  try{await api(`/demo/payments/${encodeURIComponent(state.checkout.paymentId)}/${outcome}`,{method:'POST'});
    if(outcome==='approve'){const page=state.checkout.type==='RESERVATION'?'reservations':'orders';state.checkout=null;await navigate(page);notify('Pagamento simulado aprovado','Nenhum dinheiro foi movimentado.');}
    else notify('Pagamento simulado recusado','Você pode tentar novamente ou cancelar a operação.','info');
  }catch(error){notify('Falha na simulação',error.message,'error');}
  finally{state.paymentStarting=false;render();}
}

function renderOrders(...args){return customerViews.renderOrders(...args);}

function trackingMessage(...args){return customerViews.trackingMessage(...args);}

function renderReservations(...args){return customerViews.renderReservations(...args);}

function chatKey(type,id){return `${type}:${id}`;}
function chatMessagesHtml(thread){return thread.messages.length?thread.messages.map(m=>`<article class="chat-message ${String(m.senderId)===String(state.account?.id)?'mine':''}"><strong>${esc(m.senderName)}</strong><p>${esc(m.body)}</p><time>${shortDate(m.createdAt)}</time></article>`).join(''):'<p class="chat-empty">Ainda não há mensagens. Escreva se precisar combinar algum detalhe.</p>';}
const chatSession = createChatSession({
  request: api,
  visible: () => document.visibilityState === 'visible',
  onLoad: (key, thread) => { state.chatThreads[key] = thread; renderPage(); },
  onUpdate: applyChatUpdate,
  onError: error => { state.openChat = null; notify('Não foi possível abrir a conversa', error.message, 'error'); renderPage(); },
});
function stopChat(){chatSession.stop();state.openChat=null;}
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
function chatPanel(type,id){const key=chatKey(type,id);if(state.openChat!==key)return '';const thread=state.chatThreads[key];if(!thread)return `<section class="chat-panel"><p role="status">Carregando conversa…</p><button type="button" class="btn outline small" data-close-chat="${esc(key)}">Fechar</button></section>`;return `<section class="chat-panel" data-chat-panel="${esc(key)}"><div class="chat-heading"><div><strong>${type==='deliveries'?'Chat com o entregador':type==='reservations'?'Conversa sobre a reserva':'Conversa sobre o pedido'}</strong><small>${thread.open?'Mensagens visíveis para as pessoas envolvidas nesta operação.':esc(thread.closedReason||'Conversa encerrada; o histórico continua disponível.')}</small></div><button class="btn outline small" data-close-chat="${esc(key)}">Fechar</button></div><div class="chat-messages">${chatMessagesHtml(thread)}</div>${thread.open?`<form class="chat-compose" data-chat-form data-chat-type="${type}" data-chat-id="${esc(id)}"><label class="sr-only" for="message-${esc(key)}">Escreva uma mensagem</label><textarea id="message-${esc(key)}" name="body" maxlength="1000" rows="2" required placeholder="Escreva sua mensagem…" data-chat-draft="${esc(key)}">${esc(state.chatDrafts[key]||'')}</textarea><button class="btn small" type="submit">Enviar</button></form>`:'<div class="chat-closed">Este chat foi encerrado conforme o andamento da operação. Você ainda pode consultar as mensagens anteriores.</div>'}</section>`;}

async function openChat(type,id){const key=chatKey(type,id);stopChat();state.openChat=key;state.chatThreads[key]=null;renderPage();await chatSession.open(type,id);}
function closeChat(key){if(state.openChat!==key)return;stopChat();renderPage();}
async function sendChatMessage(event){
  event.preventDefault();
  const form=event.currentTarget, key=chatKey(form.dataset.chatType,form.dataset.chatId);
  const body=String(new FormData(form).get('body')||'').trim();
  if(!body || state.openChat!==key)return;
  const button=form.querySelector('button[type="submit"]');
  if(button)button.disabled=true;
  await chatSession.send(key,body,{
    onSent: thread=>{state.chatThreads[key]=thread;delete state.chatDrafts[key];renderPage();},
    onFailure: error=>{if(button)button.disabled=false;notify('Não foi possível confirmar o envio',`${error.message} Confira a conversa antes de reenviar.`, 'error');}
  });
}

function renderAdminDefinitions(...args){return adminViews.renderAdminDefinitions(...args);}

function loadAdminAuditEvents(...args){return adminActions.loadAdminAuditEvents(...args);}

function openAdminStoreEditor(...args){return adminActions.openAdminStoreEditor(...args);}

function renderAccount(...args){return accountViews.renderAccount(...args);}

function requestOperatorAccess(...args){return accountActions.requestOperatorAccess(...args);}
function requestCityAccess(...args){return accountActions.requestCityAccess(...args);}

function refreshCitiesAfterSave(...args){return adminActions.refreshCitiesAfterSave(...args);}
function createServiceCity(...args){return adminActions.createServiceCity(...args);}
function refreshStoreCategories(...args){return adminActions.refreshStoreCategories(...args);}
function createStoreCategory(...args){return adminActions.createStoreCategory(...args);}
function deleteStoreCategory(...args){return adminActions.deleteStoreCategory(...args);}
function updateServiceCity(...args){return adminActions.updateServiceCity(...args);}
function toggleServiceCity(...args){return adminActions.toggleServiceCity(...args);}
function saveAdminProfile(...args){return adminActions.saveAdminProfile(...args);}

function openAdminAccountEditor(...args){return adminActions.openAdminAccountEditor(...args);}
function saveAdminAccount(...args){return adminActions.saveAdminAccount(...args);}

function saveProfile(...args){return accountActions.saveProfile(...args);}

function changePassword(...args){return accountActions.changePassword(...args);}

function requestPhoneVerification(...args){return accountActions.requestPhoneVerification(...args);}
function confirmPhoneVerification(...args){return accountActions.confirmPhoneVerification(...args);}

async function switchRole(nextRole){if(!(state.account?.grantedRoles||[]).includes(nextRole))return;invalidatePageLoads();stopChat();state.activeRole=nextRole;state.account={...state.account,activeRole:nextRole};persistAccountHint(state.account);state.page=nextRole==='ADMIN'?'admin':nextRole==='MERCHANT'?'merchant':nextRole==='COURIER'?'courier':'home';await loadCurrentPage();render();}

function renderNotifications(...args){return accountViews.renderNotifications(...args);}

function renderFinance(...args){return accountViews.renderFinance(...args);}

async function openNotificationTarget(target,id){
  const destination={ORDER:role()==='MERCHANT'?'merchantOrders':'orders',RESERVATION:role()==='MERCHANT'?'merchantReservations':'reservations',DELIVERY:role()==='COURIER'?'courier':'orders',STORE:'merchant',FINANCE:'finance',ADMIN_REQUESTS:'admin'}[target];
  if(target==='SUPPORT'){
    if(role()==='ADMIN'){state.adminSection='tickets';await loadAdminData();render();if(id)await openTicket(id,true);}
    else {await openSupportCenter();if(id)await openTicket(id,false);}
    return;
  }
  if(!destination||!navByRole[role()]?.some(([page])=>page===destination))return;
  if(target==='ADMIN_REQUESTS')state.adminSection='applications';
  if(target==='STORE'&&role()==='MERCHANT'){state.merchantStoreId=id||state.merchantStoreId;state.merchantSection='branding';}
  await navigate(destination);
  if(id){const selectors={ORDER:`[data-order-card="${CSS.escape(id)}"]`,RESERVATION:`[data-reservation-card="${CSS.escape(id)}"]`,DELIVERY:`[data-delivery-card="${CSS.escape(id)}"]`};const card=selectors[target]?document.querySelector(selectors[target]):null;card?.scrollIntoView({behavior:'smooth',block:'center'});card?.classList.add('notification-target-highlight');window.setTimeout(()=>card?.classList.remove('notification-target-highlight'),2200);}
}

function renderMerchant(...args){return merchantViews.renderMerchant(...args);}

function activeMerchantStore(){return state.ownStores.find(s=>String(s.id)===String(state.merchantStoreId))||state.ownStores[0]||null;}
async function selectMerchantStore(event) {
  state.merchantStoreId = event.target.value;
  await loadCurrentPage();
}


function renderMerchantAll(...args){return merchantViews.renderMerchantAll(...args);}

function storeCategoryOptions(...args){return merchantViews.storeCategoryOptions(...args);}
function storeTimezoneOptions(...args){return merchantViews.storeTimezoneOptions(...args);}
function storeFields(...args){return merchantViews.storeFields(...args);}

function renderMerchantOrders(...args){return merchantViews.renderMerchantOrders(...args);}

function merchantOrderAction(...args){return merchantViews.merchantOrderAction(...args);}

function renderMerchantReservations(...args){return merchantViews.renderMerchantReservations(...args);}

function reservationActions(r){if(r.cancellationRequestStatus==='REQUESTED')return `<div class="alert-strip warn"><span>◷</span><span>Cliente solicitou cancelamento${r.cancellationRequestNote?`: ${esc(r.cancellationRequestNote)}`:''}. Ao aceitar, qualquer pagamento online será estornado integralmente.</span></div><div class="action-row"><button class="btn danger small" data-reservation-cancellation-decision="${esc(r.id)}" data-accepted="true">Aceitar cancelamento</button><button class="btn outline small" data-reservation-cancellation-decision="${esc(r.id)}" data-accepted="false">Recusar solicitação</button></div>`;const action=r.status==='CONFIRMED'?['IN_SERVICE','Iniciar atendimento']:r.status==='IN_SERVICE'?['COMPLETED','Concluir atendimento']:null;const progress=action?`<button class="btn small" data-reservation-status="${action[0]}" data-id="${esc(r.id)}">${action[1]}</button>`:'';const cancel=r.status==='CONFIRMED'?`<button class="btn small danger" data-reservation-status="CANCELLED" data-id="${esc(r.id)}">Cancelar e solicitar estorno</button>`:'';return progress||cancel?`<div class="action-row">${progress}${cancel}</div>`:'—';}

function renderCourier(...args){return courierViews.renderCourier(...args);}

function courierNext(...args){return courierViews.courierNext(...args);}

function renderAdmin(...args){return adminViews.renderAdmin(...args);}

function renderAdminAll(...args){return adminViews.renderAdminAll(...args);}

async function createStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const fee=parseCurrencyDecimal(form.get('deliveryFee'));if(!Number.isFinite(fee)||fee<0||fee>1000){notify('Confira a taxa de entrega','Informe um valor entre R$ 0,00 e R$ 1.000,00.','info');return;}try{const coordinates=coordinatesFromForm(form);const body={name:String(form.get('name')).trim(),cnpj:String(form.get('cnpj')||'').replace(/\D/g,''),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:true,deliveryFeeCents:Math.round(fee*100),timezone:String(form.get('timezone')||'America/Sao_Paulo'),...coordinates,...fulfillmentPayload(form)};const created=await api('/merchant/stores',{method:'POST',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');state.merchantStoreId=created.id;state.platformRequests=await api('/account/platform-requests');notify('Loja enviada para revisão','O administrador analisará os dados da loja.');render();}catch(e){notify('Não conseguimos cadastrar a loja',e.message,'error');}}
async function resubmitStore(id){try{await api(`/merchant/stores/${id}/resubmit`,{method:'POST'});state.platformRequests=await api('/account/platform-requests');notify('Loja reenviada','A administração receberá uma nova solicitação para análise.');render();}catch(e){notify('Não foi possível reenviar a loja',e.message,'error');}}


async function updateStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const store=activeMerchantStore();if(!store)return;const fee=parseCurrencyDecimal(form.get('deliveryFee'));if(!Number.isFinite(fee)||fee<0||fee>1000){notify('Confira a taxa de entrega','Informe um valor entre R$ 0,00 e R$ 1.000,00.','info');return;}try{const coordinates=coordinatesFromForm(form);const body={cnpj:store.cnpj,name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:store.active,deliveryFeeCents:Math.round(fee*100),timezone:String(form.get('timezone')||store.timezone||'America/Sao_Paulo'),...coordinates,...fulfillmentPayload(form)};await api(`/merchant/stores/${store.id}`,{method:'PUT',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');notify('Perfil atualizado','As informações e a localização da loja foram salvas.');render();}catch(e){notify('Não conseguimos atualizar a loja',e.message,'error');}}

function syncCatalogItemFields(form){if(!form)return;const service=form.querySelector('[name="serviceItem"]')?.value==='true';const stock=form.querySelector('[data-stock-field]'),stockInput=stock?.querySelector('input'),payment=form.querySelector('[data-service-payment-field]'),duration=form.querySelector('[data-service-duration-field]'),durationInput=duration?.querySelector('input');if(stock)stock.hidden=service;if(stockInput){stockInput.disabled=service;stockInput.required=!service;}if(duration)duration.hidden=!service;if(durationInput){durationInput.disabled=!service;durationInput.required=service;}if(payment)payment.hidden=!service;syncCatalogItemRisk(form);}
function syncCatalogItemRisk(form){if(!form)return;const service=form.querySelector('[name="serviceItem"]')?.value==='true',payAfter=form.querySelector('[name="payAfterService"]')?.value==='true';const warning=form.querySelector('[data-no-show-warning]'),ack=form.querySelector('[data-risk-ack-field]'),checkbox=form.querySelector('[name="acceptNoShowRisk"]');if(warning)warning.hidden=!(service&&payAfter);if(ack)ack.hidden=!(service&&payAfter);if(checkbox)checkbox.required=service&&payAfter;}
async function createItem(event){event.preventDefault();const store=activeMerchantStore();if(!store)return;const form=new FormData(event.currentTarget);const price=parseCurrencyDecimal(form.get('price'));if(!Number.isFinite(price)||price<=0){notify('Confira o preço','Informe um valor maior que zero.','info');return;}const serviceItem=form.get('serviceItem')==='true',payAfterService=serviceItem&&form.get('payAfterService')==='true',appointmentDurationMinutes=serviceItem?Number(form.get('appointmentDurationMinutes')):30;if(serviceItem&&(!Number.isInteger(appointmentDurationMinutes)||appointmentDurationMinutes<5||appointmentDurationMinutes>480)){notify('Confira a duração','Escolha de 5 minutos a 8 horas.','info');return;}if(payAfterService&&form.get('acceptNoShowRisk')!=='on'){notify('Confirme que entendeu o risco','Marque a confirmação para oferecer pagamento após o atendimento.','info');return;}const body={name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),priceCents:Math.round(price*100),stock:serviceItem?0:Number(form.get('stock')),serviceItem,payAfterService,appointmentDurationMinutes,active:true};try{await api(`/merchant/stores/${store.id}/items`,{method:'POST',body:JSON.stringify(body)});state.storeItems=await api(`/merchant/stores/${store.id}/items`);notify('Item adicionado','O catálogo da sua loja foi atualizado.');render();}catch(e){notify('Não conseguimos adicionar o item',e.message,'error');}}

async function setCatalogItemActive(id,active){
  const store=activeMerchantStore();
  if(!store)return;
  const item=state.storeItems.find(row=>String(row.id)===String(id));
  if(!item)return;
  return runOnce(`catalog:${id}:${active?'activate':'remove'}`,async()=>{
    const button=document.querySelector(`[data-item-${active?'activate':'deactivate'}="${CSS.escape(String(id))}"]`);
    if(button){button.disabled=true;button.textContent=active?'Reativando…':'Removendo…';}
    try{
      if(active){
        await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'PUT',body:JSON.stringify({name:item.name,description:item.description||'',priceCents:item.priceCents,stock:item.stock,active:true,serviceItem:item.serviceItem,payAfterService:item.payAfterService,appointmentDurationMinutes:item.appointmentDurationMinutes||30})});
      }else{
        await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'DELETE'});
      }
      state.storeItems=await api(`/merchant/stores/${encodeURIComponent(store.id)}/items`);
      notify(active?'Item reativado':'Item removido do catálogo',active?'Ele voltou a ficar disponível para os clientes.':'O histórico foi preservado; o item deixou de aparecer para clientes.','success');
      renderPage();
    }catch(error){
      notify(active?'Não foi possível reativar o item':'Não foi possível remover o item',error.message,'error');
      if(button?.isConnected){button.disabled=false;button.textContent=active?'Reativar':'Remover';}
    }
  });
}

function openCatalogItemEditor(id){
  const item=state.storeItems.find(row=>String(row.id)===String(id));if(!item)return;
  $('#catalog-item-modal')?.remove();const wrap=document.createElement('div');wrap.id='catalog-item-modal';wrap.className='modal-backdrop';
  wrap.innerHTML=`<section class="modal catalog-edit-modal" role="dialog" aria-modal="true" aria-labelledby="catalog-item-title"><div class="modal-head"><div><span class="eyebrow">Editar catálogo</span><h2 id="catalog-item-title">${esc(item.name)}</h2><p>Altere o produto ou transforme este item em um serviço agendado.</p></div><button class="close-button" type="button" data-close-item aria-label="Fechar">×</button></div><form class="form-grid catalog-edit-form" data-catalog-item-update="${esc(item.id)}"><div class="field span-2"><label for="edit-item-name">Nome</label><input id="edit-item-name" name="name" required maxlength="140" value="${esc(item.name)}"/></div><div class="field span-2"><label for="edit-item-description">Descrição</label><textarea id="edit-item-description" name="description" maxlength="1000">${esc(item.description||'')}</textarea></div><div class="field"><label for="edit-item-price">Preço</label><input id="edit-item-price" name="price" data-mask="currency" inputmode="numeric" required value="${esc(formatCurrencyValue(item.priceCents/100))}"/></div><div class="field"><label for="edit-item-kind">Tipo</label><select id="edit-item-kind" name="serviceItem" data-item-kind><option value="false" ${item.serviceItem?'':'selected'}>Produto à venda</option><option value="true" ${item.serviceItem?'selected':''}>Serviço agendado</option></select></div><div class="field" data-stock-field ${item.serviceItem?'hidden':''}><label for="edit-item-stock">Estoque</label><input id="edit-item-stock" name="stock" type="number" min="0" required value="${item.stock}" ${item.serviceItem?'disabled':''}/></div><div class="field" data-service-duration-field ${item.serviceItem?'':'hidden'}><label for="edit-item-duration">Duração (minutos)</label><input id="edit-item-duration" name="appointmentDurationMinutes" type="number" min="5" max="480" step="5" required value="${item.appointmentDurationMinutes||30}" ${item.serviceItem?'':'disabled'}/><small>O atendimento precisa caber no expediente e não pode coincidir com outro do profissional.</small></div><div class="field" data-service-payment-field ${item.serviceItem?'':'hidden'}><label for="edit-item-payment">Quando cobrar?</label><select id="edit-item-payment" name="payAfterService" data-pay-after-service><option value="false" ${item.payAfterService?'':'selected'}>Antes: pagamento online para confirmar</option><option value="true" ${item.payAfterService?'selected':''}>Depois: pagamento direto à loja</option></select></div><div class="alert-strip warn span-2" data-no-show-warning ${item.serviceItem&&item.payAfterService?'':'hidden'}><span>!</span><span>Pagamento após o atendimento traz risco de falta ou não pagamento. Fora da cidade do cliente, a plataforma exige pagamento antecipado.</span></div><label class="check-row span-2" data-risk-ack-field ${item.serviceItem&&item.payAfterService?'':'hidden'}><input type="checkbox" name="acceptNoShowRisk"/><span>Confirmo que aceito o risco de oferecer pagamento após o atendimento.</span></label><div class="span-2 action-row"><button class="btn" type="submit">Salvar alterações</button><button class="btn outline" type="button" data-close-item>Cancelar</button></div></form></section>`;
  document.body.append(wrap);wrap.querySelectorAll('[data-close-item]').forEach(button=>button.addEventListener('click',()=>wrap.remove()));wrap.addEventListener('click',event=>{if(event.target===wrap)wrap.remove();});const form=wrap.querySelector('form');form.addEventListener('submit',saveCatalogItem);form.querySelector('[data-mask="currency"]').addEventListener('input',event=>event.target.value=formatCurrencyMask(event.target.value));form.querySelector('[name="serviceItem"]').addEventListener('change',()=>syncCatalogItemFields(form));form.querySelector('[name="payAfterService"]').addEventListener('change',()=>syncCatalogItemRisk(form));
}
async function saveCatalogItem(event){
  event.preventDefault();const form=event.currentTarget,item=state.storeItems.find(row=>String(row.id)===String(form.dataset.catalogItemUpdate));if(!item)return;
  const data=new FormData(form),price=parseCurrencyDecimal(data.get('price')),serviceItem=data.get('serviceItem')==='true',payAfterService=serviceItem&&data.get('payAfterService')==='true';
  if(!Number.isFinite(price)||price<=0){notify('Confira o preço','Informe um valor maior que zero.','info');return;}
  if(payAfterService&&data.get('acceptNoShowRisk')!=='on'){notify('Confirme que entendeu o risco','Marque a confirmação para continuar oferecendo pagamento após o atendimento.','info');return;}
  const stock=serviceItem?0:Number(data.get('stock'));if(!Number.isInteger(stock)||stock<0){notify('Confira o estoque','Informe uma quantidade válida, igual ou maior que zero.','info');return;}
  const appointmentDurationMinutes=serviceItem?Number(data.get('appointmentDurationMinutes')):30;if(serviceItem&&(!Number.isInteger(appointmentDurationMinutes)||appointmentDurationMinutes<5||appointmentDurationMinutes>480)){notify('Confira a duração','Escolha de 5 minutos a 8 horas.','info');return;}
  try{await api(`/merchant/items/${item.id}`,{method:'PUT',body:JSON.stringify({name:String(data.get('name')).trim(),description:String(data.get('description')||'').trim(),priceCents:Math.round(price*100),stock,active:item.active,serviceItem,payAfterService,appointmentDurationMinutes})});state.storeItems=await api(`/merchant/stores/${activeMerchantStore().id}/items`);$('#catalog-item-modal')?.remove();notify('Item atualizado','Tipo, duração, preço e forma de pagamento foram salvos.','success');renderPage();}catch(error){notify('Não foi possível atualizar o item',error.message,'error');}
}
async function createProfessional(event){event.preventDefault();const form=event.currentTarget,store=activeMerchantStore(),name=String(new FormData(form).get('name')||'').trim();if(!store||!name)return;try{await api(`/merchant/stores/${store.id}/professionals`,{method:'POST',body:JSON.stringify({name})});state.merchantProfessionals=await api(`/merchant/stores/${store.id}/professionals`);form.reset();notify('Profissional adicionado','Agora os clientes podem escolher essa pessoa ou pedir o primeiro disponível.','success');renderPage();}catch(error){notify('Não foi possível adicionar o profissional',error.message,'error');}}
async function setProfessionalActive(id,active){try{await api(`/merchant/professionals/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.merchantProfessionals=await api(`/merchant/stores/${activeMerchantStore().id}/professionals`);notify(active?'Profissional ativado':'Profissional desativado',active?'Ele voltou a aparecer nas reservas.':'Novas reservas não serão atribuídas a essa pessoa.','info');renderPage();}catch(error){notify('Não foi possível atualizar o profissional',error.message,'error');}}

function createOperator(...args){return adminActions.createOperator(...args);}

function setAccountActive(...args){return adminActions.setAccountActive(...args);}
function setAccountRole(...args){return adminActions.setAccountRole(...args);}
function resetAccountPassword(...args){return adminActions.resetAccountPassword(...args);}

async function reviewPlatformRequest(id,decision){return runOnce(`admin:review-request:${id}`,async()=>{const button=document.querySelector(`[data-platform-review="${CSS.escape(String(id))}"][data-decision="${decision}"]`);if(button){button.disabled=true;button.textContent=decision==='true'?'Aprovando…':'Reprovando…';}try{await api(`/admin/platform-requests/${id}/review`,{method:'PATCH',body:JSON.stringify({approved:decision==='true'})});notify(decision==='true'?'Solicitação aprovada':'Solicitação reprovada','A decisão foi registrada e a pessoa será notificada.',decision==='true'?'success':'info');await loadAdminData();renderPage();if(state.adminLoadError)notify('Decisão registrada, atualização parcial',state.adminLoadError,'info');}catch(error){notify('Não foi possível analisar a solicitação',error.message,'error');if(button?.isConnected)button.disabled=false;}});}

function reviewOperatorApplication(...args){return adminActions.reviewOperatorApplication(...args);}

function setOperatorActive(...args){return adminActions.setOperatorActive(...args);}

function approveStore(...args){return adminActions.approveStore(...args);}

function setStoreActive(...args){return adminActions.setStoreActive(...args);}

function updateOrder(...args){return orderActions.updateOrder(...args);}

function offerDelivery(...args){return orderActions.offerDelivery(...args);}

function updateReservation(...args){return orderActions.updateReservation(...args);}

function acceptDelivery(...args){return orderActions.acceptDelivery(...args);}

function updateDelivery(...args){return orderActions.updateDelivery(...args);}

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

function cancelUnpaidOrder(...args){return orderActions.cancelUnpaidOrder(...args);}
function cancelUnpaidReservation(...args){return orderActions.cancelUnpaidReservation(...args);}
function requestOrderCancellation(...args){return orderActions.requestOrderCancellation(...args);}
function requestReservationCancellation(...args){return orderActions.requestReservationCancellation(...args);}
function decideOrderCancellation(...args){return orderActions.decideOrderCancellation(...args);}
function decideReservationCancellation(...args){return orderActions.decideReservationCancellation(...args);}

async function bindPageActions(){
  document.querySelectorAll('#page-content [data-add-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=true;renderPage();}));
  document.querySelectorAll('#page-content [data-cancel-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=false;renderPage();}));
  document.querySelectorAll('#page-content [data-address-form]').forEach(form=>form.addEventListener('submit',saveCheckoutAddress));
  $('[data-clear-notifications]')?.addEventListener('click',async()=>{try{await api('/account/notifications',{method:'DELETE'});localStorage.removeItem(notificationKey(state.account.id));localStorage.removeItem(`pronto_notifications:${state.account.id}`);state.notifications=[];render();notify('Avisos limpos','Você está em dia.','info',false);}catch(e){notify('Não foi possível limpar os avisos',e.message,'error');}});
  document.querySelectorAll('[data-notification-target]').forEach(item=>{
    const activate=()=>openNotificationTarget(item.dataset.notificationTarget,item.dataset.targetId);
    item.addEventListener('click',activate);
    item.addEventListener('keydown',event=>{if(event.key==='Enter'||event.key===' '){event.preventDefault();activate();}});
  });
  $('[data-action="reload-merchant-orders"]')?.addEventListener('click', () => loadCurrentPage());
  $('[data-action="reload-merchant-reservations"]')?.addEventListener('click', () => loadCurrentPage());
  $('[data-action="reload-courier"]')?.addEventListener('click', () => loadCurrentPage());
  $('[data-action="go-home"]')?.addEventListener('click',()=>navigate('home'));
  $('[data-action="reload-stores"]')?.addEventListener('click', () => loadCurrentPage());
  document.querySelectorAll('[data-store-commission]').forEach(form=>form.addEventListener('submit',saveCommission));
  document.querySelectorAll('[data-connect-mercadopago]').forEach(button=>button.addEventListener('click',connectMercadoPago));
  document.querySelectorAll('[data-resume-order]').forEach(button=>button.addEventListener('click',()=>resumeOrderPayment(button.dataset.resumeOrder)));
  document.querySelectorAll('[data-resume-reservation]').forEach(button=>button.addEventListener('click',()=>resumeReservationPayment(button.dataset.resumeReservation)));
  document.querySelectorAll('[data-cancel-order]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidOrder(button.dataset.cancelOrder)));
  document.querySelectorAll('[data-cancel-reservation]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidReservation(button.dataset.cancelReservation)));
  document.querySelectorAll('[data-request-order-cancellation]').forEach(button=>button.addEventListener('click',()=>requestOrderCancellation(button.dataset.requestOrderCancellation)));
  document.querySelectorAll('[data-request-reservation-cancellation]').forEach(button=>button.addEventListener('click',()=>requestReservationCancellation(button.dataset.requestReservationCancellation)));
  document.querySelectorAll('[data-order-cancellation-decision]').forEach(button=>button.addEventListener('click',()=>decideOrderCancellation(button.dataset.orderCancellationDecision,button.dataset.accepted==='true')));
  document.querySelectorAll('[data-reservation-cancellation-decision]').forEach(button=>button.addEventListener('click',()=>decideReservationCancellation(button.dataset.reservationCancellationDecision,button.dataset.accepted==='true')));
  document.querySelectorAll('[data-default-address]').forEach(button=>button.addEventListener('click',()=>setDefaultAddress(button.dataset.defaultAddress)));
  document.querySelectorAll('[data-delete-address]').forEach(button=>button.addEventListener('click',()=>deleteAddress(button.dataset.deleteAddress)));
  document.querySelectorAll('[data-lookup-cep]').forEach(button=>button.addEventListener('click',()=>lookupCep(button.closest('form'))));
  document.querySelectorAll('[data-cep]').forEach(input=>input.addEventListener('blur',()=>{if(input.value.replace(/\D/g,'').length===8)lookupCep(input.closest('form'));}));
}

function setDefaultAddress(...args){return accountActions.setDefaultAddress(...args);}

function deleteAddress(...args){return accountActions.deleteAddress(...args);}

function saveCommission(...args){return adminActions.saveCommission(...args);}

async function connectMercadoPago(event){const button=event.currentTarget;button.disabled=true;try{const result=await api(`/merchant/stores/${button.dataset.connectMercadopago}/mercadopago/connect`,{method:'POST'});window.location.assign(result.authorizationUrl);}catch(e){button.disabled=false;notify('Não foi possível conectar a conta',e.message,'error');}}

function resumeOrderPayment(...args){return orderActions.resumeOrderPayment(...args);}
function resumeReservationPayment(...args){return orderActions.resumeReservationPayment(...args);}

function bindEvents() {
  app.addEventListener('click', event => {
    document.querySelectorAll('.admin-action-menu[open]').forEach(menu=>{if(!menu.contains(event.target))menu.open=false;});
    const logout=event.target.closest('[data-logout]');if(logout){event.preventDefault();showLogoutConfirmation();return;}
    const page=event.target.closest('[data-page]');if(page){event.preventDefault();navigate(page.dataset.page);}
  });
  document.addEventListener('keydown',event=>{
    if(event.key==='Escape')document.querySelectorAll('.admin-action-menu[open]').forEach(menu=>{menu.open=false;menu.querySelector('summary')?.focus();});
  });
}

window.addEventListener('popstate',async()=>{const page=location.hash.slice(1);if(knownPage(page)&&(navByRole[role()]?.some(([allowed])=>allowed===page)||(['categories','categoryStores'].includes(page)&&canShop()))){if(state.page!==page)stopChat();state.page=page;await loadCurrentPage();render();}});

function showLogoutConfirmation(){
  $('#logout-confirm')?.remove();
  const dialog=document.createElement('div');dialog.id='logout-confirm';dialog.className='modal-backdrop confirm-backdrop';
  dialog.innerHTML='<section class="confirm-dialog" role="dialog" aria-modal="true" aria-labelledby="logout-title"><span class="confirm-mark">↗</span><h2 id="logout-title">Quer sair da sua conta?</h2><p>Você precisará entrar novamente para acessar seus pedidos e dados.</p><div class="confirm-actions"><button type="button" class="btn outline" data-cancel-logout>Continuar conectado</button><button type="button" class="btn danger" data-confirm-logout>Sair da conta</button></div></section>';
  document.body.append(dialog);
  dialog.querySelector('[data-cancel-logout]').addEventListener('click',()=>dialog.remove());
  dialog.addEventListener('click',event=>{if(event.target===dialog)dialog.remove();});
  dialog.querySelector('[data-confirm-logout]').addEventListener('click',()=>{dialog.remove();performLogout();});
}

async function performLogout(){
  invalidatePageLoads();
  try { await api('/auth/logout',{method:'POST'}); }
  catch(error) { notify('Não foi possível encerrar a sessão',error.message,'error'); return; }
  clearSession();render();notify('Você saiu da conta','Até a próxima!','info',false);
}

// Compose presentation modules after state and helpers have been initialized.
const discoveryViews = createDiscoveryViews({ apiAssetUrl, categoryEmoji, categoryMatches, categoryOptions, categoryStyles, emptyState, esc, fulfillmentBadges, icon, state });
const customerViews = createCustomerViews({ chatPanel, dateTimeInZone, emptyState, esc, formatStatus, groupCustomerReservations, money, pill, refundNotice, shortDate, state });
const merchantViews = createMerchantViews({ activeMerchantStore, catalogItemIcon, categoryOptions, chatPanel, dateTimeInZone, displayName, emptyState, esc, formatCurrencyValue, fulfillmentFields, money, pill, reservationActions, shortDate, state, storeMapFields });
const courierViews = createCourierViews({ chatPanel, emptyState, esc, money, pill, state });
const adminViews = createAdminViews({ adminAreaNames, adminPagination, adminStoreStatus, categoryEmoji, categoryOptions, emptyState, esc, filterAdminStores, formatCpfMask, icon, money, pill, renderAdminAreas, renderAdminTeamArea, roleNames, shortDate, state });
const accountViews = createAccountViews({ activeMerchantStore, addressText, emptyState, esc, formatCpfMask, icon, pill, renderAdminDefinitions, role, roleNames, shortDate, state });
const authViews = createAuthViews({ $, app, esc, icon, state, submitAuth, submitRecovery });

// Bind actions through live callbacks so render replacements remain observable.
const accountActions = createAccountActions({ api, loadAddresses: (...args) => loadAddresses(...args), loadStores: (...args) => loadStores(...args), notify: (...args) => notify(...args), persistAccountHint: (...args) => persistAccountHint(...args), render: (...args) => render(...args), renderPage: (...args) => renderPage(...args), state });
const adminActions = createAdminActions({ $, adminProfilePayload, api, applyAdminProfileResponse, applyPublicCategories: (...args) => applyPublicCategories(...args), categoryOptions, editStoreDialog, esc, formatCpfMask, loadAdminData: (...args) => loadAdminData(...args), loadAdminList: (...args) => loadAdminList(...args), notify: (...args) => notify(...args), persistAccountHint: (...args) => persistAccountHint(...args), render: (...args) => render(...args), renderPage: (...args) => renderPage(...args), role, runOnce, state, storeApprovalPath });
const orderActions = createOrderActions({ api, formatStatus, loadCurrentPage: (...args) => loadCurrentPage(...args), navigate: (...args) => navigate(...args), notify: (...args) => notify(...args), render: (...args) => render(...args), state, stopCourierLocation: (...args) => stopCourierLocation(...args) });
const supportActions = createSupportActions({ $, api, esc, loadAdminData: (...args) => loadAdminData(...args), loadAdminList: (...args) => loadAdminList(...args), notify: (...args) => notify(...args), pill, render: (...args) => render(...args), renderPage: (...args) => renderPage(...args), renderSupportReplyFields, role, shortDate, state, supportDetailPath, supportMessagePath });
const shoppingViews = createShoppingViews({ $, addToCart: (...args) => addToCart(...args), addressText, api, apiAssetUrl, catalogItemIcon, checkout: (...args) => checkout(...args), cleanupStoreMaps, closeCart: (...args) => closeCart(...args), currentStoreOpen: (...args) => currentStoreOpen(...args), emptyState: (...args) => emptyState(...args), esc, fulfillmentBadges, isCustomerOutsideStoreCity: (...args) => isCustomerOutsideStoreCity(...args), localDateTimeInZoneToIso, lookupCep: (...args) => lookupCep(...args), money, mountAvailableSlots, mountStoreDirectoryMap, navigate: (...args) => navigate(...args), notify: (...args) => notify(...args), render: (...args) => render(...args), renderPublicHours, saveCheckoutAddress: (...args) => saveCheckoutAddress(...args), state, storeHoursStatusRelevant, storeMapUrl, storeOpenAt, storeScheduleAllowsAppointment });

bindEvents();

// Rebind page-specific events after each render. The app shell remains delegated.
const originalRenderPage = renderPage;
renderPage = function(){
  const el=document.activeElement,keep=el&&el.id&&el.matches?.('#page-content textarea, #page-content input')?{id:el.id,value:el.value,start:el.selectionStart,end:el.selectionEnd}:null;
  originalRenderPage();bindPageActions();
  if(keep){const next=document.getElementById(keep.id);if(next){if(next.value!==keep.value)next.value=keep.value;next.focus({preventScroll:true});try{next.setSelectionRange(keep.start,keep.end);}catch{/* tipos sem seleção */}}}
};

initializeAccessibility();
boot();
