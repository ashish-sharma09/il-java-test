package co.uk.henry.promotion;

import java.time.LocalDate;
import java.time.Period;

public class ValidityPeriod {

    private final Period validFrom;
    private final Period validTo;

    public ValidityPeriod(final Period validFrom, final Period validFromTo) {
        this.validFrom = validFrom;
        this.validTo = validFromTo;
    }

    public boolean isValidFor(final LocalDate date) {
        return
                date.isAfter(toDate(validFrom.minusDays(1).minusMonths(1)))
                        &&
                        date.isBefore(toDate(validTo.plusDays(1).plusMonths(1)));
    }

    private LocalDate toDate(Period period) {
        return LocalDate.now().plusDays(period.getDays()).plusMonths(period.getMonths());
    }
}
