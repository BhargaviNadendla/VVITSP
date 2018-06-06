package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import oracle.jbo.Row;

public class SemesterSubject {
    
    private Long relBranchSemesterSubjectId;
    private Map<Long, List<SubjectExam>> subjectExamsMap = new HashMap<Long, List<SubjectExam>>();
    private List<SubjectExam> subjectExams = new ArrayList<SubjectExam>();

    public SemesterSubject() {
        super();
    }
    
    public SemesterSubject(Row row) {
        Number id = (Number) row.getAttribute("RelBranchSemesterSubjectId");
        this.setRelBranchSemesterSubjectId(id != null ? id.longValue() : null);
        id = (Number) row.getAttribute("RelBranchSemesterSubjectI");
        if (id != null) {
            
        }
        else {
            
        }
    }
    
    public void setRelBranchSemesterSubjectId(Long relBranchSemesterSubjectId) {
        this.relBranchSemesterSubjectId = relBranchSemesterSubjectId;
    }

    public Long getRelBranchSemesterSubjectId() {
        return relBranchSemesterSubjectId;
    }

    public void setSubjectExams(List<SubjectExam> subjectExam) {
        this.subjectExams = subjectExam;
    }

    public List<SubjectExam> getSubjectExams() {
        return subjectExams;
    }

    private void setSubjectExamsMap(Map<Long, List<SubjectExam>> subjectExams) {
        this.subjectExamsMap = subjectExams;
    }

    private Map<Long, List<SubjectExam>> getSubjectExamsMap() {
        return subjectExamsMap;
    }
}
