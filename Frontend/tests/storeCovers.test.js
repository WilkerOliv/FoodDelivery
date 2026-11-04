import { test } from 'node:test';
import assert from 'node:assert/strict';
import { coverPlacement,COVER_WIDTH,COVER_HEIGHT } from '../src/storeCovers.js';
test('cover has a fixed 2:1 proportion',()=>{assert.equal(COVER_WIDTH,1200);assert.equal(COVER_HEIGHT,600);});
test('a square logo fits entirely with horizontal whitespace',()=>{assert.deepEqual(coverPlacement(600,600),{x:300,y:0,width:600,height:600});});
test('a square photo fills the cover with centered crop',()=>{assert.deepEqual(coverPlacement(600,600,'crop'),{x:0,y:-300,width:1200,height:1200});});
test('wide photos are centered without stretching',()=>{assert.deepEqual(coverPlacement(2400,600),{x:0,y:150,width:1200,height:300});});
test('invalid or oversized source dimensions are rejected',()=>{for(const value of [0,-1,NaN,Infinity,4097])assert.throws(()=>coverPlacement(value,600));});
