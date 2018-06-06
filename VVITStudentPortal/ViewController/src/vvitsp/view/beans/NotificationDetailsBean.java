package vvitsp.view.beans;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import vvitsp.model.bc.appModule.CommonAMImpl;

import vvitsp.view.utils.ADFUtil;

public class NotificationDetailsBean {
    
    public NotificationDetailsBean() {
        super();
    }
    
    public void refreshNotificationListView(ReturnEvent returnEvent) {
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getAdminNotificationROVO1();
        vo.executeQuery();
        ADFUtil.refreshComponentInRoot("t1");
    }
    
    private CommonAMImpl getAM() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        return (CommonAMImpl) dc.getDataProvider();
    }
}
