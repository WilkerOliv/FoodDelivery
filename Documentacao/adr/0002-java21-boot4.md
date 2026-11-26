# Plataforma Java da API

[LIDO Backend/pom.xml:8] A versão escolhida é Spring Boot 4.2.0 com Java 21. Manter a JVM permite verificar separadamente a mudança das bibliotecas e dos formatos. A referência de compatibilidade é a [documentação do Spring Boot](https://docs.spring.io/spring-boot/system-requirements.html).

[EXECUTADO] Em 25/11/2026, o inventário gerado por Maven CycloneDX incluiu 110 componentes, entre eles Spring MVC 7.1.0, Spring Security 7.2.0, Jackson Databind 3.1.8, Hibernate 7.4.15.Final e Flyway 12.11.0. Trivy 0.69.3 sobre esse inventário, com filtro HIGH/CRITICAL e sem arquivo de exceções, retornou 0 para Java.

[EXECUTADO] A suíte Maven registrou 151 testes, 0 falhas, 0 erros e 0 ignorados. O ensaio com scripts/test_postgres.py aprovou concorrência, migração, backup, restauração e reversão em banco descartável. O comando python3 scripts/test_persistent_deploy.py --container passou com TLS verificado, sessão preservada após reinício e recuperação após queda do banco.

[NÃO VERIFICADO] A migração ainda requer aceite da versão publicada. O resultado do inventário Java tem escopo distinto das imagens de contêiner e do teste independente da aplicação.

[LIDO Backend/pom.xml:8] Para reverter a mudança de plataforma, abra um PR que reverta o squash completo, execute as mesmas suítes e revise novamente os alertas de dependências antes de aprovar a reversão.
