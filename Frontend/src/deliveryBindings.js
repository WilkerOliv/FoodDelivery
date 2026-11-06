/** Connect each offer to the identifier rendered on its accept button. */
export function bindCourierOfferActions(root, accept) {
  root.querySelectorAll('[data-accept-delivery]').forEach(button => {
    button.addEventListener('click', () => accept(button.dataset.acceptDelivery));
  });
}
