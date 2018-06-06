package vvitsp.model.constants;

public enum AttendanceType {
    
    Present(601L, "PRESENT"),
    Leave(602L, "LEAVE");

    @SuppressWarnings("compatibility:-2941488753365811561")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    
    private AttendanceType(Long id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public static String name(Long id) {
        for (AttendanceType attendance : values()) {
            if (attendance.id.longValue() == id.longValue()) {
                return attendance.name();
            }
        }
        
        return null;
    }
}
