package app.com.bisnode.utils;

import java.util.ArrayList;
import java.util.List;

import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.objects.Company;


public class ModelUtils {

    public static List<CompanyModel> convertCompanyToCompanyModel(List<Company> companies) {
        List<CompanyModel> resultList = new ArrayList<CompanyModel>();
        int i = 0;
        for (Company comp: companies) {
            CompanyModel companyModel = new CompanyModel(i, R.drawable.osvc, comp.getName(), comp.getCity());
            resultList.add(companyModel);
            i++;
        }
        return resultList;
    }

}
