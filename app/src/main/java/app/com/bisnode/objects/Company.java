package app.com.bisnode.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ondra on 19.11.2014.
 */
public class Company {

    public static final int ADDRESS_FIELDS = 3;

    // BASIC INFO
    private String name;
    private int IC;
    private String DIC;

    // CONTACT INFO
    private String[] address;
    private long phoneNumber;
    private String email;
    private String webAddress;

    // DETAILS TODO financial analysis fields
    private ArrayList<String> activities;
    private ArrayList<Person> management;
    private HashMap<Integer, Integer> turnover;
    private HashMap<Integer, Integer> employees;

    // INDICATORS TODO enums
    private Scoring scoring;
    private int paymentIndex;
    private ArrayList<NegativeIndicator> negatives;




}
