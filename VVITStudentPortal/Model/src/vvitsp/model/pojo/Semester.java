package vvitsp.model.pojo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Semester implements Serializable {

    @SuppressWarnings("compatibility:6141311891909710415")
    private static final long serialVersionUID = 1L;
    
    private Long semesterId;
    private String code;
    private String name;
    private int year;
    private List<Subject> subjects = new ArrayList<Subject>();
    
    public Semester() {
        super();
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSemesterId() {
        return semesterId;
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

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
    
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
