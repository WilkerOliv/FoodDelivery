import test from 'node:test';
import assert from 'node:assert/strict';
import { shellMarkup } from '../src/views/shellView.js';

for (const [role, home] of Object.entries({CUSTOMER:'home', MERCHANT:'merchant', ADMIN:'admin', COURIER:'courier'})) {
  test('Destino inicial e navegação de ' + role, () => {
    const html = shellMarkup({
      state: {notifications: [{read:false}], account:{name:'Teste'}, cart:[], page:home, search:''},
      links:[[home, 'home', 'Início']], role, roleLabel:role, canShop:role === 'CUSTOMER',
      compact:true, esc:String, icon:() => '', initials:() => 'T'
    });
    assert.ok(html.includes('class="brand" data-page="' + home + '"'));
    assert.equal(html.includes('id="open-cart"'), role === 'CUSTOMER');
    assert.ok(html.includes('id="page-content"'));
    assert.ok(html.includes('aria-label="Buscar lojas, produtos ou serviços"'));
  });
}
