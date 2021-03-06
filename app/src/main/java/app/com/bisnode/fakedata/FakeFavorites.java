package app.com.bisnode.fakedata;

import java.util.Arrays;
import java.util.List;

import app.com.bisnode.objects.Company;

public class FakeFavorites {

    public static Company[] array = new Company[] {
            new Company("MADETA, a.s.", "České Budějovice"),
            new Company("Bisnode Česká republika, a.s.", "Praha 5"),
            new Company("Siemens, s.r.o.", "Praha 5"),
            new Company("KVELB s.r.o.", "Mohelnice"),
            new Company("Eva Chmelařová - Chmelař", "Praha 4"),
            new Company("BENKO SK s.r.o.", "Opava"),
            new Company("REBA International spol. s r.o.", "Praha 3"),
            new Company("TROMA, spol. s r.o.", "Žďár nad Sázavou"),
            new Company("TĚSNOHLÍDEK s.r.o.", "Prahatice")
    };

    public static List<Company> list = Arrays.asList(array);
}
