/** Shared, explicitly labelled reply controls for customer and administrator. */
export function renderSupportReplyFields(isAdmin) {
  const label = isAdmin ? 'Resposta ao cliente' : 'Mensagem para o atendimento';
  const placeholder = isAdmin ? 'Escreva uma resposta para o cliente…' : 'Digite sua mensagem…';
  return `<div class="field support-reply-field"><label for="support-reply-body">${label}</label><textarea id="support-reply-body" name="body" maxlength="2000" required aria-describedby="support-reply-help" placeholder="${placeholder}"></textarea><small id="support-reply-help">Até 2.000 caracteres. Não envie senhas nem códigos de verificação.</small></div><button class="btn small" type="submit">Enviar resposta</button>`;
}
