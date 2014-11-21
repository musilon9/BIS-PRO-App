package app.com.bisnode.fakedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import app.com.bisnode.objects.Company;
import app.com.bisnode.objects.NegativeIndicator;
import app.com.bisnode.objects.Person;
import app.com.bisnode.objects.Scoring;

/**
 * Created by Ondra on 21.11.2014.
 */
public class FakeSearch {
    public static Company[] array = new Company[] {
            new Company("MADETA Agro a. s.", "České Budějovice"),
            new Company("MADETA a.s. Závod Řípec", "Veselí nad Lužnicí"),
            new Company("MADETA a.s., závod Pelhřimov", "Pelhřimov"),
            new Company("MADETA a.s. závod Jindřichův Hradec", "Jindřichův Hradec"),
            new Company("MADETA a.s., závod České Budějovice", "České Budějovice"),
            new Company("MADETA a.s. závod Planá nad Lužnicí", "Sezimovo Ústí"),
            new Company("MADETA a.s., závod Prachatice", "Prachatice"),
    };

    public static List<Company> list = Arrays.asList(array);

    public static void addExample() {
        String name = "MADETA a. s.";
        String IC = "63275635";
        String DIC = "CZ63275635";
        String address = "Rudolfovská tř. 246/83";
        String city = "České Budějovice";
        String zip = "37001";
        String state = "Česká republika";
        ArrayList<String> phoneNumbers= new ArrayList<String>();
        phoneNumbers.add("389 136 318");
        phoneNumbers.add("387 736 225");
        phoneNumbers.add("387 736 111");
        ArrayList<String> emails = new ArrayList<String>();
        emails.add("madeta.cb@madeta.cz");
        emails.add("madeta@madeta.cz");
        String webAddress = "http://www.madeta.cz";
        ArrayList<String> activities = new ArrayList<String>();
        activities.add("Zpracování mléka, výroba mléčných výrobků a sýrů");
        activities.add("Zprostředkování velkoobchodu a velkoobchod v zastoupení");
        activities.add("Destilace, rektifikace a míchání lihovin");
        activities.add("Opravy a údržba motorových vozidel, kromě motocyklů");
        activities.add("Výroba, obchod a služby neuvedené v přílohách 1 až 3 živnostenského zákona");
        activities.add("Silniční nákladní doprava");
        ArrayList<Person> management = new ArrayList<Person>();
        management.add(new Person("Teplý Milan, Ing", "Předseda představenstva"));
        management.add(new Person("Buryánek Pavel, Ing.", "Místopředseda představenstva"));
        management.add(new Person("Prouzová Katarína, Mgr.", "Předseda dozorčí rady"));
        HashMap<Integer, Integer> turnover = new HashMap<Integer, Integer>();
        turnover.put(2010, 5026881);
        turnover.put(2011, 5142480);
        turnover.put(2012, 5133169);
        turnover.put(2013, 5318528);
        HashMap<Integer, Integer> employees = new HashMap<Integer, Integer>();
        employees.put(2010, 1682);
        employees.put(2011, 1605);
        employees.put(2012, 1508);
        employees.put(2013, 1740);
        Scoring scoring = Scoring.AA;
        int paymentIndex = 0;
        ArrayList<NegativeIndicator> negatives = new ArrayList<NegativeIndicator>();
        list.add(1, new Company(name, IC, DIC, address, city, zip, state, phoneNumbers,
                emails, webAddress, activities ,management, turnover, employees,
                scoring, paymentIndex, negatives));
    }
}
