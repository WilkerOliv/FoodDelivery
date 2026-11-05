import { test } from 'node:test';
import assert from 'node:assert/strict';
import { fulfillmentBadges, fulfillmentPayload } from '../src/storeOptions.js';
test('delivery only has a clear badge without pickup or appointment',()=>{const text=fulfillmentBadges({deliveryEnabled:true,pickupEnabled:false,inPersonEnabled:false});assert.match(text,/Somente delivery/);assert.doesNotMatch(text,/Retirada na loja|Agendamento presencial/);});
test('selection is explicit and all-disabled is rejected',()=>{const form=new FormData();assert.throws(()=>fulfillmentPayload(form));form.set('pickupEnabled','on');assert.deepEqual(fulfillmentPayload(form),{deliveryEnabled:false,pickupEnabled:true,inPersonEnabled:false});});
test('legacy stores preserve the three existing capabilities',()=>{assert.match(fulfillmentBadges({}),/Entrega/);assert.match(fulfillmentBadges({}),/Retirada na loja/);assert.match(fulfillmentBadges({}),/Agendamento presencial/);});
