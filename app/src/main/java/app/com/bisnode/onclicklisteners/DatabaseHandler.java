package app.com.bisnode.onclicklisteners;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.com.bisnode.adapters.CompanyModel;

public class DatabaseHandler extends SQLiteOpenHelper implements View.OnClickListener  {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "BLA";

    private static final String FAVOURITE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS FAVOURITES ("
        + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
        + "icon           INTEGER    ,"
        + "api_id         LONG         NOT NULL,"
        + "name           CHAR(50)     NOT NULL,"
        + "location        CHAR(50)  NOT NULL,"
        + "history           INTEGER     NOT NULL"
        + ");";

    private static final String KEY_ID = "ID";
    private static final String KEY_ICON = "icon";
    private static final String KEY_API_ID = "api_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_HISTORY = "history";
    private CompanyModel company = null;


    public DatabaseHandler(Context context, CompanyModel company) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.company = company;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FAVOURITE_TABLE_CREATE);
    }

    public List<CompanyModel> getHistory() {
        return getData(" = 1");
    }

    public List<CompanyModel> getFavourites() {
        return getData(" = 0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        values.put(KEY_API_ID, company.getApiId());
        values.put(KEY_NAME, company.getName());
        values.put(KEY_ICON, company.getIcon());
        values.put(KEY_LOCATION, company.getLocation());
        values.put(KEY_HISTORY, 0);
        try {
            getWritableDatabase().insert("FAVOURITES", null, values);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public List<CompanyModel> getData(String where) {
        List<CompanyModel> companyModels = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("FAVOURITES", new String[]{KEY_ID,
                        KEY_ICON, KEY_API_ID, KEY_NAME, KEY_LOCATION, KEY_HISTORY}, KEY_HISTORY + where,
                null, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                CompanyModel model = new CompanyModel(cursor.getLong(2),
                        cursor.getInt(1),
                        cursor.getString(3),
                        cursor.getString(4));
                model.setId(cursor.getInt(0));
                companyModels.add(model);
                cursor.moveToNext();
            }
        }
        if(cursor != null) {
            cursor.close();
        }
        return companyModels;
    }

    public void deleteCompany(long companyId) {
        getWritableDatabase().delete("FAVOURITES", "ID = "+companyId, null);
    }

    public void insertCompanyAsHistory(CompanyModel company) {
        ContentValues values = new ContentValues();
        values.put(KEY_API_ID, company.getApiId());
        values.put(KEY_NAME, company.getName());
        values.put(KEY_ICON, company.getIcon());
        values.put(KEY_LOCATION, company.getLocation());
        values.put(KEY_HISTORY, 1);
        getWritableDatabase().insert("FAVOURITES", null, values);
    }
}