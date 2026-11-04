#!/usr/bin/env python3
"""Isolated, loopback-only demo. Does not load .env or reuse existing services."""
import base64
import json
import os
from pathlib import Path
import secrets
import socket
import subprocess
import sys
import time
import urllib.request
import urllib.error

ROOT=Path(__file__).resolve().parents[1]

def stop(process):
    if process and process.poll() is None:
        process.terminate()
        try: process.wait(timeout=15)
        except subprocess.TimeoutExpired: process.kill();process.wait()

def main():
    api=frontend=None
    mailpit=False
    try:
        for port in (8181,5180):
            with socket.socket() as probe:
                probe.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
                probe.bind(('127.0.0.1',port))
        # Allow-list avoids inherited database URLs, credentials and Spring config overrides.
        env={key:value for key,value in os.environ.items() if key in ('PATH','HOME','JAVA_HOME','LANG','LC_ALL','MAVEN_HOME','TMPDIR')}
        env['JWT_SECRET']=base64.b64encode(secrets.token_bytes(32)).decode()
        env['SPRING_PROFILES_ACTIVE']='homologacao'
        if '--mailpit' in sys.argv:
            subprocess.run(['docker','compose','-f','Backend/compose.homologacao.yaml','up','-d'],cwd=ROOT,check=True)
            mailpit=True
            env.update({'SMTP_HOST':'127.0.0.1','SMTP_PORT':'1026','SMTP_AUTH':'false','SMTP_STARTTLS':'false','APP_DEMO_MAILPIT':'true'})
        api=subprocess.Popen(['mvn','spring-boot:run'],cwd=ROOT/'Backend',env=env)
        deadline=time.monotonic()+120
        while time.monotonic()<deadline:
            if api.poll() is not None: raise RuntimeError('A API de homologação encerrou durante a inicialização.')
            try:
                with urllib.request.urlopen('http://127.0.0.1:8181/api/v1/demo/status',timeout=1) as response:
                    status=json.load(response)
                    if status.get('mode')=='homologacao' and status.get('ready') is True: break
            except (OSError,urllib.error.URLError): pass
            time.sleep(1)
        else: raise RuntimeError('A API de homologação não ficou disponível em 120 segundos.')
        env['FOODDELIVERY_DEMO']='1';env['FOODDELIVERY_FRONTEND_PORT']='5180'
        frontend=subprocess.Popen([sys.executable,'scripts/start.py'],cwd=ROOT/'Frontend',env=env)
        print('\nHOMOLOGAÇÃO: http://127.0.0.1:5180\nMensagens: http://127.0.0.1:5180/demo.html\nContas: admin, cliente, vendedor, entregador @example.test\nSenha local de todas: DemoLocal!2026#Teste\nCtrl+C encerra apenas estes processos. Dados em Backend/data/homologacao.\n',flush=True)
        while api.poll() is None and frontend.poll() is None: time.sleep(1)
        raise RuntimeError('Um serviço de homologação foi encerrado. Consulte o log acima.')
    except KeyboardInterrupt: return 0
    except (OSError,RuntimeError,subprocess.CalledProcessError) as error: print(str(error),file=sys.stderr);return 1
    finally:
        stop(frontend);stop(api)
        if mailpit: subprocess.run(['docker','compose','-f','Backend/compose.homologacao.yaml','stop'],cwd=ROOT,check=False)

if __name__=='__main__': raise SystemExit(main())
