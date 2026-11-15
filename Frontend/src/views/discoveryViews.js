/** Presentation only; state and domain helpers are supplied by the application. */
export function createDiscoveryViews({ apiAssetUrl, categoryEmoji, categoryMatches, categoryOptions, categoryStyles, emptyState, esc, fulfillmentBadges, icon, state }) {
function renderHome() {
  const count=state.filteredStores.length;
  const sectionCategories=categoryOptions.slice(1).map(category=>({category,stores:state.filteredStores.filter(store=>categoryMatches(store.category,category))})).filter(section=>section.stores.length);
  const ranked=categoryOptions.slice(1).map((name,order)=>({name,emoji:categoryEmoji[name]||'✦',activeStores:state.stores.filter(store=>categoryMatches(store.category,name)).length,order})).filter(item=>item.activeStores>0).sort((a,b)=>b.activeStores-a.activeStores||a.order-b.order).slice(0,6);
  const storeSections=state.category==='Todas'&&!state.search.trim()
    ? sectionCategories.map(section=>`<section class="store-category-section"><div class="section-head"><div><span class="eyebrow">${esc(section.category)}</span><h2>${esc(section.category)}</h2></div><button data-category="${esc(section.category)}">Ver todas →</button></div><div class="store-grid">${section.stores.slice(0,4).map((store,index)=>storeCard(store,index)).join('')}</div></section>`).join('')
    : state.filteredStores.length?`<div class="store-grid">${state.filteredStores.map((store,index)=>storeCard(store,index)).join('')}</div>`:'';
  const activeCity=state.browseLocation?.city||state.account?.city,activeUf=state.browseLocation?.state||state.account?.state;const city=activeCity?`${activeCity}${activeUf?`, ${activeUf}`:''}`:'';
  return `<section class="home-location"><span class="home-location-icon">${icon('pin',20)}</span><span><strong>${city?`Lojas e serviços em ${esc(city)}`:'Escolha sua cidade para encontrar lojas próximas'}</strong><small>${city?'Mostrando estabelecimentos disponíveis nesta região.':'Informe seu endereço para ver estabelecimentos da sua região.'}</small></span><button class="btn outline small" data-location-picker>${city?'Alterar cidade':'Escolher cidade'}</button></section><section class="hero"><div class="hero-copy"><span class="eyebrow">Um bairro inteiro para descobrir</span><h1>Seu dia fica melhor<br/>quando é por perto.</h1><p>Peça seu favorito, marque um horário e apoie quem faz parte da sua rotina.</p><button data-scroll-stores>Explorar agora <span>→</span></button></div><div class="hero-art">🥗</div><div class="hero-note">Hoje é dia de <b>pedir algo gostoso</b> ✨</div></section>
    <section class="discovery-panel" aria-label="Categorias de lugares"><div class="discovery-heading"><div><span class="eyebrow">Escolha o seu momento</span><h2>O que combina com hoje?</h2><p>Explore os comércios da sua cidade por tipo.</p></div><span class="home-results-count"><strong>${count}</strong><span>${count===1?'lugar encontrado':'lugares encontrados'}</span></span></div>
    <div class="category-tiles" role="group" aria-label="Categorias em destaque">${ranked.map((item,index)=>categoryTile(item,index)).join('')}<button type="button" class="category-tile category-tile-all" data-page="categories"><span class="category-tile-icon">${icon('grid',29)}</span><strong>Todas as categorias</strong><small>Explore todos os tipos</small><b>Ver categorias <span aria-hidden="true">→</span></b></button></div></section>
    <section class="panel store-public-map" data-store-directory-map></section>
    <div class="section-head discovery-results-heading" id="stores-heading"><div><span class="eyebrow">Perto de você</span><h2>${state.category==='Todas'?'Descubra por perto':`Lugares em ${esc(state.category)}`}</h2></div><button data-category="Todas">Ver todos <span aria-hidden="true">→</span></button></div>
    ${storeSections||emptyState('⌕',state.stores.length?'Ainda não encontramos lugares por aqui':'Ainda não há lojas disponíveis nesta cidade',state.stores.length?'Tente outra busca ou escolha uma categoria diferente.':`Não encontramos estabelecimentos aprovados em ${city||'uma cidade selecionada'}. As lojas aparecem na região em que foram cadastradas; escolha outra cidade para continuar explorando.` ,state.stores.length?'Atualizar lista':'Ver outra cidade',state.stores.length?'refresh-stores':'choose-city')}`;
}

function categoryTile(item,index=0){
  const stores=state.stores.filter(store=>categoryMatches(store.category,item.name)).length;
  return `<button type="button" class="category-tile category-tone-${index%6}" data-category-select="${esc(item.name)}"><span class="category-tile-icon">${esc(item.emoji||categoryEmoji[item.name]||'✦')}</span><strong>${esc(item.name)}</strong><small>${stores} ${stores===1?'loja disponível':'lojas disponíveis'}</small><b>Explorar <span aria-hidden="true">→</span></b></button>`;
}

function renderAllCategories(){
  const categories=categoryOptions.slice(1).map((name,index)=>({name,emoji:categoryEmoji[name]||'✦',activeStores:state.stores.filter(store=>categoryMatches(store.category,name)).length,index}));
  return `<div class="page-head"><div><span class="eyebrow">Comércio local</span><h1>Todas as categorias</h1><p>Escolha o que procura e veja os estabelecimentos disponíveis na sua cidade.</p></div><button class="btn outline" data-page="home">← Voltar para descobrir</button></div><div class="category-tiles category-tiles-all">${categories.map((item,index)=>categoryTile(item,index)).join('')}</div>`;
}

function renderCategoryStores(){
  const selected=state.category==='Todas'?'':state.category;
  const stores=state.stores.filter(store=>selected&&categoryMatches(store.category,selected));
  return `<div class="page-head category-store-head"><div><span class="eyebrow">Estabelecimentos na sua cidade</span><h1>${esc(selected||'Escolha uma categoria')}</h1><p>${stores.length} ${stores.length===1?'opção disponível':'opções disponíveis'}${state.browseLocation?.city?` em ${esc(state.browseLocation.city)}, ${esc(state.browseLocation.state||'')}`:''}.</p></div><button class="btn outline" data-page="categories">← Todas as categorias</button></div><section class="panel store-public-map" data-store-directory-map></section>${stores.length?`<div class="store-grid">${stores.map((store,index)=>storeCard(store,index)).join('')}</div>`:emptyState('⌕','Ainda não há estabelecimentos nesta categoria','Volte para a lista completa e escolha outro tipo de comércio.','Ver todas as categorias','go-categories')}`;
}

function storeCard(store,index=0) {
  const style=categoryStyles[store.category]||['orange','green','purple','blue','gold','rose'][index%6];
  const emoji=categoryEmoji[store.category]||'🥘';
  const cover=store.coverImageUrl?`style="background-image:linear-gradient(180deg,rgba(20,25,22,.02),rgba(20,25,22,.42)),url('${esc(apiAssetUrl(store.coverImageUrl))}')"`:'';
  return `<article class="store-card" data-store="${esc(store.id)}" tabindex="0" role="button" aria-label="Abrir ${esc(store.name)}"><div class="store-cover ${style} ${store.coverImageUrl?'has-cover':''}" ${cover}><span class="store-badge">${esc(store.category||'Comércio local')}</span>${store.coverImageUrl?'':`<span>${emoji}</span>`}</div><div class="store-card-body"><div class="store-title-row"><h3>${esc(store.name)}</h3><span class="status-pill">Local</span></div><p class="store-desc">${esc(store.description||'Uma boa escolha pertinho de você.')}</p><div class="store-meta"><span>⌖ ${esc(store.address||'Na sua região')}</span></div>${fulfillmentBadges(store)}<div class="store-card-foot"><span>Consulte opções e valores</span><strong>Ver opções →</strong></div></div></article>`;
}
  return { renderHome, categoryTile, renderAllCategories, renderCategoryStores, storeCard };
}
