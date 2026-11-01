export function storeMapUrl(store) {
  const query = store?.latitude != null && store?.longitude != null
    ? `${store.latitude},${store.longitude}`
    : `${store?.address || ''}, ${store?.city || ''}, ${store?.state || ''}`;
  return `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(query)}`;
}

export function coordinatesFromForm(form) {
  const latitudeText = String(form.get('latitude') || '').trim();
  const longitudeText = String(form.get('longitude') || '').trim();
  if (Boolean(latitudeText) !== Boolean(longitudeText)) {
    throw new Error('Informe latitude e longitude juntas ou deixe os dois campos vazios.');
  }
  if (!latitudeText) return { latitude: null, longitude: null };

  const latitude = Number(latitudeText);
  const longitude = Number(longitudeText);
  if (!Number.isFinite(latitude) || latitude < -90 || latitude > 90
      || !Number.isFinite(longitude) || longitude < -180 || longitude > 180) {
    throw new Error('As coordenadas informadas estão fora dos limites válidos.');
  }
  return { latitude, longitude };
}
