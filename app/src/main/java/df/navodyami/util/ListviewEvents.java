package df.navodyami.util;

import java.util.ArrayList;

/**
 * Created by User on 4/26/2018.
 */

public class ListviewEvents {

    public String schedule_id;
    public String Event_Description;
    public String Event_TypeSlno;
    public String Event_Type;
    public String Event_Id;
    public String Event_Name;
    public String Event_Fees;
    public String Total_Stalls;
    public String FromDate;
    public String ToDate;
    public String EventStatus;
    public String isEventClosed;
    public String EventClosed_Date;
    public String isChecklistCompleted;
    public String isExpenseUpdated;
    public String isAccountantVerified;
    public String Verified_Date;
    public String User_Id;
    public String username;
    public String email_id;
    public String Status;
    public String isAttendance_Taken;
    public String Attendance_Date;
    public String Participants_Count;
    public String Incharge_Id;

    //*-------------------------
    public static ArrayList<ListviewEvents> listview_info_arr=new ArrayList<ListviewEvents>();

  /*  public UserInfo(String schedule_ID, String lavel_ID, String schedule_Date, String end_Time, String start_Time, String schedule_Session, String schedule_Status,String subject_Name) {
        Schedule_ID = schedule_ID;
        Lavel_ID = lavel_ID;
        Schedule_Date = schedule_Date;
        End_Time = end_Time;
        Start_Time = start_Time;
        Schedule_Session = schedule_Session;
        Schedule_Status =schedule_Status;
        Subject_Name = subject_Name;
    }*/

    public ListviewEvents() {

    }

    public ListviewEvents(String schedule_id, String event_Description, String event_TypeSlno, String event_Type, String event_Id, String event_Name, String event_Fees, String total_Stalls, String fromDate, String toDate, String eventStatus, String isEventClosed, String eventClosed_Date, String isChecklistCompleted, String isExpenseUpdated, String isAccountantVerified, String verified_Date, String user_Id, String username, String email_id, String status) {
        this.schedule_id = schedule_id;
        Event_Description = event_Description;
        Event_TypeSlno = event_TypeSlno;
        Event_Type = event_Type;
        Event_Id = event_Id;
        Event_Name = event_Name;
        Event_Fees = event_Fees;
        Total_Stalls = total_Stalls;
        FromDate = fromDate;
        ToDate = toDate;
        EventStatus = eventStatus;
        this.isEventClosed = isEventClosed;
        EventClosed_Date = eventClosed_Date;
        this.isChecklistCompleted = isChecklistCompleted;
        this.isExpenseUpdated = isExpenseUpdated;
        this.isAccountantVerified = isAccountantVerified;
        Verified_Date = verified_Date;
        User_Id = user_Id;
        this.username = username;
        this.email_id = email_id;
        Status = status;
    }

