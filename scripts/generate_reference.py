"""Generate a structural reference of every explicit Java/JS function; no runtime secrets."""
from pathlib import Path
import hashlib
import re
from tree_sitter import Language, Parser
import tree_sitter_java
import tree_sitter_javascript

ROOT=Path(__file__).resolve().parents[1]
CONTEXT={
 'identity':'Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.',
 'catalog':'Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.',
 'orders':'Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.',
 'reservations':'Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.',
 'payments':'Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.',
 'delivery':'Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação.',
 'finance':'Remuneração e repasses do entregador. O valor por entrega é definido pela administração e congelado na tarefa. Crédito único, saldo sob bloqueio, reautenticação, código de verificação, idempotência e aprovação protegem o registro; nenhuma transferência externa é presumida.',
 'settings':'Configuração privada. Uma lista permitida restringe alterações, novos segredos são cifrados com AES-GCM e contexto autenticado, e a API informa presença sem devolver credenciais. Chaves mestras permanecem no servidor.',
 'whatsapp':'Automação por comandos. Vínculo com telefone verificado, assinatura do webhook, deduplicação, carrinho cifrado, confirmação explícita e fila de respostas separam mensagens de confirmação financeira. O bot não declara pagamento por mensagem.',
 'admin':'Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.',
 'platform':'Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.',
 'communication':'Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso.',
 'support':'Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.',
 'notifications':'Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.',
 'config':'Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.',
 'location':'Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias.',
 'pageLoading':'Controle de carregamento por contexto e revisão. Aborta consultas anteriores, ignora respostas obsoletas e distingue falha de lista vazia. Repetição consulta dados; não repete mutações.',
 'pageData':'Consultas de página produzem um patch separado do estado global. Busca por loja/cidade, carga parcial explícita e rejeição de catálogo indisponível preservam a coerência da tela.',
 'main':'Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea.',
 'api':'Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade.',
 'accessibility':'Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais.',
 'adminStoreEditor':'Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração.',
 'adminSecurity':'Formulários administrativos para configurações cifradas e segundo fator. Senha e códigos são enviados apenas à API; presença de configuração não comprova autenticação com provedor. Ativação ou remoção de MFA revoga sessões antigas.',
 'courierFinance':'Interface de ganhos e repasses. Mostra saldo e histórico, exige senha e código para solicitar, oferece análise administrativa e registra comprovante externo. Estado visual não comprova transferência bancária.',
 'bookingAvailability':'Grade de horários livres, política da agenda e disponibilidade por profissional. Requisições anteriores são abortadas ao mudar a seleção; confirmar exige nova validação na API. A interface não reserva capacidade apenas por selecionar um horário.',
 'storeHours':'Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas.',
 'storeTime':'Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior.',
 'inputMasks':'Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor.',
 'catalogBindings':'Ligação explícita dos eventos do catálogo após renderizar a página. O envio cancela a navegação nativa e a edição recebe o identificador do item; o teste usa elementos simulados e a revisão manual confirma a persistência.',
 'reservationList':'Agrupamento imutável das reservas do cliente. Atendimentos em curso aparecem antes das próximas reservas, ordenadas por proximidade; registros encerrados ou vencidos ficam no histórico. A ordenação não altera estados de domínio.',
 'catalogPolicy':'Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável.',
 'storeMaps':'Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista.',
 'storeOptions':'Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção.',
 'storeLocation':'Coordenadas e links de mapa. Coordenadas precisam formar um par válido; link de mapa não cadastra automaticamente uma empresa no Google.',
 'actionGuard':'Controle visual de ações assíncronas. Evita repetição acidental de clique; idempotência e concorrência continuam sendo responsabilidade do backend.',
 'mobileNavigation':'Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal.',
 'adminSections':'Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API.',
 'adminProfile':'Edição administrativa de perfil. CPF é opcional e pode ser explicitamente removido; não reter o valor antigo no estado após a resposta.',
 'postalCode':'Consulta de CEP com alternativa local de homologação. Falhas de conexão precisam permitir correção manual e não bloquear toda a navegação.',
 'storeCovers':'Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise.',
 'demoInbox':'Caixa de mensagens fictícias. Exige administração na API e existe para conferir avisos sem entregar mensagem a pessoas reais.',
 'supportComposer':'Controles compartilhados de resposta ao atendimento. Rótulo explícito identifica o destino, ajuda descreve limites e não solicitar senhas. Conteúdo enviado continua sujeito à autorização e validação da API.',
 'deliveryBindings':'Ligação dos botões de aceite de oferta com o identificador data-accept-delivery renderizado na tela. O teste reproduz o clique sem um atributo data-id genérico; a API valida novamente disponibilidade e entregador.',
}

