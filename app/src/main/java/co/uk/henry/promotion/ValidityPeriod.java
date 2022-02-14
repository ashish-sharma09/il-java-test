package co.uk.henry.promotion;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;

public class ValidityPeriod {

    public static ValidityPeriod FOREVER = new ValidityPeriod(Period.ZERO, Period.ofYears(Integer.MAX_VALUE));

    private final Period validFrom;
    private final Period validTo;

    public ValidityPeriod(final Period validFrom, final Period validFromTo) {
        this.validFrom = validFrom;
        this.validTo = validFromTo;
    }

    public boolean isValidFor(final LocalDate date) {
        return
                date.isAfter(toDate(validFrom.minusDays(1)))
                        &&
                        date.isBefore(toDate(validTo.plusDays(1)));
    }

    private LocalDate toDate(Period period) {
        final LocalDate localDate = LocalDate.now().plusDays(period.getDays());
        if (period.getMonths() != 0) {
            return localDate.plusMonths(period.getMonths()).with(TemporalAdjusters.lastDayOfMonth());
        }
        return localDate;
    }
}
