package df.navodyami;

import java.util.ArrayList;

public class EventListModule {
   /* <schedule_id>1</schedule_id>
    <Event_Description>Regular vidyanagar santhe</Event_Description>
<Event_TypeSlno>1</Event_TypeSlno>
    <Event_Type>Market Channel</Event_Type>
<Event_Id>1</Event_Id>
    <Event_Name>Regular Santhe</Event_Name>
<Event_Fees>1100</Event_Fees>
<Total_Stalls>30</Total_Stalls>
<FromDate>10-11-2019</FromDate>
<ToDate>12-11-2019</ToDate>
<EventStatus>1</EventStatus>
<isEventClosed>0</isEventClosed>
<EventClosed_Date/>
<isChecklistCompleted>0</isChecklistCompleted>
<isExpenseUpdated>0</isExpenseUpdated>
<isAccountantVerified>0</isAccountantVerified>
<Verified_Date>12-11-2019</Verified_Date>
<User_Id>1</User_Id>
<username>sharad</username>
<email_id>sharad.tech@dfmail.org</email_id>
<Status>Success</Status>*/
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


    public static ArrayList<EventListModule> eventListModuleArrayList=new ArrayList<>();

    public EventListModule(){

    }
    public EventListModule(String schedule_id, String event_Description, String event_TypeSlno, String event_Type, String event_Id, String event_Name, String event_Fees, String total_Stalls, String fromDate, String toDate, String eventStatus, String isEventClosed, String isChecklistCompleted, String isExpenseUpdated, String isAccountantVerified, String verified_Date, String user_Id, String username, String email_id, String status) {
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
      //  EventClosed_Date = eventClosed_Date;
        this.isChecklistCompleted = isChecklistCompleted;
        this.isExpenseUpdated = isExpenseUpdated;
        this.isAccountantVerified = isAccountantVerified;
        Verified_Date = verified_Date;
        User_Id = user_Id;
        this.username = username;
        this.email_id = email_id;
        Status = status;
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
}
