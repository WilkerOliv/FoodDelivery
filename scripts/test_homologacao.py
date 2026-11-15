#!/usr/bin/env python3
"""Contract smoke checks. Creates only demo records; requires start:demo running."""
import datetime
import http.cookiejar
import json
import urllib.request
import urllib.error
import uuid
import time
from cover_fixture import png_data_url

BASE='http://127.0.0.1:5180'
PASSWORD='DemoLocal!2026#Teste'

class Client:
    def __init__(self):
        self.jar=http.cookiejar.CookieJar()
        self.http=urllib.request.build_opener(urllib.request.HTTPCookieProcessor(self.jar))
    def call(self,path,method='GET',body=None,expected=200):
        data=None if body is None else json.dumps(body).encode()
        request=urllib.request.Request(BASE+'/api/v1'+path,data=data,method=method,headers={'Content-Type':'application/json','Origin':BASE})
        try: response=self.http.open(request,timeout=15)
        except urllib.error.HTTPError as error: response=error
        with response:
            raw=response.read()
            assert response.status==expected,(path,response.status,raw.decode()[:500])
            return json.loads(raw) if raw and 'json' in response.headers.get('Content-Type','') else None
    def login(self,name): return self.call('/auth/login','POST',{'email':name+'@example.test','password':PASSWORD})

def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js',timeout=5) as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read(),'Servidor não é de homologação.'
    guest=Client();guest.call('/demo/messages',expected=401)
    admin=Client();admin.login('admin')
    customer=Client();customer.login('cliente')
    assert any(cookie.name=='fooddelivery_demo_session' for cookie in customer.jar)
    customer.call('/demo/messages',expected=403)
    customer.call('/demo/payments/'+str(uuid.uuid4())+'/approve','POST',{},expected=404)
    merchant=Client();merchant.login('vendedor')
    courier=Client();courier.login('entregador')
    # Each run owns its store, catalog and schedule; manual QA may change the seeded stores.
    market=merchant.call('/merchant/stores','POST',{'name':'Integração QA '+uuid.uuid4().hex[:8],
        'address':'Rua fictícia, 10','city':'Nantes','state':'SP','category':'Mercados',
        'active':True,'timezone':'America/Sao_Paulo','deliveryFeeCents':0,
        'deliveryEnabled':True,'pickupEnabled':True,'inPersonEnabled':True},201)
    try:
        request=next(r for r in admin.call('/admin/platform-requests') if r['storeId']==market['id'])
        admin.call('/admin/platform-requests/'+request['id']+'/review','PATCH',{'approved':True})
        product=merchant.call('/merchant/stores/'+market['id']+'/items','POST',
            {'name':'Produto QA','priceCents':1500,'stock':5,'serviceItem':False},201)
        service=merchant.call('/merchant/stores/'+market['id']+'/items','POST',
            {'name':'Serviço QA','priceCents':2500,'stock':0,'serviceItem':True,
             'payAfterService':False,'appointmentDurationMinutes':30},201)
        order=customer.call('/orders','POST',{'storeId':market['id'],'fulfillmentType':'PICKUP','items':[{'itemId':product['id'],'quantity':1}]},201)
        payment=order['paymentId']
        other=Client()
        if not any(a['email']=='outro@example.test' for a in admin.call('/admin/accounts')):
            other.call('/auth/register','POST',{'name':'Outro cliente de teste','email':'outro@example.test','password':PASSWORD,'cpf':'93541134780'},expected=201)
        other.login('outro')
        other.call('/demo/payments/'+payment+'/approve','POST',{},expected=403)
        merchant.call('/demo/payments/'+payment+'/approve','POST',{},expected=403)
        customer.call('/payments/'+payment+'/checkout','POST',{},expected=409)
        customer.call('/demo/payments/'+payment+'/decline','POST',{})
        approved=customer.call('/demo/payments/'+payment+'/approve','POST',{})
        assert approved['status']=='PAID'
        assert customer.call('/demo/payments/'+payment+'/approve','POST',{})['status']=='PAID'
        assert any(o['id']==order['order']['id'] for o in merchant.call('/merchant/orders'))
        start=(datetime.datetime.now(datetime.timezone.utc)+datetime.timedelta(days=2+uuid.uuid4().int%300)).replace(second=0,microsecond=0)
        reservation=customer.call('/reservations','POST',{'storeId':market['id'],'serviceItemId':service['id'],'startsAt':start.isoformat()},201)
        customer.call('/demo/payments/'+reservation['paymentId']+'/approve','POST',{})
        customer.call('/auth/password-recovery','POST',{'email':'cliente@example.test','channel':'EMAIL'},202)
        messages=admin.call('/demo/messages');assert any(m['channel']=='EMAIL' for m in messages)
        customer.call('/account/phone-verification','POST',{},202)
        messages=admin.call('/demo/messages');assert any(m['channel']=='WHATSAPP' for m in messages)
        own=market
        image=merchant.call('/merchant/stores/'+own['id']+'/cover','POST',{'dataUrl':png_data_url()},201)
        assert image['status']=='PENDING'
        assert admin.call('/admin/store-covers/'+image['id']+'/review','PATCH',{'approved':True})['status']=='APPROVED'
        places=guest.call('/geography/search?query=Nantes');assert 'simulado' in places[0]['display_name']
        order_id=order['order']['id']
        customer.call('/orders/'+order_id+'/cancellation-request','POST',{'note':'Cancelamento de homologação'})
        merchant.call('/merchant/orders/'+order_id+'/cancellation-request','PATCH',{'accepted':True})
        deadline=time.monotonic()+40
        while time.monotonic()<deadline:
            if customer.call('/payments/'+payment)['status']=='REFUNDED':break
            time.sleep(2)
        else: raise AssertionError('Job não confirmou estorno simulado em 40 segundos.')
        print('APROVADO: quatro perfis, cookie isolado, inbox restrita, bloqueio de checkout real, pagamento aprovado/recusado/repetido, pedido na loja, reserva paga, códigos EMAIL/WHATSAPP, upload de capa com aprovação e cancelamento com estorno local.')

    finally:
        admin.call('/admin/stores/'+market['id']+'/active','PATCH',{'active':False})

if __name__=='__main__': main()