def walk(node):
    yield node
    for child in node.named_children: yield from walk(child)

def compact(value): return re.sub(r'\s+',' ',value).strip()

def describe(name,kind):
    if kind=='constructor_declaration': return 'Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.'
    if name.startswith(('get','is')): return 'Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.'
    if name.startswith(('find','exists','count')): return 'Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.'
    words=re.sub(r'([a-z0-9])([A-Z])',r'\1 \2',name).replace('_',' ')
    return f'Executa a etapa `{words}` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.'

def generate(language,files,filename):
    parser=Parser(Language((tree_sitter_java if language=='java' else tree_sitter_javascript).language()))
    title='backend Java' if language=='java' else 'frontend JavaScript'
    out=[f'# Referência completa de funções — {title}', '', 'Gerada a partir da árvore sintática do código final. Inclui funções explícitas, métodos privados, construtores e callbacks. Não é uma certificação de segurança: use junto da análise de engenharia e da matriz de testes.', '', 'Cada seção identifica localização, assinatura, regras locais, chamadas e efeitos observáveis. Getters implícitos de `record`, métodos herdados de bibliotecas e APIs nativas são descritos por seus contratos, não reproduzidos como código do projeto.', '', 'Regeneração: criar ambiente Python isolado, instalar `scripts/reference-requirements.txt` e executar `python scripts/generate_reference.py` na raiz.', '']
    count=0
    digest=hashlib.sha256()
    for path in files:
        data=path.read_bytes();digest.update(str(path.relative_to(ROOT)).encode()+data)
        tree=parser.parse(data)
        if tree.root_node.has_error: raise RuntimeError(f'Falha de análise: {path}')
        txt=lambda n: data[n.start_byte:n.end_byte].decode()
        rel=path.relative_to(ROOT)
        context=CONTEXT.get(path.parent.name if language=='java' else path.stem,'Inicialização da aplicação e ligação dos módulos; manter separação entre configuração e regra de negócio.')
        out += [f'## {rel}', '',context,'',f'Fonte: [{path.name}]({path}) · SHA-256 `{hashlib.sha256(data).hexdigest()}`','']
        declarations=[n for n in walk(tree.root_node) if n.type in ('record_declaration','enum_declaration')]
        for n in declarations:
            header=txt(n).split('{',1)[0]
            out += ['**Contrato de dados/estados:**',f'```{language}',header.strip(),'```','Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.','']
        types={'method_declaration','constructor_declaration','compact_constructor_declaration'} if language=='java' else {'function_declaration','arrow_function','function_expression','method_definition','generator_function_declaration'}
        funcs=[n for n in walk(tree.root_node) if n.type in types]
        for n in funcs:
            count+=1; body=n.child_by_field_name('body'); name_node=n.child_by_field_name('name')
            name=txt(name_node) if name_node else ''
            if not name and n.parent and n.parent.type=='variable_declarator':
                field=n.parent.child_by_field_name('name');name=txt(field) if field else ''
            if not name: name=f'callback@{n.start_point.row+1}:{n.start_point.column+1}'
            source=txt(n);signature=compact(data[n.start_byte:body.start_byte].decode() if body else source)
            children=list(walk(body)) if body else []
            calls=[]
            for c in children:
                if c.type in ('method_invocation','call_expression'):
                    call=txt(c).split('(',1)[0]
                    if call not in calls:calls.append(call)
            returns=[compact(txt(c)) for c in children if c.type=='return_statement']
            guards=[compact(txt(c)) for c in children if c.type=='throw_statement']
            conditions=[]
            for c in children:
                if c.type in ('if_statement','ternary_expression'):
                    condition=c.child_by_field_name('condition')
                    if condition is not None:
                        value=compact(txt(condition))
                        if value not in conditions:conditions.append(value)
            effects=[line.strip() for line in source.splitlines() if re.search(r'\.save\(|\.delete|\.set[A-Z]|\.update|\.send|\.transition|\.paid\(|\.advance|\.requestFullRefund|\.innerHTML\s*=|\.textContent\s*=|localStorage\.|sessionStorage\.|\.addEventListener|\.fetch\(|await api\(',line)]
            out += [f'### {name} — linha {n.start_point.row+1}', '', f'[Abrir implementação]({path}:{n.start_point.row+1})', '',f'```{language}',signature,'```','',describe(name,n.type),'']
            if calls:out += ['**Colaborações:** '+ '; '.join(f'`{compact(c)}`' for c in calls)+'.','']
            if guards:out += ['**Rejeições locais:**']+['- `'+g.replace('`','\\`')+'`' for g in guards]+['']
            if conditions:out += ['**Decisões condicionais examinadas:**']+['- `'+c.replace('`','\\`')+'`' for c in conditions]+['']
            if returns:out += ['**Saída declarada:**']+['- `'+r.replace('`','\\`')+'`' for r in returns]+['']
            if effects:out += ['**Efeitos identificados no corpo (inclui callbacks internos):**',f'```{language}', '\n'.join(dict.fromkeys(effects)),'```','']
            if not body:out += ['Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.','']
            out += ['**Decisão de engenharia:** '+context+' Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.','']
        if not funcs:out += ['Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.','']
    out[2]+=f' Cobertura estrutural: **{len(files)} arquivos e {count} funções explícitas**. Hash agregado: `{digest.hexdigest()}`.'
    (ROOT/'Documentacao'/filename).write_text('\n'.join(out)+'\n')
    print(filename,len(files),count)

