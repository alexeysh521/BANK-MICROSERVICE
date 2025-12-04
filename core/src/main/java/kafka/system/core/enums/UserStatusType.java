package kafka.system.core.enums;

public enum UserStatusType {
    ACTIVE,
    INACTIVE,
    BLOCKED,
    LIMITED_ACCESS;

    public static UserStatusType getUserStatus(String status){
        try{
            return UserStatusType.valueOf(status.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid user status status");
        }
    }
}
