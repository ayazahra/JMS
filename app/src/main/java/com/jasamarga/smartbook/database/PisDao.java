package com.jasamarga.smartbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Base64;
import android.util.Log;

import com.jasamarga.smartbook.object.PersonalInfoSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public class PisDao extends BaseDao<PersonalInfoSearch> implements DBSchema.PersonalInfoHeader {

    private final static String TAG = PisDao.class.getSimpleName();

    public PisDao(Context c) {
        super(c, TABLE_NAME);
    }

    public PisDao(Context c, boolean willWrite) {
        super(c, TABLE_NAME, willWrite);
    }

    public PisDao(DBHelper db) {
        super(db, TABLE_NAME);
    }

    public PisDao(DBHelper db, boolean willWrite) {
        super(db, TABLE_NAME, willWrite);
    }

    public PersonalInfoSearch getByNpp(String npp) {
        String qry = "SELECT * FROM " + getTable() + " WHERE " + COL_NPP_ID + " = " + npp;
        Cursor c = getSqliteDb().rawQuery(qry, null);
        PersonalInfoSearch personalInfoSearch = null;
        try {
            if(c != null && c.moveToFirst()) {
                personalInfoSearch = getByCursor(c);
            }
        } finally {
            c.close();
        }
        return personalInfoSearch;
    }

    public PersonalInfoSearch getById(int id) {
        String qry = "SELECT * FROM " + getTable() + " WHERE " + COL_ID + " = " + id;
        Cursor c = getSqliteDb().rawQuery(qry, null);
        PersonalInfoSearch personalInfoSearch = null;
        try {
            if(c != null && c.moveToFirst()) {
                personalInfoSearch = getByCursor(c);
            }
        } finally {
            c.close();
        }
        return personalInfoSearch;
    }

    public List<PersonalInfoSearch> getListPersonalInfoSearch(String param) {
        String nama = "";
        String limit = "";
        String query = "";
        if (param.contains("kword")) {
            String[] params = param.split("&");
            nama = params[1].substring(params[1].indexOf("=")+1);
            limit = params[0].substring(params[0].indexOf("=")+1);
            query = "SELECT * FROM " + getTable() + " WHERE NAMA LIKE '%" + nama + "%' LIMIT "+ limit + ", 20";
        }else{
            limit = param.substring(param.indexOf("=")+1);
            query = "SELECT * FROM " + getTable() + " LIMIT "+ limit + ", 20";
        }
        Log.d(TAG, "query " + query);

        Cursor c = getSqliteDb().rawQuery(query, null);
        List<PersonalInfoSearch> personalInfoSearchList = new ArrayList<>();
        try {
            if(c != null && c.moveToFirst()) {
                personalInfoSearchList.add(getByCursor(c));
                while (c.moveToNext()) {
                    personalInfoSearchList.add(getByCursor(c));
                    Log.d(TAG, "query " + c.getString(1));

                }
            }
        } finally {
            c.close();
        }
        return personalInfoSearchList;
    }

    @Override
    public PersonalInfoSearch getByCursor(Cursor c) {
        PersonalInfoSearch personalInfoSearch = new PersonalInfoSearch();
        personalInfoSearch.setPersonNpp(c.getString(0));
        personalInfoSearch.setPersonName(c.getString(1));
        personalInfoSearch.setPersonJabatan(c.getString(2));
        personalInfoSearch.setPersonUrl(c.getString(3));
        personalInfoSearch.setPersonImage(Base64.decode(c.getBlob(4), Base64.DEFAULT));
        personalInfoSearch.setIsImageLoaded(c.getInt(5));
        return personalInfoSearch;
    }

    @Override
    protected ContentValues upDataValues(PersonalInfoSearch personalInfoSearch, boolean update) {
        ContentValues cv = new ContentValues();
        if (update == false) cv.put(COL_NPP_ID, personalInfoSearch.getPersonNpp());
        cv.put(COL_NAME_ID, personalInfoSearch.getPersonName());
        cv.put(COL_JABATAN_ID, personalInfoSearch.getPersonJabatan());
        cv.put(COL_URL_ID, personalInfoSearch.getPersonUrl());
        cv.put(COL_IMAGE_ID, Base64.encodeToString(personalInfoSearch.getPersonImage(), Base64.DEFAULT));
        cv.put(COL_LOADED_ID, personalInfoSearch.getIsImageLoaded());
        return cv;
    }

}
