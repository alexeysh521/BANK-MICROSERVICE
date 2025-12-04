package kafka.system.core.enums;

public enum RolesType {
    USER,
    ADMIN,
    MANAGER,
    TEMPSUSPENSION,
    BANNED;

    public static RolesType getRoleType(String role){
        try{
            return RolesType.valueOf(role.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid role status.");
        }
    }

    public static String fromString(RolesType role){
        try{
            return role.name();
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid role status.");
        }
    }
}
