#!/usr/bin/env python3
"""Disposable PostgreSQL concurrency and dump/restore drill. Never reads Backend/.env."""
import json,os,secrets,subprocess,tempfile,time,pathlib
ROOT=pathlib.Path(__file__).resolve().parents[1]
def run(*args,**kw):return subprocess.run(args,check=True,text=True,**kw)
def main():
    name='fooddelivery-qa-'+secrets.token_hex(6);password=secrets.token_urlsafe(32)
    env={k:v for k,v in os.environ.items() if k in ['PATH','HOME','JAVA_HOME','MAVEN_HOME','LANG','LC_ALL','TMPDIR']}
    try:
        run('docker','run','--detach','--name',name,'--publish','127.0.0.1::5432','--env','POSTGRES_DB=fooddelivery_qa','--env','POSTGRES_USER=qa','--env','POSTGRES_PASSWORD='+password,'postgres:17-alpine',stdout=subprocess.DEVNULL)
        deadline=time.monotonic()+60
        while time.monotonic()<deadline:
            if subprocess.run(['docker','exec',name,'pg_isready','-U','qa','-d','fooddelivery_qa'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL).returncode==0:break
            time.sleep(1)
        else:raise RuntimeError('PostgreSQL descartável não ficou pronto.')
        port=run('docker','port',name,'5432/tcp',capture_output=True).stdout.strip().rsplit(':',1)[1]
        env.update(QA_DATABASE_URL=f'jdbc:postgresql://127.0.0.1:{port}/fooddelivery_qa',QA_DATABASE_USERNAME='qa',QA_DATABASE_PASSWORD=password)
        run('mvn','-f',str(ROOT/'Backend/pom.xml'),'-Dtest=PostgresConcurrencyIT','test','-q',env=env)
        run('docker','exec',name,'pg_dump','-U','qa','-d','fooddelivery_qa','--format=custom','--file=/tmp/qa.dump')
        run('docker','exec',name,'createdb','-U','qa','fooddelivery_restore_qa')
        run('docker','exec',name,'pg_restore','-U','qa','--dbname=fooddelivery_restore_qa','--no-owner','--exit-on-error','/tmp/qa.dump')
        counts=[]
        for db in ['fooddelivery_qa','fooddelivery_restore_qa']:
            counts.append(run('docker','exec',name,'psql','-U','qa','-d',db,'-Atc',"select (select count(*) from customer_orders),(select count(*) from reservations),(select count(*) from payments),(select count(*) from flyway_schema_history where success)",capture_output=True).stdout.strip())
        assert counts[0]==counts[1], 'Restauração divergiu do banco de origem.'
        print('APROVADO: PostgreSQL real, disputa de estoque, conflito de agenda, migrações e restauração em banco descartável; contagens conferidas.')
    finally:
        subprocess.run(['docker','rm','--force','--volumes',name],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL)
if __name__=='__main__':main()
