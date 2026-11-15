import { readdirSync, readFileSync, existsSync } from 'node:fs';
import { resolve, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';
import { spawnSync } from 'node:child_process';

const root = fileURLToPath(new URL('../src/', import.meta.url));
function collect(dir) {
  return readdirSync(dir, { withFileTypes: true }).flatMap(entry =>
    entry.isDirectory() ? collect(resolve(dir, entry.name)) : entry.name.endsWith('.js') ? [resolve(dir, entry.name)] : []);
}
const files = collect(root), graph = new Map();
for (const file of files) {
  const result = spawnSync(process.execPath, ['--check', file], { stdio: 'inherit' });
  if (result.status !== 0) process.exit(result.status ?? 1);
  const imports = [...readFileSync(file, 'utf8').matchAll(/(?:from\s*|import\s*)['"](\.[^'"]+)['"]/g)]
    .map(match => resolve(dirname(file), match[1].split('?')[0]));
  for (const target of imports) if (!existsSync(target)) throw new Error(`Import inexistente: ${file} → ${target}`);
  graph.set(file, imports);
}
function visit(file, stack = [], done = new Set()) {
  if (stack.includes(file)) throw new Error(`Dependência circular: ${[...stack, file].join(' → ')}`);
  if (done.has(file)) return;
  for (const child of graph.get(file) || []) visit(child, [...stack, file], done);
  done.add(file);
}
for (const file of files) visit(file);
console.log(`Sintaxe e imports válidos em ${files.length} módulos JavaScript, sem ciclos.`);
