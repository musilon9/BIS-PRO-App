package app.com.bisnode.objects;

/**
 * Created by Ondra on 19.11.2014.
 */
public enum Scoring {
    AAA("Minimální riziko"),
    AA("Velmi nízké riziko"),
    A("Nízké riziko"),
    BBB("Střední riziko"),
    BB("Akceptovatelné riziko"),
    B("Zvýšené riziko"),
    CCC("Vysoké riziko"),
    CC("Velmi vysoké riziko"),
    C("Extrémně vysoké riziko"),
    D("Úpadek"),
    NA("Nehodnoceno");

    private String description;

    Scoring(String description) {
        this.description = description;
    }
}
