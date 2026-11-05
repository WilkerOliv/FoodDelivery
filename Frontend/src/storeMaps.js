import { api } from './api.js';

const maps=new Set();
export function cleanupStoreMaps(){for(const entry of maps)if(!entry.root.isConnected){entry.observer.disconnect();entry.map.remove();maps.delete(entry);}}

export async function loadLeaflet(){
  if(window.L)return window.L;
  if(!window.__foodDeliveryLeaflet){
    window.__foodDeliveryLeaflet=new Promise((resolve,reject)=>{
      const css=document.createElement('link');css.rel='stylesheet';css.href='https://cdn.jsdelivr.net/npm/leaflet@1.9.4/dist/leaflet.css';document.head.append(css);
      const script=document.createElement('script');script.src='https://cdn.jsdelivr.net/npm/leaflet@1.9.4/dist/leaflet.js';
      script.onload=()=>resolve(window.L);script.onerror=()=>{script.remove();css.remove();reject(new Error('O mapa não carregou. Tente novamente ou informe as coordenadas manualmente.'));};document.head.append(script);
    });
    window.__foodDeliveryLeaflet.catch(()=>{window.__foodDeliveryLeaflet=null;});
  }
  return window.__foodDeliveryLeaflet;
}
function createMap(L,root){
  const map=L.map(root,{scrollWheelZoom:false,zoomControl:false}).setView([-14.235,-51.925],4);
  L.control.zoom({position:'bottomright'}).addTo(map);
  const tiles=L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png',{maxZoom:19,attribution:'&copy; <a href="https://www.openstreetmap.org/copyright" target="_blank" rel="noopener noreferrer">OpenStreetMap contributors</a>'}).addTo(map);
  const message=document.createElement('p');message.className='store-map-tile-status';message.setAttribute('role','status');message.hidden=true;root.append(message);
  tiles.on('tileerror',()=>{message.hidden=false;message.textContent='Parte do mapa não carregou. Confira sua conexão; o endereço e a lista de lojas continuam disponíveis.';});
  tiles.on('tileload',()=>{message.hidden=true;});
  const observer=new ResizeObserver(()=>map.invalidateSize());observer.observe(root);maps.add({map,root,observer});return map;
}
function point(store){return store.latitude!=null&&store.longitude!=null&&Number.isFinite(Number(store.latitude))&&Number.isFinite(Number(store.longitude))&&Math.abs(Number(store.latitude))<=90&&Math.abs(Number(store.longitude))<=180;}
function markerIcon(L){return L.divIcon({className:'shop-map-pin',html:'<span aria-hidden="true">●</span>',iconSize:[30,38],iconAnchor:[15,38]});}

export function storeMapFields(store={}){
  // Coordinates are numbers from the API, never arbitrary interpolated text.
  const lat=point(store)?Number(store.latitude):'',lon=point(store)?Number(store.longitude):'';
  return `<section class="store-map-editor span-2" data-store-map-picker><div class="section-head"><div><h3>Localização no mapa <small>(opcional)</small></h3><p>Marque a entrada do comércio para facilitar a chegada. O ponto salvo ficará público quando a loja for aprovada.</p></div></div><button class="btn outline" type="button" data-map-toggle>Abrir mapa para marcar</button><div data-map-content hidden><label class="field"><span>Buscar endereço ou cidade</span><input name="mapSearch" maxlength="100" placeholder="Ex.: Rua Central, Nantes, SP"/></label><button class="btn outline" type="button" data-map-search>Localizar endereço</button><div class="store-map-results" data-map-results></div><div class="store-interactive-map" data-map-canvas role="region" aria-label="Mapa da loja: clique para marcar a entrada"></div><p class="store-desc">Clique no mapa ou arraste o marcador. Confira também o endereço escrito: marcar o mapa não altera cidade ou endereço.</p></div><p class="store-map-feedback" data-map-feedback role="status">${lat!==''?'Ponto definido. Confira no mapa antes de salvar.':'Sem marcação: sua loja continua disponível na lista.'}</p><button class="text-button" type="button" data-map-clear>Remover marcação</button><details><summary>Informar coordenadas manualmente</summary><div class="form-grid"><label class="field"><span>Latitude</span><input name="latitude" type="number" min="-90" max="90" step="any" value="${lat}"/></label><label class="field"><span>Longitude</span><input name="longitude" type="number" min="-180" max="180" step="any" value="${lon}"/></label></div></details></section>`;
}

