package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import vvitsp.model.bc.appModule.CommonAMImpl;

import vvitsp.model.util.AMUtil;

import vvitsp.model.util.CommonUtil;

import vvitsp.view.utils.ADFUtil;

public class AdminSubjectBean {

    private Long branchId;
    private Long subjectId;
    private Long semesterId;
    private String code;
    private String name;
    private String description;
    private boolean lab = Boolean.FALSE;
    private List<SelectItem> semesters = new ArrayList<SelectItem>();
    private boolean registerationFailed = Boolean.FALSE;
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;

    public AdminSubjectBean() {
        super();
    }

    public void initSubjectDetails() {
        // Add event code here...
        LoginBean loginBean =
            (LoginBean)ADFContext.getCurrent().getSessionScope().get("loginBean");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session =
            (HttpSession)context.getExternalContext().getSession(true);
        this.setBranchId(loginBean.getUser().getStaff().getBranchId());
        session.setAttribute("branchId",
                             loginBean.getUser().getStaff().getBranchId());
        this.fetchSemesterDetails();
    }

    private void fetchSemesterDetails() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getSemesterVO1();
            vo.executeQuery();
            Row row = null;
            SelectItem item = null;
            while (vo.hasNext()) {
                row = vo.next();
                item = new SelectItem(((Number)row.getAttribute("SemesterId")).longValue(),
                                      (String)row.getAttribute("DisplayName"));
                this.addSemester(item);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }
        }
    }

    public void refreshSubjectVO() {
        this.reset();
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getAdminSubjectROVO1();
        vo.executeQuery();
    }

    public void refreshSubjectListView(ReturnEvent returnEvent) {
        this.refreshSubjectVO();
        ADFUtil.refreshComponentInRoot("subjectListTable");
    }

    private CommonAMImpl getAM() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        return (CommonAMImpl)dc.getDataProvider();
    }

    public void fetchSubjectDetailsForEdit() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        CommonAMImpl am = (CommonAMImpl)dc.getDataProvider();
        ViewObject vo = am.getAdminSubjectROVO1();
        Row currentRow = vo.getCurrentRow();
        this.setSubjectId(((Number) currentRow.getAttribute("SubjectId")).longValue());
        this.setSemesterId(((Number) currentRow.getAttribute("SemesterId")).longValue());
        this.setName((String) currentRow.getAttribute("Name"));
        this.setCode((String) currentRow.getAttribute("Code"));
        this.setDescription((String) currentRow.getAttribute("Description"));
        Number lab = (Number) currentRow.getAttribute("IsLab");
        this.setLab(lab != null && lab.intValue() == 1);
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSemesters(List<SelectItem> semesters) {
        this.semesters = semesters;
    }

    public List<SelectItem> getSemesters() {
        return semesters;
    }

    private void addSemester(SelectItem semester) {
        if (semester != null) {
            this.getSemesters().add(semester);
        }
    }
    
    public String createOrEditSubjectDetailsAction() {
        // Add event code here...
        this.trimFields();
        String errors = this.validateSubjectFields();
        if (CommonUtil.isNotBlank(errors)) {
            this.setRegisterationFailed(Boolean.TRUE);
            this.setErrors(errors);
            return null;
        }
        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getAdminSubjectROVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria =
                vo.getViewCriteriaManager().getViewCriteria("findDuplicateSubjectVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindCode", this.getCode());
            manager.setVariableValue("bindBranchId", this.getBranchId());
            manager.setVariableValue("bindSemesterId", this.getSemesterId());
            if (this.getSubjectId() != null) {
                manager.setVariableValue("bindSubjectId", this.getSubjectId());
            }
            
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            if (vo.first() != null) {
                this.setErrors("<div style='color:red;'>Subject with Code already exists in the semester of the Branch.</div>");
                this.setRegisterationFailed(Boolean.TRUE);
                return null;
            }
    
            vo = null;
            vo = commonAM.getSubjectVO1();
            Row subjectRow = null;
            if (this.getSubjectId() == null) {
                subjectRow = vo.createRow();
                subjectRow.setAttribute("Active", 1);
            }
            else {
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                criteria = vo.getViewCriteriaManager().getViewCriteria("findBySubjectIdVC");
                manager = vo.ensureVariableManager();
                manager.setVariableValue("bindSubjectId", this.getSubjectId());                
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                subjectRow = vo.first();
            }
            
            subjectRow.setAttribute("Code", this.getCode());
            subjectRow.setAttribute("Name", this.getName());
            subjectRow.setAttribute("Description", this.getDescription());
            subjectRow.setAttribute("IsLab", this.isLab() ? 1 : 0);
            
            if (this.getSubjectId() == null) {
                vo.insertRow(subjectRow);
            }
                
            ViewObject besvo = commonAM.getBranchSemesterSubjectVO1();
            Row besRow = null;
            if (this.getSubjectId() == null) {
                besRow = besvo.createRow();
                besRow.setAttribute("SubjectId", subjectRow.getAttribute("SubjectId"));
                besRow.setAttribute("Active", 1);
            }
            else {
                besvo.setWhereClause(null);
                besvo.setWhereClauseParams(null);
                besvo.applyViewCriteria(null);
                criteria = besvo.getViewCriteriaManager().getViewCriteria("findByBranchSemesterSubjectVC");
                manager = besvo.ensureVariableManager();
                manager.setVariableValue("bindSubjectId", this.getSubjectId());
                besvo.applyViewCriteria(criteria);
                besvo.executeQuery();
                besRow = besvo.first();
            }
            
            besRow.setAttribute("BranchId", this.getBranchId());
            besRow.setAttribute("SemesterId", this.getSemesterId());    
            if (this.getSubjectId() == null) {
                besvo.insertRow(besRow);
            }
            
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "backToListView";
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
        this.setCode(this.getCode().trim());
        this.setName(this.getName().trim());
        this.setDescription(this.getDescription().trim());
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
    
    private String validateSubjectFields() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (CommonUtil.isBlank(this.getCode())) {
            sb.append("<div style='color:red;'>");
            sb.append("Code is mandatory <br/>");
            failed = Boolean.TRUE;
        }

        if (CommonUtil.isBlank(this.getName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Name is mandatory <br/>");
        }
        
        if (null == this.getBranchId()) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Branch is mandatory <br/>");
        }
        
        if (null == this.getSemesterId()) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Semester is mandatory <br/>");
        }
                
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
        
        return sb.toString();
    }

    public void setLab(boolean lab) {
        this.lab = lab;
    }

    public boolean isLab() {
        return lab;
    }
    
    private void reset() {
        subjectId = null;
        semesterId = null;
        code = null;
        name = null;
        description = null;
        lab = Boolean.FALSE;
        registerationFailed = Boolean.FALSE;
        errors = null;
        txnSuccess = Boolean.FALSE;
    }
}
