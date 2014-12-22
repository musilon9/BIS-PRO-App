package app.com.bisnode.adapters;


public class CompanyModel {

    private long api_id;
    private int icon;
    private String name;
    private String location;
    private int id;

    public CompanyModel(long api_id, int icon, String name, String location) {
        this.api_id = api_id;
        this.icon = icon;
        this.name = name;
        this.location = location;
    }

    public int getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public long getApiId() {
        return api_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyModel that = (CompanyModel) o;

        return api_id == that.api_id;

    }

    @Override
    public int hashCode() {
        return (int) (api_id ^ (api_id >>> 32));
    }

    public long getId() {
        return id;
    }
}
