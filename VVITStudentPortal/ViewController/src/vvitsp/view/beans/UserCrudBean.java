package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.User;
import vvitsp.model.util.AMUtil;
import vvitsp.model.util.CommonUtil;

import vvitsp.view.utils.ADFUtil;


public class UserCrudBean {
    
    private List<SelectItem> branches = new ArrayList<SelectItem>();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long branchId;
    private Long userType = 503L;
    private Long genderType;
    private List<SelectItem> genderTypes = new ArrayList<SelectItem>();
    private String phone;
    private String mobile;
    private boolean registerationFailed = Boolean.FALSE;
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    private boolean hod = Boolean.FALSE;
    private Long staffId;
    private String qualification;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String failureMessage;
    private boolean updationFailed = Boolean.FALSE;
    private RichPopup successPopup;
    private User user;
    private Number loggedStaffId;

    public UserCrudBean() {
        super();
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        this.setGenderTypes(loginBean.getSeedData().getGenderTypes()); 
        this.setUser(loginBean.getUser());
        this.setLoggedStaffId(new Number(loginBean.getUser().getStaff().getStaffId()));
        this.fetchBranchDetails();
    }
    
    public void fetchUserDetailsForEdit() {
        this.reset();
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStaffUserROVO1();
        Row currentRow = vo.getCurrentRow();
        this.setStaffId(((Number) currentRow.getAttribute("StaffId")).longValue());
        this.setBranchId(((Number) currentRow.getAttribute("BranchId")).longValue());
        this.setEmail((String) currentRow.getAttribute("Email"));
        this.setFirstName((String) currentRow.getAttribute("FirstName"));
        this.setGenderType(((Number) currentRow.getAttribute("Gender")).longValue());
        this.setLastName((String) currentRow.getAttribute("LastName"));
        this.setMobile((String) currentRow.getAttribute("Mobile"));
        this.setPhone((String) currentRow.getAttribute("Phone"));
        Number hod = (Number) currentRow.getAttribute("IsHod");
        this.setHod(hod != null && hod.intValue() == 1);
        this.setUserType(((Number) currentRow.getAttribute("UserType")).longValue());
        this.setQualification((String) currentRow.getAttribute("Qualification"));
    }

    public void refreshUserList() {
        this.reset();
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStaffUserROVO1();
        vo.executeQuery();
    }
    
    private void reset() {
        firstName = null;
        lastName = null;
        email = null;
        password = null;
        branchId = null;
        userType = 503L;
        genderType  = null;
        phone = null;
        mobile = null;
        registerationFailed = Boolean.FALSE;
        errors = null;
        txnSuccess = Boolean.FALSE;
        hod = Boolean.FALSE;
        staffId = null;
        qualification = null;
        currentPassword = null;
        newPassword = null;
        confirmPassword = null;
        failureMessage = null;
        updationFailed = Boolean.FALSE;
    }
    