    public ListviewEvents(String scheduleId, String sFdate, String sEdate, String sEventDiscription, String sEventFees, String sEventName, String sEventStatus, String sEventType, String sTotalStalls, String attendance_Date, String isAttendance_Taken, String participants_Count, String incharge_Id, String user_Id,String isEventClosed) {
    this.schedule_id=scheduleId;
    FromDate=sFdate;
    ToDate=sEdate;
    Event_Description=sEventDiscription;
    Event_Fees=sEventFees;
    Event_Name=sEventName;
    EventStatus=sEventStatus;
    Event_Type=sEventType;
    Total_Stalls=sTotalStalls;
        Attendance_Date=attendance_Date;
        this.isAttendance_Taken=isAttendance_Taken;
        Participants_Count=participants_Count;
        Incharge_Id=incharge_Id;
        User_Id = user_Id;
        this.isEventClosed = isEventClosed;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getEvent_Description() {
        return Event_Description;
    }

    public void setEvent_Description(String event_Description) {
        Event_Description = event_Description;
    }

    public String getEvent_TypeSlno() {
        return Event_TypeSlno;
    }

    public void setEvent_TypeSlno(String event_TypeSlno) {
        Event_TypeSlno = event_TypeSlno;
    }

    public String getEvent_Type() {
        return Event_Type;
    }

    public void setEvent_Type(String event_Type) {
        Event_Type = event_Type;
    }

    public String getEvent_Id() {
        return Event_Id;
    }

    public void setEvent_Id(String event_Id) {
        Event_Id = event_Id;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    public String getEvent_Fees() {
        return Event_Fees;
    }

    public void setEvent_Fees(String event_Fees) {
        Event_Fees = event_Fees;
    }

    public String getTotal_Stalls() {
        return Total_Stalls;
    }

    public void setTotal_Stalls(String total_Stalls) {
        Total_Stalls = total_Stalls;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getEventStatus() {
        return EventStatus;
    }

    public void setEventStatus(String eventStatus) {
        EventStatus = eventStatus;
    }

    public String getIsEventClosed() {
        return isEventClosed;
    }

    public void setIsEventClosed(String isEventClosed) {
        this.isEventClosed = isEventClosed;
    }

    public String getEventClosed_Date() {
        return EventClosed_Date;
    }

    public void setEventClosed_Date(String eventClosed_Date) {
        EventClosed_Date = eventClosed_Date;
    }

    public String getIsChecklistCompleted() {
        return isChecklistCompleted;
    }

    public void setIsChecklistCompleted(String isChecklistCompleted) {
        this.isChecklistCompleted = isChecklistCompleted;
    }

    public String getIsExpenseUpdated() {
        return isExpenseUpdated;
    }

    public void setIsExpenseUpdated(String isExpenseUpdated) {
        this.isExpenseUpdated = isExpenseUpdated;
    }

    public String getIsAccountantVerified() {
        return isAccountantVerified;
    }

    public void setIsAccountantVerified(String isAccountantVerified) {
        this.isAccountantVerified = isAccountantVerified;
    }

    public String getVerified_Date() {
        return Verified_Date;
    }

    public void setVerified_Date(String verified_Date) {
        Verified_Date = verified_Date;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIsAttendance_Taken() {
        return isAttendance_Taken;
    }

    public void setIsAttendance_Taken(String isAttendance_Taken) {
        this.isAttendance_Taken = isAttendance_Taken;
    }

    public String getAttendance_Date() {
        return Attendance_Date;
    }

    public void setAttendance_Date(String attendance_Date) {
        Attendance_Date = attendance_Date;
    }

    public String getParticipants_Count() {
        return Participants_Count;
    }

    public void setParticipants_Count(String participants_Count) {
        Participants_Count = participants_Count;
    }

    public String getIncharge_Id() {
        return Incharge_Id;
    }

    public void setIncharge_Id(String incharge_Id) {
        Incharge_Id = incharge_Id;
    }

    /*   public String strScheduleId;
    public String strDate;
    public String strStartTime;
    public String strEndTime;
    public String strFacultyName;
    public String strCohort;
    public String strClassroom;
    public String strModule;
    public String strFellowship;
 //   public boolean eventUpdate;
    public String strstatus;
    public String strAttandence;

    public ListviewEvents() {
    }
    public static ArrayList<ListviewEvents> listview_info_arr=new ArrayList<ListviewEvents>();

    public ListviewEvents(String strScheduleId,String strDate, String strStartTime, String strEndTime, String strFacultyName, String strCohort, String strClassroom, String strModule, String strFellowship, String strstatus,String strAttandence) {
        this.strScheduleId = strScheduleId;
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strFacultyName = strFacultyName;
        this.strCohort = strCohort;
        this.strClassroom = strClassroom;
        this.strModule = strModule;
        this.strFellowship = strFellowship;
        this.strstatus=strstatus;
        this.strAttandence=strAttandence;
    }

    public String getStrScheduleId() {
        return strScheduleId;
    }

    public void setStrScheduleId(String strScheduleId) {
        this.strScheduleId = strScheduleId;
    }

    public String getStrAttandence() {
        return strAttandence;
    }

    public void setStrAttandence(String strAttandence) {
        this.strAttandence = strAttandence;
    }

    public String getStrstatus() {
        return strstatus;
    }

    public void setStrstatus(String strstatus) {
        this.strstatus = strstatus;
    }

    public String getStrFellowship() {
        return strFellowship;
    }

    public void setStrFellowship(String strFellowship) {
        this.strFellowship = strFellowship;
    }

    public String getStrDate() {
        return strDate;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public String getStrFacultyName() {
        return strFacultyName;
    }

    public String getStrCohort() {
        return strCohort;
    }

    public String getStrClassroom() {
        return strClassroom;
    }

    public String getStrModule() {
        return strModule;
    }*/


}
