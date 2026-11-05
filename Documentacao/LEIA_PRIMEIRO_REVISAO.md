# Documentação da revisão do FoodDelivery

Atualização: 05/11/2026. Esta coleção descreve o código que está no projeto. Não é um histórico de versões.

## Por onde começar

| Documento | Conteúdo e público |
|---|---|
| [Engenharia e avaliação](Engenharia_e_avaliacao.md) | Decisões, fluxos completos, segurança, concorrência, vantagens, limites e prioridades. |
| [Referência de backend](Referencia_Backend.md) | Todos os 107 arquivos Java: 857 métodos/construtores explícitos, assinatura, saídas, colaborações e rejeições locais. |
| [Referência de frontend](Referencia_Frontend.md) | Todos os 18 módulos em `Frontend/src`: funções nomeadas e callbacks, contratos e efeitos. A quantidade é informada no próprio documento gerado. |
| [Funcionamento e reservas](Funcionamento_e_reservas.md) | Turnos, pausas, exceções, prévia, cancelamento seguro, avisos e testes de 05/11. |
| [Mapa e atendimento](Mapa_e_atendimento_das_lojas.md) | Seleção opcional de ponto, mapa público, bandeiras, validação de modalidades e evidências de 05/11. |
| [Capas das lojas](Capas_das_lojas.md) | Upload de foto/logo, formato 2:1, aprovação administrativa, permissões, armazenamento e testes. |
| [Guia das atividades](Guia_de_atividades.md) | Como cada perfil usa as telas e quais regras precisa conhecer. |
| [Inventário de interface e dados](Inventario_Interfaces_e_Dados.md) | 186 atributos de interação, contratos HTTP, migrações e arquivos operacionais. |
| [Testes e evidências](Validacao_2026-11-04.md) | O que foi de fato executado, resultado, evidência visual e o que não está comprovado. |
| [Modelo de dados](Modelo_de_Dados.md) | Entidades, relações, integridade e versões de migração. |
| [Ambiente sem dados reais](Homologacao_sem_dados_reais.md) | Inicialização isolada, contas fictícias, mensagens e simulação de pagamento. |
| [Arquitetura e operação](Arquitetura_e_operacao.md) | Operação, deploy, backup e limites operacionais. |

## Como interpretar a cobertura

A referência usa análise de árvore sintática: inclui cada função explícita, inclusive callbacks. Isso comprova cobertura do inventário, não cobertura de execução. Anotações Spring e consultas declarativas também fazem parte do contrato; métodos herdados de bibliotecas não são funções escritas pelo projeto.

Os testes automatizados e as jornadas HTTP cobrem cenários identificados na matriz. A revisão manual usa o navegador real em telas específicas; não equivale a executar cada combinação possível de estados e dispositivos. Integrações simuladas não homologam fornecedores reais.

## Atualizar a referência após mexer no código

```sh
python3 -m venv /tmp/fooddelivery-doc-tools
/tmp/fooddelivery-doc-tools/bin/pip install -r scripts/reference-requirements.txt
/tmp/fooddelivery-doc-tools/bin/python scripts/generate_reference.py
```

O gerador não lê `.env`, banco ou credenciais. Cada arquivo documentado tem hash para identificar divergência entre fonte e referência. Atualize também a análise manual e a matriz: gerar novamente uma lista não revisa automaticamente a qualidade do código.
