package br.com.fooddelivery.catalog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** Store-local weekly hours plus dated exceptions. An empty/disabled schedule preserves legacy 24/7 behavior. */
@Service
public class StoreOperatingHours {
    private final ObjectMapper mapper;

    public StoreOperatingHours(ObjectMapper mapper) { this.mapper = mapper; }

    public record Interval(String opensAt, String closesAt) {}
    public record ExceptionHours(LocalDate date, boolean closed, String opensAt, String closesAt, String note) {}
    public record Configuration(boolean enabled, Map<DayOfWeek, Interval> weekly, List<ExceptionHours> exceptions) {}

    public Configuration read(Store store) {
        try {
            Map<DayOfWeek, Interval> weekly = mapper.readValue(store.getWeeklyHoursJson(), mapper.getTypeFactory()
                    .constructMapType(EnumMap.class, DayOfWeek.class, Interval.class));
            List<ExceptionHours> exceptions = mapper.readValue(store.getHoursExceptionsJson(), mapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, ExceptionHours.class));
            return new Configuration(store.isOperatingHoursEnabled(), weekly == null ? Map.of() : weekly,
                    exceptions == null ? List.of() : exceptions);
        } catch (JsonProcessingException | RuntimeException ex) {
            throw new IllegalStateException("A configuração de horário desta loja está inválida.", ex);
        }
    }

    public Configuration save(Store store, Configuration configuration) {
        Configuration normalized = validate(configuration);
        try {
            store.setOperatingHours(normalized.enabled(), mapper.writeValueAsString(normalized.weekly()),
                    mapper.writeValueAsString(normalized.exceptions()));
            return normalized;
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Não foi possível salvar os horários da loja.", ex);
        }
    }

    public Configuration validate(Configuration input) {
        if (input == null) throw new IllegalArgumentException("Informe a configuração de funcionamento.");
        Map<DayOfWeek, Interval> weekly = new EnumMap<>(DayOfWeek.class);
        if (input.weekly() != null) input.weekly().forEach((day, interval) -> {
            if (day == null || interval == null) throw new IllegalArgumentException("Revise os dias e horários informados.");
            validateInterval(interval);
            weekly.put(day, interval);
        });
        List<ExceptionHours> exceptions = input.exceptions() == null ? List.of() : List.copyOf(input.exceptions());
        if (exceptions.size() > 90) throw new IllegalArgumentException("Cadastre no máximo 90 exceções de horário.");
        Set<LocalDate> dates = new HashSet<>();
        for (ExceptionHours exception : exceptions) {
            if (exception == null || exception.date() == null) throw new IllegalArgumentException("Cada exceção precisa ter uma data válida.");
            if (!dates.add(exception.date())) throw new IllegalArgumentException("Há mais de uma exceção cadastrada para a mesma data.");
            if (!exception.closed()) validateInterval(new Interval(exception.opensAt(), exception.closesAt()));
            if (exception.note() != null && exception.note().length() > 160) throw new IllegalArgumentException("A observação da exceção pode ter até 160 caracteres.");
        }
        if (input.enabled() && weekly.isEmpty()) throw new IllegalArgumentException("Defina ao menos um dia de funcionamento antes de ativar os horários.");
        return new Configuration(input.enabled(), Collections.unmodifiableMap(weekly), exceptions.stream()
                .sorted(Comparator.comparing(ExceptionHours::date)).toList());
    }

    public boolean isOpenAt(Store store, Instant instant) {
        Configuration config = read(store);
        ZoneId zone = ZoneId.of(store.getTimezone());
        return isOpenAt(config, zone, instant);
    }

    /** Requires the entire service interval to fit inside opening hours, including date exceptions. */
    public void requireOpenForInterval(Store store, Instant startsAt, Instant endsAt) {
        if (startsAt == null || endsAt == null || !endsAt.isAfter(startsAt))
            throw new IllegalArgumentException("Informe um intervalo de atendimento válido.");
        if (Duration.between(startsAt, endsAt).toMinutes() > 480)
            throw new IllegalArgumentException("O atendimento não pode ultrapassar 8 horas.");
        Configuration config = read(store);
        ZoneId zone = ZoneId.of(store.getTimezone());
        for (Instant point = startsAt; point.isBefore(endsAt); point = point.plusSeconds(60)) {
            if (!isOpenAt(config, zone, point)) throw new IllegalArgumentException("O serviço não cabe por inteiro no horário de funcionamento da loja.");
        }
        if (!isOpenAt(config, zone, endsAt.minusNanos(1)))
            throw new IllegalArgumentException("O serviço não cabe por inteiro no horário de funcionamento da loja.");
    }

    private boolean isOpenAt(Configuration config, ZoneId zone, Instant instant) {
        ZonedDateTime local = instant.atZone(zone);
        ExceptionHours todayException = exceptionFor(config, local.toLocalDate());
        if (todayException != null) {
            return !todayException.closed() && contains(local.toLocalTime(), todayException.opensAt(), todayException.closesAt(), false);
        }
        if (!config.enabled()) return true;
        Interval today = config.weekly().get(local.getDayOfWeek());
        if (today != null && contains(local.toLocalTime(), today.opensAt(), today.closesAt(), false)) return true;

        LocalDate yesterday = local.toLocalDate().minusDays(1);
        ExceptionHours yesterdayException = exceptionFor(config, yesterday);
        if (yesterdayException != null) {
            return !yesterdayException.closed() && crossesMidnight(new Interval(yesterdayException.opensAt(), yesterdayException.closesAt()))
                    && local.toLocalTime().isBefore(LocalTime.parse(yesterdayException.closesAt()));
        }
        Interval overnight = config.weekly().get(yesterday.getDayOfWeek());
        return overnight != null && crossesMidnight(overnight)
                && local.toLocalTime().isBefore(LocalTime.parse(overnight.closesAt()));
    }

    public void requireOpenNow(Store store) {
        if (!isOpenAt(store, Instant.now())) throw new IllegalStateException("A loja está fechada neste horário e não pode receber novos pedidos.");
    }

    public void requireOpenAt(Store store, Instant instant) {
        if (!isOpenAt(store, instant)) throw new IllegalArgumentException("Este horário está fora do funcionamento da loja. Escolha um dia e horário em que ela esteja aberta.");
    }

    private static ExceptionHours exceptionFor(Configuration c, LocalDate date) {
        return c.exceptions().stream().filter(e -> e.date().equals(date)).findFirst().orElse(null);
    }

    private static boolean contains(LocalTime time, String opens, String closes, boolean overnightOnly) {
        LocalTime open = LocalTime.parse(opens), close = LocalTime.parse(closes);
        if (close.isAfter(open)) return !overnightOnly && !time.isBefore(open) && time.isBefore(close);
        return !overnightOnly && !time.isBefore(open) || overnightOnly && time.isBefore(close);
    }

    private static boolean crossesMidnight(Interval interval) { return !LocalTime.parse(interval.closesAt()).isAfter(LocalTime.parse(interval.opensAt())); }

    private static void validateInterval(Interval interval) {
        try {
            LocalTime open = LocalTime.parse(Objects.requireNonNull(interval.opensAt()));
            LocalTime close = LocalTime.parse(Objects.requireNonNull(interval.closesAt()));
            if (open.equals(close)) throw new IllegalArgumentException("A abertura e o fechamento não podem ser iguais.");
        } catch (DateTimeException | NullPointerException ex) {
            throw new IllegalArgumentException("Informe horários válidos no formato HH:mm.", ex);
        }
    }
}
