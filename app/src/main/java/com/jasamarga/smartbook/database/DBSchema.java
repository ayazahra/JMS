package com.jasamarga.smartbook.database;

/**
 * Created by apridosandyasa on 1/22/16.
 */
public interface DBSchema {

    public interface Base {
        public static final String COL_ID = "ID";
    }

    public interface MainMenu {
        public static final String TABLE_NAME = "MAINMENU";
        public static final String COL_MENU_ID = "MENUID";
        public static final String COL_MENU_DESC = "DESC";
        public static final String COL_MENU_URL = "URLICON";
        public static final String COL_MENU_PADDING = "PADDING";
        public static final String COL_MENU_KANTOR_ID = "KANTORID";
    }

    public interface Cabang {
        public static final String TABLE_NAME = "CABANG";
        public static final String COL_KANTOR_ID = "KANTORID";
        public static final String COL_DESC = "DESC";
    }

    public interface AnakPerusahaan {
        public static final String TABLE_NAME = "ANAKPERUSAHAAN";
        public static final String COL_AP_ID = "APID";
        public static final String COL_AP_DESC = "DESC";
        public static final String COL_KRITERIA_ID = "KRITERIAID";
    }

    public interface Artikel {
        public static final String TABLE_NAME = "ARTIKEL";
        public static final String COL_ARTIKEL_ID = "ARTIKELID";
        public static final String COL_KRITERIA_ID = "KRITERIAID";
        public static final String COL_TITLE = "TITLE";
        public static final String COL_CONTENT = "CONTENT";
        public static final String COL_HEADER = "HEADER";
        public static final String COL_FOOTER = "FOOTER";
        public static final String COL_RINGKAS = "RINGKAS";
    }

    public interface PersonalInfoHeader {
        public static final String TABLE_NAME = "PERSONALINFOHEADER";
        public static final String COL_NPP_ID = "NPP";
        public static final String COL_NAME_ID = "NAMA";
        public static final String COL_JABATAN_ID = "JABATAN";
        public static final String COL_URL_ID = "URL";
        public static final String COL_IMAGE_ID = "IMAGE";
        public static final String COL_LOADED_ID = "ISLOADED";
    }

}
