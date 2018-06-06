package vvitsp.model.pojo;

import java.io.Serializable;

public class Section implements Serializable {

    @SuppressWarnings("compatibility:-5094049021177007618")
    private static final long serialVersionUID = 1L;
    
    private Long sectionId;
    private String code;
    private String name;
    private int noOfStudents;
    
    public Section() {
        super();
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }
}
