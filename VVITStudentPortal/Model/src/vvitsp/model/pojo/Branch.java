package vvitsp.model.pojo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Branch implements Serializable {

    @SuppressWarnings("compatibility:8569303180654916058")
    private static final long serialVersionUID = 1L;
    
    private Long branchId;
    private String code;
    private String name;
    private String description;
    private Long hodId;
    private Staff hod;
    private List<Section> sections = new ArrayList<Section>();
    private List<Semester> semesters = new ArrayList<Semester>();
        
    public Branch() {
        super();
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
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

    public void setHodId(Long hodId) {
        this.hodId = hodId;
    }

    public Long getHodId() {
        return hodId;
    }

    public void setHod(Staff hod) {
        this.hod = hod;
    }

    public Staff getHod() {
        return hod;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Section> getSections() {
        return sections;
    }
    
    private void addSection(Section section) {
        if (null != section) {
            this.sections.add(section);
        }
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
    
    private void addSemester(Semester semester) {
        if (null != semester) {
            this.semesters.add(semester);
        }
    }
}
