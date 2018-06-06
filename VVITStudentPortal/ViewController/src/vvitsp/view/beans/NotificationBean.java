package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.Notification;
import vvitsp.model.util.AMUtil;

public class NotificationBean {
    
    private List<Notification> notifications = 
        new ArrayList<Notification>();
    
    public NotificationBean() {
        super();
    }    
    
    public void getAllNotifications() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getNotificationROVO1();
            vo.executeQuery();
            Row row = null;
            while (vo.hasNext()) {
                row = vo.next();
                this.addNotification(Notification.getInstance(row));
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }
        }
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
    
    private void addNotification(Notification notification) {
        if (notification != null) {
            this.getNotifications().add(notification);
        }
    }
}
