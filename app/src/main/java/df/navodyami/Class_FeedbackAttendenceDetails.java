package df.navodyami;

import java.util.ArrayList;

import df.navodyami.util.ListviewEvents;

class Class_FeedbackAttendenceDetails {

    String Slno;
    String Application_Slno;
    String Entreprenuer_Id;
    String First_Name;
    String Mobile_No;
    String FeedBack;
    String Attendance;
    String Status;

    public static ArrayList<Class_FeedbackAttendenceDetails> feedbackAttendenceDetails_info_arr=new ArrayList<Class_FeedbackAttendenceDetails>();

    public Class_FeedbackAttendenceDetails(String slno, String application_Slno, String entreprenuer_Id, String first_Name, String mobile_No, String feedBack, String attendance, String status) {
        Slno = slno;
        Application_Slno = application_Slno;
        Entreprenuer_Id = entreprenuer_Id;
        First_Name = first_Name;
        Mobile_No = mobile_No;
        FeedBack = feedBack;
        Attendance = attendance;
        Status = status;
    }

    public String getSlno() {
        return Slno;
    }

    public void setSlno(String slno) {
        Slno = slno;
    }

    public String getApplication_Slno() {
        return Application_Slno;
    }

    public void setApplication_Slno(String application_Slno) {
        Application_Slno = application_Slno;
    }

    public String getEntreprenuer_Id() {
        return Entreprenuer_Id;
    }

    public void setEntreprenuer_Id(String entreprenuer_Id) {
        Entreprenuer_Id = entreprenuer_Id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getFeedBack() {
        return FeedBack;
    }

    public void setFeedBack(String feedBack) {
        FeedBack = feedBack;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        Attendance = attendance;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
