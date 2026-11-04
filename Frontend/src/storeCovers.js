import { api, apiAssetUrl } from './api.js';

export const COVER_WIDTH=1200,COVER_HEIGHT=600,COVER_MAX_BYTES=5*1024*1024;

/** Calculate a centered crop or contain rectangle without stretching a photo/logo. */
export function coverPlacement(width,height,mode='contain') {
  if(!Number.isFinite(width)||!Number.isFinite(height)||width<1||height<1||width>4096||height>4096)throw new Error('A imagem deve ter até 4.096 × 4.096 pixels.');
  const scale=mode==='crop'?Math.max(COVER_WIDTH/width,COVER_HEIGHT/height):Math.min(COVER_WIDTH/width,COVER_HEIGHT/height);
  return {x:(COVER_WIDTH-width*scale)/2,y:(COVER_HEIGHT-height*scale)/2,width:width*scale,height:height*scale};
}

export async function mountStoreCover(root,store,{onSaved=()=>{},onError=()=>{}}={}) {
  root.innerHTML=`<div class="section-head"><div><span class="eyebrow">Identidade do seu comércio</span><h3>Capa da loja</h3><p>Envie uma foto do comércio ou sua logo. A publicação depende da aprovação do administrador.</p></div><span class="cover-ratio-badge">2:1 · 1.200 × 600 px</span></div><div class="cover-published"></div><p class="cover-status" role="status">Consultando o último envio…</p><div class="cover-submission"></div><form class="cover-upload-form"><label class="field"><span>Escolher foto ou logo</span><input type="file" name="cover" accept="image/png,image/jpeg" required/><small>PNG ou JPG, até 5 MB e 4.096 × 4.096 pixels. A prévia será ajustada ao tamanho da capa.</small></label><label class="field"><span>Como ajustar a imagem?</span><select name="fit"><option value="contain">Logo: mostrar tudo</option><option value="crop">Foto: preencher a capa</option></select><small>Logo: preserva a imagem inteira com margens. Foto: preenche a capa com recorte central, sem distorção.</small></label><div class="cover-local-preview" hidden><canvas width="1200" height="600" role="img" aria-label="Prévia da capa antes do envio"></canvas><p>Confira o enquadramento. Esta prévia ainda não foi enviada nem publicada.</p></div><p class="cover-error" role="alert"></p><button class="btn" type="submit" disabled>Enviar para aprovação</button></form>`;
  const form=root.querySelector('form'),file=form.elements.cover,fit=form.elements.fit,canvas=root.querySelector('canvas'),submit=form.querySelector('button');
  const error=root.querySelector('.cover-error'),status=root.querySelector('.cover-status');
  let image=null,reading=0,busy=false;
  const showImage=(container,url,alt)=>{const img=document.createElement('img');img.src=apiAssetUrl(url);img.alt=alt;img.className='store-cover-preview';container.replaceChildren(img);};
  function draw(){
    if(!image)return;
    const context=canvas.getContext('2d'),p=coverPlacement(image.naturalWidth,image.naturalHeight,fit.value);
    context.fillStyle='#fff';context.fillRect(0,0,COVER_WIDTH,COVER_HEIGHT);
    context.drawImage(image,p.x,p.y,p.width,p.height);
    root.querySelector('.cover-local-preview').hidden=false;submit.disabled=false;
  }
  file.addEventListener('change',()=>{
    const selected=file.files?.[0],ticket=++reading;image=null;submit.disabled=true;error.textContent='';root.querySelector('.cover-local-preview').hidden=true;
    if(!selected)return;
    if(!['image/png','image/jpeg'].includes(selected.type)||selected.size>COVER_MAX_BYTES){error.textContent='Escolha um arquivo PNG ou JPG de até 5 MB.';file.value='';return;}
    const url=URL.createObjectURL(selected),loaded=new Image();
    loaded.onload=()=>{URL.revokeObjectURL(url);if(ticket!==reading||!root.isConnected)return;try{coverPlacement(loaded.naturalWidth,loaded.naturalHeight);image=loaded;draw();}catch(e){error.textContent=e.message;file.value='';}};
    loaded.onerror=()=>{URL.revokeObjectURL(url);if(ticket===reading){error.textContent='Não foi possível ler essa imagem. Escolha outro arquivo.';file.value='';}};
    loaded.src=url;
  });
  fit.addEventListener('change',draw);
  async function refresh(){
    try{const result=await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover-submission`);if(!root.isConnected)return;
      const published=root.querySelector('.cover-published');published.replaceChildren();
      if(result.publishedUrl){const label=document.createElement('p');label.textContent='Capa aprovada';published.append(label);const frame=document.createElement('div');published.append(frame);showImage(frame,result.publishedUrl,'Capa aprovada da loja');}
      const s=result.submission,container=root.querySelector('.cover-submission');container.replaceChildren();
      const labels={PENDING:'Aguardando aprovação. A capa atual continua sendo exibida.',APPROVED:'Última imagem aprovada.',REJECTED:'Imagem não aprovada. Confira o motivo e envie outra.',SUPERSEDED:'Este envio foi substituído por uma imagem mais recente.'};
      status.textContent=s?labels[s.status]||'Confira o último envio.':'Nenhuma imagem enviada para análise.';
      if(s&&s.status!=='APPROVED'){showImage(container,s.imageUrl,'Imagem enviada para análise');if(s.reviewNote){const note=document.createElement('p');note.textContent='Motivo: '+s.reviewNote;container.append(note);}}
    }catch(e){if(root.isConnected){status.textContent='Não foi possível consultar o envio. Reabra esta seção para tentar novamente.';onError(e);}}
  }
  form.addEventListener('submit',async event=>{
    event.preventDefault();if(!image||busy)return;busy=true;submit.disabled=true;file.disabled=true;fit.disabled=true;error.textContent='';submit.textContent='Enviando imagem…';
    try{const dataUrl=canvas.toDataURL('image/png');if(dataUrl.length>7_000_000)throw new Error('A imagem processada ultrapassa 5 MB. Escolha uma imagem mais simples.');
      await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover`,{method:'POST',body:JSON.stringify({dataUrl})});
      if(!root.isConnected)return;image=null;file.value='';root.querySelector('.cover-local-preview').hidden=true;await refresh();onSaved();
    }catch(e){if(root.isConnected)error.textContent=e.message;}
    finally{busy=false;submit.disabled=!image;file.disabled=false;fit.disabled=false;submit.textContent='Enviar para aprovação';}
  });
  await refresh();
}

