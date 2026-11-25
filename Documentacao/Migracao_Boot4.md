# Proposta de migração para Spring Boot 4

[NÃO VERIFICADO] Estimativa inicial de 5 a 10 dias de engenharia, sujeita à compilação e aos testes de integração. O dono decide quando iniciar. Manter o ambiente de origem disponível para reversão e usar banco descartável.

[LIDO NO CÓDIGO] Backend/pom.xml declara a plataforma e dependências. Conferir a árvore resolvida do BOM Boot 4 antes de remover overrides. Selecionar uma versão mantida que resolva Framework 7.0.9 ou posterior. [Guia oficial de migração](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide).

[LIDO NO CÓDIGO] Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:18 recebe ObjectMapper. Inventariar os imports Jackson 2 e adaptar os contratos para Jackson 3. Comparar datas, campos ausentes, números, erros HTTP e assinaturas com fixtures.

[NÃO VERIFICADO] Para Security 7, revisar DSL, filtros, JWT, autorização e ordem das cadeias. Ensaiar os quatro perfis, sessão revogada, CORS e login. Para Hibernate 7, conferir mapeamentos e JPQL nos dois dialetos. No Flyway, revisar o módulo PostgreSQL e comparar checksums e migrações em cópia descartável.

[NÃO VERIFICADO] Revisar starters e módulos de testes do Boot 4, mocks e imports de autoconfiguração. Exigir compilação Java 21, todas as suítes, boot com TLS, scan sem exceções e ensaio de rollback antes da decisão de integração.
