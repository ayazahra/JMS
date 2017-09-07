package com.jasamarga.smartbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.jasamarga.smartbook.BuildConfig.DB_NAME;
import static com.jasamarga.smartbook.BuildConfig.DB_VERSION;

/**
 * Created by apridosandyasa on 1/22/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper dbHelperInstance;
    private Context mContext;

    public static DBHelper getDbHelperInstance(Context c) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DBHelper(c);
        }
        return dbHelperInstance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        setupTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void setupTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `MAINMENU` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`MENUID`\tINTEGER,\n" +
                "\t`DESC`\tTEXT,\n" +
                "\t`URLICON`\tTEXT,\n" +
                "\t`PADDING`\tINTEGER,\n" +
                "\t`KANTORID`\tINTEGER\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS `CABANG` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`KANTORID`\tINTEGER,\n" +
                "\t`DESC`\tTEXT\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS `ANAKPERUSAHAAN` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`APID`\tINTEGER,\n" +
                "\t`DESC`\tTEXT,\n" +
                "\t`KRITERIAID`\tINTEGER\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS `ARTIKEL` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`ARTIKELID`\tTEXT,\n" +
                "\t`KRITERIAID`\tINTEGER,\n" +
                "\t`TITLE`\tTEXT,\n" +
                "\t`CONTENT`\tTEXT,\n" +
                "\t`HEADER`\tTEXT,\n" +
                "\t`FOOTER`\tTEXT,\n" +
                "\t`RINGKAS`\tTEXT\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS `PERSONALINFOHEADER` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`NPP`\tTEXT NOT NULL,\n" +
                "\t`NAMA`\tTEXT NOT NULL,\n" +
                "\t`JABATAN`\tTEXT,\n" +
                "\t`URL`\tTEXT,\n" +
                "\t`IMAGE`\tBLOB,\n" +
                "\t`ISLOADED`\tINTEGER\n" +
                ");");
    }
}
