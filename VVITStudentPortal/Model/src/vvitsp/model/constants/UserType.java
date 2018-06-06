package vvitsp.model.constants;

public enum UserType {
    
    Student(501L, "STUDENT"),
    Parent(502L, "PARENT"),
    Admin(503L, "ADMIN"),
    Principal(504L, "PRINCIPAL"),
    Lecturer(505L, "LECTURER"),
    DEO(506L, "DEO");
    
    @SuppressWarnings("compatibility:-5799248363699678575")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    
    private UserType(Long id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public static String name(Long id) {
        for (UserType userType : values()) {
            if (userType.id.longValue() == id.longValue()) {
                return userType.name();
            }
        }
        
        return null;
    }
    
    public static UserType getUserType(Long id) {
        for (UserType userType : values()) {
            if (userType.id.longValue() == id.longValue()) {
                return userType;
            }
        }
        
        return null;
    }
    
    public boolean isStudent() {
        return (id == 501L);
    }
    
    public boolean isAdmin() {
        return (id == 503L);
    }
    
    public boolean isPrincipal() {
        return (id == 504L);
    }
    
    public boolean isDEO() {
        return (id == 506L);
    }
}
