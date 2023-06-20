package io.blueharvest.technicalassignment.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Objects;

@UtilityClass
public final class PricingUtils {

    public static Double sumDouble(Double a, Double b) {
        Objects.requireNonNull(a, "Value must not be null");
        Objects.requireNonNull(b, "Value must not be null");
        return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).doubleValue();
    }
}
