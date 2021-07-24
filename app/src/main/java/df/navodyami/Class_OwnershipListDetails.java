package df.navodyami;

public class Class_OwnershipListDetails {
    int id;
    String Id;
    String Name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Class_OwnershipListDetails() {
    }

    @Override
    public String toString() {
        return Name;
    }
}
