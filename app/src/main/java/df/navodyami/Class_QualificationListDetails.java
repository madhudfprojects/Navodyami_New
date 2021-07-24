package df.navodyami;

class Class_QualificationListDetails {
    int id;
    String QualificationId;
    String QualificationName;

    public Class_QualificationListDetails(){}

    public String getQualificationId() {
        return QualificationId;
    }

    public void setQualificationId(String qualificationId) {
        QualificationId = qualificationId;
    }

    public String getQualificationName() {
        return QualificationName;
    }

    public void setQualificationName(String qualificationName) {
        QualificationName = qualificationName;
    }

    @Override
    public String toString() {
        return QualificationName;
    }

}

/*package df.navodyami;

class Class_QualificationListDetails {
    int id;
    String QualificationId;
    String QualificationName;

    public Class_QualificationListDetails(){}

    public String getQualificationId() {
        return QualificationId;
    }

    public void setQualificationId(String qualificationId) {
        QualificationId = qualificationId;
    }

    public String getQualificationName() {
        return QualificationName;
    }

    public void setQualificationName(String qualificationName) {
        QualificationName = qualificationName;
    }
}*/
