package vvitsp.model.pojo;

import java.io.Serializable;

public class Exam implements Serializable {

    @SuppressWarnings("compatibility:6541447185671280037")
    private static final long serialVersionUID = 1L;
    
    private Long examId;
    private Long typeId;
    private String typeName;
    private int passMark;
    private int marksObtained;
    private int creditsObtained;
    
    public Exam() {
        super();
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

    public int getPassMark() {
        return passMark;
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
