package df.navodyami.FeesModule;

import java.util.ArrayList;


public class FeesListModel {

    String Slno;
    String Schedule_Id;
    String Entreprenuer_Slno;
    String Entreprenuer_Id;
    String Mobile_No;
    String Email_Id;
    String Sector_Name;
    String Allocated_Fees;
    String Collected_Fees;
    String Discount_Amount;
    String Discount_Remark;
    String Balance;
    String Stall_No;
    String Name;
    String UserName;
    String Payment_Status;
    String Submit_Count;
    String Verified_Count;
    String Discount_Date;

    String UserId;
    String IsLocation;
    String IsAdmin;
    String isInchage;
    String Event_Date;
    String Event_Name;

    public static ArrayList<FeesListModel> feesDetails_info_arr=new ArrayList<FeesListModel>();
    public FeesListModel(String slno, String schedule_Id, String entreprenuer_Slno, String entreprenuer_Id, String mobile_No, String email_Id, String sector_Name, String allocated_Fees, String collected_Fees, String discount_Amount, String discount_Remark,String discount_Date, String balance, String stall_No, String name, String userName, String payment_Status, String submit_Count, String verified_Count, String userId,String isAdmin,String isInchage,String isLocation,String event_date,String event_name) {
        Slno = slno;
        Schedule_Id = schedule_Id;
        Entreprenuer_Slno = entreprenuer_Slno;
        Entreprenuer_Id = entreprenuer_Id;
        Mobile_No = mobile_No;
        Email_Id = email_Id;
        Sector_Name = sector_Name;
        Allocated_Fees = allocated_Fees;
        Collected_Fees = collected_Fees;
        Discount_Amount = discount_Amount;
        Discount_Remark = discount_Remark;
        Discount_Date = discount_Date;
        Balance = balance;
        Stall_No = stall_No;
        Name = name;
        UserName = userName;
        Payment_Status = payment_Status;
        Submit_Count = submit_Count;
        Verified_Count = verified_Count;
        UserId = userId;
        IsAdmin= isAdmin;
        this.isInchage=isInchage;
        IsLocation=isLocation;
        Event_Date=event_date;
        Event_Name = event_name;

    }

    public String getIsInchage() {
        return isInchage;
    }

    public void setIsInchage(String isInchage) {
        this.isInchage = isInchage;
    }

    public String getSlno() {
        return Slno;
    }

    public void setSlno(String slno) {
        Slno = slno;
    }

    public String getSchedule_Id() {
        return Schedule_Id;
    }

    public void setSchedule_Id(String schedule_Id) {
        Schedule_Id = schedule_Id;
    }

    public String getEntreprenuer_Slno() {
        return Entreprenuer_Slno;
    }

    public void setEntreprenuer_Slno(String entreprenuer_Slno) {
        Entreprenuer_Slno = entreprenuer_Slno;
    }

    public String getEntreprenuer_Id() {
        return Entreprenuer_Id;
    }

    public void setEntreprenuer_Id(String entreprenuer_Id) {
        Entreprenuer_Id = entreprenuer_Id;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    public String getSector_Name() {
        return Sector_Name;
    }

    public void setSector_Name(String sector_Name) {
        Sector_Name = sector_Name;
    }

    public String getAllocated_Fees() {
        return Allocated_Fees;
    }

    public void setAllocated_Fees(String allocated_Fees) {
        Allocated_Fees = allocated_Fees;
    }

    public String getCollected_Fees() {
        return Collected_Fees;
    }

    public void setCollected_Fees(String collected_Fees) {
        Collected_Fees = collected_Fees;
    }

    public String getDiscount_Amount() {
        return Discount_Amount;
    }

    public void setDiscount_Amount(String discount_Amount) {
        Discount_Amount = discount_Amount;
    }

    public String getDiscount_Remark() {
        return Discount_Remark;
    }

    public void setDiscount_Remark(String discount_Remark) {
        Discount_Remark = discount_Remark;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getStall_No() {
        return Stall_No;
    }

    public void setStall_No(String stall_No) {
        Stall_No = stall_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPayment_Status() {
        return Payment_Status;
    }

    public void setPayment_Status(String payment_Status) {
        Payment_Status = payment_Status;
    }

    public String getSubmit_Count() {
        return Submit_Count;
    }

    public void setSubmit_Count(String submit_Count) {
        Submit_Count = submit_Count;
    }

    public String getVerified_Count() {
        return Verified_Count;
    }

    public void setVerified_Count(String verified_Count) {
        Verified_Count = verified_Count;
    }

    public String getDiscount_Date() {
        return Discount_Date;
    }

    public void setDiscount_Date(String discount_Date) {
        Discount_Date = discount_Date;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getIsLocation() {
        return IsLocation;
    }

    public void setIsLocation(String isLocation) {
        IsLocation = isLocation;
    }

    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }

    public String getEvent_Date() {
        return Event_Date;
    }

    public void setEvent_Date(String event_Date) {
        Event_Date = event_Date;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }
}
