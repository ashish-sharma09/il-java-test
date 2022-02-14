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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidityPeriod)) return false;

        ValidityPeriod that = (ValidityPeriod) o;

        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        return validTo != null ? validTo.equals(that.validTo) : that.validTo == null;
    }

    @Override
    public int hashCode() {
        int result = validFrom != null ? validFrom.hashCode() : 0;
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
