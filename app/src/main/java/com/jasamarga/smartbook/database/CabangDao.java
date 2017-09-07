package com.jasamarga.smartbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jasamarga.smartbook.object.Cabang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 9/28/16.
 */

public class CabangDao extends BaseDao<Cabang> implements DBSchema.Cabang {

    private final static String TAG = CabangDao.class.getSimpleName();

    public CabangDao(Context c) {
        super(c, TABLE_NAME);
    }

    public CabangDao(Context c, boolean willWrite) {
        super(c, TABLE_NAME, willWrite);
    }

    public CabangDao(DBHelper db) {
        super(db, TABLE_NAME);
    }

    public CabangDao(DBHelper db, boolean willWrite) {
        super(db, TABLE_NAME, willWrite);
    }

    public Cabang getById(int id) {
        String qry = "SELECT * FROM " + getTable() + " WHERE " + COL_ID + " = " + id;
        Cursor c = getSqliteDb().rawQuery(qry, null);
        Cabang cabang = new Cabang();
        try {
            if(c != null && c.moveToFirst()) {
                cabang = getByCursor(c);
            }
        } finally {
            c.close();
        }
        return cabang;
    }

    public List<Cabang> getListCabang() {
        String query = "SELECT * FROM " + getTable();

        Cursor c = getSqliteDb().rawQuery(query, null);
        List<Cabang> cabangList = new ArrayList<>();
        try {
            if(c != null && c.moveToFirst()) {
                cabangList.add(getByCursor(c));
                while (c.moveToNext()) {
                    cabangList.add(getByCursor(c));

                }
            }
        } finally {
            c.close();
        }
        return cabangList;
    }

    @Override
    public Cabang getByCursor(Cursor c) {
        Cabang cabang = new Cabang();
        cabang.setCabangId(c.getInt(1));
        cabang.setCabangDesc(c.getString(2));
        return cabang;
    }

    @Override
    protected ContentValues upDataValues(Cabang cabang, boolean update) {
        ContentValues cv = new ContentValues();
        if (update == false) cv.put(COL_KANTOR_ID, cabang.getCabangId());
        cv.put(COL_DESC, cabang.getCabangDesc());
        return cv;
    }

}
