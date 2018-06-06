package vvitsp.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.ReturnEvent;

import vvitsp.model.bc.appModule.CommonAMImpl;
import vvitsp.model.util.AMUtil;
import vvitsp.model.util.CommonUtil;

import vvitsp.view.utils.ADFUtil;


public class StudentProfileCrudBean {
    
//    private List<SelectItem> branches = new ArrayList<SelectItem>();
    private List<SelectItem> sections = new ArrayList<SelectItem>();
    private List<SelectItem> semesters = new ArrayList<SelectItem>();
    private String firstName;
    private String lastName;
    private String rollNo;
    private String email;
    private String password;
    private Long branchId;
    private Long semesterId;
    private Long sectionId;
    private Long userType = 501L;
    private Long genderType;
    private List<SelectItem> genderTypes = new ArrayList<SelectItem>();
    private String phone;
    private String mobile;
    private String address;
    private boolean registerationFailed = Boolean.FALSE;
    private String errors;
    private boolean txnSuccess = Boolean.FALSE;
    private boolean monitor = Boolean.FALSE;
    private Long studentId;
    private List<SemesterSubject> firstSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> secondSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> thirdSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> fourthSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> fifthSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> sixthSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> seventhSemSubjects = new ArrayList<SemesterSubject>();
    private List<SemesterSubject> eigthSemSubjects = new ArrayList<SemesterSubject>();
    
    public StudentProfileCrudBean() {
        super();  
    }

    public void refreshStudentList() {
        this.reset();
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStudentDetailsROVO1();
        vo.executeQuery();
    }

