package app.com.bisnode.objects;

public enum Scoring {
//    AAA("Minimální riziko", "#88a942"),
//    AA("Velmi nízké riziko", "#a6ba38"),
//    A("Nízké riziko", "#c4c92e"),
//    BBB("Střední riziko", "#e1d923"),
//    BB("Akceptovatelné riziko", "#ffe81a"),
//    B("Zvýšené riziko", "#f8b51a"),
//    CCC("Vysoké riziko", "#f1811a"),
//    CC("Velmi vysoké riziko", "#ea4e1a"),
//    C("Extrémně vysoké riziko", "#e31a1a"),
//    D("Úpadek", "#861a2b"),
//    NA("Nehodnoceno", "#c3c3c3");

    // Guidelines-friendly colors
    AAA("Minimální riziko", "#83b600"),
    AA("Velmi nízké riziko", "#83b600"),
    A("Nízké riziko", "#83b600"),
    BBB("Střední riziko", "#ffa713"),
    BB("Akceptovatelné riziko", "#ffa713"),
    B("Zvýšené riziko", "#ffa713"),
    CCC("Vysoké riziko", "#e92727"),
    CC("Velmi vysoké riziko", "#e92727"),
    C("Extrémně vysoké riziko", "#e92727"),
    D("Úpadek", "#b368d9"),
    NA("Nehodnoceno", "#c3c3c3");

    private String description;
    private String colorCode;

    Scoring(String description, String colorCode) {
        this.description = description;
        this.colorCode = colorCode;
    }

    public String getDescription() {
        return description;
    }

    public String getColorCode() {
        return colorCode;
    }
}
