package app.com.bisnode.adapters;


public class CompanyModel {

    private int id;
    private int icon;
    private String name;
    private String location;

    public CompanyModel(int it, int icon, String name, String location) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
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
}
