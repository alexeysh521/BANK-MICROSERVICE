package kafka.system.core.enums;

public enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER;

    public static TransactionType getTransType(String role){
        try{
            return TransactionType.valueOf(role.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid transaction status.");
        }
    }
}
