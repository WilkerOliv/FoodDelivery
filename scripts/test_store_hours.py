"""Isolated demo contracts for split hours, booking preservation and safe cancellation."""
import datetime as dt
import time
import urllib.request
import uuid
from zoneinfo import ZoneInfo
from test_homologacao import BASE,Client

def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js') as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read(),'Use somente homologação.'
    merchant=Client();merchant.login('vendedor');customer=Client();customer.login('cliente');admin=Client();admin.login('admin')
    created=None
    try:
        created=merchant.call('/merchant/stores','POST',{'name':'Horários QA '+uuid.uuid4().hex[:8],'address':'Rua fictícia, 10','city':'Nantes','state':'SP','category':'Barbearias','active':True,'timezone':'America/Sao_Paulo','deliveryFeeCents':0,'deliveryEnabled':False,'pickupEnabled':False,'inPersonEnabled':True},201)
        sid=created['id'];url='/merchant/stores/'+sid+'/hours'
        request=next(r for r in admin.call('/admin/platform-requests') if r['storeId']==sid)
        admin.call('/admin/platform-requests/'+request['id']+'/review','PATCH',{'approved':True})
        online=merchant.call('/merchant/stores/'+sid+'/items','POST',{'name':'Corte QA online','priceCents':2500,'stock':0,'serviceItem':True,'payAfterService':False,'appointmentDurationMinutes':30},201)
        later=merchant.call('/merchant/stores/'+sid+'/items','POST',{'name':'Corte QA na loja','priceCents':2000,'stock':0,'serviceItem':True,'payAfterService':True,'appointmentDurationMinutes':30},201)
        today=dt.datetime.now(ZoneInfo('America/Sao_Paulo')).date();date=today+dt.timedelta(days=3)
        config={'enabled':True,'weeklyShifts':{d:[{'opensAt':'08:00','closesAt':'12:00'},{'opensAt':'14:00','closesAt':'18:00'}] for d in ['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY']},'exceptions':[{'date':today.isoformat(),'closed':True,'note':'Informação privada de teste'}]}
        customer.call(url+'/preview','POST',config,403)
        def save(payload,action='KEEP'):
            preview=merchant.call(url+'/preview','POST',payload)
            response=merchant.call(url,'PUT',{**payload,'conflictAction':action,'expectedAffectedIds':[r['id'] for r in preview['affected']],'revision':preview['revision']})
            assert 'enabled' in response and 'weeklyShifts' in response
            return response
        save(config)
        admin_url='/admin/stores/'+sid+'/hours'
        merchant.call(admin_url,expected=403)
        assert admin.call(admin_url)['weeklyShifts']==config['weeklyShifts']
        admin_preview=admin.call(admin_url+'/preview','POST',config)
        admin_saved=admin.call(admin_url,'PUT',{**config,'expectedAffectedIds':[],'revision':admin_preview['revision']})
        assert admin_saved['changeSummary']['notified']==0
        public=customer.call('/stores/'+sid+'/hours');assert all(e['note'] is None for e in public['exceptions']);assert len(public['weeklyShifts']['MONDAY'])==2
        def book(hour,item=online,expected=201):
            start=dt.datetime.combine(date,dt.time.fromisoformat(hour),ZoneInfo('America/Sao_Paulo')).isoformat()
            return customer.call('/reservations','POST',{'storeId':sid,'serviceItemId':item['id'],'startsAt':start},expected)
        book('12:30',expected=409);book('11:45',expected=409)
        pending=book('09:00');paid=book('10:00');postpaid=book('15:00',later)
        changed_profile={**created,'timezone':'America/Manaus'}
        merchant.call('/merchant/stores/'+sid,'PUT',changed_profile,409)
        admin.call('/admin/stores/'+sid+'/profile','PUT',changed_profile,409)
        customer.call('/demo/payments/'+paid['paymentId']+'/approve','POST',{})
        closed={**config,'exceptions':config['exceptions']+[{'date':date.isoformat(),'closed':True,'note':'Não deve ser enviada ao cliente'}]}
        preview=merchant.call(url+'/preview','POST',closed);ids=[r['id'] for r in preview['affected']];assert len(ids)==3
        merchant.call(url,'PUT',closed,409)
        merchant.call(url,'PUT',{**closed,'conflictAction':'CANCEL','expectedAffectedIds':[str(uuid.uuid4())],'revision':preview['revision']},409)
        kept=save(closed);assert kept['changeSummary']=={'cancelled':0,'kept':3,'notified':3}
        mine={r['id']:r for r in customer.call('/reservations/mine')}
        for result in [pending,paid,postpaid]:
            r=mine[result['reservation']['id']];assert r['operatingHoursOverride'];assert r['status']!='CANCELLED';assert 'Não deve ser enviada' not in r['operatingHoursNotice']
        save(config) # Reopening clears only the old exemption; keeps the occupied slots.
        book('09:00',expected=409)
        before=merchant.call(url+'/preview','POST',closed)
        cancelled=save(closed,'CANCEL');assert cancelled['changeSummary']['cancelled']==3
        old_request={**closed,'conflictAction':'CANCEL','expectedAffectedIds':ids,'revision':before['revision']}
        merchant.call(url,'PUT',old_request,409)
        mine={r['id']:r for r in customer.call('/reservations/mine')}
        assert all(mine[r['reservation']['id']]['status']=='CANCELLED' for r in [pending,paid,postpaid])
        assert customer.call('/payments/'+paid['paymentId'])['status'] in ['REFUND_PENDING','REFUNDED']
        # A late payment must trigger refund, never resurrect a cancelled reservation.
        customer.call('/demo/payments/'+pending['paymentId']+'/approve','POST',{})
        assert next(r for r in customer.call('/reservations/mine') if r['id']==pending['reservation']['id'])['status']=='CANCELLED'
        assert customer.call('/payments/'+pending['paymentId'])['status'] in ['REFUND_PENDING','REFUNDED']
        notices=customer.call('/account/notifications')
        affected_ids=set(ids)
        assert len([n for n in notices if n.get('targetId') in affected_ids and n['title'] in ['Seu horário foi mantido','Reserva cancelada por mudança no expediente']])==6
        save(config)
        new=book('09:00');customer.call('/reservations/'+new['reservation']['id']+'/cancel','POST',{})
        deadline=time.monotonic()+45
        while time.monotonic()<deadline:
            if all(customer.call('/payments/'+p['paymentId'])['status']=='REFUNDED' for p in [pending,paid]):break
            time.sleep(2)
        else:raise AssertionError('Estorno simulado não foi confirmado em 45 segundos.')
        print('APROVADO: múltiplos turnos, pausa e fechamento, reserva futura com loja fechada hoje, privacidade, prévia obrigatória, preservação, slots, cancelamento, aviso único, confirmação desatualizada, pagamento tardio e estorno simulado.')
    finally:
        if created:admin.call('/admin/stores/'+created['id']+'/active','PATCH',{'active':False})

if __name__=='__main__':main()
