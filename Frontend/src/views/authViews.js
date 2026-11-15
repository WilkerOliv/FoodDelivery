/** Authentication presentation and local event binding; requests are supplied by the application. */
export function createAuthViews({ $, app, esc, icon, state, submitAuth, submitRecovery }) {
function renderAuth() {
  const recovery = state.recoveryMode;
  app.innerHTML = `<main class="auth-screen">
    <section class="auth-art"><div class="brand"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">pedidos e reservas perto de você</small></div></div>
      <div class="auth-pitch"><span class="eyebrow">A cidade cabe no seu pedido</span><h1>O que você quer viver hoje?</h1><p>Sabores, serviços e bons momentos por perto. Descubra o comércio local e resolva tudo em poucos passos.</p></div>
      <div class="auth-quote">Feito para conectar pessoas e negócios do seu bairro.<button type="button" class="auth-about-link" data-about-us>Conheça o FoodDelivery <span aria-hidden="true">→</span></button></div>
    </section>
    <section class="auth-form-wrap"><form class="auth-form" id="auth-form">
      <div class="mobile-auth-brand"><div class="brand-mark">${icon('store',24)}</div><div class="brand-word">FoodDelivery<small class="brand-caption">pedidos e reservas perto de você</small></div><button type="button" class="text-button" data-about-us>Saiba mais</button></div>
      ${state.backendStatus==='offline'?'<div class="alert-strip warn"><span>!</span><span>Não conseguimos acessar o servidor agora. Você pode entrar, mas pedidos e reservas só serão enviados quando a conexão voltar.</span></div>':''}
      ${recovery ? `<h2>${state.recoveryStep==='request'?'Recupere seu acesso':'Crie uma nova senha'}</h2>
      <p>${state.recoveryStep==='request'?'Escolha onde quer receber um código de segurança.':'Digite o código de 6 números enviado e escolha uma senha nova.'}</p>
      ${state.recoveryStep==='request'?`<div class="field"><label for="recovery-email">E-mail da conta</label><input id="recovery-email" type="email" autocomplete="email" value="${esc(state.recoveryEmail)}" required /></div>
      <div class="field"><label for="recovery-channel">Enviar código por</label><select id="recovery-channel"><option value="EMAIL" ${state.recoveryChannel==='EMAIL'?'selected':''}>E-mail</option><option value="WHATSAPP" ${state.recoveryChannel==='WHATSAPP'?'selected':''}>WhatsApp confirmado</option></select></div>
      <button class="btn block" type="submit">Enviar código <span>→</span></button>`:`<div class="field"><label for="recovery-code">Código de segurança</label><input id="recovery-code" inputmode="numeric" autocomplete="one-time-code" pattern="[0-9]{6}" maxlength="6" placeholder="000000" required /></div>
      <div class="field"><label for="recovery-new-password">Nova senha</label><input id="recovery-new-password" type="password" autocomplete="new-password" minlength="12" maxlength="72" required /></div>
      <div class="field"><label for="recovery-confirm-password">Repita a nova senha</label><input id="recovery-confirm-password" type="password" autocomplete="new-password" minlength="12" maxlength="72" required /></div>
      <button class="btn block" type="submit">Redefinir senha <span>→</span></button>`}
      <div class="auth-foot"><button type="button" class="text-button" data-recovery-back>Voltar para entrar</button></div>` : `<h2>${state.authMode === 'login' ? 'Que bom ter você por aqui' : 'Vamos começar?'}</h2>
      <p>${state.authMode === 'login' ? 'Entre na sua conta para continuar.' : 'Crie sua conta de cliente. É rapidinho.'}</p>
      <div class="auth-tabs"><button type="button" data-auth-mode="login" class="${state.authMode==='login'?'active':''}">Entrar</button><button type="button" data-auth-mode="register" class="${state.authMode==='register'?'active':''}">Criar conta</button></div>
      ${state.authMode==='register'?`<div class="field"><label for="auth-name">Seu nome</label><input id="auth-name" name="name" autocomplete="name" placeholder="Como podemos chamar você?" required maxlength="120" /></div>`:''}
      ${state.authMode==='register'?`<div class="field"><label for="auth-cpf">CPF</label><input id="auth-cpf" name="cpf" data-mask="cpf" autocomplete="off" inputmode="numeric" maxlength="14" placeholder="000.000.000-00" required /></div>`:''}
      <div class="field"><label for="auth-email">E-mail</label><input id="auth-email" name="email" type="email" autocomplete="email" placeholder="voce@email.com" required /></div>
      <div class="field"><label for="auth-password">Senha</label><input id="auth-password" name="password" type="password" autocomplete="${state.authMode==='login'?'current-password':'new-password'}" placeholder="${state.authMode==='login'?'Sua senha':'Pelo menos 12 caracteres'}" required minlength="${state.authMode==='register'?12:1}" maxlength="72" /></div>
      ${state.authMode==='register'?'<p class="warning-small">Ao criar a conta, você concorda com os termos e com o uso dos dados necessários para processar pedidos e reservas.</p>':''}
      ${state.authMode==='login'?'<details class="auth-mfa-input"><summary>Usa autenticador de administrador?</summary><label for="auth-mfa-code">Código do autenticador ou de recuperação</label><input id="auth-mfa-code" autocomplete="one-time-code" maxlength="32" placeholder="Digite somente se ativou o segundo fator"/></details>':''}
      <button class="btn block" type="submit" style="margin-top:15px">${state.authMode==='login'?'Entrar na minha conta':'Criar conta gratuita'} <span>→</span></button>
      <div class="auth-foot">${state.authMode==='login'?'<button type="button" class="text-button" data-forgot-password>Esqueceu sua senha?</button><button type="button" class="text-button" data-about-us>Saiba mais sobre nós</button>':'Ao continuar, seus dados são usados para autenticar você e atender seus pedidos.'}</div>`}
    </form></section>
    <section class="about-modal-backdrop" data-about-modal hidden><article class="about-modal" role="dialog" aria-modal="true" aria-labelledby="about-title"><button class="close-button" type="button" data-close-about aria-label="Fechar">×</button><span class="eyebrow">Comércio local, mais perto</span><h2 id="about-title">A cidade inteira em um só lugar.</h2><p>O FoodDelivery ajuda moradores a encontrar lojas, produtos e serviços da cidade sem precisar procurar em vários lugares.</p><div class="about-benefits"><section><span>🛍️</span><h3>Para quem compra</h3><p>Descubra estabelecimentos locais, faça pedidos e agende serviços em uma experiência simples.</p></section><section><span>🏪</span><h3>Para quem empreende</h3><p>Apresente sua loja e seu catálogo a clientes da região em um canal digital próprio da cidade.</p></section><section><span>🛵</span><h3>Para entregadores</h3><p>Encontre oportunidades de entrega local e acompanhe os pedidos aceitos pelo seu perfil.</p></section></div><p class="about-note">Lojas e perfis passam por análise. Pagamentos online são processados pelo provedor indicado no checkout.</p><button type="button" class="btn" data-close-about>Voltar para entrar</button></article></section>
  </main>`;
  $('#auth-form').addEventListener('submit', recovery ? submitRecovery : submitAuth);
  document.querySelectorAll('[data-auth-mode]').forEach(button => button.addEventListener('click', () => { state.authMode = button.dataset.authMode; renderAuth(); }));
  $('[data-forgot-password]')?.addEventListener('click',()=>{state.recoveryMode=true;state.recoveryStep='request';state.recoveryEmail=$('#auth-email')?.value||'';renderAuth();});
  $('[data-recovery-channel]')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
  $('#recovery-channel')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
  $('[data-recovery-back]')?.addEventListener('click',()=>{state.recoveryMode=false;state.recoveryStep='request';renderAuth();});
  document.querySelectorAll('[data-about-us]').forEach(button=>button.addEventListener('click',()=>{const modal=$('[data-about-modal]');modal.hidden=false;modal.querySelector('[data-close-about]')?.focus();}));
  document.querySelectorAll('[data-close-about]').forEach(button=>button.addEventListener('click',()=>{$('[data-about-modal]').hidden=true;}));
  $('[data-about-modal]')?.addEventListener('click',event=>{if(event.target===event.currentTarget)event.currentTarget.hidden=true;});
  $('[data-about-modal]')?.addEventListener('keydown',event=>{if(event.key==='Escape'){event.currentTarget.hidden=true;}});
}
  return { renderAuth };
}
