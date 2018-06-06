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
import vvitsp.model.util.CommonUtil;

import vvitsp.view.utils.ADFUtil;


public class CreateOrEditNotificationBean {
    
    private String details;
    private Long notificationId;
    private boolean registerationFailed = Boolean.FALSE;
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    
    public CreateOrEditNotificationBean() {
        super();
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
    
    public void fetchNotificationDetails() {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        CommonAMImpl am = (CommonAMImpl) dc.getDataProvider();
        ViewObject vo = am.getAdminNotificationROVO1();
        Row currentRow = vo.getCurrentRow();
        Long notificationId = ((Number) currentRow.getAttribute("NotificationId")).longValue();        
        this.setNotificationId(notificationId);
        this.setDetails((String) currentRow.getAttribute("Details"));
    }
    
    public String saveAction() {
        // Add event code here...
        if (CommonUtil.isNotBlank(this.getDetails())) {
            this.setDetails(this.getDetails().trim());
        }
                
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getNotificationVO1();
            Row notificationRow = null;
            if (this.getNotificationId() == null) {
                notificationRow = vo.createRow();
                notificationRow.setAttribute("Active", 1);
            }
            else {
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByNotificationIdVC");
                VariableValueManager manager = vo.ensureVariableManager();
                manager.setVariableValue("bindNotificationId", this.getNotificationId());                
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                notificationRow = vo.first();
            }
            
            notificationRow.setAttribute("Details", this.getDetails());
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

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }
}
