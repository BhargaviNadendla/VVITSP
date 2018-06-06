package vvitsp.model.pojo;

import java.io.Serializable;


public class StudentHistory implements Serializable {
    
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    private String address;
    
    public StudentHistory() {
        super();
    }
    
    public StudentHistory(Student student) {
        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.setPhone(student.getPhone());
        this.setMobile(student.getMobile());
        this.setAddress(student.getAddress());
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
