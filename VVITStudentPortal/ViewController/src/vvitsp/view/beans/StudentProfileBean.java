package vvitsp.view.beans;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.Student;
import vvitsp.model.pojo.StudentHistory;
import vvitsp.model.pojo.User;
import vvitsp.model.util.AMUtil;


public class StudentProfileBean {
    
    private User user;
    private Student student;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private RichPopup successPopup;
    private RichPopup profileSuccessPopup;
    private String failureMessage;
    private boolean updationFailed = Boolean.FALSE;
    private StudentHistory studentHistory;

    public StudentProfileBean() {
        super();
    }
    
    public void fetchStudentProfile() {
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        User user = loginBean.getUser();
        this.setUser(user);
        System.out.println(user.getUserId());
        System.out.println(user.isVvitspStudent());
        System.out.println(user.getStudent().getStudentId());
        if (user.isVvitspStudent()) {
            this.setStudent(user.getStudent());
        }
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
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

    public void savePasswordAction(ActionEvent actionEvent) {
        // Add event code here...
        if (!this.getCurrentPassword().equals(this.getStudent().getPassword())) {
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
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudent().getStudentId());
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
    
    public void hidePopup() {
       this.getSuccessPopup().hide();     
    }    
    
    public void setSuccessPopup(RichPopup successPopup) {
        this.successPopup = successPopup;
    }

    public RichPopup getSuccessPopup() {
        return successPopup;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setStudentHistory(StudentHistory studentHistory) {
        this.studentHistory = studentHistory;
    }

    public StudentHistory getStudentHistory() {
        return studentHistory;
    }
    
    public void updateStudentHistory() {        
        this.setStudentHistory(new StudentHistory(this.getStudent()));
        this.getStudentHistory().setEmail(this.getUser().getEmail());
    }

    public String returnToProfilePageAction() {
        // Add event code here...
        this.setFailureMessage(null);
        this.setUpdationFailed(Boolean.FALSE);
        this.hidePopup();
        return "return";
    }
    
    public String returnFromProfileUpdatePageAction() {
        // Add event code here...
        this.setFailureMessage(null);
        this.setUpdationFailed(Boolean.FALSE);
        this.getProfileSuccessPopup().hide();     
        return "return";
    }

    public void updateProfileAction(ActionEvent actionEvent) {
        // Add event code here...
        CommonAMImpl commonAM = null;
        boolean isTxnSuccess = Boolean.FALSE;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getStudentHistoryVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudent().getStudentId());
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            Row row = vo.first();
            if (row != null) {
                row.setAttribute("Active", 0);
            }      
            
            row = vo.createRow();
            row.setAttribute("StudentId", this.getStudent().getStudentId());
            row.setAttribute("FirstName", this.getStudentHistory().getFirstName());
            row.setAttribute("LastName", this.getStudentHistory().getLastName());
            row.setAttribute("Address", this.getStudentHistory().getAddress());
            row.setAttribute("Mobile", this.getStudentHistory().getMobile());
            row.setAttribute("Phone", this.getStudentHistory().getPhone());
            row.setAttribute("Email", this.getStudentHistory().getEmail());
            row.setAttribute("Active", 1);
            vo.insertRow(row);           
            
            commonAM.getDBTransaction().commit();
            isTxnSuccess = Boolean.TRUE;
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getProfileSuccessPopup().show(hints);     
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setUpdationFailed(Boolean.TRUE);
            this.setFailureMessage("Internal Error, please contact Admin");
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
    
    public void resetBean() {
        this.setFailureMessage(null);
        this.setUpdationFailed(Boolean.FALSE);
        this.setConfirmPassword(null);
        this.setCurrentPassword(null);
        this.setNewPassword(null);
    }

    public void setProfileSuccessPopup(RichPopup profileSuccessPopup) {
        this.profileSuccessPopup = profileSuccessPopup;
    }

    public RichPopup getProfileSuccessPopup() {
        return profileSuccessPopup;
    }
}
