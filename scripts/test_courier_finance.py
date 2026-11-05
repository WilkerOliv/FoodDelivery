#!/usr/bin/env python3
"""Fake delivery, earning, payout verification and manual payment record. No external transfer."""
import uuid,urllib.request
from test_homologacao import Client,BASE
PASSWORD='DemoLocal!2026#Teste'
def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js') as r:assert b'FOODDELIVERY_DEMO=true' in r.read()
    admin,merchant,customer,courier=[Client() for _ in range(4)]
    for c,n in [(admin,'admin'),(merchant,'vendedor'),(customer,'cliente'),(courier,'entregador')]:c.login(n)
    customer.call('/admin/courier-finance',expected=403);merchant.call('/courier/finance',expected=403)
    admin.call('/admin/courier-finance/policy','POST',{'amountCents':500,'password':PASSWORD})
    before=courier.call('/courier/finance')['summary']
    store=next(s for s in merchant.call('/merchant/stores/mine') if s['name']=='Mercado Demonstração')
    item=merchant.call('/merchant/stores/'+store['id']+'/items','POST',{'name':'Produto financeiro QA '+uuid.uuid4().hex[:6],'priceCents':1500,'stock':1,'serviceItem':False},201)
    order=customer.call('/orders','POST',{'storeId':store['id'],'fulfillmentType':'DELIVERY','deliveryAddress':'Rua fictícia 10','items':[{'itemId':item['id'],'quantity':1}]},201)
    customer.call('/demo/payments/'+order['paymentId']+'/approve','POST',{})
    for status in ['ACCEPTED','PREPARING','READY']:merchant.call('/merchant/orders/'+order['order']['id']+'/status','PATCH',{'status':status})
    delivery=merchant.call('/merchant/orders/'+order['order']['id']+'/delivery','POST',{},201);assert delivery['courierFeeCents']==500
    admin.call('/admin/courier-finance/policy','POST',{'amountCents':600,'password':PASSWORD})
    courier.call('/courier/offers/'+delivery['id']+'/accept','POST',{})
    for status in ['PICKED_UP','IN_TRANSIT','DELIVERED']:courier.call('/courier/deliveries/'+delivery['id'],'PATCH',{'status':status})
    courier.call('/courier/deliveries/'+delivery['id'],'PATCH',{'status':'DELIVERED'},409)
    after=courier.call('/courier/finance')['summary'];assert after['earnedCents']==before['earnedCents']+500
    courier.call('/courier/payout-code','POST',{'password':'wrong-password'},403)
    courier.call('/courier/payout-code','POST',{'password':PASSWORD})
    code=next(m['code'] for m in admin.call('/demo/messages') if m['recipient']=='entregador@example.test' and m['channel']=='EMAIL')
    body={'amountCents':300,'requestKey':str(uuid.uuid4()),'password':PASSWORD,'code':code}
    wrong={**body,'code':'111111' if code!='111111' else '222222'};courier.call('/courier/payouts','POST',wrong,409)
    payout=courier.call('/courier/payouts','POST',body);assert code not in str(payout);assert '12345678909' not in str(payout)
    assert courier.call('/courier/payouts','POST',body)['id']==payout['id']
    courier.call('/courier/payouts','POST',{**body,'amountCents':301},409)
    admin.call('/admin/courier-finance/payouts/'+payout['id']+'/paid','POST',{'externalReference':'SIM-'+uuid.uuid4().hex,'password':PASSWORD},409)
    approved=admin.call('/admin/courier-finance/payouts/'+payout['id']+'/decision','POST',{'approved':True,'password':PASSWORD});assert approved['pixKeyType']=='CPF';assert approved['payout']['status']=='APPROVED'
    courier.call('/courier/payouts/'+payout['id']+'/cancel','POST',{},409)
    reference='SIM-TRANSFER-'+uuid.uuid4().hex
    paid=admin.call('/admin/courier-finance/payouts/'+payout['id']+'/paid','POST',{'externalReference':reference,'password':PASSWORD});assert paid['status']=='PAID'
    assert admin.call('/admin/courier-finance/payouts/'+payout['id']+'/paid','POST',{'externalReference':reference,'password':PASSWORD})['status']=='PAID'
    summary=courier.call('/courier/finance')['summary'];assert summary['paidCents']==before['paidCents']+300;assert summary['availableCents']==before['availableCents']+200
    print('APROVADO: remuneração congelada, crédito único, saldo, senha, OTP, idempotência, aprovação administrativa, destino protegido e registro de transferência fictícia.')
if __name__=='__main__':main()