    private CommonAMImpl getAM() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        return (CommonAMImpl) dc.getDataProvider();
    }
    
    private void fetchBranchDetails() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getBranchVO1();
            vo.executeQuery();
            Row row = null;
            SelectItem item = null;
            while (vo.hasNext()) {
                row = vo.next();
                item = new SelectItem(((Number) row.getAttribute("BranchId")).longValue(),
                                      (String) row.getAttribute("Code"));
                this.addBranch(item);
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

    public void setBranches(List<SelectItem> branches) {
        this.branches = branches;
    }

    public List<SelectItem> getBranches() {
        return branches;
    }
    
    private void addBranch(SelectItem branch) {
        if (null != branch) {
            this.getBranches().add(branch);
        }
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
        if (this.isLoggedInUser()) {
            return this.getUser().getPassword();
        }
        
        return "password";
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Long getUserType() {
        return userType;
    }

    public void setGenderType(Long genderType) {
        this.genderType = genderType;
    }

    public Long getGenderType() {
        return genderType;
    }

    public void setGenderTypes(List<SelectItem> genderTypes) {
        this.genderTypes = genderTypes;
    }

    public List<SelectItem> getGenderTypes() {
        return genderTypes;
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

    public void setRegisterationFailed(boolean registerationFailed) {
        this.registerationFailed = registerationFailed;
    }

    public boolean isRegisterationFailed() {
        return registerationFailed;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors;
    }

    public void setTxnSuccess(boolean txnSuccess) {
        this.txnSuccess = txnSuccess;
    }

    public boolean isTxnSuccess() {
        return txnSuccess;
    }

    public void setHod(boolean hod) {
        this.hod = hod;
    }

    public boolean isHod() {
        return hod;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }
    
    public String createOrEditStaffDetailsAction() {
        // Add event code here...
        this.trimFields();
        String errors = this.validateUserForRegistration();
        if (CommonUtil.isNotBlank(errors)) {
            this.setRegisterationFailed(Boolean.TRUE);
            this.setErrors(errors);
            return null;
        }
        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getStaffUserROVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria =
                vo.getViewCriteriaManager().getViewCriteria("findDuplicateStaffByEmailVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindEmail", this.getEmail());
            if (this.getStaffId() != null) {
                manager.setVariableValue("bindStaffId", this.getStaffId());
            }
            
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            if (vo.first() != null) {
                this.setErrors("<div style='color:red;'>User with Email already exists</div>");
                this.setRegisterationFailed(Boolean.TRUE);
                return null;
            }
    
            vo = null;
            vo = commonAM.getStaffVO1();
            Row staffRow = null;
            if (this.getStaffId() == null) {
                staffRow = vo.createRow();
                staffRow.setAttribute("Active", 1);
            }
            else {
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                criteria = vo.getViewCriteriaManager().getViewCriteria("findByStaffIdVC");
                manager = vo.ensureVariableManager();
                manager.setVariableValue("bindStaffId", this.getStaffId());                
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                staffRow = vo.first();
            }
            
            staffRow.setAttribute("FirstName", this.getFirstName());
            staffRow.setAttribute("LastName", this.getLastName());
            staffRow.setAttribute("Gender", this.getGenderType());
            if (CommonUtil.isNotBlank(this.getMobile())) {
                staffRow.setAttribute("Mobile", this.getMobile()); 
            }
            
            if (CommonUtil.isNotBlank(this.getPhone())) {
                staffRow.setAttribute("Phone", this.getPhone()); 
            }
            
            if (CommonUtil.isNotBlank(this.getQualification())) {
                staffRow.setAttribute("Qualification", this.getQualification());
            }
            
            staffRow.setAttribute("IsHod", this.isHod() ? 1 : 0);
            staffRow.setAttribute("BranchId", this.getBranchId());
            
            if (this.getStaffId() == null) {
                vo.insertRow(staffRow);
            }
    
            Number userId = null;
            ViewObject userVO = commonAM.getUserVO1();
            Row userRow = null;
            if (this.getStaffId() == null) {
                userRow = userVO.createRow();
                userRow.setAttribute("Password", this.getPassword());
                userId = (Number) staffRow.getAttribute("StaffId");
                userRow.setAttribute("StaffId", userId);
                userRow.setAttribute("UserType", new Number(this.getUserType()));
                userRow.setAttribute("Active", 1);
            }
            else {
                userVO.setWhereClause(null);
                userVO.setWhereClauseParams(null);
                userVO.applyViewCriteria(null);
                criteria = userVO.getViewCriteriaManager().getViewCriteria("findByStaffIdVC");
                manager = userVO.ensureVariableManager();
                manager.setVariableValue("bindStaffId", this.getStaffId());                
                userVO.applyViewCriteria(criteria);
                userVO.executeQuery();
                userRow = userVO.first();
            }
            
            userRow.setAttribute("Email", this.getEmail());                        
            if (this.getStaffId() == null) {
                userVO.insertRow(userRow);
            }
            
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "refresh";
        } catch (Exception e) {
            e.printStackTrace();
            this.setErrors("<div style='color:red;'>Internal Error, Please contact Admin</div>");
            this.setRegisterationFailed(Boolean.TRUE);
        } finally {
            if (commonAM != null) {
                if (!isTxnSuccess()) {
                    commonAM.getDBTransaction().rollback();
                }

                AMUtil.releaseAM(commonAM);
            }

            if (CommonUtil.isNotBlank(this.getErrors())) {
                ADFUtil.refreshComponentInRoot("ot2");
                ADFUtil.refreshComponentInRoot("s9");
            }
        }
        return null;
    }

    private void trimFields() {
        this.setFirstName(this.getFirstName().trim());
        this.setLastName(this.getLastName().trim());
        if (CommonUtil.isNotBlank(this.getMobile())) {
            this.setMobile(this.getMobile().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getPhone())) {
            this.setPhone(this.getPhone().trim());
        }
        
        this.setPassword(this.getPassword().trim());
        this.setEmail(this.getEmail().trim());
        if (CommonUtil.isNotBlank(this.getQualification())) {
            this.setQualification(this.getQualification().trim());
        }
    }
    
    private String validateUserForRegistration() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (CommonUtil.isBlank(this.getFirstName())) {
            sb.append("<div style='color:red;'>");
            sb.append("First Name is mandatory <br/>");
            failed = Boolean.TRUE;
        }
        
        if (CommonUtil.isBlank(this.getLastName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Last Name is mandatory <br/>");
        }
        
        if (this.getGenderType() == null) {
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
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
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
    
    public boolean isLoggedInUser() {
        return this.getStaffId() != null 
               && this.getStaffId().longValue() == this.getUser().getStaff().getStaffId().longValue();
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setUpdationFailed(boolean updationFailed) {
        this.updationFailed = updationFailed;
    }

    public boolean isUpdationFailed() {
        return updationFailed;
    }
    
    public void fetchUserDetailsForChangePassword() {
        this.reset();
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStaffUserROVO1();
        Row currentRow = vo.getCurrentRow();
        this.getUser().setPassword((String) currentRow.getAttribute("Password"));
        this.setStaffId(((Number) currentRow.getAttribute("StaffId")).longValue());
    }
    
    public void savePasswordAction(ActionEvent actionEvent) {
        // Add event code here...
        if (!this.getCurrentPassword().equals(this.getPassword())) {
            this.setUpdationFailed(Boolean.TRUE);
            this.setFailureMessage("Current Password is not valid");
            return;
        }
        
        if (!this.getNewPassword().equals(this.getConfirmPassword())) {
            this.setUpdationFailed(Boolean.TRUE);
            this.setFailureMessage("New and Confirm password are not same");
            return;
        }
        
        CommonAMImpl commonAM = null;
        boolean isTxnSuccess = Boolean.FALSE;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getUserVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStaffIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStaffId", this.getStaffId());
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            Row row = vo.first();
            row.setAttribute("Password", this.getNewPassword());
            commonAM.getDBTransaction().commit();
            isTxnSuccess = Boolean.TRUE;
            this.showPopup();
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setUpdationFailed(Boolean.TRUE);
            this.setFailureMessage("Internal Error, Please contact Admin");
        }
        finally {
            if (commonAM != null) {
                if (!isTxnSuccess) {
                    commonAM.getDBTransaction().rollback();
                }
                
                AMUtil.releaseAM(commonAM);
            }
        }
    }
    
    public void showPopup() {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getSuccessPopup().show(hints);     
    }

    public void setSuccessPopup(RichPopup successPopup) {
        this.successPopup = successPopup;
    }

    public RichPopup getSuccessPopup() {
        return successPopup;
    }
    
    public String returnToProfilePageAction() {
        // Add event code here...
        this.reset();
        this.hidePopup();
        return "refresh";
    }
    
    public void hidePopup() {
       this.getSuccessPopup().hide();     
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setLoggedStaffId(Number loggedStaffId) {
        this.loggedStaffId = loggedStaffId;
    }

    public Number getLoggedStaffId() {
        return loggedStaffId;
    }
    
    public void refreshUserListView(ReturnEvent returnEvent) {
        // Add event code here...
        this.refreshUserList();
        ADFUtil.refreshComponentInRoot("resId1");
    }
}
