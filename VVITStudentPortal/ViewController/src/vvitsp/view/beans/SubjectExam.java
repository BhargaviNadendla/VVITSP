package vvitsp.view.beans;

public class SubjectExam {
    
    private Long studentSemesterExamId;
    private Long examId;
    private String examName;
    private int marksObtained;
    private int creditsObtained;
    
    public SubjectExam() {
        super();
    }

    public void setStudentSemesterExamId(Long studentSemesterExamId) {
        this.studentSemesterExamId = studentSemesterExamId;
    }

    public Long getStudentSemesterExamId() {
        return studentSemesterExamId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamName() {
        return examName;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setCreditsObtained(int creditsObtained) {
        this.creditsObtained = creditsObtained;
    }

    public int getCreditsObtained() {
        return creditsObtained;
    }
}
