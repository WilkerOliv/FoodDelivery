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

[Passo a passo no Render](Documentacao/Publicacao_gratuita_de_homologacao.md): demonstração protegida por senha, endereço HTTPS gratuito, dados fictícios e descartáveis. O Blueprint `render.yaml` usa um único serviço Free. Não serve para operação comercial nem guarda dados após reinícios.

[Inicialização, nova publicação e verificações de segurança no GitHub](Documentacao/Publicacao_e_verificacoes_de_seguranca.md), com os limites de CodeQL em repositórios privados e a análise gratuita disponível.

[Validação da homologação publicada](Documentacao/Validacao_homologacao_publicada_2026-11-05.md): evidências, correção do limite de login compartilhado pelo gateway e etapas que ainda precisam de verificação externa e visual.

## Desenvolvimento e validação

Configuração normal: [backend e operação](Backend/README.md). Validação e comandos reproduzíveis: [matriz de testes](Documentacao/Validacao_2026-11-04.md). O teste de esquema usa chave exclusiva de teste; produção exige segredo próprio.

Capas: vendedor envia foto/logo em proporção 2:1; publicação exige aprovação administrativa. [Fluxo, segurança e operação](Documentacao/Capas_das_lojas.md).

Localização opcional no mapa e modalidades de atendimento: [configuração, comportamento e validação](Documentacao/Mapa_e_atendimento_das_lojas.md).

### Funcionamento da loja e reservas

[Regras de turnos, pausas, exceções e tratamento das reservas afetadas](Documentacao/Funcionamento_e_reservas.md), incluindo contratos, segurança e testes locais.

[Fidelidade da homologação](Documentacao/Fidelidade_da_homologacao.md): regras compartilhadas, diferenças dos simuladores e critérios de validação antes da operação real.

Relatório da continuação manual: [Validação manual complementar — 06/11/2026](Documentacao/Validacao_manual_complementar_2026-11-06.md), com o defeito do aceite de entrega, a correção e a repetição manual concluída após o deploy de `4c55b8a`, além dos limites da evidência.
