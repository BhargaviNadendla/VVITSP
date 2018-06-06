package vvitsp.view.beans;

import oracle.jbo.domain.Timestamp;

import vvitsp.model.util.CommonUtil;


public class UserBean {
    
    private Long userType = 501L;
    private String rollNo;
    private String email;
    private String confirmPassword;
    private Timestamp dob;
    private Long gender = 201L;
    private String firstName;
    private String lastName;
    private Long parentType = 101L;
    private String phone;
    private String mobile;
    private String password;

    public UserBean() {
        super();
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Long getUserType() {
        return userType;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRollNo() {
        return rollNo;
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
    
    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getGender() {
        return gender;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
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

    public void setParentType(Long parentType) {
        this.parentType = parentType;
    }

    public Long getParentType() {
        return parentType;
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
    
    public void reset() {
        this.setUserType(501L);
        this.setRollNo(null);
        this.setEmail(null);
        this.setPassword(null);
        this.setConfirmPassword(null);
        this.setDob(null);
        this.setGender(201L);
        this.setFirstName(null);
        this.setLastName(null);
        this.setParentType(101L);
        this.setPhone(null);
        this.setMobile(null);       
    }
    
    String validateUserForRegistration() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (this.getUserType().longValue() == 501L) {
            if (CommonUtil.isBlank(this.getRollNo())) {
                sb.append("<div style='color:red;'>");
                sb.append("Roll No is mandatory <br/>");
                failed = Boolean.TRUE;
            }
        }
        else if (this.getUserType().longValue() == 502L) {
            if (this.getParentType() == null) {
                if (!failed) {
                    sb.append("<div style='color:red;'>");
                }
                
                sb.append("Relationship is mandatory <br/>");
            }
            
            if (CommonUtil.isBlank(this.getRollNo())) {
                if (!failed) {
                    sb.append("<div style='color:red;'>");
                }
                
                sb.append("Roll No is mandatory <br/>");
            }
        }
        
        if (CommonUtil.isBlank(this.getFirstName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("First Name is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(this.getLastName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Last Name is mandatory <br/>");
        }
        
        if (this.getGender() == null) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Gender is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Email is mandatory  <br/>");
        }
        
        if (CommonUtil.isBlank(this.getPassword())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Password is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(this.getConfirmPassword())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Confirm Password is mandatory <br/>");
        }
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
        
        if (!this.getPassword().equalsIgnoreCase(this.getConfirmPassword())) {
            sb.append("<div style='color:red;'>");
            sb.append("Password and Confirm Password doesnot match <br/>");
            failed = Boolean.TRUE;
        }
        
        if (!CommonUtil.isValidEmailAddress(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Enter valid Email address <br/>");
        }
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
            
        return sb.toString();
    }
    
    String validateUserForForgetPassword() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (this.getUserType().longValue() == 501L) {
            if (CommonUtil.isBlank(this.getRollNo())) {
                sb.append("<div style='color:red;'>");
                sb.append("Roll No is mandatory <br/>");
                failed = Boolean.TRUE;
            }
        }
        
        if (CommonUtil.isBlank(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Email is mandatory  <br/>");
            failed = Boolean.TRUE;
        }
        
        if (CommonUtil.isBlank(sb.toString())) return sb.toString();
        return sb.append("</div>").toString();
    }

    String validateUserForLogin() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (this.getUserType().longValue() == 501L) {
            if (CommonUtil.isBlank(this.getRollNo())) {
                sb.append("<div style='color:red;'>");
                sb.append("Roll No is mandatory <br/>");
                failed = Boolean.TRUE;
            }
        }
        else {
            if (CommonUtil.isBlank(this.getEmail())) {
                sb.append("<div style='color:red;'>");
                sb.append("Email is mandatory  <br/>");
                failed = Boolean.TRUE;
            }
        }
        
        if (CommonUtil.isBlank(this.getPassword())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Password is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(sb.toString())) return sb.toString();
        return sb.append("</div>").toString();
    }
    
}
