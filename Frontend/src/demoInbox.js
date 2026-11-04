import { api } from './api.js';
const status=document.querySelector('#status');
async function refresh(){
  if(!globalThis.FOODDELIVERY_DEMO){status.textContent='Disponível somente no ambiente de homologação.';return;}
  try{const messages=await api('/demo/messages');const target=document.querySelector('#messages');target.replaceChildren();
    for(const message of messages){const card=document.createElement('article');card.className='order-card';card.textContent=`${message.channel} · ${message.recipient} · Código: ${message.code} · ${new Date(message.createdAt).toLocaleString('pt-BR')}`;target.append(card);}
    status.textContent=messages.length?`${messages.length} mensagem(ns) capturada(s).`:'Nenhuma mensagem capturada. Solicite recuperação de senha no site.';
  }catch(error){status.textContent=error.message;}
}
document.querySelector('#demo-login').addEventListener('submit',async event=>{event.preventDefault();const form=new FormData(event.currentTarget);try{await api('/auth/login',{method:'POST',body:JSON.stringify(Object.fromEntries(form))});await refresh();}catch(error){status.textContent=error.message;}});
document.querySelector('#refresh').addEventListener('click',refresh);
refresh();
