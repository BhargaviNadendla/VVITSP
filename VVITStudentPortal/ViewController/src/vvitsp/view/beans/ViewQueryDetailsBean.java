package vvitsp.view.beans;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import vvitsp.model.bc.appModule.CommonAMImpl;

public class ViewQueryDetailsBean {
    
    private Long queryId;
    private String question;
    private String answer;
    
    public ViewQueryDetailsBean() {
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

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public Long getQueryId() {
        return queryId;
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
}
