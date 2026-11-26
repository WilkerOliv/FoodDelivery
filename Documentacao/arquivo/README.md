# Relatórios e evidências históricas de novembro de 2026

Estes registros descrevem o ambiente e os testes de cada data. A [matriz de aceite de 25/11](../Aceite_2026-11-25.md) acompanha os resultados e pendências da revisão posterior.

Os sete documentos e os 34 anexos originais estão preservados na revisão `15e652fd5950366bf0c2230ad6d7d5348cce8cb3`, ancestral da branch principal. Os links abrem os arquivos ou diretórios nessa revisão, inclusive após mudanças de nome no projeto.

## Documentos originais

| Registro | O que foi registrado na época |
|---|---|
| [Validação prática de 04/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Validacao_2026-11-04.md) | Revisão dos quatro perfis, matriz HTTP de autorizações e transições de pedido, revisão visual e limites de acessibilidade. Passaram 68 testes Java, 32 de frontend e seis do supervisor local. |
| [Homologação publicada de 05 e 06/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Validacao_homologacao_publicada_2026-11-05.md) | Cinco jornadas em 197 requisições na versão publicada. O relatório distingue essa rodada da verificação posterior do formulário de entrada e registra correções na edição do catálogo e na apresentação das reservas. |
| [Validação manual complementar de 06/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Validacao_manual_complementar_2026-11-06.md) | Falha ao aceitar uma oferta pela tela do entregador e repetição após a correção em `4c55b8a`. O registro acompanha estoque, cancelamento, entrega concluída, crédito exibido e resposta do suporte. O pagamento foi simulado. |
| [Modularização e validação de 14/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Modularizacao_e_validacao_2026-11-14.md) | Isolamento dos dados ao trocar de conta, seleção de arquivos para a imagem web e testes das capas. A rodada registrou 142 testes Java, três de integração com PostgreSQL, 90 de frontend e 39 de ferramentas Python. |
| [Ativação da persistência de 16/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Ativacao_persistencia_2026-11-16.md) | Conexão PostgreSQL com verificação do certificado e do nome do servidor. A equipe gravou, leu e removeu uma imagem sintética no bucket privado. A jornada de moderação na interface publicada continuava pendente. |
| [Publicação persistente de 17/11](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Publicacao_persistente_2026-11-17.md) | Correção das permissões e dos diretórios temporários do Caddy, 31 migrações aplicadas e identidade preservada entre verificações. O proprietário confirmou a revisão publicada `40209c3` naquela data. |
| [Plano de conclusão anterior](https://github.com/WilkerOliv/FoodDelivery/blob/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Plano_de_conclusao.md) | Guardava os critérios de aceite por fluxo, evidência de fornecedor, versão publicada, restauração com prazo medido e decisão sobre limites comerciais. Também documentava a decisão de usar capas enviadas para moderação e de registrar manualmente os repasses Pix. |

## Anexos originais

| Conjunto | Conteúdo |
|---|---|
| [Validação de 04/11](https://github.com/WilkerOliv/FoodDelivery/tree/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Evidencias_2026-11-04) | Sete capturas por perfil e um arquivo de resultados. |
| [Homologação publicada de 05 e 06/11](https://github.com/WilkerOliv/FoodDelivery/tree/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Evidencias_homologacao_publicada_2026-11-05) | 23 arquivos com capturas, respostas de teste e resultados das jornadas de compra, entrega e suporte. |
| [Modularização de 14/11](https://github.com/WilkerOliv/FoodDelivery/tree/15e652fd5950366bf0c2230ad6d7d5348cce8cb3/Documentacao/Evidencias_modularizacao_2026-11-14) | Duas capturas do administrador e um arquivo de resultados. |

Em um clone com o histórico completo, é possível consultar um original com `git show 15e652fd5950366bf0c2230ad6d7d5348cce8cb3:Documentacao/Plano_de_conclusao.md`. Para consultar outro arquivo, substitua o caminho após o SHA.
