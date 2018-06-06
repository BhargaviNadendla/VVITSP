package vvitsp.view.beans;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.util.AMUtil;


public class DeleteStudentBean {
    
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    
    public DeleteStudentBean() {
    }

    public String deleteStudentAction() {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        CommonAMImpl am = (CommonAMImpl) dc.getDataProvider();
        ViewObject vo = am.getStudentDetailsROVO1();
        Row currentRow = vo.getCurrentRow();
        Long studentId = ((Number) currentRow.getAttribute("StudentId")).longValue();        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            vo = commonAM.getStudentVO1();
            Row studentRow = null;
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", studentId);                
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            studentRow = vo.first();
            studentRow.setAttribute("Active", 0);            
            
            ViewObject userVO = commonAM.getUserVO1();
            Row userRow = null;
            userVO.setWhereClause(null);
            userVO.setWhereClauseParams(null);
            userVO.applyViewCriteria(null);
            criteria = userVO.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            manager = userVO.ensureVariableManager();
            manager.setVariableValue("bindStudentId", studentId);                
            userVO.applyViewCriteria(criteria);
            userVO.executeQuery();
            userRow = userVO.first();
            userRow.setAttribute("Active", 0);
            
            ViewObject besvo = commonAM.getStudentBranchSectionVO1();
            Row besRow = null;
            besvo.setWhereClause(null);
            besvo.setWhereClauseParams(null);
            besvo.applyViewCriteria(null);
            criteria = besvo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            manager = besvo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", studentId);                
            besvo.applyViewCriteria(criteria);
            besvo.executeQuery();
            besRow = besvo.first();
            besRow.setAttribute("Active", 0);
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "backToStudentListView";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (commonAM != null) {
                if (!isTxnSuccess()) {
                    commonAM.getDBTransaction().rollback();
                }

                AMUtil.releaseAM(commonAM);
            }
        }
        return null;
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
}
