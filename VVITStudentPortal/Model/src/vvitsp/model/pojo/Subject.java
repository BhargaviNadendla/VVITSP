package vvitsp.model.pojo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Subject implements Serializable {

    @SuppressWarnings("compatibility:4332842828991927356")
    private static final long serialVersionUID = 1L;
    
    private Long subjectId;
    private String code;
    private String name;
    private String description;
    private boolean isLab;
    private List<Exam> exams = new ArrayList<Exam>();
    
    public Subject() {
        super();
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() {
        return subjectId;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIsLab(boolean isLab) {
        this.isLab = isLab;
    }

    public boolean isIsLab() {
        return isLab;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Exam> getExams() {
        return exams;
    }
}
