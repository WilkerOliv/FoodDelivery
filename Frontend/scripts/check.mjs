import { readdirSync } from 'node:fs';
import { spawnSync } from 'node:child_process';

const files = readdirSync(new URL('../src/', import.meta.url))
  .filter((file) => file.endsWith('.js'))
  .map((file) => new URL(`../src/${file}`, import.meta.url).pathname);

for (const file of files) {
  const result = spawnSync(process.execPath, ['--check', file], { stdio: 'inherit' });
  if (result.status !== 0) process.exit(result.status ?? 1);
}

process.stdout.write(`Sintaxe válida em ${files.length} módulos JavaScript.\n`);
