package df.navodyami;

public class Class_CatgaryListDetails {
    int id;
    String CatgaryId;
    String CatgaryName;

    public String getCatgaryId() {
        return CatgaryId;
    }

    public void setCatgaryId(String catgaryId) {
        CatgaryId = catgaryId;
    }

    public String getCatgaryName() {
        return CatgaryName;
    }

    public void setCatgaryName(String catgaryName) {
        CatgaryName = catgaryName;
    }

    public Class_CatgaryListDetails() {
    }

    @Override
    public String toString() {
        return CatgaryName;
    }
}