export async function mountCoverReview(root,{onSaved=()=>{}}={}) {
  root.innerHTML='<p role="status">Carregando imagens para aprovação…</p>';
  try{const submissions=await api('/admin/store-covers');if(!root.isConnected)return;root.replaceChildren();
    if(!submissions.length){const p=document.createElement('p');p.className='cover-empty';p.textContent='Nenhuma imagem aguardando aprovação.';root.append(p);return;}
    for(const s of submissions){
      const card=document.createElement('article');card.className='panel cover-review-card';
      const title=document.createElement('h3');title.textContent=s.storeName;
      const owner=document.createElement('p');owner.textContent='Vendedor: '+s.ownerName;
      const img=document.createElement('img');img.src=apiAssetUrl(s.imageUrl);img.alt='Capa enviada por '+s.storeName;img.className='store-cover-preview';
      const form=document.createElement('form');form.innerHTML='<label class="field"><span>Motivo da recusa ou orientação ao vendedor</span><textarea name="note" maxlength="500" placeholder="Obrigatório para recusar a imagem"></textarea></label><p class="cover-error" role="alert"></p><div class="action-row"><button class="btn" type="submit" value="approve">Aprovar e publicar</button><button class="btn danger" type="submit" value="reject">Recusar imagem</button></div>';
      form.addEventListener('submit',async event=>{event.preventDefault();const approved=event.submitter?.value==='approve',note=form.elements.note.value.trim(),error=form.querySelector('.cover-error');error.textContent='';if(!approved&&!note){error.textContent='Informe o motivo para o vendedor corrigir a imagem.';form.elements.note.focus();return;}
        const buttons=[...form.querySelectorAll('button')];if(buttons.some(b=>b.disabled))return;buttons.forEach(b=>b.disabled=true);
        try{await api(`/admin/store-covers/${encodeURIComponent(s.id)}/review`,{method:'PATCH',body:JSON.stringify({approved,note})});await mountCoverReview(root,{onSaved});onSaved(approved);}
        catch(e){error.textContent=e.message;buttons.forEach(b=>b.disabled=false);}
      });
      card.append(title,owner,img,form);root.append(card);
    }
  }catch(e){if(root.isConnected){root.replaceChildren();const p=document.createElement('p');p.className='cover-error';p.setAttribute('role','alert');p.textContent=e.message;const retry=document.createElement('button');retry.className='btn outline';retry.textContent='Tentar novamente';retry.onclick=()=>mountCoverReview(root,{onSaved});root.append(p,retry);}}
}
