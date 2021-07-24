package df.navodyami;

class Class_AddEnquiryDetailsDetails {
    String FName;
    String MName;
    String LName;
    String MobileNo;
    String EmailId;
    String StateId;
    String DistrictId;
    String TalukaI;
    String VillageId;
    String SectorId;
    String BusinessName;
    String DeviceType;
    String UserId;
    String EnquiryId;
    String Gender;

    public Class_AddEnquiryDetailsDetails(String FName, String MName, String LName, String mobileNo, String emailId, String stateId, String districtId, String talukaI, String villageId, String sectorId, String businessName, String deviceType, String userId, String enquiryId,String gender) {
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
        MobileNo = mobileNo;
        EmailId = emailId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaI = talukaI;
        VillageId = villageId;
        SectorId = sectorId;
        BusinessName = businessName;
        DeviceType = deviceType;
        UserId = userId;
        EnquiryId = enquiryId;
        Gender = gender;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
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

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
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

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEnquiryId() {
        return EnquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        EnquiryId = enquiryId;
    }
}
