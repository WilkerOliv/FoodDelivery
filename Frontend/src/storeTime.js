function partsAt(instant, timeZone) {
  const parts = new Intl.DateTimeFormat('en-CA', {
    timeZone, year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit', second: '2-digit', hourCycle: 'h23'
  }).formatToParts(instant);
  return Object.fromEntries(parts.filter(part => part.type !== 'literal').map(part => [part.type, Number(part.value)]));
}

export function localDateTimeInZoneToIso(value, timeZone) {
  const match = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})$/.exec(value || '');
  if (!match) throw new Error('Escolha uma data e um horário válidos.');
  const desired = match.slice(1).map(Number);
  const [year, month, day, hour, minute] = desired;
  const target = Date.UTC(year, month - 1, day, hour, minute);
  let candidate = target;
  for (let i = 0; i < 4; i += 1) {
    const local = partsAt(new Date(candidate), timeZone);
    const represented = Date.UTC(local.year, local.month - 1, local.day, local.hour, local.minute, local.second);
    candidate += target - represented;
  }
  const verified = partsAt(new Date(candidate), timeZone);
  if (verified.year !== year || verified.month !== month || verified.day !== day || verified.hour !== hour || verified.minute !== minute)
    throw new Error('Esse horário não existe no fuso da loja por causa da mudança de relógio. Escolha outro horário.');
  return new Date(candidate).toISOString();
}

export function localDateTimeMinimum(timeZone, minutesFromNow = 31, now = new Date()) {
  const local = partsAt(new Date(now.getTime() + minutesFromNow * 60_000), timeZone);
  return `${local.year}-${String(local.month).padStart(2, '0')}-${String(local.day).padStart(2, '0')}T${String(local.hour).padStart(2, '0')}:${String(local.minute).padStart(2, '0')}`;
}

export function dateTimeInZone(value, timeZone) {
  return new Intl.DateTimeFormat('pt-BR', { dateStyle: 'medium', timeStyle: 'short', timeZone }).format(new Date(value));
}

export function storeOpenAt(configuration, timeZone, instant = new Date()) {
  if (!configuration) return true;
  const local = partsAt(instant, timeZone || 'America/Sao_Paulo');
  const date = `${local.year}-${String(local.month).padStart(2, '0')}-${String(local.day).padStart(2, '0')}`;
  const todayOverride = configuration.exceptions?.find(exception => exception.date === date);
  if (todayOverride) return !todayOverride.closed && intervalContains(`${String(local.hour).padStart(2, '0')}:${String(local.minute).padStart(2, '0')}`, todayOverride);
  if (!configuration.enabled) return true;
  const weekday = new Date(Date.UTC(local.year, local.month - 1, local.day)).getUTCDay();
  const dayKey = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'][weekday];
  const time = `${String(local.hour).padStart(2, '0')}:${String(local.minute).padStart(2, '0')}`;
  const interval = configuration.weekly?.[dayKey];
  if (interval && intervalContains(time, interval)) return true;
  const previousDate = new Date(Date.UTC(local.year, local.month - 1, local.day - 1));
  const previousKey = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'][previousDate.getUTCDay()];
  const previous = configuration.weekly?.[previousKey];
  const previousIso = `${previousDate.getUTCFullYear()}-${String(previousDate.getUTCMonth() + 1).padStart(2, '0')}-${String(previousDate.getUTCDate()).padStart(2, '0')}`;
  const yesterdayOverride = configuration.exceptions?.find(exception => exception.date === previousIso);
  const previousSchedule = yesterdayOverride || previous;
  return !!previousSchedule && !previousSchedule.closed && previousSchedule.closesAt <= previousSchedule.opensAt && time < previousSchedule.closesAt;
}

export function storeHoursStatusRelevant(configuration, timeZone, instant = new Date()) {
  if (!configuration) return false;
  if (configuration.enabled) return true;
  const local = partsAt(instant, timeZone || 'America/Sao_Paulo');
  const today = new Date(Date.UTC(local.year, local.month - 1, local.day));
  const isoDate = date => `${date.getUTCFullYear()}-${String(date.getUTCMonth() + 1).padStart(2, '0')}-${String(date.getUTCDate()).padStart(2, '0')}`;
  const todayIso = isoDate(today);
  today.setUTCDate(today.getUTCDate() - 1);
  const yesterdayIso = isoDate(today);
  return (configuration.exceptions || []).some(exception => exception.date === todayIso || exception.date === yesterdayIso);
}

export function storeScheduleAllowsLocalDateTime(configuration, value) {
  if (!configuration) return true;
  const match = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})$/.exec(value || '');
  if (!match) return false;
  const [, year, month, day, hour, minute] = match;
  const date = `${year}-${month}-${day}`;
  const time = `${hour}:${minute}`;
  const override = configuration.exceptions?.find(exception => exception.date === date);
  if (override) return !override.closed && intervalContains(time, override);
  if (!configuration.enabled) return true;
  const weekday = new Date(Date.UTC(Number(year), Number(month) - 1, Number(day))).getUTCDay();
  const key = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'][weekday];
  const interval = configuration.weekly?.[key];
  if (interval && intervalContains(time, interval)) return true;
  const previousDate = new Date(Date.UTC(Number(year), Number(month) - 1, Number(day) - 1));
  const previousIso = `${previousDate.getUTCFullYear()}-${String(previousDate.getUTCMonth() + 1).padStart(2, '0')}-${String(previousDate.getUTCDate()).padStart(2, '0')}`;
  const previousOverride = configuration.exceptions?.find(exception => exception.date === previousIso);
  const previousKey = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'][previousDate.getUTCDay()];
  const previous = previousOverride || configuration.weekly?.[previousKey];
  return !!previous && !previous.closed && previous.closesAt <= previous.opensAt && time < previous.closesAt;
}

function intervalContains(time, interval) {
  if (!interval?.opensAt || !interval?.closesAt) return false;
  if (interval.closesAt > interval.opensAt) return time >= interval.opensAt && time < interval.closesAt;
  // A madrugada pertence ao intervalo do dia anterior, consultado pelo chamador.
  return time >= interval.opensAt;
}
