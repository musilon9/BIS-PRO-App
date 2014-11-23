package app.com.bisnode.objects;

public enum Scoring {
    AAA("Minimální riziko", "#7ba641"),
    AA("Velmi nízké riziko", "#a0b73b"),
    A("Nízké riziko", "#c7c31"),
    BBB("Střední riziko", "#e7d927"),
    BB("Akceptovatelné riziko", "#ffe820"),
    B("Zvýšené riziko", "#ffad00"),
    CCC("Vysoké riziko", "#ff7200"),
    CC("Velmi vysoké riziko", "#ff3600"),
    C("Extrémně vysoké riziko", "#fb0000"),
    D("Úpadek", "#920001f"),
    NA("Nehodnoceno", "c0c0c0");

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
