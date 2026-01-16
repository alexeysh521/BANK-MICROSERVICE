package kafka.system.core.enums;

public enum TransactionStatusType {

    SUCCESS, // успешно
    PENDING, // ждем ответа
    FAILED, // ошибка (недостаточно средств)
    TIMEOUT, //консюмер не ответил вовремя, статус не ясен
    CANCELLED; // отмененная транзакция вручну пользователем или банком

    public static TransactionStatusType getTranStatType(String type){
        try{
            return TransactionStatusType.valueOf(type.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid transaction status status");
        }
    }

}
