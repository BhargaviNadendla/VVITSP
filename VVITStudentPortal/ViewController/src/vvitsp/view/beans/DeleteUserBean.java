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

public class DeleteUserBean {
    
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    
    public DeleteUserBean() {
        super();
    }
    
    public String deleteUserAction() {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        CommonAMImpl am = (CommonAMImpl) dc.getDataProvider();
        ViewObject vo = am.getStaffUserROVO1();
        Row currentRow = vo.getCurrentRow();
        Long staffId = ((Number) currentRow.getAttribute("StaffId")).longValue();        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            vo = commonAM.getStaffVO1();
            Row studentRow = null;
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStaffIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStaffId", staffId);                
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            studentRow = vo.first();
            studentRow.setAttribute("Active", 0);            
            
            ViewObject userVO = commonAM.getUserVO1();
            Row userRow = null;
            userVO.setWhereClause(null);
            userVO.setWhereClauseParams(null);
            userVO.applyViewCriteria(null);
            criteria = userVO.getViewCriteriaManager().getViewCriteria("findByStaffIdVC");
            manager = userVO.ensureVariableManager();
            manager.setVariableValue("bindStaffId", staffId);                
            userVO.applyViewCriteria(criteria);
            userVO.executeQuery();
            userRow = userVO.first();
            userRow.setAttribute("Active", 0);
            
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "backToUserList";
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
