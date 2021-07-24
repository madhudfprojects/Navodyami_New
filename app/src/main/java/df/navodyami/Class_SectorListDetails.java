package df.navodyami;

class Class_SectorListDetails {
    int id;
    String SectorId;
    String SectorName;

    public Class_SectorListDetails(){}

    public String getSectorId() {
        return SectorId;
    }

    public void setSectorId(String sectorId) {
        SectorId = sectorId;
    }

    public String getSectorName() {
        return SectorName;
    }

    public void setSectorName(String sectorName) {
        SectorName = sectorName;
    }

    public Class_SectorListDetails(String sectorId, String sectorName) {
        SectorId = sectorId;
        SectorName = sectorName;
    }

    @Override
    public String toString() {
        return SectorName;
    }

}