export function mountLocationPicker(root){
  const form=root.closest('form'),lat=form.elements.latitude,lon=form.elements.longitude,feedback=root.querySelector('[data-map-feedback]'),toggle=root.querySelector('[data-map-toggle]'),content=root.querySelector('[data-map-content]');
  let map=null,marker=null,loading=false,searchTicket=0;
  function selected(){return lat.value!==''&&lon.value!==''?{latitude:Number(lat.value),longitude:Number(lon.value)}:{};}
  function move(latitude,longitude){
    const selected={latitude,longitude};if(!point(selected))return;
    lat.value=latitude.toFixed(6);lon.value=longitude.toFixed(6);feedback.textContent='Ponto marcado. Use “Salvar perfil” ou envie o cadastro para confirmar.';
    if(map){if(marker)marker.setLatLng([latitude,longitude]);else{marker=window.L.marker([latitude,longitude],{draggable:true,icon:markerIcon(window.L),title:'Entrada do comércio'}).addTo(map);marker.on('dragend',()=>{const p=marker.getLatLng();move(p.lat,p.lng);});}}
  }
  async function open(){
    if(map){content.hidden=false;map.invalidateSize();return true;}if(loading)return false;
    loading=true;toggle.disabled=true;feedback.textContent='Carregando mapa…';
    try{const L=await loadLeaflet();if(!root.isConnected)return false;content.hidden=false;map=createMap(L,root.querySelector('[data-map-canvas]'));map.on('click',e=>move(e.latlng.lat,e.latlng.lng));const p=selected();if(point(p)){map.setView([p.latitude,p.longitude],17);move(p.latitude,p.longitude);}else feedback.textContent='Busque o endereço e clique na entrada do comércio.';toggle.textContent='Mapa aberto: marque a entrada';return true;}
    catch(error){feedback.textContent=error.message;return false;}finally{loading=false;toggle.disabled=false;}
  }
  toggle.onclick=open;
  root.querySelector('[data-map-clear]').onclick=()=>{searchTicket++;lat.value='';lon.value='';if(marker){marker.remove();marker=null;}feedback.textContent='Marcação removida. Salve o perfil para confirmar. O endereço escrito será mantido.';};
  for(const input of [lat,lon])input.addEventListener('change',()=>{const p=selected();if(point(p)){move(p.latitude,p.longitude);map?.setView([p.latitude,p.longitude],17);}});
  root.querySelector('[data-map-search]').onclick=async()=>{
    const query=form.elements.mapSearch.value.trim()||['city','state','address'].map(k=>form.elements[k]?.value||'').filter(Boolean).join(', ').slice(0,100);
    if(query.length<3){feedback.textContent='Digite o endereço ou a cidade para buscar.';return;}
    const ticket=++searchTicket,results=root.querySelector('[data-map-results]');results.replaceChildren();feedback.textContent='Buscando endereço…';
    try{const found=await api('/geography/search?query='+encodeURIComponent(query));if(ticket!==searchTicket||!root.isConnected)return;
      const places=found.filter(p=>point({latitude:p.lat,longitude:p.lon}));if(!places.length){feedback.textContent='Endereço não encontrado. Você pode navegar no mapa e marcar o ponto manualmente.';return;}
      feedback.textContent='Escolha o resultado e ajuste o marcador na entrada exata.';
      for(const place of places){const button=document.createElement('button');button.type='button';button.className='store-map-result';button.textContent=place.display_name;button.onclick=()=>{move(Number(place.lat),Number(place.lon));map?.setView([Number(place.lat),Number(place.lon)],17);results.replaceChildren();};results.append(button);}
    }catch(error){if(ticket===searchTicket)feedback.textContent=error.message;}
  };
  form.elements.mapSearch.addEventListener('keydown',e=>{if(e.key==='Enter'){e.preventDefault();root.querySelector('[data-map-search]').click();}});
}

export function mountStoreDirectoryMap(root,stores,{onOpen=null,label='Ver lojas no mapa'}={}){
  const located=stores.filter(point),missing=stores.length-located.length;
  const button=document.createElement('button');button.type='button';button.className='btn outline';button.textContent=label;button.disabled=!located.length;
  const hint=document.createElement('p');hint.className='store-desc';hint.textContent=located.length?`${located.length} ${located.length===1?'loja com ponto marcado':'lojas com ponto marcado'}${missing?` · ${missing} sem marcação, disponíveis na lista`:''}.`:'Nenhuma loja deste filtro marcou a localização. Confira os estabelecimentos na lista.';
  root.replaceChildren(button,hint);let map=null;
  button.onclick=async()=>{if(map)return;button.disabled=true;
    try{const L=await loadLeaflet();if(!root.isConnected)return;const box=document.createElement('div');box.className='store-interactive-map';box.setAttribute('role','region');box.setAttribute('aria-label','Mapa de estabelecimentos');root.append(box);map=createMap(L,box);
      const bounds=[];
      for(const store of located){const coords=[Number(store.latitude),Number(store.longitude)];bounds.push(coords);const popup=document.createElement('div');popup.className='shop-map-popup';const title=document.createElement('strong');title.textContent=store.name;const address=document.createElement('p');address.textContent=store.address||'';const modes=document.createElement('p');modes.textContent=[store.deliveryEnabled!==false?(store.pickupEnabled===false&&store.inPersonEnabled===false?'Somente delivery':'Entrega'):null,store.pickupEnabled!==false?'Retirada':null,store.inPersonEnabled!==false?'Agendamento presencial':null].filter(Boolean).join(' · ');const open=document.createElement('button');open.type='button';open.className='btn small';open.textContent='Ver loja';open.onclick=()=>onOpen?.(store.id);popup.append(title,address,modes);if(onOpen)popup.append(open);L.marker(coords,{icon:markerIcon(L),title:store.name,alt:store.name}).addTo(map).bindPopup(popup);}
      if(bounds.length===1)map.setView(bounds[0],16);else map.fitBounds(bounds,{padding:[35,35],maxZoom:16});button.textContent='Lojas no mapa';
      // Keyboard users can open exactly the same stores without operating the map.
      const list=document.createElement('div');list.className='map-store-links';for(const store of onOpen?located:[]){const link=document.createElement('button');link.type='button';link.className='btn outline small';link.textContent=store.name;link.onclick=()=>onOpen(store.id);list.append(link);}root.append(list);
    }catch(error){hint.textContent=error.message;button.disabled=false;}
  };
}
