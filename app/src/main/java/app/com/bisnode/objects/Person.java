package app.com.bisnode.objects;

public class Person {

    private String name;
    private String position;

    public Person(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    // TODO contacts?

}
