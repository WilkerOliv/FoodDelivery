import { test } from 'node:test';
import assert from 'node:assert/strict';
import { createChatSession } from '../src/chatSession.js';
const deferred = () => { let resolve, reject; const promise = new Promise((a,b)=>{resolve=a;reject=b;}); return {promise,resolve,reject}; };
function setup(){
 const requests=[],events=[],timers=new Map();let counter=0;
 const chat=createChatSession({request:(path,options)=>{const d=deferred();requests.push({...d,path,options});return d.promise;},
 onLoad:(key,value)=>events.push(['load',key,value]),onUpdate:(key,value)=>events.push(['update',key,value]),onError:e=>events.push(['error',e.message]),
 schedule:fn=>{timers.set(++counter,fn);return counter;},cancel:id=>timers.delete(id)});
 const tick=()=>{const [id,fn]=timers.entries().next().value;timers.delete(id);return fn();};
 return {chat,requests,events,timers,tick};
}
test('inverted openings keep only the latest response and timer',async()=>{const s=setup();const a=s.chat.open('orders','a'),b=s.chat.open('orders','b');s.requests[1].resolve('B');await b;s.requests[0].resolve('A');await a;assert.deepEqual(s.events,[['load','orders:b','B']]);assert.equal(s.timers.size,1);assert.equal(s.requests[0].options.signal.aborted,true);});
test('a late opening failure cannot close a newer conversation',async()=>{const s=setup();const a=s.chat.open('orders','a'),b=s.chat.open('orders','b');s.requests[1].resolve('B');await b;s.requests[0].reject(new Error('old'));await a;assert.equal(s.events.length,1);assert.equal(s.timers.size,1);});
test('closing while loading prevents render and polling',async()=>{const s=setup();const a=s.chat.open('orders','a');s.chat.stop();s.requests[0].resolve('A');await a;assert.equal(s.events.length,0);assert.equal(s.timers.size,0);});
test('reopening same key still invalidates the previous opening',async()=>{const s=setup();const a=s.chat.open('orders','a'),b=s.chat.open('orders','a');s.requests[1].resolve('new');await b;s.requests[0].resolve('old');await a;assert.deepEqual(s.events,[['load','orders:a','new']]);});
test('refresh waits for completion before scheduling another and retries failure',async()=>{const s=setup();const a=s.chat.open('orders','a');s.requests[0].resolve('A');await a;const poll=s.tick();assert.equal(s.timers.size,0);s.requests[1].reject(new Error('offline'));await poll;assert.equal(s.timers.size,1);s.chat.stop();assert.equal(s.timers.size,0);});
test('late message response does not change another opening or its draft',async()=>{const s=setup();const a=s.chat.open('orders','a');s.requests[0].resolve('A');await a;let calls=0;const sent=s.chat.send('orders:a','hello',{onSent:()=>calls++,onFailure:()=>calls++});const b=s.chat.open('orders','b');s.requests[1].resolve('sent');await sent;assert.equal(calls,0);s.requests[2].resolve('B');await b;});
test('sending suppresses duplicate submissions and invalidates an earlier refresh',async()=>{const s=setup();const a=s.chat.open('orders','a');s.requests[0].resolve('A');await a;const poll=s.tick();let sent=0;const callbacks={onSent:()=>sent++,onFailure:()=>{throw new Error('unexpected');}};const send=s.chat.send('orders:a','hello',callbacks);await s.chat.send('orders:a','hello',callbacks);assert.equal(s.requests.length,3);s.requests[2].resolve('new');await send;s.requests[1].resolve('old');await poll;assert.equal(sent,1);assert.equal(s.events.length,1);});
