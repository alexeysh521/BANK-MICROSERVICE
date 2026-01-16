package kafka.system.core.enums;

public enum TransactionPeriod {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static TransactionPeriod getTransactionPeriod(String period){
        try{
            return TransactionPeriod.valueOf(period.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid transaction period");
        }
    }
}
