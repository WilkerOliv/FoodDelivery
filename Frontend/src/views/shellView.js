import { mobileNavigation } from '../mobileNavigation.js';

export function shellMarkup({ state, links, role, roleLabel, canShop, compact, esc, icon, initials }) {
  const mobileNav = mobileNavigation(links, role);
  const hasUnread = state.notifications.some(notification => !notification.read);
  const homePage = { CUSTOMER: 'home', MERCHANT: 'merchant', COURIER: 'courier', ADMIN: 'admin' }[role] || 'admin';
  const city = state.browseLocation?.city || state.account.city;
  const region = state.browseLocation?.state || state.account.state;
  const locationLabel = city ? [city, region].filter(Boolean).join(', ') : 'Escolher minha cidade';
  const avatar = esc(initials(state.account.name));
  return `<div class="app-shell ${state.sidebarCollapsed?'sidebar-collapsed':''}">
    <div class="mobile-nav-backdrop ${state.mobileMenu?'show':''}" id="nav-backdrop"></div>
    <aside class="sidebar ${state.mobileMenu?'open':''}">
      <a href="#" class="brand" data-page="${homePage}"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">${esc(roleLabel)}</small></div></a>
      <div class="nav-label">Menu principal</div><nav class="nav-list">${links.map(([page,iconName,label])=>`<button class="nav-item ${state.page===page?'active':''}" data-page="${page}"><span class="nav-icon">${icon(iconName)}</span>${label}${page==='notifications'&&hasUnread?'<span class="nav-count">novo</span>':''}</button>`).join('')}</nav>
      <div class="side-bottom"><div class="help-card"><strong>${role==='ADMIN'?'Central de atendimento':'Precisa de uma mão?'}</strong><p>${role==='ADMIN'?'Acompanhe e responda as solicitações de suporte.':'Estamos aqui para ajudar você a aproveitar melhor o FoodDelivery.'}</p><button data-help>${role==='ADMIN'?'Ver chamados →':'Falar com atendimento →'}</button></div>
      <div class="profile-mini"><div class="avatar">${avatar}</div><div><strong>${esc(state.account.name)}</strong><small>${esc(roleLabel)}</small></div><button class="profile-more" data-page="account" aria-label="Abrir ${role==='ADMIN'?'definições':'minha conta'}">${role==='ADMIN'?'Definições':'Minha conta'}</button></div></div>
    </aside>
    <nav class="mobile-bottom-nav" aria-label="Navegação principal" style="--mobile-nav-count:${mobileNav.visible.length+(mobileNav.hasMore?1:0)}">${mobileNav.visible.map(([page,iconName,label])=>`<button class="mobile-nav-item ${state.page===page?'active':''}" data-page="${page}" aria-label="${label}" title="${label}"><span>${icon(iconName)}</span><small>${label}</small>${page==='notifications'&&hasUnread?'<i class="mobile-nav-dot"></i>':''}</button>`).join('')}${mobileNav.hasMore?`<button class="mobile-nav-item mobile-nav-more ${state.mobileMenu?'active':''}" data-mobile-more aria-expanded="${state.mobileMenu}" aria-label="Mais opções" title="Mais opções"><span>${icon('grid')}</span><small>Menu</small></button>`:''}</nav>
    <div class="main-column"><header class="topbar">
      <button class="icon-button mobile-menu" id="mobile-menu" aria-label="${state.mobileMenu?'Fechar menu':'Abrir ou recolher menu'}" aria-expanded="${compact?state.mobileMenu:!state.sidebarCollapsed}">☰</button>
      <button class="page-location location-button" data-location-picker aria-label="Alterar cidade de entrega"><span class="location-pin">${icon('pin')}</span><span><small>Lojas em destaque na cidade</small><strong>${esc(locationLabel)} <span aria-hidden="true">⌄</span></strong></span></button>
      <label class="top-search"><span aria-hidden="true">⌕</span><input id="global-search" type="search" aria-label="Buscar lojas, produtos ou serviços" placeholder="Buscar lojas, pratos ou serviços" value="${esc(state.search)}" /></label>
      <div class="top-actions"><button class="icon-button" data-page="notifications" aria-label="Notificações">${icon('bell')}${hasUnread?'<i class="dot"></i>':''}</button>${canShop?`<button class="cart-top" id="open-cart">Sacola <b>${state.cart.reduce((n,i)=>n+i.quantity,0)}</b></button>`:''}<button class="top-account" data-page="account" title="Minha conta">${avatar}</button><button class="header-logout" data-logout aria-label="Sair da conta">Sair</button></div>
    </header><main class="content" id="page-content"></main></div>
  </div>`;
}
