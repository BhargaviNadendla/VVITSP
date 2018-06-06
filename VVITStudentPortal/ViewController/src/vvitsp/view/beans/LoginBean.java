package vvitsp.view.beans;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.User;
import vvitsp.model.util.AMUtil;
import vvitsp.model.util.CommonUtil;
import vvitsp.model.util.PasswordGenerator;

import vvitsp.view.utils.ADFUtil;


public class LoginBean {

    private UserBean userBean;
    private User user;
    private SeedData seedData;
    private boolean registerationFailed = Boolean.FALSE;
    private boolean txnSuccess = Boolean.FALSE;
    private String errors;

    public LoginBean() {
        if (user != null)
            return;
        this.setUserBean(new UserBean());
        this.setSeedData(new SeedData());
    }

    public void reset() {
        this.setErrors(null);
        this.setRegisterationFailed(Boolean.FALSE);
        this.setTxnSuccess(Boolean.FALSE);
    }

    public void resetUserData() {
        this.getUserBean().reset();
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public String createUerAction() {
        CommonAMImpl commonAM = null;
        try {
            String errors = this.getUserBean().validateUserForRegistration();
            if (CommonUtil.isNotBlank(errors)) {
                this.setRegisterationFailed(Boolean.TRUE);
                this.setErrors(errors);
                return null;
            }

            commonAM = AMUtil.getCommonAM();
            ViewObject vo = null;
            if (this.getUserBean().getUserType().longValue() == 501L) {
                vo = commonAM.getStudentUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByRollNoPasswordVC");
                VariableValueManager manager = vo.ensureVariableManager();
                manager.setVariableValue("bindRollNo",
                                         this.getUserBean().getRollNo());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() != null) {
                    this.setErrors("<div style='color:red;'>Student with Roll No already exists</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
                vo = null;
                vo = commonAM.getStudentVO1();
            }
            //            else if (this.getUserBean().getUserType().longValue() == 502L) {
            //                vo = commonAM.getParentUserROVO1();
            //                vo.setWhereClause(null);
            //                vo.setWhereClauseParams(null);
            //                vo.applyViewCriteria(null);
            //                ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByParentTypeAndRollNoVC");
            //                VariableValueManager manager = vo.ensureVariableManager();
            //                manager.setVariableValue("bindRollNo", this.getUserBean().getRollNo());
            //                manager.setVariableValue("bindParentType", this.getUserBean().getParentType());
            //                vo.applyViewCriteria(criteria);
            //                vo.executeQuery();
            //                if (vo.first() != null) {
            //                    this.setErrors("<div style='color:red;'>Parent of this type for the Roll No already exists</div>");
            //                    this.setRegisterationFailed(Boolean.TRUE);
            //                }
            //                vo = null;
            //                vo = commonAM.getParentVO1();
            //            }
            else if (this.getUserBean().getUserType().longValue() == 503L ||
                     this.getUserBean().getUserType().longValue() == 504L ||
                     this.getUserBean().getUserType().longValue() == 505L) {
                vo = commonAM.getStaffUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByEmailPasswordVC");
                VariableValueManager manager = vo.ensureVariableManager();
                manager.setVariableValue("bindEmail",
                                         this.getUserBean().getEmail());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() != null) {
                    this.setErrors("<div style='color:red;'>User with the Email already exists</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
                vo = null;
                vo = commonAM.getStaffVO1();
            }

            Row createRow = vo.createRow();
            if (this.getUserBean().getUserType().longValue() == 501L ||
                this.getUserBean().getUserType().longValue() == 502L) {
                createRow.setAttribute("RollNo",
                                       this.getUserBean().getRollNo());
            }

            //            if (this.getUserBean().getUserType().longValue() == 502L) {
            //                createRow.setAttribute("ParentType",
            //                                       new Number(this.getUserBean().getParentType().longValue()));
            //            }

            createRow.setAttribute("FirstName",
                                   this.getUserBean().getFirstName());
            createRow.setAttribute("LastName",
                                   this.getUserBean().getLastName());
            createRow.setAttribute("Gender", this.getUserBean().getGender());
            createRow.setAttribute("Active", 1);
            vo.insertRow(createRow);

            Number userId = null;
            ViewObject userVO = commonAM.getUserVO1();
            Row createUser = userVO.createRow();
            createUser.setAttribute("Email", this.getUserBean().getEmail());
            createUser.setAttribute("Password",
                                    this.getUserBean().getPassword());
            createUser.setAttribute("UserType",
                                    new Number(this.getUserBean().getUserType()));
            if (this.getUserBean().getUserType().longValue() == 501L) {
                userId = (Number)createRow.getAttribute("StudentId");
                createUser.setAttribute("StudentId", userId);
            } else if (this.getUserBean().getUserType().longValue() == 502L) {
                userId = (Number)createRow.getAttribute("ParentId");
                createUser.setAttribute("ParentId", userId);
            } else if (this.getUserBean().getUserType().longValue() == 503L ||
                       this.getUserBean().getUserType().longValue() == 504L ||
                       this.getUserBean().getUserType().longValue() == 505L) {
                userId = (Number)createRow.getAttribute("StaffId");
                createUser.setAttribute("StaffId", userId);
            }

            createUser.setAttribute("Active", 1);
            userVO.insertRow(createUser);
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return null;
        } catch (Exception e) {
            this.setErrors("<div style='color:red;'>Internal Error, Please contact Admin</div>");
            this.setRegisterationFailed(Boolean.TRUE);
        } finally {
            if (commonAM != null) {
                if (isTxnSuccess()) {
                    commonAM.getDBTransaction().rollback();
                }

                AMUtil.releaseAM(commonAM);
            }

            if (CommonUtil.isNotBlank(this.getErrors())) {
                ADFUtil.refreshComponentInRoot("ot1");
                ADFUtil.refreshComponentInRoot("s9");
            }
        }
        return null;
    }

    public void setSeedData(SeedData seedData) {
        this.seedData = seedData;
    }

    public SeedData getSeedData() {
        return seedData;
    }

    public String registerUserAction() {
        // Add event code here...
        this.resetUserData();
        this.reset();
        return "register";
    }

    public String loginPageAction() {
        this.resetUserData();
        this.reset();
        return "loginPage";
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors;
    }

    public String loginUserAction() {
        // Add event code here...
        CommonAMImpl commonAM = null;
        try {
            String errors = this.getUserBean().validateUserForLogin();
            if (CommonUtil.isNotBlank(errors)) {
                this.setRegisterationFailed(Boolean.TRUE);
                this.setErrors(errors);
                return null;
            }

            commonAM = AMUtil.getCommonAM();
            ViewObject vo = null;
            if (this.getUserBean().getUserType().longValue() == 501L) {
                vo = commonAM.getStudentUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByRollNoPasswordVC");
                VariableValueManager manager = vo.ensureVariableManager();
                manager.setVariableValue("bindRollNo",
                                         this.getUserBean().getRollNo());
                manager.setVariableValue("bindPassword",
                                         this.getUserBean().getPassword());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() == null) {
                    this.setErrors("<div style='color:red;'>Invalid Roll No or Password</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
            }
            //            else if (this.getUserBean().getUserType().longValue() == 502L) {
            //                vo = commonAM.getParentUserROVO1();
            //                vo.setWhereClause(null);
            //                vo.setWhereClauseParams(null);
            //                vo.applyViewCriteria(null);
            //                ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByEmailPasswordVC");
            //                VariableValueManager manager = vo.ensureVariableManager();
            //                manager.setVariableValue("bindEmail", this.getUserBean().getEmail());
            //                manager.setVariableValue("bindPassword", this.getUserBean().getPassword());
            //                vo.applyViewCriteria(criteria);
            //                vo.executeQuery();
            //                if (vo.first() == null) {
            //                    this.setErrors("<div style='color:red;'>Invalid Email or Password</div>");
            //                    this.setRegisterationFailed(Boolean.TRUE);
            //                }
            //            }
            else if (this.getUserBean().getUserType().longValue() == 503L ||
                     this.getUserBean().getUserType().longValue() == 504L ||
                     this.getUserBean().getUserType().longValue() == 505L) {
                vo = commonAM.getStaffUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByEmailPasswordVC");
                VariableValueManager manager =
                    criteria.ensureVariableManager();
                manager.setVariableValue("bindEmail",
                                         this.getUserBean().getEmail());
                manager.setVariableValue("bindPassword",
                                         this.getUserBean().getPassword());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() == null) {
                    this.setErrors("<div style='color:red;'>Invalid Email or Password</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
            }

            if (vo.first() != null) {
                User user = new User(vo.first());
                this.setUser(user);
                return "portalAccess";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setErrors("<div style='color:red;'>Internal Error, Please contact Admin</div>");
            this.setRegisterationFailed(Boolean.TRUE);
        } finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }

            if (CommonUtil.isNotBlank(this.getErrors())) {
                ADFUtil.refreshComponentInRoot("ot1");
                ADFUtil.refreshComponentInRoot("s9");
            }
        }
        return null;
    }

    public void setRegisterationFailed(boolean registerationFailed) {
        this.registerationFailed = registerationFailed;
    }

    public boolean isRegisterationFailed() {
        return registerationFailed;
    }

    public void setTxnSuccess(boolean txnSuccess) {
        this.txnSuccess = txnSuccess;
    }

    public boolean isTxnSuccess() {
        return txnSuccess;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String resetPasswordAction() {
        // Add event code here...
        CommonAMImpl commonAM = null;
        try {
            String errors = this.getUserBean().validateUserForForgetPassword();
            if (CommonUtil.isNotBlank(errors)) {
                this.setRegisterationFailed(Boolean.TRUE);
                this.setErrors(errors);
                return null;
            }

            commonAM = AMUtil.getCommonAM();
            ViewObject vo = null;
            if (this.getUserBean().getUserType().longValue() == 501L) {
                vo = commonAM.getStudentUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByRollNoEmailVC");
                VariableValueManager manager = vo.ensureVariableManager();
                manager.setVariableValue("bindRollNo",
                                         this.getUserBean().getRollNo());
                manager.setVariableValue("bindEmail",
                                         this.getUserBean().getEmail());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() == null) {
                    this.setErrors("<div style='color:red;'>No Student exists with this combination.</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
            } else if (this.getUserBean().getUserType().longValue() == 503L ||
                       this.getUserBean().getUserType().longValue() == 504L ||
                       this.getUserBean().getUserType().longValue() == 505L) {
                vo = commonAM.getStaffUserROVO1();
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria =
                    vo.getViewCriteriaManager().getViewCriteria("findByEmailPasswordVC");
                VariableValueManager manager =
                    criteria.ensureVariableManager();
                manager.setVariableValue("bindEmail",
                                         this.getUserBean().getEmail());
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                if (vo.first() == null) {
                    this.setErrors("<div style='color:red;'>No User found with this Email</div>");
                    this.setRegisterationFailed(Boolean.TRUE);
                }
            }

            if (vo.first() != null) {
                PasswordGenerator pg = new PasswordGenerator();
                String password = pg.newPassword();
                //            Ema
                return "null";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setErrors("<div style='color:red;'>Internal Error, Please contact Admin</div>");
            this.setRegisterationFailed(Boolean.TRUE);
        } finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }

            if (CommonUtil.isNotBlank(this.getErrors())) {
                ADFUtil.refreshComponentInRoot("ot1");
                ADFUtil.refreshComponentInRoot("s9");
            }
        }
        return null;
    }

    public String forgetPasswordAction() {
        // Add event code here...
        this.resetUserData();
        this.reset();
        return "forgetPassword";
    }
}
