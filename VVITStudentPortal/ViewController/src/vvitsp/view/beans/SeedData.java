package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.util.AMUtil;


public class SeedData {

    private List<SelectItem> userTypes = new ArrayList<SelectItem>();
    private List<SelectItem> genderTypes = new ArrayList<SelectItem>();
    private List<SelectItem> parentTypes = new ArrayList<SelectItem>();
    private List<SelectItem> yearTypes = new ArrayList<SelectItem>();
    private List<SelectItem> examTypes = new ArrayList<SelectItem>();
    private List<SelectItem> attendanceTypes = new ArrayList<SelectItem>();

    public SeedData() {
        super();
        this.fetchSeedData();
    }
    
    private void fetchSeedData() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getLookupItemsROVO1();
            vo.executeQuery();
            Row row = null;
            SelectItem seedData = null;
            String code = null;
            while (vo.hasNext()) {
                row = vo.next();
                code = (String) row.getAttribute("Code");
                if ("PARENT_TYPE".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addParentType(seedData);
                }
                else if ("GENDER".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addGenderType(seedData);
                }
                else if ("ATTENDANCE".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addAttendanceType(seedData);
                }
                else if ("USER_TYPE".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addUserType(seedData);
                }
                else if ("EXAM_TYPE".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addExamType(seedData);
                }
                else if ("SEMESTER_YEAR".equalsIgnoreCase(code)) {
                    seedData = new SelectItem(((Number) row.getAttribute("LookupItemId")).longValue(),
                                              (String) row.getAttribute("Name1"));
                    this.addYearType(seedData);
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }
        }
    }

    public void setUserTypes(List<SelectItem> userTypes) {
        this.userTypes = userTypes;
    }

    public List<SelectItem> getUserTypes() {
        return userTypes;
    }

    private void addUserType(SelectItem userType) {
        if (userType != null) {
            this.userTypes.add(userType);
        }
    }

    public void setGenderTypes(List<SelectItem> genderTypes) {
        this.genderTypes = genderTypes;
    }

    public List<SelectItem> getGenderTypes() {
        return genderTypes;
    }

    private void addGenderType(SelectItem genderType) {
        if (genderType != null) {
            this.genderTypes.add(genderType);
        }
    }
    
    public void setParentTypes(List<SelectItem> parentTypes) {
        this.parentTypes = parentTypes;
    }

    public List<SelectItem> getParentTypes() {
        return parentTypes;
    }
    
    private void addParentType(SelectItem parentType) {
        if (parentType != null) {
            this.parentTypes.add(parentType);
        }
    }

    public void setYearTypes(List<SelectItem> yearTypes) {
        this.yearTypes = yearTypes;
    }

    public List<SelectItem> getYearTypes() {
        return yearTypes;
    }
    
    private void addYearType(SelectItem yearType) {
        if (yearType != null) {
            this.yearTypes.add(yearType);
        }
    }

    public void setExamTypes(List<SelectItem> examTypes) {
        this.examTypes = examTypes;
    }

    public List<SelectItem> getExamTypes() {
        return examTypes;
    }
    
    private void addExamType(SelectItem examType) {
        if (examType != null) {
            this.examTypes.add(examType);
        }
    }

    public void setAttendanceTypes(List<SelectItem> attendanceTypes) {
        this.attendanceTypes = attendanceTypes;
    }

    public List<SelectItem> getAttendanceTypes() {
        return attendanceTypes;
    }
    
    private void addAttendanceType(SelectItem attendanceType) {
        if (attendanceType != null) {
            this.attendanceTypes.add(attendanceType);
        }
    }
}
