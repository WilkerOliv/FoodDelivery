package br.com.fooddelivery.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.fooddelivery.identity.Account;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreOperatingHoursTest {
    private final StoreOperatingHours hours = new StoreOperatingHours(new ObjectMapper().findAndRegisterModules());

    private Store store() {
        Account owner = new Account("hours@example.com", "Hours", "encoded");
        owner.setRole(br.com.fooddelivery.identity.AccountRole.MERCHANT);
        Store store = new Store(owner, "Loja", null, "Rua A", "Mercados");
        store.setTimezone("UTC");
        return store;
    }

    @Test void disabledScheduleLeavesExistingStoresUnrestricted() {
        assertTrue(hours.isOpenAt(store(), Instant.parse("2026-11-02T03:00:00Z")));
    }

    @Test void weeklyScheduleAllowsOnlyConfiguredDaysAndUsesExclusiveClosingTime() {
        Store store = store();
        store.setOperatingHours(true, "{\"MONDAY\":{\"opensAt\":\"09:00\",\"closesAt\":\"20:00\"}}", "[]");
        assertTrue(hours.isOpenAt(store, Instant.parse("2026-11-02T09:00:00Z")));
        assertTrue(hours.isOpenAt(store, Instant.parse("2026-11-02T19:59:59Z")));
        assertFalse(hours.isOpenAt(store, Instant.parse("2026-11-02T20:00:00Z")));
        assertFalse(hours.isOpenAt(store, Instant.parse("2026-11-03T10:00:00Z")));
    }

    @Test void datedClosureOverridesWeeklyScheduleEvenWhenWeeklyScheduleIsDisabled() {
        Store store = store();
        store.setOperatingHours(false, "{}", "[{\"date\":\"2026-11-02\",\"closed\":true,\"opensAt\":null,\"closesAt\":null,\"note\":\"Imprevisto\"}]");
        assertFalse(hours.isOpenAt(store, Instant.parse("2026-11-02T10:00:00Z")));
        assertTrue(hours.isOpenAt(store, Instant.parse("2026-11-03T10:00:00Z")));
    }

    @Test void specialDateHoursReplaceWeeklyHoursAndCloseEarlier() {
        Store store = store();
        store.setOperatingHours(true, "{\"MONDAY\":{\"opensAt\":\"09:00\",\"closesAt\":\"20:00\"}}",
                "[{\"date\":\"2026-11-02\",\"closed\":false,\"opensAt\":\"08:00\",\"closesAt\":\"12:00\",\"note\":null}]");
        assertTrue(hours.isOpenAt(store, Instant.parse("2026-11-02T08:00:00Z")));
        assertFalse(hours.isOpenAt(store, Instant.parse("2026-11-02T12:00:00Z")));
    }

    @Test void overnightScheduleContinuesIntoFollowingDayUntilItsClosingTime() {
        Store store = store();
        store.setOperatingHours(true, "{\"FRIDAY\":{\"opensAt\":\"22:00\",\"closesAt\":\"02:00\"}}", "[]");
        assertTrue(hours.isOpenAt(store, Instant.parse("2026-11-07T01:59:00Z")));
        assertFalse(hours.isOpenAt(store, Instant.parse("2026-11-07T02:00:00Z")));
    }

    @Test void completeAppointmentMustFitBeforeClosingTime() {
        Store store = store();
        store.setOperatingHours(true, "{\"MONDAY\":{\"opensAt\":\"09:00\",\"closesAt\":\"12:00\"}}", "[]");
        assertDoesNotThrow(() -> hours.requireOpenForInterval(store, Instant.parse("2026-11-02T10:00:00Z"), Instant.parse("2026-11-02T11:00:00Z")));
        assertThrows(IllegalArgumentException.class, () -> hours.requireOpenForInterval(store,
                Instant.parse("2026-11-02T11:30:00Z"), Instant.parse("2026-11-02T12:30:00Z")));
    }

    @Test void completeAppointmentCanUseOvernightOpeningHours() {
        Store store = store();
        store.setOperatingHours(true, "{\"FRIDAY\":{\"opensAt\":\"22:00\",\"closesAt\":\"02:00\"}}", "[]");
        assertDoesNotThrow(() -> hours.requireOpenForInterval(store, Instant.parse("2026-11-07T00:30:00Z"), Instant.parse("2026-11-07T01:30:00Z")));
        assertThrows(IllegalArgumentException.class, () -> hours.requireOpenForInterval(store,
                Instant.parse("2026-11-07T01:30:00Z"), Instant.parse("2026-11-07T02:30:00Z")));
    }

    @Test void configurationRejectsDuplicateDatesAndEqualOpenAndClose() {
        assertThrows(IllegalArgumentException.class, () -> hours.validate(new StoreOperatingHours.Configuration(true,
                java.util.Map.of(java.time.DayOfWeek.MONDAY, new StoreOperatingHours.Interval("09:00", "09:00")), java.util.List.of())));
        assertThrows(IllegalArgumentException.class, () -> hours.validate(new StoreOperatingHours.Configuration(false,
                java.util.Map.of(), java.util.List.of(
                new StoreOperatingHours.ExceptionHours(java.time.LocalDate.parse("2026-11-02"), true, null, null, null),
                new StoreOperatingHours.ExceptionHours(java.time.LocalDate.parse("2026-11-02"), true, null, null, null)))));
    }

    @Test void multipleShiftsRejectLunchBreakAndAcceptClosingBoundary(){
        Store s=store();hours.save(s,new StoreOperatingHours.Configuration(true,java.util.Map.of(),java.util.List.of(),java.util.Map.of(java.time.DayOfWeek.MONDAY,java.util.List.of(new StoreOperatingHours.Interval("08:00","12:00"),new StoreOperatingHours.Interval("14:00","18:00")))));
        assertFalse(hours.isOpenAt(s,Instant.parse("2026-11-02T13:00:00Z")));
        assertTrue(hours.isOpenAt(s,Instant.parse("2026-11-02T14:00:00Z")));
        assertTrue(hours.fits(s,Instant.parse("2026-11-02T11:30:00Z"),Instant.parse("2026-11-02T12:00:00Z")));
        assertFalse(hours.fits(s,Instant.parse("2026-11-02T11:30:00Z"),Instant.parse("2026-11-02T14:30:00Z")));
        assertEquals(2,hours.read(s).weeklyShifts().get(java.time.DayOfWeek.MONDAY).size());
    }
    @Test void rejectOverlapsWithinDayAndAcrossWeekBoundary(){
        var a=new StoreOperatingHours.Interval("22:00","02:00");var b=new StoreOperatingHours.Interval("01:00","03:00");
        assertThrows(IllegalArgumentException.class,()->hours.validate(new StoreOperatingHours.Configuration(true,java.util.Map.of(),java.util.List.of(),java.util.Map.of(java.time.DayOfWeek.SUNDAY,java.util.List.of(a),java.time.DayOfWeek.MONDAY,java.util.List.of(b)))));
        assertThrows(IllegalArgumentException.class,()->hours.validate(new StoreOperatingHours.Configuration(true,java.util.Map.of(),java.util.List.of(),java.util.Map.of(java.time.DayOfWeek.MONDAY,java.util.List.of(new StoreOperatingHours.Interval("08:00","12:00"),new StoreOperatingHours.Interval("11:00","14:00"))))));
    }
    @Test void allDayAndMultipleSpecialShiftsAreSupported(){
        Store s=store();hours.save(s,new StoreOperatingHours.Configuration(true,java.util.Map.of(),java.util.List.of(new StoreOperatingHours.ExceptionHours(java.time.LocalDate.parse("2026-11-02"),false,null,null,null,java.util.List.of(new StoreOperatingHours.Interval("08:00","10:00"),new StoreOperatingHours.Interval("16:00","18:00")))),java.util.Map.of(java.time.DayOfWeek.TUESDAY,java.util.List.of(new StoreOperatingHours.Interval("00:00","24:00")))));
        assertFalse(hours.isOpenAt(s,Instant.parse("2026-11-02T15:00:00Z")));assertTrue(hours.isOpenAt(s,Instant.parse("2026-11-02T17:00:00Z")));assertTrue(hours.isOpenAt(s,Instant.parse("2026-11-03T23:59:59Z")));
    }
    @Test void exactCoverageFindsOneMinuteGapAcrossMidnight(){
        Store s=store();hours.save(s,new StoreOperatingHours.Configuration(true,java.util.Map.of(),java.util.List.of(),java.util.Map.of(java.time.DayOfWeek.MONDAY,java.util.List.of(new StoreOperatingHours.Interval("22:00","23:59")),java.time.DayOfWeek.TUESDAY,java.util.List.of(new StoreOperatingHours.Interval("00:00","02:00")))));
        assertFalse(hours.fits(s,Instant.parse("2026-11-02T23:58:30Z"),Instant.parse("2026-11-03T00:01:30Z")));
    }
    @Test void explicitClosureOverridesPreviousNightAndInvalidSecondsAreRejected(){
        Store s=store();s.setOperatingHours(true,"{\"FRIDAY\":{\"opensAt\":\"22:00\",\"closesAt\":\"02:00\"}}","[{\"date\":\"2026-11-07\",\"closed\":true}]");
        assertFalse(hours.isOpenAt(s,Instant.parse("2026-11-07T01:00:00Z")));
        assertThrows(IllegalArgumentException.class,()->hours.validate(new StoreOperatingHours.Configuration(true,java.util.Map.of(java.time.DayOfWeek.MONDAY,new StoreOperatingHours.Interval("08:00:30","12:00")),java.util.List.of())));
    }
}
