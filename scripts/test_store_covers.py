"""Moderation/authorization checks using only the isolated demo environment."""
import urllib.request
from test_homologacao import BASE, Client
from cover_fixture import png_data_url

def main():
    with urllib.request.urlopen(BASE+'/runtime-config.js') as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read(), 'Use apenas homologação.'
    admin=Client();admin.login('admin')
    merchant=Client();merchant.login('vendedor')
    customer=Client();customer.login('cliente')
    guest=Client()
    store=merchant.call('/merchant/stores/mine')[0];sid=store['id']
    base='/merchant/stores/'+sid
    payload={'dataUrl':png_data_url()}
    customer.call(base+'/cover','POST',payload,403)
    merchant.call('/admin/store-covers',expected=403)
    merchant.call(base+'/cover','POST',{'dataUrl':png_data_url(600,600)},409)
    first=merchant.call(base+'/cover','POST',payload,201)
    assert first['status']=='PENDING'
    guest.call(first['imageUrl'].removeprefix('/api/v1'),expected=401)
    customer.call(first['imageUrl'].removeprefix('/api/v1'),expected=403)
    merchant.call(first['imageUrl'].removeprefix('/api/v1'))
    assert merchant.call('/merchant/stores/mine')[0]['coverImageUrl']==store['coverImageUrl']
    second=merchant.call(base+'/cover','POST',payload,201)
    admin.call('/admin/store-covers/'+first['id']+'/review','PATCH',{'approved':True},409)
    admin.call('/admin/store-covers/'+second['id']+'/review','PATCH',{'approved':False,'note':' '},409)
    rejected=admin.call('/admin/store-covers/'+second['id']+'/review','PATCH',{'approved':False,'note':'Use uma foto legível'})
    assert rejected['status']=='REJECTED'
    third=merchant.call(base+'/cover','POST',payload,201)
    admin.call('/admin/store-covers/'+third['id']+'/review','PATCH',{'approved':True})
    published=merchant.call('/merchant/stores/mine')[0]['coverImageUrl']
    guest.call(published.removeprefix('/api/v1'))
    merchant.call(base+'/published-cover')
    customer.call(base+'/published-cover',expected=403)
    guest.call(base+'/published-cover',expected=401)
    admin.call('/admin/store-covers/'+third['id']+'/review','PATCH',{'approved':True},409)
    fourth=merchant.call(base+'/cover','POST',payload,201)
    assert merchant.call('/merchant/stores/mine')[0]['coverImageUrl']==published
    assert merchant.call(base+'/cover-submission')['submission']['id']==fourth['id']
    assert any(s['id']==fourth['id'] for s in admin.call('/admin/store-covers'))
    admin.call('/admin/store-covers/'+fourth['id']+'/review','PATCH',{'approved':False,'note':'Rejeição de teste preserva a capa anterior'})
    assert merchant.call('/merchant/stores/mine')[0]['coverImageUrl']==published
    print('APROVADO: envio, proporção, permissões, prévia privada, substituição pendente, recusa com motivo, aprovação, publicação e preservação da capa anterior.')

if __name__=='__main__':main()
