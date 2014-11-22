package app.com.bisnode.fakedata;

import java.util.List;

import app.com.bisnode.objects.Company;

public class FakeHistory {

    public static List<Company> list = FakeFavorites.list.subList(3, FakeFavorites.list.size());
}
