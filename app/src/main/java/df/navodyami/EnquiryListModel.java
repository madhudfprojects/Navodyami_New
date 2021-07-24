package df.navodyami;

/**
 * Created by Admin on 28-05-2018.
 */

public class EnquiryListModel {

    private String Fname;
    private String Enquiry_id;
    private String MobileNo;
    private String BussinessName;
    private String EmailId;
    private String status;
    private String YearCode;

    String MName;
    String LName;
    String StateId;
    String DistrictId;
    String TalukaI;
    String VillageId;
    String SectorId;
    String Gender;
    String isApplicant;
    String TempId;
    String dataSyncStatus;

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getEnquiry_id() {
        return Enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        Enquiry_id = enquiry_id;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getBussinessName() {
        return BussinessName;
    }

    public void setBussinessName(String bussinessName) {
        BussinessName = bussinessName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getYearCode() {
        return YearCode;
    }

    public void setYearCode(String yearCode) {
        YearCode = yearCode;
    }

    public String getTempId() {
        return TempId;
    }

    public void setTempId(String tempId) {
        TempId = tempId;
    }

    public String getDataSyncStatus() {
        return dataSyncStatus;
    }

    public void setDataSyncStatus(String dataSyncStatus) {
        this.dataSyncStatus = dataSyncStatus;
    }

    /* public EnquiryListModel(String student_name, String lead_id, String registration_date, String college_name, String phone_number, String status) {
        this.student_name = student_name;
        this.lead_id = lead_id;
        this.registration_date = registration_date;
        this.college_name = college_name;
        this.phone_number = phone_number;
        this.status = status;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EnquiryListModel(String fname, String enquiry_id, String mobileNo, String bussinessName, String emailId, String status, String yearCode, String tempId,String dataSyncStatus) {
        Fname = fname;
        Enquiry_id = enquiry_id;
        MobileNo = mobileNo;
        BussinessName = bussinessName;
        EmailId = emailId;
        this.status = status;
        YearCode = yearCode;
        TempId=tempId;
        this.dataSyncStatus=dataSyncStatus;
    }

    public EnquiryListModel(String fname, String enquiry_id, String mobileNo, String bussinessName, String emailId, String status, String yearCode, String MName, String LName, String stateId, String districtId, String talukaI, String villageId, String sectorId, String gender, String isApplicant) {
        Fname = fname;
        Enquiry_id = enquiry_id;
        MobileNo = mobileNo;
        BussinessName = bussinessName;
        EmailId = emailId;
        this.status = status;
        YearCode = yearCode;
        this.MName = MName;
        this.LName = LName;
        StateId = stateId;
        DistrictId = districtId;
        TalukaI = talukaI;
        VillageId = villageId;
        SectorId = sectorId;
        Gender = gender;
        this.isApplicant = isApplicant;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getStateId() {
        return StateId;
    }

    public void setStateId(String stateId) {
        StateId = stateId;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getTalukaI() {
        return TalukaI;
    }

    public void setTalukaI(String talukaI) {
        TalukaI = talukaI;
    }

    public String getVillageId() {
        return VillageId;
    }

    public void setVillageId(String villageId) {
        VillageId = villageId;
    }

    public String getSectorId() {
        return SectorId;
    }

    public void setSectorId(String sectorId) {
        SectorId = sectorId;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIsApplicant() {
        return isApplicant;
    }

    public void setIsApplicant(String isApplicant) {
        this.isApplicant = isApplicant;
    }
}
