package df.navodyami;

class Class_AddApplicationThreeDetails {
    String BusinessSource;
    String LastLoan;
    String Investment;
    String Knowledge;
    String AppliedAmt;
    String SanctionedAmt;
    String InterestRate;
    String AppliedAt;
    String EnquiryId;
    String Repaymentperiod;

    String Product_Improvement;
    String Working_Expenses;
    String Land;
    String Equipment;
    String Finance_Trading;
    String Application_Slno;

    String ApplicationFees;
    String VerifiedDate;
    String Remark;
    String Manual_Receipt_No;
    String Payment_Mode;
    String dataSyncStatus;
    String IsAccountVerified;

  /*  public Class_AddApplicationThreeDetails(String businessSource, String lastLoan, String investment, String knowledge, String appliedAmt, String sanctionedAmt, String interestRate, String appliedAt,String enquiryId,String repaymentperiod) {
        BusinessSource = businessSource;
        LastLoan = lastLoan;
        Investment = investment;
        Knowledge = knowledge;
        AppliedAmt = appliedAmt;
        SanctionedAmt = sanctionedAmt;
        InterestRate = interestRate;
        AppliedAt = appliedAt;
        EnquiryId = enquiryId;
        Repaymentperiod=repaymentperiod;
    }*/

    public Class_AddApplicationThreeDetails(String businessSource, String lastLoan, String investment, String knowledge, String appliedAmt, String sanctionedAmt, String interestRate, String appliedAt, String enquiryId, String repaymentperiod, String product_Improvement, String working_Expenses, String land, String equipment, String finance_Trading,String application_Slno,String applicationFees,String verifiedDate,String remark,String manual_Receipt_No,String payment_Mode) {
        BusinessSource = businessSource;
        LastLoan = lastLoan;
        Investment = investment;
        Knowledge = knowledge;
        AppliedAmt = appliedAmt;
        SanctionedAmt = sanctionedAmt;
        InterestRate = interestRate;
        AppliedAt = appliedAt;
        EnquiryId = enquiryId;
        Repaymentperiod = repaymentperiod;
        Product_Improvement = product_Improvement;
        Working_Expenses = working_Expenses;
        Land = land;
        Equipment = equipment;
        Finance_Trading = finance_Trading;
        Application_Slno = application_Slno;
        ApplicationFees = applicationFees;
        VerifiedDate = verifiedDate;
        Remark = remark;
        Manual_Receipt_No = manual_Receipt_No;
        Payment_Mode = payment_Mode;
    }
    public Class_AddApplicationThreeDetails(String businessSource, String lastLoan, String investment, String knowledge, String appliedAmt, String sanctionedAmt, String interestRate, String appliedAt, String enquiryId, String repaymentperiod, String product_Improvement, String working_Expenses, String land, String equipment, String finance_Trading,String application_Slno,String applicationFees,String verifiedDate,String remark,String manual_Receipt_No,String payment_Mode,String dataSyncStatus,String isAccountVerified) {
        BusinessSource = businessSource;
        LastLoan = lastLoan;
        Investment = investment;
        Knowledge = knowledge;
        AppliedAmt = appliedAmt;
        SanctionedAmt = sanctionedAmt;
        InterestRate = interestRate;
        AppliedAt = appliedAt;
        EnquiryId = enquiryId;
        Repaymentperiod = repaymentperiod;
        Product_Improvement = product_Improvement;
        Working_Expenses = working_Expenses;
        Land = land;
        Equipment = equipment;
        Finance_Trading = finance_Trading;
        Application_Slno = application_Slno;
        ApplicationFees = applicationFees;
        VerifiedDate = verifiedDate;
        Remark = remark;
        Manual_Receipt_No = manual_Receipt_No;
        Payment_Mode = payment_Mode;
        this.dataSyncStatus=dataSyncStatus;
        IsAccountVerified=isAccountVerified;
    }

    public Class_AddApplicationThreeDetails() {

    }

    public String getIsAccountVerified() {
        return IsAccountVerified;
    }

    public void setIsAccountVerified(String isAccountVerified) {
        IsAccountVerified = isAccountVerified;
    }

    public String getDataSyncStatus() {
        return dataSyncStatus;
    }

    public void setDataSyncStatus(String dataSyncStatus) {
        this.dataSyncStatus = dataSyncStatus;
    }

    public String getManual_Receipt_No() {
        return Manual_Receipt_No;
    }

    public void setManual_Receipt_No(String manual_Receipt_No) {
        Manual_Receipt_No = manual_Receipt_No;
    }

    public String getEnquiryId() {
        return EnquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        EnquiryId = enquiryId;
    }

    public String getBusinessSource() {
        return BusinessSource;
    }

    public void setBusinessSource(String businessSource) {
        BusinessSource = businessSource;
    }

    public String getLastLoan() {
        return LastLoan;
    }

    public void setLastLoan(String lastLoan) {
        LastLoan = lastLoan;
    }

    public String getInvestment() {
        return Investment;
    }

    public void setInvestment(String investment) {
        Investment = investment;
    }

    public String getKnowledge() {
        return Knowledge;
    }

    public void setKnowledge(String knowledge) {
        Knowledge = knowledge;
    }

    public String getAppliedAmt() {
        return AppliedAmt;
    }

    public void setAppliedAmt(String appliedAmt) {
        AppliedAmt = appliedAmt;
    }

    public String getSanctionedAmt() {
        return SanctionedAmt;
    }

    public void setSanctionedAmt(String sanctionedAmt) {
        SanctionedAmt = sanctionedAmt;
    }

    public String getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(String interestRate) {
        InterestRate = interestRate;
    }

    public String getAppliedAt() {
        return AppliedAt;
    }

    public void setAppliedAt(String appliedAt) {
        AppliedAt = appliedAt;
    }

    public void setRepaymentperiod(String repaymentperiod) {
        Repaymentperiod = repaymentperiod;
    }

    public String getRepaymentperiod() {
        return Repaymentperiod;
    }

    public String getProduct_Improvement() {
        return Product_Improvement;
    }

    public void setProduct_Improvement(String product_Improvement) {
        Product_Improvement = product_Improvement;
    }

    public String getWorking_Expenses() {
        return Working_Expenses;
    }

    public void setWorking_Expenses(String working_Expenses) {
        Working_Expenses = working_Expenses;
    }

    public String getLand() {
        return Land;
    }

    public void setLand(String land) {
        Land = land;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void setEquipment(String equipment) {
        Equipment = equipment;
    }

    public String getFinance_Trading() {
        return Finance_Trading;
    }

    public void setFinance_Trading(String finance_Trading) {
        Finance_Trading = finance_Trading;
    }

    public String getApplication_Slno() {
        return Application_Slno;
    }

    public void setApplication_Slno(String application_Slno) {
        Application_Slno = application_Slno;
    }

    public String getApplicationFees() {
        return ApplicationFees;
    }

    public void setApplicationFees(String applicationFees) {
        ApplicationFees = applicationFees;
    }

    public String getVerifiedDate() {
        return VerifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        VerifiedDate = verifiedDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPayment_Mode() {
        return Payment_Mode;
    }

    public void setPayment_Mode(String payment_Mode) {
        Payment_Mode = payment_Mode;
    }
}
