# FoodDelivery

Marketplace local de produtos, serviços, reservas e entregas, com áreas de cliente, vendedor, entregador e administrador.

## Documentação

Comece pelo [índice da revisão](Documentacao/LEIA_PRIMEIRO_REVISAO.md). Ele reúne a explicação de engenharia, a referência de todas as funções de frontend/backend, o guia de atividades, o modelo de dados e as evidências de teste.

## Testar sem dados reais

```sh
npm run start:demo
```

Abra `http://127.0.0.1:5180`. Para SMTP local com Docker, use `npm run start:demo:mailpit`. Instruções, contas fictícias e limites: [homologação sem dados reais](Documentacao/Homologacao_sem_dados_reais.md).

O ambiente é isolado e não movimenta dinheiro. Não substituir homologação de provedor por simulação local.

## Desenvolvimento e validação

Configuração normal: [backend e operação](Backend/README.md). Validação e comandos reproduzíveis: [matriz de testes](Documentacao/Validacao_2026-11-04.md). O teste de esquema usa chave exclusiva de teste; produção exige segredo próprio.

Capas: vendedor envia foto/logo em proporção 2:1; publicação exige aprovação administrativa. [Fluxo, segurança e operação](Documentacao/Capas_das_lojas.md).
