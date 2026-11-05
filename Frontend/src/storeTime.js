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

export function intervalsForDay(configuration, day) {
  return configuration?.weeklyShifts?.[day] ?? (configuration?.weekly?.[day] ? [configuration.weekly[day]] : []);
}
function minutes(value) { if(value==='24:00')return 1440;const [h,m]=String(value).split(':').map(Number);return h*60+m; }
function exceptionIntervals(exception) { return exception.closed ? [] : exception.shifts ?? [{opensAt:exception.opensAt,closesAt:exception.closesAt}]; }
function isoDate(date) { return date.toISOString().slice(0,10); }
export function rangesForDate(configuration, date) {
  const today=configuration?.exceptions?.find(e=>e.date===date);
  if(today?.closed)return [];
  if(!today&&!configuration?.enabled)return [[0,1440]];
  const day=new Date(date+'T12:00:00Z');
  if(Number.isNaN(day.getTime())||isoDate(day)!==date)return [];
  const keys=['SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY'];
  const ranges=(today?exceptionIntervals(today):intervalsForDay(configuration,keys[day.getUTCDay()])).map(v=>[minutes(v.opensAt),Math.min(1440,minutes(v.closesAt)>minutes(v.opensAt)?minutes(v.closesAt):minutes(v.closesAt)+1440)]);
  if(!today){day.setUTCDate(day.getUTCDate()-1);const previous=configuration?.exceptions?.find(e=>e.date===isoDate(day));
    for(const v of previous?exceptionIntervals(previous):intervalsForDay(configuration,keys[day.getUTCDay()])){const open=minutes(v.opensAt),close=minutes(v.closesAt);if(close<open)ranges.push([0,close]);}
  }
  return ranges.sort((a,b)=>a[0]-b[0]);
}
export function storeOpenAt(configuration,timeZone,instant=new Date()) {
  if(!configuration)return true;
  const local=partsAt(instant,timeZone||'America/Sao_Paulo');
  const date=`${local.year}-${String(local.month).padStart(2,'0')}-${String(local.day).padStart(2,'0')}`;
  const minute=local.hour*60+local.minute+local.second/60;
  return rangesForDate(configuration,date).some(([start,end])=>minute>=start&&minute<end);
}
export function storeHoursStatusRelevant(configuration,timeZone,instant=new Date()) {
  if(!configuration)return false;if(configuration.enabled)return true;
  const local=partsAt(instant,timeZone||'America/Sao_Paulo'),date=new Date(Date.UTC(local.year,local.month-1,local.day));
  const today=isoDate(date);date.setUTCDate(date.getUTCDate()-1);const yesterday=isoDate(date);
  return (configuration.exceptions||[]).some(e=>e.date===today||e.date===yesterday);
}
export function storeScheduleAllowsLocalDateTime(configuration,value) {
  const match=/^(\d{4}-\d{2}-\d{2})T(\d{2}):(\d{2})$/.exec(value||'');if(!match)return false;
  const [,date,h,m]=match,minute=Number(h)*60+Number(m);if(Number(h)>23||Number(m)>59)return false;
  return rangesForDate(configuration,date).some(([start,end])=>minute>=start&&minute<end);
}
export function storeScheduleAllowsAppointment(configuration,value,duration,timeZone) {
  if(!Number.isInteger(duration)||duration<1||duration>480)return false;
  let start;try{start=new Date(localDateTimeInZoneToIso(value,timeZone));}catch{return false;}
  // Input and schedule boundaries have minute precision; use elapsed time across timezone transitions.
  for(let minute=0;minute<duration;minute++)if(!storeOpenAt(configuration,timeZone,new Date(start.getTime()+minute*60000)))return false;
  return storeOpenAt(configuration,timeZone,new Date(start.getTime()+duration*60000-1));
}