    public void getRequiredDataForStudentCrud() {
//        this.reset();
    }

//    private void fetchBranchDetails() {
//        CommonAMImpl commonAM = null;
//        try {
//            commonAM = AMUtil.getCommonAM();
//            ViewObject vo = commonAM.getBranchVO1();
//            vo.executeQuery();
//            Row row = null;
//            SelectItem item = null;
//            while (vo.hasNext()) {
//                row = vo.next();
//                item = new SelectItem(((Number) row.getAttribute("BranchId")).longValue(),
//                                      (String) row.getAttribute("Code"));
//                this.addBranch(item);
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (commonAM != null) {
//                AMUtil.releaseAM(commonAM);
//            }
//        }
//    }

    private void fetchSectionDetails() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getSectionVO1();
            vo.executeQuery();
            Row row = null;
            SelectItem item = null;
            while (vo.hasNext()) {
                row = vo.next();
                item = new SelectItem(((Number) row.getAttribute("SectionId")).longValue(),
                                      (String) row.getAttribute("Name"));
                this.addSection(item);
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

    private void fetchSemesterDetails() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getSemesterVO1();
            vo.executeQuery();
            Row row = null;
            SelectItem item = null;
            while (vo.hasNext()) {
                row = vo.next();
                item = new SelectItem(((Number) row.getAttribute("SemesterId")).longValue(),
                                      (String) row.getAttribute("DisplayName"));
                this.addSemester(item);
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

//    public void setBranches(List<SelectItem> branches) {
//        this.branches = branches;
//    }
//
//    public List<SelectItem> getBranches() {
//        return branches;
//    }
//    
//    private void addBranch(SelectItem branch) {
//        if (branch != null) {
//            this.getBranches().add(branch);
//        }
//    }

    public void setSections(List<SelectItem> sections) {
        this.sections = sections;
    }

    public List<SelectItem> getSections() {
        return sections;
    }
    
    private void addSection(SelectItem section) {
        if (section != null) {
            this.getSections().add(section);
        }
    }

    public void setSemesters(List<SelectItem> semesters) {
        this.semesters = semesters;
    }

    public List<SelectItem> getSemesters() {
        return semesters;
    }
    
    private void addSemester(SelectItem semester) {
        if (semester != null) {
            this.getSemesters().add(semester);
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return rollNo;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Long getUserType() {
        return userType;
    }

    public void setGenderType(Long genderType) {
        this.genderType = genderType;
    }

    public Long getGenderType() {
        return genderType;
    }

    public void setGenderTypes(List<SelectItem> genderTypes) {
        this.genderTypes = genderTypes;
    }

    public List<SelectItem> getGenderTypes() {
        return genderTypes;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String createOrEditStudentDetailsAction() {
        // Add event code here...
        this.trimFields();
        String errors = this.validateUserForRegistration();
        if (CommonUtil.isNotBlank(errors)) {
            this.setRegisterationFailed(Boolean.TRUE);
            this.setErrors(errors);
            return null;
        }
        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getStudentUserROVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria =
                vo.getViewCriteriaManager().getViewCriteria("findDuplicateStudentWithRollNoVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindRollNo", this.getRollNo());
            if (this.getStudentId() != null) {
                manager.setVariableValue("bindStudentId", this.getStudentId());
            }
            
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            if (vo.first() != null) {
                this.setErrors("<div style='color:red;'>Student with Roll No already exists</div>");
                this.setRegisterationFailed(Boolean.TRUE);
                return null;
            }
    
            vo = null;
            vo = commonAM.getStudentVO1();
            Row studentRow = null;
            if (this.getStudentId() == null) {
                studentRow = vo.createRow();
                studentRow.setAttribute("Active", 1);
            }
            else {
                vo.setWhereClause(null);
                vo.setWhereClauseParams(null);
                vo.applyViewCriteria(null);
                criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
                manager = vo.ensureVariableManager();
                manager.setVariableValue("bindStudentId", this.getStudentId());                
                vo.applyViewCriteria(criteria);
                vo.executeQuery();
                studentRow = vo.first();
            }
            
            studentRow.setAttribute("RollNo", this.getRollNo());
            studentRow.setAttribute("FirstName", this.getFirstName());
            studentRow.setAttribute("LastName", this.getLastName());
            studentRow.setAttribute("Gender", this.getGenderType());
            if (CommonUtil.isNotBlank(this.getMobile())) {
                studentRow.setAttribute("Mobile", this.getMobile()); 
            }
            
            if (CommonUtil.isNotBlank(this.getPhone())) {
                studentRow.setAttribute("Phone", this.getPhone()); 
            }
            
            if (CommonUtil.isNotBlank(this.getAddress())) {
                studentRow.setAttribute("Address", this.getAddress()); 
            }
            
            if (this.getStudentId() == null) {
                vo.insertRow(studentRow);
            }
    
            Number userId = null;
            ViewObject userVO = commonAM.getUserVO1();
            Row userRow = null;
            if (this.getStudentId() == null) {
                userRow = userVO.createRow();
                userRow.setAttribute("Password", this.getPassword());
                userId = (Number) studentRow.getAttribute("StudentId");
                userRow.setAttribute("StudentId", userId);
                userRow.setAttribute("UserType", new Number(this.getUserType()));
                userRow.setAttribute("Active", 1);
            }
            else {
                userVO.setWhereClause(null);
                userVO.setWhereClauseParams(null);
                userVO.applyViewCriteria(null);
                criteria = userVO.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
                manager = userVO.ensureVariableManager();
                manager.setVariableValue("bindStudentId", this.getStudentId());                
                userVO.applyViewCriteria(criteria);
                userVO.executeQuery();
                userRow = userVO.first();
            }
            
            userRow.setAttribute("Email", this.getEmail());                        
            if (this.getStudentId() == null) {
                userVO.insertRow(userRow);
            }
            
            ViewObject besvo = commonAM.getStudentBranchSectionVO1();
            Row besRow = null;
            if (this.getStudentId() == null) {
                besRow = besvo.createRow();
                besRow.setAttribute("StudentId", userId);
                besRow.setAttribute("Active", 1);
            }
            else {
                besvo.setWhereClause(null);
                besvo.setWhereClauseParams(null);
                besvo.applyViewCriteria(null);
                criteria = besvo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
                manager = besvo.ensureVariableManager();
                manager.setVariableValue("bindStudentId", this.getStudentId());                
                besvo.applyViewCriteria(criteria);
                besvo.executeQuery();
                besRow = besvo.first();
            }
            
            besRow.setAttribute("BranchId", this.getBranchId());
            besRow.setAttribute("SectionId", this.getSectionId());
            besRow.setAttribute("SemesterId", this.getSemesterId());            
            besRow.setAttribute("IsMonitor", this.isMonitor() ? 1 : 0);            
            if (this.getStudentId() == null) {
                besvo.insertRow(besRow);
            }
            
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "refresh";
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

    private void trimFields() {
        this.setRollNo(this.getRollNo().trim());
        this.setFirstName(this.getFirstName().trim());
        this.setLastName(this.getLastName().trim());
        if (CommonUtil.isNotBlank(this.getAddress())) {
            this.setAddress(this.getAddress().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getMobile())) {
            this.setMobile(this.getMobile().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getPhone())) {
            this.setPhone(this.getPhone().trim());
        }
        
        this.setPassword(this.getPassword().trim());
        this.setEmail(this.getEmail().trim());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private void reset() {
        firstName = null;
        lastName = null;
        rollNo = null;
        email = null;
        password = null;
//        branchId = null;
        semesterId = null;
        sectionId = null;
        userType = 501L;
        genderType  = null;
        phone = null;
        mobile = null;
        address = null;
        registerationFailed = Boolean.FALSE;
        errors = null;
        txnSuccess = Boolean.FALSE;
        monitor = Boolean.FALSE;
        studentId = null;
    }
    
    private String validateUserForRegistration() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        if (CommonUtil.isBlank(this.getRollNo())) {
            sb.append("<div style='color:red;'>");
            sb.append("Roll No is mandatory <br/>");
            failed = Boolean.TRUE;
        }

        if (CommonUtil.isBlank(this.getFirstName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("First Name is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(this.getLastName())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Last Name is mandatory <br/>");
        }
        
        if (this.getGenderType() == null) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Gender is mandatory <br/>");
        }
        
        if (CommonUtil.isBlank(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Email is mandatory  <br/>");
        }
        
        if (CommonUtil.isBlank(this.getPassword())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Password is mandatory <br/>");
        }
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
        
        if (!CommonUtil.isValidEmailAddress(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Enter valid Email address <br/>");
        }
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
            
        return sb.toString();
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

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public boolean isMonitor() {
        return monitor;
    }

    public void fetchStudentDetailsForEdit() {
        // Add event code here...
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStudentDetailsROVO1();
        Row currentRow = vo.getCurrentRow();
        this.setStudentId(((Number) currentRow.getAttribute("StudentId")).longValue());
        this.setAddress((String) currentRow.getAttribute("Address"));
        this.setBranchId(((Number) currentRow.getAttribute("BranchId")).longValue());
        this.setEmail((String) currentRow.getAttribute("Email"));
        this.setFirstName((String) currentRow.getAttribute("FirstName"));
        this.setGenderType(((Number) currentRow.getAttribute("Gender")).longValue());
        this.setLastName((String) currentRow.getAttribute("LastName"));
        this.setMobile((String) currentRow.getAttribute("Mobile"));
        this.setPhone((String) currentRow.getAttribute("Phone"));
        Number monitor = (Number) currentRow.getAttribute("IsMonitor");
        this.setMonitor(monitor != null && monitor.intValue() == 1);
        this.setRollNo((String) currentRow.getAttribute("RollNo"));
        this.setSectionId(((Number) currentRow.getAttribute("SectionId")).longValue());
        this.setSemesterId(((Number) currentRow.getAttribute("SemesterId")).longValue());
        this.setUserType(((Number) currentRow.getAttribute("UserType")).longValue());
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void refreshStudentListView(ReturnEvent returnEvent) {
        // Add event code here...
        this.refreshStudentList();
        ADFUtil.refreshComponentInRoot("resId1");
    }
    
    public void fetchStudentHistoryDetails() {
        CommonAMImpl am = this.getAM();
        ViewObject vo = am.getStudentDetailsROVO1();
        Row currentRow = vo.getCurrentRow();
        this.setStudentId(((Number) currentRow.getAttribute("StudentId")).longValue());
        vo = am.getStudentHistoryVO1();
        vo.setWhereClause(null);
        vo.setWhereClauseParams(null);
        vo.applyViewCriteria(null);
        ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
        VariableValueManager manager = vo.ensureVariableManager();
        manager.setVariableValue("bindStudentId", this.getStudentId());                
        vo.applyViewCriteria(criteria);
        vo.executeQuery();
        Row historyRow = vo.first();
        this.setAddress((String) historyRow.getAttribute("Address"));
        this.setEmail((String) historyRow.getAttribute("Email"));
        this.setFirstName((String) historyRow.getAttribute("FirstName"));
        this.setLastName((String) historyRow.getAttribute("LastName"));
        this.setMobile((String) historyRow.getAttribute("Mobile"));
        this.setPhone((String) historyRow.getAttribute("Phone"));
    }
    
    public String approveStudentRequestAction() {
        if (CommonUtil.isNotBlank(this.getFirstName())) {
            this.setFirstName(this.getFirstName().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getLastName())) {
            this.setLastName(this.getLastName().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getAddress())) {
            this.setAddress(this.getAddress().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getMobile())) {
            this.setMobile(this.getMobile().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getPhone())) {
            this.setPhone(this.getPhone().trim());
        }
        
        if (CommonUtil.isNotBlank(this.getFirstName())) {
            this.setEmail(this.getEmail().trim());
        }   
        
        String errors = this.validateStudentForUpdate();
        if (CommonUtil.isNotBlank(errors)) {
            this.setRegisterationFailed(Boolean.TRUE);
            this.setErrors(errors);
            return null;
        }
        
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = null;
            ViewCriteria criteria = null;
            VariableValueManager manager = null;
            vo = commonAM.getStudentVO1();
            Row studentRow = null;
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudentId());                
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            studentRow = vo.first();
            if (CommonUtil.isNotBlank(this.getFirstName())) {
                studentRow.setAttribute("FirstName", this.getFirstName());
            }
            
            if (CommonUtil.isNotBlank(this.getLastName())) {            
                studentRow.setAttribute("LastName", this.getLastName());
            }
            
            if (CommonUtil.isNotBlank(this.getMobile())) {
                studentRow.setAttribute("Mobile", this.getMobile()); 
            }
            
            if (CommonUtil.isNotBlank(this.getPhone())) {
                studentRow.setAttribute("Phone", this.getPhone()); 
            }
            
            if (CommonUtil.isNotBlank(this.getAddress())) {
                studentRow.setAttribute("Address", this.getAddress()); 
            }
            
            ViewObject userVO = commonAM.getUserVO1();
            Row userRow = null;
            userVO.setWhereClause(null);
            userVO.setWhereClauseParams(null);
            userVO.applyViewCriteria(null);
            criteria = userVO.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            manager = userVO.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudentId());                
            userVO.applyViewCriteria(criteria);
            userVO.executeQuery();
            userRow = userVO.first();
            if (CommonUtil.isNotBlank(this.getEmail())) {
                userRow.setAttribute("Email", this.getEmail());                        
            }
            
            vo = commonAM.getStudentHistoryVO1();
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentIdVC");
            manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudentId());                
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            Row historyRow = vo.first();
            historyRow.setAttribute("Active", 0);
            commonAM.getDBTransaction().commit();
            this.setTxnSuccess(Boolean.TRUE);
            return "refresh";
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
    
    private CommonAMImpl getAM() {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CommonAMDataControl");
        return (CommonAMImpl) dc.getDataProvider();
    }

    private String validateStudentForUpdate() {
        boolean failed = Boolean.FALSE;
        StringBuilder sb = new StringBuilder();
        
        if (CommonUtil.isNotBlank(this.getEmail()) 
                && !CommonUtil.isValidEmailAddress(this.getEmail())) {
            if (!failed) {
                sb.append("<div style='color:red;'>");
            }
            
            sb.append("Enter valid Email address <br/>");
        }
        
        if (CommonUtil.isNotBlank(sb.toString())) {
            return sb.append("</div>").toString();
        }
            
        return sb.toString();
    }

    public void initStudentProfile() {
        // Add event code here...
        LoginBean loginBean = (LoginBean) 
            ADFContext.getCurrent().getSessionScope().get("loginBean");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
        this.setBranchId(loginBean.getUser().getStaff().getBranchId());
        session.setAttribute("branchId", loginBean.getUser().getStaff().getBranchId());
        this.setGenderTypes(loginBean.getSeedData().getGenderTypes());        
//        this.fetchBranchDetails();
        this.fetchSectionDetails();
        this.fetchSemesterDetails();
    }
    
    public void fetchStudentMarks() {
        CommonAMImpl commonAM = null;
        try {
            commonAM = AMUtil.getCommonAM();
            ViewObject vo = commonAM.getStudentMarksROVO1();
            Row studentMarksRow = null;
            vo.setWhereClause(null);
            vo.setWhereClauseParams(null);
            vo.applyViewCriteria(null);
            ViewCriteria criteria = vo.getViewCriteriaManager().getViewCriteria("findByStudentBranchVC");
            VariableValueManager manager = vo.ensureVariableManager();
            manager.setVariableValue("bindStudentId", this.getStudentId()); 
            manager.setVariableValue("bindBranchId", this.getBranchId()); 
            vo.applyViewCriteria(criteria);
            vo.executeQuery();
            while (vo.hasNext()) {
                studentMarksRow = vo.next();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (commonAM != null) {
                AMUtil.releaseAM(commonAM);
            }
        }
    }

    public void setFirstSemSubjects(List<SemesterSubject> firstSemSubjects) {
        this.firstSemSubjects = firstSemSubjects;
    }

    public List<SemesterSubject> getFirstSemSubjects() {
        return firstSemSubjects;
    }

    public void setSecondSemSubjects(List<SemesterSubject> secondSemSubjects) {
        this.secondSemSubjects = secondSemSubjects;
    }

    public List<SemesterSubject> getSecondSemSubjects() {
        return secondSemSubjects;
    }

    public void setThirdSemSubjects(List<SemesterSubject> thirdSemSubjects) {
        this.thirdSemSubjects = thirdSemSubjects;
    }

    public List<SemesterSubject> getThirdSemSubjects() {
        return thirdSemSubjects;
    }

    public void setFourthSemSubjects(List<SemesterSubject> fourthSemSubjects) {
        this.fourthSemSubjects = fourthSemSubjects;
    }

    public List<SemesterSubject> getFourthSemSubjects() {
        return fourthSemSubjects;
    }

    public void setFifthSemSubjects(List<SemesterSubject> fifthSemSubjects) {
        this.fifthSemSubjects = fifthSemSubjects;
    }

    public List<SemesterSubject> getFifthSemSubjects() {
        return fifthSemSubjects;
    }

    public void setSixthSemSubjects(List<SemesterSubject> sixthSemSubjects) {
        this.sixthSemSubjects = sixthSemSubjects;
    }

    public List<SemesterSubject> getSixthSemSubjects() {
        return sixthSemSubjects;
    }

    public void setSeventhSemSubjects(List<SemesterSubject> seventhSemSubjects) {
        this.seventhSemSubjects = seventhSemSubjects;
    }

    public List<SemesterSubject> getSeventhSemSubjects() {
        return seventhSemSubjects;
    }

    public void setEigthSemSubjects(List<SemesterSubject> eigthSemSubjects) {
        this.eigthSemSubjects = eigthSemSubjects;
    }

    public List<SemesterSubject> getEigthSemSubjects() {
        return eigthSemSubjects;
    }
}