generate('java',sorted((ROOT/'Backend/src/main/java').rglob('*.java')),'Referencia_Backend.md')
generate('javascript',sorted((ROOT/'Frontend/src').glob('*.js')),'Referencia_Frontend.md')

inventory=['# Inventário de interface, HTTP e migrações','', 'Inventário estático da árvore de código documentada; uma linha aqui não significa que a ação foi executada em todas as combinações. Consulte a matriz de validação.','', '## Atributos de interação do frontend','', 'Os atributos `data-*` conectam botões, formulários e estados aos handlers documentados na referência. Alguns são seletores de apresentação, não ações. Valores dinâmicos são escapados no template e revalidados no servidor.','', '| Atributo | Primeira ocorrência |','|---|---|']
attrs={}
for path in sorted((ROOT/'Frontend').rglob('*.js')):
    if 'node_modules' in path.parts:continue
    for line,text in enumerate(path.read_text().splitlines(),1):
        for key in re.findall(r'\bdata-[a-z][a-z0-9-]*',text):attrs.setdefault(key,(path,line))
for key,(path,line) in sorted(attrs.items()):inventory.append(f'| `{key}` | [{path.name}:{line}]({path}:{line}) |')
inventory += ['', '## Contratos HTTP declarados','', 'A rota efetiva combina o prefixo da classe e o sufixo do método. `SecurityConfig` decide acesso público/autenticado; `@PreAuthorize` e as verificações de titularidade no corpo restringem a operação. Uma anotação ausente não significa acesso público.','']
parser=Parser(Language(tree_sitter_java.language()))
for path in sorted((ROOT/'Backend/src/main/java').rglob('*.java')):
    data=path.read_bytes();tree=parser.parse(data);text=lambda n:data[n.start_byte:n.end_byte].decode()
    routes=[]
    for n in walk(tree.root_node):
        if n.type!='method_declaration':continue
        body=n.child_by_field_name('body');head=data[n.start_byte:body.start_byte].decode() if body else text(n)
        if not re.search(r'@(Get|Post|Put|Patch|Delete|Request)Mapping',head):continue
        parent=n.parent
        while parent is not None and parent.type!='class_declaration':parent=parent.parent
        class_head=text(parent).split('{',1)[0] if parent is not None else ''
        name=n.child_by_field_name('name')
        routes.append((n.start_point.row+1,text(name),compact(class_head),compact(head)))
    if routes:
        inventory += [f'### {path.stem}','']
        for line,name,cls,head in routes:inventory += [f'- [{name}]({path}:{line})',f'  - Classe: `{cls}`',f'  - Método: `{head}`']
        inventory.append('')
