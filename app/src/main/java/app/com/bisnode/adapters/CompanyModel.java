package app.com.bisnode.adapters;


public class CompanyModel {

    private int id;
    private int icon;
    private String name;
    private String location;

    public CompanyModel(int id, int icon, String name, String location) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.location = location;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyModel that = (CompanyModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
