/** Feature controller; shared state, API and navigation are injected explicitly. */
export function createAccountActions({ api, loadAddresses, loadStores, notify, persistAccountHint, render, renderPage, state }) {
async function requestOperatorAccess(event){event.preventDefault();const roleName=String(new FormData(event.currentTarget).get('role'));try{await api('/account/platform-requests/roles',{method:'POST',body:JSON.stringify({role:roleName})});state.platformRequests=await api('/account/platform-requests');notify('Solicitação enviada','O administrador analisará o novo perfil vinculado à sua conta.','info');render();}catch(error){notify('Não foi possível solicitar o acesso',error.message,'error');}}

async function requestCityAccess(event){event.preventDefault();const form=new FormData(event.currentTarget);try{await api('/account/platform-requests/cities',{method:'POST',body:JSON.stringify({city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),postalCode:String(form.get('postalCode')||'').trim()||null})});state.platformRequests=await api('/account/platform-requests');notify('Cidade solicitada','A administração recebeu o pedido e fará a análise.','info');render();}catch(error){notify('Não foi possível solicitar a cidade',error.message,'error');}}

async function saveProfile(event) {
  event.preventDefault();const form=new FormData(event.currentTarget);
  try{state.profile=await api('/account/profile',{method:'PUT',body:JSON.stringify(Object.fromEntries(['name','cpf','phone','addressLine','addressComplement','neighborhood','city','state','postalCode'].map(k=>[k,String(form.get(k)||'').trim()||null]))) });state.account={...state.account,...state.profile,activeRole:state.activeRole};persistAccountHint(state.account);if(state.account.grantedRoles?.includes('CUSTOMER')&&state.profile.addressLine&&state.profile.neighborhood&&state.profile.city&&state.profile.state&&state.profile.postalCode){const current=state.addresses.find(a=>a.defaultAddress);const body={label:current?.label||'Casa',addressLine:state.profile.addressLine,addressComplement:state.profile.addressComplement,neighborhood:state.profile.neighborhood,city:state.profile.city,state:state.profile.state,postalCode:state.profile.postalCode,defaultAddress:true};if(current)await api(`/account/addresses/${current.id}`,{method:'PUT',body:JSON.stringify(body)});else await api('/account/addresses',{method:'POST',body:JSON.stringify(body)});await loadAddresses();}if(state.page==='account'&&state.account.grantedRoles?.includes('CUSTOMER'))await loadStores().catch(()=>{});notify('Perfil atualizado','Seus dados foram salvos com sucesso.');render();}
  catch(error){notify('Não conseguimos salvar seus dados',error.message,'error');}
}

async function changePassword(event){event.preventDefault();const form=new FormData(event.currentTarget);const currentPassword=String(form.get('currentPassword')),newPassword=String(form.get('newPassword'));if(newPassword!==String(form.get('confirmPassword'))){notify('As senhas não conferem','Digite a mesma senha nos dois campos.','error');return;}try{const response=await api('/account/password',{method:'POST',body:JSON.stringify({currentPassword,newPassword})});state.account={...state.account,...response.account,activeRole:state.activeRole};state.profile=state.account;state.mustChangePassword=false;persistAccountHint(state.account);notify('Senha alterada','Sua conta está protegida com a nova senha.');render();}catch(error){notify('Não foi possível alterar a senha',error.message,'error');}}

async function requestPhoneVerification(){try{await api('/account/phone-verification',{method:'POST'});state.phoneVerificationSent=true;notify('Código solicitado','Se o WhatsApp estiver configurado, você receberá um código no telefone cadastrado.');renderPage();}catch(error){notify('Não foi possível solicitar o código',error.message,'error');}}

async function confirmPhoneVerification(event){event.preventDefault();const code=String(new FormData(event.currentTarget).get('code')||'').trim();try{await api('/account/phone-verification/confirm',{method:'POST',body:JSON.stringify({code})});state.profile={...state.profile,phoneVerified:true};state.account={...state.account,phoneVerified:true};state.phoneVerificationSent=false;persistAccountHint(state.account);notify('Telefone confirmado','A recuperação por WhatsApp está habilitada para esta conta.');renderPage();}catch(error){notify('Código não confirmado',error.message,'error');}}

async function setDefaultAddress(id) {
  try { await api(`/account/addresses/${id}/default`,{method:'PATCH'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);notify('Endereço principal atualizado','Este será o endereço sugerido nos próximos pedidos.');render(); }
  catch(error){notify('Não foi possível atualizar o endereço',error.message,'error');}
}

async function deleteAddress(id) {
  if(!window.confirm('Remover este endereço salvo?'))return;
  try { await api(`/account/addresses/${id}`,{method:'DELETE'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);notify('Endereço removido','O endereço foi retirado da sua lista.','info');render(); }
  catch(error){notify('Não foi possível remover o endereço',error.message,'error');}
}
  return { requestOperatorAccess, requestCityAccess, saveProfile, changePassword, requestPhoneVerification, confirmPhoneVerification, setDefaultAddress, deleteAddress };
}
