import { api } from './api.js';
import { formatCnpjMask, formatCurrencyValue, parseCurrencyDecimal } from './inputMasks.js';
import { coordinatesFromForm } from './storeLocation.js';

/** DOM-based editor avoids interpolating user data into HTML attributes. */
export function editStoreDialog(store,categories,{onSaved,onError}) {
  const overlay=document.createElement('div');overlay.className='modal-backdrop';
  overlay.innerHTML='<section class="modal admin-edit-modal"><div class="modal-head"><h2>Editar dados da loja</h2><button class="close-button" type="button" aria-label="Fechar">×</button></div><p>Confira as informações antes de salvar. Aprovação e suspensão são controladas na lista de lojas.</p><form class="form-grid"></form></section>';
  const form=overlay.querySelector('form');
  const fields=[['name','Nome','text',store.name],['category','Categoria','select',store.category],['address','Endereço','text',store.address],['city','Cidade','text',store.city],['state','Estado (UF)','text',store.state],['cnpj','CNPJ (opcional)','text',formatCnpjMask(store.cnpj||'')],['deliveryFee','Taxa de entrega','text',formatCurrencyValue((store.deliveryFeeCents||0)/100)],['timezone','Fuso horário','text',store.timezone||'America/Sao_Paulo'],['latitude','Latitude (opcional)','text',store.latitude??''],['longitude','Longitude (opcional)','text',store.longitude??''],['description','Descrição','textarea',store.description||'']];
  for(const [name,title,type,value] of fields){
    const label=document.createElement('label');label.className='field';label.append(document.createTextNode(title));
    const input=document.createElement(type==='select'?'select':type==='textarea'?'textarea':'input');input.name=name;
    if(input.tagName==='INPUT')input.type=type;
    if(type==='select'){for(const category of categories){const option=document.createElement('option');option.value=category;option.textContent=category;input.append(option);}}
    input.value=String(value??'');input.required=['name','category','address','city','state','timezone'].includes(name);
    const limits={name:140,address:250,city:100,state:2,description:1000,cnpj:18,timezone:80};if(limits[name])input.maxLength=limits[name];
    if(name==='cnpj')input.addEventListener('input',()=>{input.value=formatCnpjMask(input.value);});
    label.append(input);form.append(label);
  }
  const save=document.createElement('button');save.type='submit';save.className='btn span-2';save.textContent='Salvar dados da loja';form.append(save);
  overlay.querySelector('.close-button').onclick=()=>overlay.remove();
  overlay.onclick=event=>{if(event.target===overlay)overlay.remove();};
  form.onsubmit=async event=>{
    event.preventDefault();if(save.disabled)return;save.disabled=true;
    try{const data=new FormData(form);const fee=parseCurrencyDecimal(data.get('deliveryFee'));if(!Number.isFinite(fee)||fee<0||fee>1000)throw new Error('Informe uma taxa entre R$ 0,00 e R$ 1.000,00.');
      const payload=Object.fromEntries(['name','description','address','category','city','state','cnpj','timezone'].map(key=>[key,String(data.get(key)||'').trim()]));
      payload.state=payload.state.toUpperCase();payload.cnpj=payload.cnpj.replace(/\D/g,'');payload.deliveryFeeCents=Math.round(fee*100);Object.assign(payload,coordinatesFromForm(data));
      const updated=await api(`/admin/stores/${encodeURIComponent(store.id)}/profile`,{method:'PUT',body:JSON.stringify(payload)});overlay.remove();await onSaved(updated);
    }catch(error){onError(error);}finally{save.disabled=false;}
  };
  document.body.append(overlay);return overlay;
}
