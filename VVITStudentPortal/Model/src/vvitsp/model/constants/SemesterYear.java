package vvitsp.model.constants;

public enum SemesterYear {
    
    FirstYear(301L, "1st Year"),
    SecondYear(302L, "2nd Year"),
    ThirdYear(303L, "3rd Year"),
    FourthYear(304L, "4th Year");

    @SuppressWarnings("compatibility:-3190808465591898358")
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    
    private SemesterYear(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static String name(Long id) {
        for (SemesterYear semesterYear : values()) {
            if (semesterYear.id.longValue() == id.longValue()) {
                return semesterYear.name;
            }
        }
        
        return null;
    }
    
}
