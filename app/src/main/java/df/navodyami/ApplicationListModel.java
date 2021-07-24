package df.navodyami;

/**
 * Created by Admin on 28-05-2018.
 */

public class ApplicationListModel {


    private String Application_Slno;
    private String First_Name;
    private String Mobile_No;
    private String Business_Name;
    private String Status;
    private String YearCode;
    private String ApplFees;
    private String Enquiry_Id;
    private String dataSyncStatus;
    private String tempId;

    public ApplicationListModel(String application_Slno, String first_Name, String mobile_No, String business_Name, String status, String yearCode, String applFees, String enquiry_Id,String dataSyncStatus,String tempId) {
        Application_Slno = application_Slno;
        First_Name = first_Name;
        Mobile_No = mobile_No;
        Business_Name = business_Name;
        Status = status;
        YearCode = yearCode;
        ApplFees =applFees;
        Enquiry_Id = enquiry_Id;
        this.dataSyncStatus=dataSyncStatus;
        this.tempId=tempId;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getApplication_Slno() {
        return Application_Slno;
    }

    public void setApplication_Slno(String application_Slno) {
        Application_Slno = application_Slno;
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

    public String getBusiness_Name() {
        return Business_Name;
    }

    public void setBusiness_Name(String business_Name) {
        Business_Name = business_Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getYearCode() {
        return YearCode;
    }

    public void setYearCode(String yearCode) {
        YearCode = yearCode;
    }

    public String getApplFees() {
        return ApplFees;
    }

    public void setApplFees(String applFees) {
        ApplFees = applFees;
    }

    public String getEnquiry_Id() {
        return Enquiry_Id;
    }

    public void setEnquiry_Id(String enquiry_Id) {
        Enquiry_Id = enquiry_Id;
    }

    public String getDataSyncStatus() {
        return dataSyncStatus;
    }

    public void setDataSyncStatus(String dataSyncStatus) {
        this.dataSyncStatus = dataSyncStatus;
    }
}
