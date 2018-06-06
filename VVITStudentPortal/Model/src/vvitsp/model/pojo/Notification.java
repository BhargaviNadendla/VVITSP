package vvitsp.model.pojo;

import java.io.Serializable;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

public class Notification implements Serializable {
    
    @SuppressWarnings("compatibility:3687473564995194050")
    private static final long serialVersionUID = 1L;
    
    private Long notificationId;
    private String details;

    public Notification() {
        super();
    }
    
    private Notification(Row row) {
        Number id = (Number) row.getAttribute("NotificationId");
        this.setNotificationId(id.longValue());
        this.setDetails((String) row.getAttribute("Details"));
    }
    
    public static Notification getInstance(Row row) {
        return new Notification(row);
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
