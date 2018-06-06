package vvitsp.view.beans;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import oracle.adf.share.ADFContext;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import vvitsp.model.bc.appModule.CommonAMImpl;

import vvitsp.view.utils.ADFUtil;

public class AdminQueriesBean {

    public AdminQueriesBean() {
        super();
    }
    
    public void initQueriesInfo() {
        // Add event code here...
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
        session.setAttribute("branchId", loginBean.getUser().getStaff().getBranchId());
    }

    public void refreshQueryListView(ReturnEvent returnEvent) {
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getQueryDetailsROVO1();
        vo.executeQuery();
        ADFUtil.refreshComponentInRoot("queryListTable");
    }

    private CommonAMImpl getAM() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        return (CommonAMImpl)dc.getDataProvider();
    }
}
