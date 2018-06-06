package vvitsp.model.constants;

public enum ExamType {
            
    Internal(201L, "INTERNAL"),
    External(202L, "EXTERNAL");
    
    @SuppressWarnings("compatibility:3565264239291980510")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    
    private ExamType(Long id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public static String name(Long id) {
        for (ExamType examType : values()) {
            if (examType.id.longValue() == id.longValue()) {
                return examType.name();
            }
        }
        
        return null;
    }
}
