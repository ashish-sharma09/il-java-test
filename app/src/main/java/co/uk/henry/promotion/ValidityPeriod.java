package co.uk.henry.promotion;

import java.time.Period;

public class ValidityPeriod {

    private final Period validFrom;
    private final Period validFromTo;

    public ValidityPeriod(final Period validFrom, final Period validFromTo) {
        this.validFrom = validFrom;
        this.validFromTo = validFromTo;
    }
}
