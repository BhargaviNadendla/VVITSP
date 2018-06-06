package vvitsp.model.constants;

public enum ParentType {
    
    Father(101L, "FATHER"),
    Mother(102L, "MOTHER"),
    Guardian(103L, "GUARDIAN"),
    Caretaker(104L, "CARETAKER");
    
    @SuppressWarnings("compatibility:-3384774153413370363")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    
    private ParentType(Long id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public static String name(Long id) {
        for (ParentType parentType : values()) {
            if (parentType.id.longValue() == id.longValue()) {
                return parentType.name();
            }
        }
        
        return null;
    }
}
