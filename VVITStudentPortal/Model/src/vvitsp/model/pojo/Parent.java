package vvitsp.model.pojo;

import java.io.Serializable;


public class Parent implements Serializable {

    @SuppressWarnings("compatibility:-2947641377417423822")
    private static final long serialVersionUID = 1L;
    
    private Long parentId;
    private Student student;
    private String firstName;
    private String lastName;
    private String phone;
    private String mobile;
    private Long genderId;
    private String genderName;
    private Long parentTypeId;
    private String parentTypeName;


    public Long getParentId() {
        return parentId;
    }


    public Student getStudent() {
        return student;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getPhone() {
        return phone;
    }


    public String getMobile() {
        return mobile;
    }


    public Long getGenderId() {
        return genderId;
    }


    public Long getParentTypeId() {
        return parentTypeId;
    }


    public String getGenderName() {
        return genderName;
    }


    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public void setParentTypeId(Long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }
}
