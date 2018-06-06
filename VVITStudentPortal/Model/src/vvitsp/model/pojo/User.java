package vvitsp.model.pojo;

import java.io.Serializable;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import vvitsp.model.constants.UserType;


public class User implements Serializable {

    @SuppressWarnings("compatibility:3793481580974419441")
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String email;
    private String password;
    private UserType userType;
    private Student student;
    private Staff staff;

    public User() {
    }
        
    public User(Row userRow) {        
        Number userId = (Number) userRow.getAttribute("UserId");
        this.setUserId(userId.longValue());
        this.setEmail((String) userRow.getAttribute("Email"));
        this.setPassword((String) userRow.getAttribute("Password"));
        Number userTypeId = (Number) userRow.getAttribute("UserType");
        this.setUserType(UserType.getUserType(userTypeId.longValue()));
        if (this.getUserType().isStudent()) {
            this.setStudent(new Student(userRow));
        }
        else if (this.getUserType().isAdmin() 
                 || this.getUserType().isPrincipal()) {
            this.setStaff(new Staff(userRow));
        }
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public boolean isVvitspStudent() {
        return this.getUserType().isStudent();
    }

    public boolean isPrincipal() {
        return this.getUserType().isPrincipal();
    }
    
    public boolean isAdmin() {
        return this.getUserType().isAdmin();
    }
}
