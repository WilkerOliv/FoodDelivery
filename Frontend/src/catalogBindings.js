/** Connect catalog actions after each page render. */
export function bindCatalogActions(root, { create, edit }) {
  root.querySelectorAll('[data-create-item]').forEach(form => form.addEventListener('submit', create));
  root.querySelectorAll('[data-item-edit]').forEach(button => button.addEventListener('click', () => edit(button.dataset.itemEdit)));
}