latest_migration=max(int(re.search(r'V(\d+)',p.name)[1]) for p in (ROOT/'Backend/src/main/resources/db').rglob('V*.sql'))
inventory += ['## Migrações e razão da separação','', f'Flyway executa a sequência V1–V{latest_migration}. V9 tem variantes H2/PostgreSQL para diferenças de mecanismo. O perfil escolhe uma variante, nunca as duas. Os demais scripts são comuns. Hibernate valida o resultado; não é proprietário do esquema.','', '| Versão / arquivo | DDL e finalidade identificável |','|---|---|']
for path in sorted((ROOT/'Backend/src/main/resources/db').rglob('*.sql'),key=lambda p:(int(re.search(r'V(\d+)',p.name)[1]),str(p))):
    statements=[compact(s) for s in path.read_text().split(';') if s.strip()]
    tables=sorted(set(re.findall(r'(?:TABLE(?: IF NOT EXISTS)?|INDEX[^;]*? ON)\s+([a-z_]+)',path.read_text(),re.I)))
    inventory.append(f'| [{path.name}]({path}) | {len(statements)} instruções; relações/índices: '+', '.join(f'`{t}`' for t in tables)+' |')
inventory += ['', '## Arquivos de operação','', '| Arquivo | Papel e avaliação |','|---|---|', '| `scripts/dev.py` | Supervisor local; identifica serviços existentes para não iniciar cópias. Seis testes unitários cobrem sondagem/reutilização. |', '| `scripts/homologacao.py` | Supervisor isolado; não lê `.env`, gera chave própria, valida portas e inicia fixtures. SO_REUSEADDR permite reinício após fechamento sem compartilhar listener ocupado. |', '| `Frontend/scripts/start.py` | Servidor local de arquivos e proxy exclusivo de demonstração; não é servidor de borda para produção. |', '| `Backend/scripts/backup-db.sh` | Dump e checksum. Ensaio PostgreSQL descartável coberto por test_postgres.py; recuperação completa de produção permanece pendente. |', '| `Backend/scripts/restore-db.sh` | Confere checksum e confirmação de banco; é destrutivo no destino escolhido. Não foi executado no banco real. |', '| `scripts/test_homologacao.py` | Contratos da simulação, cinco logins por execução; não rodar repetidamente junto de outros logins sem considerar rate limit. |', '| `scripts/test_jornadas.py` | CRUD, autorização, suporte, administração e sequência completa de entrega em fixtures. |', '| `.github/workflows` | Qualidade/segurança declaradas; presença de YAML não demonstra execução bem-sucedida no GitHub. |', '| `Frontend/styles.css` / `theme.css` | Base e refinamentos de componentes, responsividade e foco; estilos sobrepostos exigem inspeção do resultado computado. |', '| `Frontend/index.html` / `demo.html` | Entrada normal e caixa de mensagens fictícias; metadados e carregamento de módulos. |', '| `Frontend/runtime-config.js` | Em estático mantém modo normal; o servidor fictício fornece marcador explícito de homologação. |', '| `Backend/src/main/resources/application*.yml` | Configuração por perfil; credenciais privadas e ambiente não devem ser publicados como documentação. |', '']
(ROOT/'Documentacao/Inventario_Interfaces_e_Dados.md').write_text('\n'.join(inventory))
print('Inventario_Interfaces_e_Dados.md',len(attrs),'atributos')
