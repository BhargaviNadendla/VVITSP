package vvitsp.view.beans;


import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.pojo.User;
import vvitsp.model.util.AMUtil;


public class AttendanceBean {
    
    private Long studentId;
    private Float percentage;
    
    public AttendanceBean() {
        super();
    }
    
    public void fetchAttendance() {
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        User user = loginBean.getUser();
        System.out.println(user.getUserId());
        System.out.println(user.isVvitspStudent());
        System.out.println(user.getStudent().getStudentId());
        if (user.isVvitspStudent()) {
            this.setStudentId(user.getStudent().getStudentId());
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
            session.setAttribute("studentId", this.getStudentId());
        }

        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getStudentAttendanceROVO1();
            vo.setNamedWhereClauseParam("bindStudentId", this.getStudentId());
            vo.executeQuery();
            
            Row row = vo.first();
            if (row == null) {
                return;
            }
            
            vo.setCurrentRow(row);
            Number total = (Number) row.getAttribute("CompNetworksNetworkProgLab");
            float totalPer = total.floatValue();
            total = (Number) row.getAttribute("ComputerNetworks");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("DatawarehousingMining");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("DesignAnalysisOfAlgorithms");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("IprPatents");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("SoftwareEngineering");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("SoftwareEngineeringLab");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("WebTechnologies");
            totalPer = totalPer + total.floatValue();
            total = (Number) row.getAttribute("WebTechnologiesLab");
            totalPer = totalPer + total.floatValue();
            totalPer = totalPer / 9;
            this.setPercentage(totalPer);
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

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Float getPercentage() {
        return percentage;
    }
}
