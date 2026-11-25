"""Exercita a resolução de endereço no Caddy descartável, somente em loopback."""
from pathlib import Path
import subprocess, tempfile, time, urllib.request
ROOT = Path(__file__).resolve().parents[1]
with tempfile.TemporaryDirectory() as tmp:
    path = Path(tmp) / 'Caddyfile'
    source = (ROOT/'deploy/Caddyfile.persistente').read_text()
    marker = 'handle /healthz {'
    assert source.count(marker) == 1
    path.write_text(source.replace(marker, 'handle /qa {\n respond "{client_ip}"\n }\n '+marker))
    command = ['docker','run','--rm','-d','-p','127.0.0.1::10000','-v',f'{path}:/etc/caddy/Caddyfile:ro','caddy:2.10.2']
    container = subprocess.check_output(command,text=True).strip()
    try:
        address = subprocess.check_output(['docker','port',container,'10000/tcp'],text=True).strip()
        def selected(value):
            headers = {'X-Forwarded-For':value} if value else {}
            with urllib.request.urlopen(urllib.request.Request('http://'+address+'/qa',headers=headers),timeout=2) as r:
                return r.read().decode()
        for _ in range(30):
            try:
                peer = selected(None)
                break
            except OSError:
                time.sleep(.2)
        else:
            raise RuntimeError('Caddy indisponível')
        assert selected('203.0.113.8') == selected('198.51.100.4, 203.0.113.8') == '203.0.113.8'
        assert selected('malformed') == peer
        assert selected('2001:zz::1') == peer
        assert selected('2001:db8::7') == '2001:db8::7'
        print('PASS: peer TCP, cadeia estrita, IPv6 válido e fallback para valores inválidos')
    finally:
        subprocess.run(['docker','rm','-f',container],check=True,stdout=subprocess.DEVNULL)
