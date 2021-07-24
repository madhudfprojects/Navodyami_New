package df.navodyami;

public class Class_DashboardTotalCount {
    String Event_Id;
    String Event_Name;
    String Total;
    String Status;

    String Enquiry_Count;
    String Application_Count;
    String App_Status;

    public Class_DashboardTotalCount(String enquiry_Count, String application_Count, String app_Status) {
        Enquiry_Count = enquiry_Count;
        Application_Count = application_Count;
        App_Status = app_Status;
    }

    public Class_DashboardTotalCount(String event_Id, String event_Name, String total, String status) {
        Event_Id = event_Id;
        Event_Name = event_Name;
        Total = total;
        Status = status;
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

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEnquiry_Count() {
        return Enquiry_Count;
    }

    public void setEnquiry_Count(String enquiry_Count) {
        Enquiry_Count = enquiry_Count;
    }

    public String getApplication_Count() {
        return Application_Count;
    }

    public void setApplication_Count(String application_Count) {
        Application_Count = application_Count;
    }

    public String getApp_Status() {
        return App_Status;
    }

    public void setApp_Status(String app_Status) {
        App_Status = app_Status;
    }
}
