package vvitsp.model.pojo;

import java.io.Serializable;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import vvitsp.model.constants.Gender;


public class Staff implements Serializable {

    @SuppressWarnings("compatibility:-325860175352400342")
    private static final long serialVersionUID = 1L;
    
    private Long staffId;
    private String firstName;
    private String lastName;
    private Timestamp dob;
    private Timestamp doj;
    private String phone;
    private String mobile;
    private Gender gender;
    private String qualification;
    private Long startYear;
    private Long endYear;
    private Long branchId;
    private boolean hod;

    public Staff() {
    }
    
    public Staff(Row staffRow) {
        Number staffId = (Number) staffRow.getAttribute("StaffId");
        this.setStaffId(staffId.longValue());
        this.setFirstName((String) staffRow.getAttribute("FirstName"));
        this.setLastName((String) staffRow.getAttribute("LastName"));
        this.setDob((Timestamp) staffRow.getAttribute("Dob"));
        this.setDoj((Timestamp) staffRow.getAttribute("Doj"));
        this.setPhone((String) staffRow.getAttribute("Phone"));
        this.setMobile((String) staffRow.getAttribute("Mobile"));
        Number gender = (Number) staffRow.getAttribute("Gender");
        this.setGender(Gender.getGender(gender.longValue()));
        this.setQualification((String) staffRow.getAttribute("Qualification"));
        Number startYear = (Number) staffRow.getAttribute("StartYear");
        if (startYear != null)
            this.setStartYear(startYear.longValue());
        
        Number endYear = (Number) staffRow.getAttribute("EndYear");
        if (endYear != null) {
            this.setEndYear(endYear.longValue());
        }
        
        Number branchId = (Number) staffRow.getAttribute("BranchId");
        if (branchId != null) {
            this.setBranchId(branchId.longValue());
        }
        
        Number hod = (Number) staffRow.getAttribute("IsHod");
        this.setHod(hod != null && hod.intValue() == 1);
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDoj(Timestamp doj) {
        this.doj = doj;
    }

    public Timestamp getDoj() {
        return doj;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setStartYear(Long startYear) {
        this.startYear = startYear;
    }

    public Long getStartYear() {
        return startYear;
    }

    public void setEndYear(Long endYear) {
        this.endYear = endYear;
    }

    public Long getEndYear() {
        return endYear;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setHod(boolean hod) {
        this.hod = hod;
    }

    public boolean isHod() {
        return hod;
    }
}
