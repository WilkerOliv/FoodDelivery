package br.com.fooddelivery.catalog;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** Store-local, half-open opening windows. Legacy single intervals remain readable. */
@Service
public class StoreOperatingHours {
    private final ObjectMapper mapper;
    public StoreOperatingHours(ObjectMapper mapper){this.mapper=mapper;}
    public record Interval(String opensAt,String closesAt){}
    public record ExceptionHours(LocalDate date,boolean closed,String opensAt,String closesAt,String note,List<Interval> shifts){
        public ExceptionHours(LocalDate date,boolean closed,String opensAt,String closesAt,String note){this(date,closed,opensAt,closesAt,note,null);}
    }
    public record Configuration(boolean enabled,Map<DayOfWeek,Interval> weekly,List<ExceptionHours> exceptions,Map<DayOfWeek,List<Interval>> weeklyShifts){
        public Configuration(boolean enabled,Map<DayOfWeek,Interval> weekly,List<ExceptionHours> exceptions){this(enabled,weekly,exceptions,null);}
    }
    public Configuration read(Store store){return read(store.isOperatingHoursEnabled(),store.getWeeklyHoursJson(),store.getHoursExceptionsJson());}
    public Configuration read(StoreProfessional professional){return read(professional.isOperatingHoursEnabled(),professional.getWeeklyHoursJson(),professional.getHoursExceptionsJson());}
    private Configuration read(boolean enabled,String weeklyJson,String exceptionsJson){
        try{
            var node=mapper.readTree(weeklyJson);Map<DayOfWeek,List<Interval>> shifts=new EnumMap<>(DayOfWeek.class);
            node.properties().forEach(entry->{DayOfWeek day=DayOfWeek.valueOf(entry.getKey());List<Interval> values=new ArrayList<>();
                if(entry.getValue().isArray())entry.getValue().forEach(value->values.add(mapper.convertValue(value,Interval.class)));
                else values.add(mapper.convertValue(entry.getValue(),Interval.class));shifts.put(day,values);});
            List<ExceptionHours> exceptions=mapper.readValue(exceptionsJson,mapper.getTypeFactory().constructCollectionType(ArrayList.class,ExceptionHours.class));
            return validate(new Configuration(enabled,Map.of(),exceptions,shifts));
        }catch(RuntimeException ex){throw new IllegalStateException("A configuração de horário desta loja está inválida.",ex);}
    }
    public Configuration save(Store store,Configuration input){
        Configuration config=validate(input);
        try{store.setOperatingHours(config.enabled(),mapper.writeValueAsString(config.weeklyShifts()),mapper.writeValueAsString(config.exceptions()));return config;}
        catch(JacksonException ex){throw new IllegalArgumentException("Não foi possível salvar os horários da loja.",ex);}
    }
    public Configuration save(StoreProfessional professional,Configuration input){var config=validate(input);try{professional.setOperatingHours(config.enabled(),mapper.writeValueAsString(config.weeklyShifts()),mapper.writeValueAsString(config.exceptions()));return config;}catch(JacksonException error){throw new IllegalArgumentException("Não foi possível salvar a disponibilidade do profissional.");}}
    public boolean fits(StoreProfessional professional,ZoneId zone,Instant start,Instant end){return fits(read(professional),zone,start,end);}
    public Configuration validate(Configuration input){
        if(input==null)throw new IllegalArgumentException("Informe a configuração de funcionamento.");
        Map<DayOfWeek,List<Interval>> shifts=new EnumMap<>(DayOfWeek.class);
        if(input.weeklyShifts()!=null)input.weeklyShifts().forEach((day,values)->{if(day==null)throw new IllegalArgumentException("Dia de funcionamento inválido.");List<Interval> normalized=normalize(values);if(!normalized.isEmpty())shifts.put(day,normalized);});
        else if(input.weekly()!=null)input.weekly().forEach((day,value)->{if(day==null||value==null)throw new IllegalArgumentException("Revise os dias e horários.");shifts.put(day,normalize(List.of(value)));});
        if(input.enabled()&&shifts.isEmpty())throw new IllegalArgumentException("Defina ao menos um dia de funcionamento antes de ativar os horários.");
        // Detect overlap across adjacent days, including Sunday -> Monday.
        List<int[]> week=new ArrayList<>();shifts.forEach((day,values)->values.forEach(value->{int start=(day.getValue()-1)*1440+minute(value.opensAt(),false),end=(day.getValue()-1)*1440+endMinute(value);if(end>10080){week.add(new int[]{start,10080});week.add(new int[]{0,end-10080});}else week.add(new int[]{start,end});}));rejectOverlap(week);
        List<ExceptionHours> exceptions=new ArrayList<>();Set<LocalDate> dates=new HashSet<>();
        if(input.exceptions()!=null)for(ExceptionHours e:input.exceptions()){
            if(e==null||e.date()==null||!dates.add(e.date()))throw new IllegalArgumentException("Cada exceção precisa ter uma data única e válida.");
            if(e.note()!=null&&e.note().length()>160)throw new IllegalArgumentException("A observação da exceção pode ter até 160 caracteres.");
            List<Interval> values=e.closed()?List.of():normalize(e.shifts()!=null?e.shifts():List.of(new Interval(e.opensAt(),e.closesAt())));
            if(!e.closed()&&values.isEmpty())throw new IllegalArgumentException("Defina ao menos um turno para o horário especial.");
            Interval first=values.isEmpty()?null:values.get(0);
            exceptions.add(new ExceptionHours(e.date(),e.closed(),first==null?null:first.opensAt(),first==null?null:first.closesAt(),e.note()==null?null:e.note().trim(),values));
        }
        if(exceptions.size()>90)throw new IllegalArgumentException("Cadastre no máximo 90 exceções de horário. Remova as datas antigas antes de adicionar novas.");
        Map<DayOfWeek,Interval> legacy=new EnumMap<>(DayOfWeek.class);shifts.forEach((day,values)->legacy.put(day,values.get(0)));
        return new Configuration(input.enabled(),Collections.unmodifiableMap(legacy),exceptions.stream().sorted(Comparator.comparing(ExceptionHours::date)).toList(),Collections.unmodifiableMap(shifts));
    }
    private static List<Interval> normalize(List<Interval> values){
        if(values==null)throw new IllegalArgumentException("Informe os turnos do dia.");
        if(values.size()>6)throw new IllegalArgumentException("Use no máximo seis turnos por dia.");
        List<Interval> result=new ArrayList<>();List<int[]> ranges=new ArrayList<>();
        for(Interval v:values){if(v==null)throw new IllegalArgumentException("Turno inválido.");int start=minute(v.opensAt(),false),close=minute(v.closesAt(),true);if(start==close)throw new IllegalArgumentException("Abertura e fechamento precisam ser diferentes. Para 24 horas, use a opção correspondente.");ranges.add(new int[]{start,close>start?close:close+1440});result.add(v);}
        rejectOverlap(ranges);result.sort(Comparator.comparingInt(v->minute(v.opensAt(),false)));return List.copyOf(result);
    }
    private static void rejectOverlap(List<int[]> ranges){ranges.sort(Comparator.comparingInt(r->r[0]));int end=-1;for(int[] range:ranges){if(range[0]<end)throw new IllegalArgumentException("Há turnos sobrepostos. Confira também os horários que atravessam a meia-noite.");end=range[1];}}
    private static int minute(String value,boolean closing){
        if(closing&&"24:00".equals(value))return 1440;
        if(value==null||!value.matches("(?:[01]\\d|2[0-3]):[0-5]\\d"))throw new IllegalArgumentException("Informe horários válidos no formato HH:mm.");
        return Integer.parseInt(value.substring(0,2))*60+Integer.parseInt(value.substring(3));
    }
    private static int endMinute(Interval value){int start=minute(value.opensAt(),false),end=minute(value.closesAt(),true);return end>start?end:end+1440;}
    private static ExceptionHours exceptionFor(Configuration config,LocalDate date){return config.exceptions().stream().filter(e->e.date().equals(date)).findFirst().orElse(null);}
    private static List<Interval> anchored(Configuration config,LocalDate date){ExceptionHours e=exceptionFor(config,date);return e!=null?e.shifts():config.weeklyShifts().getOrDefault(date.getDayOfWeek(),List.of());}
    private static List<int[]> dayRanges(Configuration config,LocalDate date){
        ExceptionHours today=exceptionFor(config,date);if(today!=null&&today.closed())return List.of();
        if(today==null&&!config.enabled())return List.of(new int[]{0,1440});
        List<int[]> ranges=new ArrayList<>();for(Interval shift:anchored(config,date))ranges.add(new int[]{minute(shift.opensAt(),false),Math.min(1440,endMinute(shift))});
        // An explicit dated exception replaces the entire calendar day, including previous-day spill.
        if(today==null)for(Interval shift:anchored(config,date.minusDays(1))){int end=endMinute(shift);if(end>1440)ranges.add(new int[]{0,end-1440});}
        ranges.sort(Comparator.comparingInt(r->r[0]));return ranges;
    }
    private static Instant boundary(LocalDate date,int minute,ZoneId zone){return minute==1440?date.plusDays(1).atStartOfDay(zone).toInstant():date.atTime(minute/60,minute%60).atZone(zone).toInstant();}
    public boolean isOpenAt(Store store,Instant instant){return isOpenAt(read(store),ZoneId.of(store.getTimezone()),instant);}
    public boolean isOpenAt(Configuration config,ZoneId zone,Instant instant){LocalDate date=instant.atZone(zone).toLocalDate();return dayRanges(config,date).stream().anyMatch(r->!instant.isBefore(boundary(date,r[0],zone))&&instant.isBefore(boundary(date,r[1],zone)));}
    public boolean fits(Store store,Instant start,Instant end){return fits(read(store),ZoneId.of(store.getTimezone()),start,end);}
    /** Exact boundary coverage, not periodic sampling: even a one-minute break rejects the booking. */
    public boolean fits(Configuration config,ZoneId zone,Instant start,Instant end){
        if(start==null||end==null||!end.isAfter(start)||Duration.between(start,end).compareTo(Duration.ofHours(8))>0)return false;
        List<Instant[]> windows=new ArrayList<>();LocalDate first=start.atZone(zone).toLocalDate(),last=end.minusNanos(1).atZone(zone).toLocalDate();
        for(LocalDate date=first;!date.isAfter(last);date=date.plusDays(1))for(int[] range:dayRanges(config,date))windows.add(new Instant[]{boundary(date,range[0],zone),boundary(date,range[1],zone)});
        windows.sort(Comparator.comparing(w->w[0]));Instant cursor=start;
        for(Instant[] window:windows){if(window[0].isAfter(cursor))break;if(window[1].isAfter(cursor))cursor=window[1];if(!cursor.isBefore(end))return true;}return false;
    }
    public void requireOpenForInterval(Store store,Instant start,Instant end){if(!fits(store,start,end))throw new IllegalArgumentException("O serviço não cabe por inteiro no horário de funcionamento da loja.");}
    public void requireOpenNow(Store store){if(!isOpenAt(store,Instant.now()))throw new IllegalStateException("A loja está fechada neste horário e não pode receber novos pedidos.");}
    public void requireOpenAt(Store store,Instant instant){if(!isOpenAt(store,instant))throw new IllegalArgumentException("Este horário está fora do funcionamento da loja. Escolha um dia e horário em que ela esteja aberta.");}
}
