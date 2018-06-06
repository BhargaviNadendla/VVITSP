package vvitsp.model.pojo;

import java.io.Serializable;

import java.util.List;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import vvitsp.model.constants.Gender;


public class Student implements Serializable {

    @SuppressWarnings("compatibility:-1639291345835336237")
    private static final long serialVersionUID = 1L;
    
    private Long studentId;
    private String rollNo;
    private String firstName;
    private String lastName;
    private Timestamp dob;
    private Timestamp doj;
    private String phone;
    private String mobile;
    private Gender gender;
    private Branch branch;
    private List<Attendance> attendance;
    private String address;
    private String password;
    
    public Student() {
    }
    
    public Student(Row studentRow) {
        Number studentId = (Number) studentRow.getAttribute("StudentId");
        this.setStudentId(studentId.longValue());
        this.setRollNo((String) studentRow.getAttribute("RollNo"));
        this.setFirstName((String) studentRow.getAttribute("FirstName"));
        this.setLastName((String) studentRow.getAttribute("LastName"));
        this.setDob((Timestamp) studentRow.getAttribute("Dob"));
        this.setDoj((Timestamp) studentRow.getAttribute("Doj"));
        this.setPhone((String) studentRow.getAttribute("Phone"));
        this.setMobile((String) studentRow.getAttribute("Mobile"));
        Number gender = (Number) studentRow.getAttribute("Gender");
        this.setGender(Gender.getGender(gender.longValue()));
        this.setAddress((String) studentRow.getAttribute("Address"));
        this.setPassword((String) studentRow.getAttribute("Password"));
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRollNo() {
        return rollNo;
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

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
