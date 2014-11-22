package app.com.bisnode.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Company {

    public static final int ADDRESS_FIELDS = 3;

    // BASIC INFO
    private String name;
    private String IC;
    private String DIC;

    // CONTACT INFO
    private String address, city, zip, state;
    private ArrayList<String> phoneNumbers;
    private ArrayList<String> emails;
    private String webAddress;

    // DETAILS TODO financial analysis fields
    private ArrayList<String> activities;
    private ArrayList<Person> management;
    private HashMap<Integer, Integer> turnover;
    private HashMap<Integer, Integer> employees;

    // INDICATORS
    private Scoring scoring;
    private int paymentIndex;
    private ArrayList<NegativeIndicator> negatives;

    // -------------- CONSTRUCTORS ----------------

    public Company(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Company(String name, String IC, String DIC, String address, String city, String zip, String state,
                   ArrayList<String> phoneNumbers, ArrayList<String> emails, String webAddress, ArrayList<String> activities,
                   ArrayList<Person> management, HashMap<Integer, Integer> turnover,
                   HashMap<Integer, Integer> employees, Scoring scoring, int paymentIndex,
                   ArrayList<NegativeIndicator> negatives) {
        this.name = name;
        this.IC = IC;
        this.DIC = DIC;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.webAddress = webAddress;
        this.activities = activities;
        this.management = management;
        this.turnover = turnover;
        this.employees = employees;
        this.scoring = scoring;
        this.paymentIndex = paymentIndex;
        this.negatives = negatives;
    }

    // --------------- GETTERS -------------------

    public static int getAddressFields() {
        return ADDRESS_FIELDS;
    }

    public String getName() {
        return name;
    }

    public String getIC() {
        return IC;
    }

    public String getDIC() {
        return DIC;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public ArrayList<Person> getManagement() {
        return management;
    }

    public HashMap<Integer, Integer> getTurnover() {
        return turnover;
    }

    public HashMap<Integer, Integer> getEmployees() {
        return employees;
    }

    public Scoring getScoring() {
        return scoring;
    }

    public int getPaymentIndex() {
        return paymentIndex;
    }

    public ArrayList<NegativeIndicator> getNegatives() {
        return negatives;
    }
}
