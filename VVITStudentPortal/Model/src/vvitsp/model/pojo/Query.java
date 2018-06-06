package vvitsp.model.pojo;

import java.io.Serializable;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

public class Query implements Serializable {

    @SuppressWarnings("compatibility:9208733595296264330")
    private static final long serialVersionUID = 1L;
    
    private Long queryId;
    private String question;
    private String answer;
    
    public Query() {
        super();
    }
    
    public Query(Row row) {
        Number queryId = (Number) row.getAttribute("QueryId");
        this.setQueryId(queryId.longValue());
        this.setQuestion((String) row.getAttribute("Question"));
        this.setAnswer((String) row.getAttribute("Answer"));
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
