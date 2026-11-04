export async function postalCodeResponse(cep, options={}) {
  if(!globalThis.FOODDELIVERY_DEMO)return fetch(`https://viacep.com.br/ws/${cep}/json/`,options);
  const data=cep==='19645000'?{cep:'19645-000',localidade:'Nantes',uf:'SP'}:cep==='19640000'?{cep:'19640-000',localidade:'Iepê',uf:'SP'}:{erro:true};
  return {ok:true,json:async()=>({...data,logradouro:'Rua de Demonstração',bairro:'Centro'})};
}
