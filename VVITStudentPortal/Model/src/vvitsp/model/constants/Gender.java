package vvitsp.model.constants;

public enum Gender {
    
    Female(201L, "FEMALE"),
    Male(202L, "MALE"),
    Others(203L, "OTHERS");
    
    @SuppressWarnings("compatibility:-5000988180217172440")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    
    private Gender(Long id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public static String name(Long id) {
        for (Gender gender : values()) {
            if (gender.id.longValue() == id.longValue()) {
                return gender.name();
            }
        }
        
        return null;
    }
    
    public static Gender getGender(Long id) {
            for (Gender gender : values()) {
            if (gender.id.longValue() == id.longValue()) {
                return gender;
            }
        }
        
        return null;
    }
}
