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

public class ResolveQueryDetailsBean {
    
    private Long queryId;
    private String question;
    private String answer;
    private boolean registerationFailed = Boolean.FALSE;
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    
    public ResolveQueryDetailsBean() {
        super();
    }

    public void fetchQueryDetails() {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        CommonAMImpl am = (CommonAMImpl)dc.getDataProvider();
        ViewObject vo = am.getQueryDetailsROVO1();
        Row currentRow = vo.getCurrentRow();
        this.setQueryId(((Number)currentRow.getAttribute("QueryId")).longValue());
        this.setQuestion((String) currentRow.getAttribute("Question"));
        this.setAnswer((String)currentRow.getAttribute("Answer"));
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public Long getQueryId() {
        return queryId;
    }
    
    public String saveAction() {
        // Add event code here...
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getQueryVO1();
            Row queryRow = null;
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByQueryIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindQueryId", this.getQueryId());                
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            queryRow = vo.first();
            queryRow.setAttribute("Answer", this.getAnswer());
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
}
