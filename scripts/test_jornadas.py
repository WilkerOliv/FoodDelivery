#!/usr/bin/env python3
"""Four-role integration checks. Run only against the isolated demo server."""
from test_homologacao import Client,BASE
import json
import urllib.request
import uuid

def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js') as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read()
    admin,customer,merchant,courier,other=[Client() for _ in range(5)]
    for client,name in [(admin,'admin'),(customer,'cliente'),(merchant,'vendedor'),(courier,'entregador'),(other,'outro')]:client.login(name)
    admin.call('/admin/courier-finance/policy','POST',{'amountCents':500,'password':'DemoLocal!2026#Teste'})
    tag=str(uuid.uuid4())[:8]
    store=next(s for s in merchant.call('/merchant/stores/mine') if s['category']=='Mercados')
    item=merchant.call('/merchant/stores/'+store['id']+'/items','POST',{'name':'Item QA '+tag,'description':'Fixture descartável','priceCents':1700,'stock':10,'serviceItem':False,'active':True,'payAfterService':False},201)
    changed={**item,'name':'Serviço QA '+tag,'stock':0,'serviceItem':True,'payAfterService':True,'appointmentDurationMinutes':45}
    assert merchant.call('/merchant/items/'+item['id'],'PUT',changed)['serviceItem']
    changed.update(serviceItem=False,payAfterService=False,stock=10)
    merchant.call('/merchant/items/'+item['id'],'PUT',changed)
    other.call('/merchant/items/'+item['id'],'DELETE',expected=403)
    merchant.call('/merchant/items/'+item['id'],'DELETE',expected=204)
    changed['active']=True;merchant.call('/merchant/items/'+item['id'],'PUT',changed)
    address=customer.call('/account/addresses','POST',{'label':'QA '+tag,'addressLine':'Rua Demo, 123','neighborhood':'Centro','city':'Nantes','state':'SP','postalCode':'19645000','defaultAddress':False},201)
    other.call('/account/addresses/'+address['id'],'DELETE',expected=404)
    customer.call('/account/addresses/'+address['id'],'DELETE',expected=204)
    ticket=customer.call('/support/tickets','POST',{'subject':'QA '+tag,'category':'GENERAL','body':'Teste de atendimento. <img src=x onerror=alert(1)>'},201)
    tid=ticket['ticket']['id']
    other.call('/support/tickets/'+tid,expected=403)
    admin.call('/admin/support-tickets/'+tid+'/messages','POST',{'body':'Resposta de homologação'},200)
    admin.call('/admin/support-tickets/'+tid+'/status','PATCH',{'status':'CLOSED'})
    assert customer.call('/support/tickets/'+tid)['ticket']['status']=='CLOSED'
    customer.call('/support/tickets/'+tid+'/messages','POST',{'body':'Reabrir teste'})
    assert admin.call('/admin/support-tickets/'+tid)['ticket']['status']=='OPEN'
    category=admin.call('/admin/store-categories','POST',{'name':'Categoria QA '+tag,'emoji':'🧰'},201)
    admin.call('/admin/store-categories/'+category['id'],'DELETE',expected=204)
    city=admin.call('/admin/service-cities','POST',{'city':'Cidade QA '+tag,'state':'SP','postalCode':None,'enabled':True},201)
    admin.call('/admin/service-cities/'+city['id'],'PUT',{'city':city['city'],'state':'SP','postalCode':None,'enabled':False})
    request=customer.call('/account/platform-requests/cities','POST',{'city':'Solicitada QA '+tag,'state':'SP','postalCode':None},201)
    reviewed=admin.call('/admin/platform-requests/'+request['id']+'/review','PATCH',{'approved':True,'note':'Homologação fictícia'})
    assert reviewed['status']=='APPROVED'
    admin.call('/admin/platform-requests/'+request['id']+'/review','PATCH',{'approved':False},409)
    payload={k:store.get(k) for k in ['name','description','address','category','city','state','cnpj','timezone','latitude','longitude']};payload['deliveryFeeCents']=store['deliveryFeeCents']
    payload['name']='Mercado Demonstração';admin.call('/admin/stores/'+store['id']+'/profile','PUT',payload)
    customer.call('/admin/accounts',expected=403);merchant.call('/admin/accounts',expected=403);courier.call('/admin/accounts',expected=403)
    order=customer.call('/orders','POST',{'storeId':store['id'],'fulfillmentType':'DELIVERY','deliveryAddress':'Rua Demo, 123','items':[{'itemId':item['id'],'quantity':1}]},201)
    oid=order['order']['id'];customer.call('/demo/payments/'+order['paymentId']+'/approve','POST',{})
    for status in ['ACCEPTED','PREPARING','READY']:merchant.call('/merchant/orders/'+oid+'/status','PATCH',{'status':status})
    task=merchant.call('/merchant/orders/'+oid+'/delivery','POST',{},201)
    assert merchant.call('/merchant/orders/'+oid+'/delivery','POST',{},201)['id']==task['id']
    courier.call('/courier/offers/'+task['id']+'/accept','POST',{})
    other.call('/deliveries/'+task['id']+'/messages',expected=403)
    customer.call('/deliveries/'+task['id']+'/messages','POST',{'body':'Olá entregador'},201)
    courier.call('/courier/deliveries/'+task['id'],'PATCH',{'status':'PICKED_UP'})
    customer.call('/orders/'+oid+'/cancellation-request','POST',{'note':'Não pode após coleta'},409)
    courier.call('/courier/deliveries/'+task['id'],'PATCH',{'status':'IN_TRANSIT'})
    courier.call('/courier/deliveries/'+task['id']+'/location','PATCH',{'latitude':-22.62,'longitude':-51.24})
    done=courier.call('/courier/deliveries/'+task['id'],'PATCH',{'status':'DELIVERED'})
    assert done['deliveryAddress'] is None and done['customerNote'] is None
    assert next(o for o in customer.call('/orders/mine') if o['id']==oid)['status']=='COMPLETED'
    customer.call('/support/tickets/not-a-uuid',expected=400)
    notifications=customer.call('/account/notifications')
    if notifications:customer.call('/account/notifications/'+notifications[0]['id']+'/read','PATCH',{})
    print('APROVADO: catálogo CRUD/tipo, endereço privado, suporte/resposta/reabertura, categoria personalizada, cidade, edição administrativa, matriz de papéis, delivery completo, chat, localização, bloqueio de cancelamento após coleta, remoção de endereço do entregador e entrada inválida.')

if __name__=='__main__':main()
