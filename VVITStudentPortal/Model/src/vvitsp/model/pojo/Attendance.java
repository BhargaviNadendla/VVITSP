package vvitsp.model.pojo;

import java.io.Serializable;

import java.util.Date;

import vvitsp.model.constants.AttendanceType;

public class Attendance implements Serializable {

    @SuppressWarnings("compatibility:-6814882241171587521")
    private static final long serialVersionUID = 1L;
    
    private Long attendanceId;
    private Date date;
    private AttendanceType type;
    private boolean leave;
    private boolean present;
    private String reason;
    private boolean holiday;
    
    public Attendance() {
        super();
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setLeave(boolean leave) {
        this.leave = leave;
    }

    public boolean isLeave() {
        return leave;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isPresent() {
        return present;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }

    public AttendanceType getType() {
        return type;
    }
}
