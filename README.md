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

### Homologação gratuita pela internet

[Piloto persistente no Render e Supabase](Documentacao/Persistencia_gratuita.md): o Blueprint `render.yaml` prepara um único serviço Free conectado a PostgreSQL e imagens externos, com pagamentos em sandbox. Exige credenciais privadas e validação da publicação; não equivale a operação comercial homologada. A [demonstração descartável](Documentacao/Publicacao_gratuita_de_homologacao.md) continua disponível em um Dockerfile separado.

[Inicialização, nova publicação e verificações de segurança no GitHub](Documentacao/Publicacao_e_verificacoes_de_seguranca.md), com os limites de CodeQL em repositórios privados e a análise gratuita disponível.

[Validação e estado atual](Documentacao/Aceite_2026-11-25.md): resultados locais, última publicação registrada e verificações externas ainda pendentes.

## Desenvolvimento e validação

Configuração normal: [backend e operação](Backend/README.md). Validação e comandos reproduzíveis: [matriz de testes](Documentacao/Aceite_2026-11-25.md). O teste de esquema usa chave exclusiva de teste; produção exige segredo próprio.

Capas: vendedor envia foto/logo em proporção 2:1; publicação exige aprovação administrativa. [Fluxo, segurança e operação](Documentacao/Capas_das_lojas.md).

Localização opcional no mapa e modalidades de atendimento: [configuração, comportamento e validação](Documentacao/Mapa_e_atendimento_das_lojas.md).

### Funcionamento da loja e reservas

[Regras de turnos, pausas, exceções e tratamento das reservas afetadas](Documentacao/Funcionamento_e_reservas.md), incluindo contratos, segurança e testes locais.

[Fidelidade da homologação](Documentacao/Fidelidade_da_homologacao.md): regras compartilhadas, diferenças dos simuladores e critérios de validação antes da operação real.

Continuação de engenharia: [Carregamento e recuperação — 07/11/2026](Documentacao/Carregamento_e_recuperacao.md), com testes de sessão, falha de rede e conferência dos quatro perfis. A [matriz de aceite](Documentacao/Aceite_2026-11-25.md) distingue o que está implementado das dependências para operação real.

### Organização e persistência

- [Arquitetura e operação](Documentacao/Arquitetura_e_operacao.md).
- [Banco e imagens persistentes](Documentacao/Persistencia_gratuita.md): procedimento de instalação e limites; a publicação registrada e os testes pendentes estão no relatório de validação.

Ferramentas de desenvolvimento: [plugins disponíveis](Documentacao/Plugins_disponiveis.md).
