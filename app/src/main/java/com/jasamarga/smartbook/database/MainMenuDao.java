package com.jasamarga.smartbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jasamarga.smartbook.object.MainMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 9/28/16.
 */

public class MainMenuDao extends BaseDao<MainMenu> implements DBSchema.MainMenu {

    private final static String TAG = MainMenuDao.class.getSimpleName();

    public MainMenuDao(Context c) {
        super(c, TABLE_NAME);
    }

    public MainMenuDao(Context c, boolean willWrite) {
        super(c, TABLE_NAME, willWrite);
    }

    public MainMenuDao(DBHelper db) {
        super(db, TABLE_NAME);
    }

    public MainMenuDao(DBHelper db, boolean willWrite) {
        super(db, TABLE_NAME, willWrite);
    }

    public MainMenu getById(int id) {
        String qry = "SELECT * FROM " + getTable() + " WHERE " + COL_ID + " = " + id;
        Cursor c = getSqliteDb().rawQuery(qry, null);
        MainMenu mainMenu = new MainMenu();
        try {
            if(c != null && c.moveToFirst()) {
                mainMenu = getByCursor(c);
            }
        } finally {
            c.close();
        }
        return mainMenu;
    }

    public List<MainMenu> getListPersonalInfoSearch() {
        String query = "SELECT * FROM " + getTable();

        Cursor c = getSqliteDb().rawQuery(query, null);
        List<MainMenu> mainMenuList = new ArrayList<>();
        try {
            if(c != null && c.moveToFirst()) {
                mainMenuList.add(getByCursor(c));
                while (c.moveToNext()) {
                    mainMenuList.add(getByCursor(c));

                }
            }
        } finally {
            c.close();
        }
        return mainMenuList;
    }

    @Override
    public MainMenu getByCursor(Cursor c) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setMainMenuId(c.getInt(1));
        mainMenu.setMainMenuDesc(c.getString(2));
        mainMenu.setMainMenuUrl(c.getString(3));
        return mainMenu;
    }

    @Override
    protected ContentValues upDataValues(MainMenu mainMenu, boolean update) {
        ContentValues cv = new ContentValues();
        if (update == false) cv.put(COL_MENU_ID, mainMenu.getMainMenuId());
        cv.put(COL_MENU_DESC, mainMenu.getMainMenuDesc());
        cv.put(COL_MENU_URL, mainMenu.getMainMenuUrl());
        return cv;
    }

}
