package vvitsp.view.utils;

import java.util.Iterator;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

public class ADFUtil {
    
    public ADFUtil() {
        super();
    }

    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }

    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent)childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }
    
    public static void refreshComponentInRoot(String id) {
        UIComponent component = findComponentInRoot(id);
        AdfFacesContext.getCurrentInstance().addPartialTarget(component);
    }
    
    public static Object getVariableInScope(String scope, String var){
        
        if(var == null)
            return null;
        if(scope == null)
            scope = "ReqestScope";
        
        Map<String, Object> scopeMap = null;
        Object value = null;
        
        if(Scope.RequestScope.toString().equalsIgnoreCase(scope))
            scopeMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        else if(Scope.ViewScope.toString().equalsIgnoreCase(scope))
            scopeMap = (Map<String, Object>)FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(), "#{viewScope}", Object.class).getValue(FacesContext.getCurrentInstance().getELContext());
        else if(Scope.PageFlowScope.toString().equalsIgnoreCase(scope))
            scopeMap = AdfFacesContext.getCurrentInstance().getPageFlowScope();
        else if(Scope.SessionScope.toString().equalsIgnoreCase(scope))
            scopeMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();   
        else
            return null;
        
        if(scopeMap != null)        
            value = scopeMap.get(var);     
        
        return value;
        
    }
    
    public enum Scope {
        RequestScope, ViewScope, PageFlowScope, SessionScope;
    }
}
