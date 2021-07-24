package df.navodyami;

class Class_YearListDetails {
    int id;
    String yearID;
    String year;
    String Display_Year;

    public Class_YearListDetails(){}

    public Class_YearListDetails(String yearID, String year) {
        this.yearID = yearID;
        this.year = year;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDisplay_Year() {
        return Display_Year;
    }

    public void setDisplay_Year(String display_Year) {
        Display_Year = display_Year;
    }

    @Override
    public String toString() {
        return year;
    }

}
