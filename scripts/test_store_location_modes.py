"""Location/fulfillment contracts; exclusively the isolated demo database."""
import datetime
import urllib.request
import uuid
from test_homologacao import BASE,Client

KEYS='name description address category city state cnpj active deliveryFeeCents timezone latitude longitude deliveryEnabled pickupEnabled inPersonEnabled'.split()
def payload(store,**changes):return {**{k:store.get(k) for k in KEYS},**changes}

def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js') as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read()
    merchant=Client();merchant.login('vendedor')
    customer=Client();customer.login('cliente')
    admin=Client();admin.login('admin')
    stores=merchant.call('/merchant/stores/mine')
    market=next(s for s in stores if s['name']=='Mercado Demonstração')
    barber=next(s for s in stores if s['name']=='Barbearia Demonstração')
    path='/merchant/stores/'+market['id']
    created_store=None
    try:
        created_store=merchant.call('/merchant/stores','POST',payload(market,name='Loja mapa QA '+uuid.uuid4().hex[:8],cnpj=None,latitude=None,longitude=None,deliveryEnabled=False,pickupEnabled=True,inPersonEnabled=False),201)
        assert created_store['latitude'] is None
        assert all(s['id']!=created_store['id'] for s in customer.call('/stores'))
        request=next(r for r in admin.call('/admin/platform-requests') if r['storeId']==created_store['id'])
        admin.call('/admin/platform-requests/'+request['id']+'/review','PATCH',{'approved':True})
        corrected=admin.call('/admin/stores/'+created_store['id']+'/profile','PUT',payload(created_store,latitude=-22.621,longitude=-51.241))
        assert corrected['latitude']==-22.621 and corrected['pickupEnabled']
        assert any(s['id']==created_store['id'] for s in customer.call('/stores'))
        customer.call(path,'PUT',payload(market),403)
        merchant.call(path,'PUT',payload(market,deliveryEnabled=False,pickupEnabled=False,inPersonEnabled=False),409)
        merchant.call(path,'PUT',payload(market,latitude=-22.62,longitude=None),409)
        selected=merchant.call(path,'PUT',payload(market,latitude=-22.62,longitude=-51.24,deliveryEnabled=True,pickupEnabled=False,inPersonEnabled=False))
        public=next(s for s in customer.call('/stores?city=Nantes&state=SP') if s['id']==market['id'])
        assert public['latitude']==selected['latitude'] and public['deliveryEnabled'] and not public['pickupEnabled']
        product=next(i for i in customer.call('/stores/'+market['id']+'/items') if not i['serviceItem'] and i['stock']>0)
        order={'storeId':market['id'],'items':[{'itemId':product['id'],'quantity':1}],'deliveryAddress':'Rua fictícia, 10'}
        customer.call('/orders','POST',{**order,'fulfillmentType':'PICKUP'},409)
        created=customer.call('/orders','POST',{**order,'fulfillmentType':'DELIVERY'},201)
        customer.call('/orders/'+created['order']['id']+'/cancel','POST',{})
        merchant.call(path,'PUT',payload(market,latitude=None,longitude=None,deliveryEnabled=False,pickupEnabled=True,inPersonEnabled=False))
        cleared=next(s for s in customer.call('/stores?city=Nantes&state=SP') if s['id']==market['id'])
        assert cleared['latitude'] is None and cleared['longitude'] is None
        customer.call('/orders','POST',{**order,'fulfillmentType':'DELIVERY'},409)
        service=next(i for i in customer.call('/stores/'+barber['id']+'/items') if i['serviceItem'])
        merchant.call('/merchant/stores/'+barber['id'],'PUT',payload(barber,deliveryEnabled=True,pickupEnabled=False,inPersonEnabled=False))
        start=(datetime.datetime.now(datetime.timezone.utc)+datetime.timedelta(days=2)).isoformat()
        customer.call('/reservations','POST',{'storeId':barber['id'],'serviceItemId':service['id'],'startsAt':start},409)
    finally:
        merchant.call(path,'PUT',payload(market))
        merchant.call('/merchant/stores/'+barber['id'],'PUT',payload(barber))
        if created_store:admin.call('/admin/stores/'+created_store['id']+'/active','PATCH',{'active':False})
    print('APROVADO: coordenadas opcionais, remoção do ponto, vitrine pública, titularidade, modalidade obrigatória, delivery/retirada/agendamento bloqueados e pedido permitido cancelado sem pagamento.')

if __name__=='__main__':main()
