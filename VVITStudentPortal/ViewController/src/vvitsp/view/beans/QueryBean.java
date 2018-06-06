package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.Query;
import vvitsp.model.pojo.User;
import vvitsp.model.util.AMUtil;
import vvitsp.model.util.CommonUtil;


public class QueryBean {
    
    private Long studentId;
    private List<Query> queries = new ArrayList<Query>();
    private Query query = new Query();
    private boolean insertFailed = Boolean.FALSE;
    private String failureMessage;
    
    public QueryBean() {
        super();
    }
    
    public void fetchAllQueries() {
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        User user = loginBean.getUser();
        System.out.println(user.getUserId());
        System.out.println(user.isVvitspStudent());
        System.out.println(user.getStudent().getStudentId());
        if (user.isVvitspStudent()) {
            this.setStudentId(user.getStudent().getStudentId());
        }

        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getQueryROVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudentId());
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            this.getQueries().clear();
            while (vo.hasNext()) {
                this.addQuery(new Query(vo.next()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setInsertFailed(Boolean.TRUE);
            this.setFailureMessage("Internal Error, Please contact Admin");
        }
        finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }
        }        
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public List<Query> getQueries() {
        return queries;
    }
    
    private void addQuery(Query query) {
        if (query != null) {
            this.getQueries().add(query);
        }
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setInsertFailed(boolean insertFailed) {
        this.insertFailed = insertFailed;
    }

    public boolean isInsertFailed() {
        return insertFailed;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void postQueryAction(ActionEvent actionEvent) {
        // Add event code here...
        if (CommonUtil.isBlank(this.getQuery().getQuestion())) {
            this.fetchAllQueries();
        }
        
        CommonAMImpl commonAM = null;
        boolean isTxnSuccess = Boolean.FALSE;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getQueryVO1();
            Row createRow = vo.createRow();
            createRow.setAttribute("StudentId", this.getStudentId());
            createRow.setAttribute("Question", this.getQuery().getQuestion());
            createRow.setAttribute("Active", 1);
            vo.insertRow(createRow);
            commonAM.getDBTransaction().commit();
            isTxnSuccess = Boolean.TRUE;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setInsertFailed(Boolean.TRUE);
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
        
        this.setQuery(new Query());
        this.fetchAllQueries();
    }
}
