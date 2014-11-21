package app.com.bisnode.fakedata;

import java.util.Collections;
import java.util.List;

import app.com.bisnode.objects.Company;

/**
 * Created by Ondra on 21.11.2014.
 */
public class FakeHistory {

    public static List<Company> list = FakeFavorites.list.subList(3, FakeFavorites.list.size());
    public static Company[] array = (Company []) list.toArray();
}
