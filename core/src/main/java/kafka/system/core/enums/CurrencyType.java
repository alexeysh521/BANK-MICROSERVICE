package kafka.system.core.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum CurrencyType {

    USD(new BigDecimal("1.00")),
    EUR(new BigDecimal("1.07")),
    GBP(new BigDecimal("1.25")),
    JPY(new BigDecimal("0.0068")),
    RUB(new BigDecimal("0.011")),
    CHF(new BigDecimal("1.10")),
    CAD(new BigDecimal("0.74")),
    AUD(new BigDecimal("0.65")),
    CNY(new BigDecimal("0.14"));

    private final BigDecimal rateToUsd;

    CurrencyType(BigDecimal rateToUsd){
        this.rateToUsd = rateToUsd;
    }

    public BigDecimal getRateToUsd() {
        return rateToUsd;
    }

    public static CurrencyType getCurrType(String type){
        try{
            return CurrencyType.valueOf(type.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid currency status");
        }
    }

    public BigDecimal convert(BigDecimal amount, CurrencyType target) {
        BigDecimal inUsd = amount.multiply(this.rateToUsd);
        return inUsd.divide(target.rateToUsd, 2, RoundingMode.HALF_UP);
    }

}
