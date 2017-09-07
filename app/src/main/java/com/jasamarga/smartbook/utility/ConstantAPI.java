package com.jasamarga.smartbook.utility;

/**
 * Created by Andy on 5/23/2016.
 */
public class ConstantAPI {
    public final static String BASE_URL = "http://Jmact.jasamarga.co.id:8000/smartbook";
    //public final static String BASE_URL = "http://smartbook.g8tech.net";

    public final static String getMainMenu() {
        return BASE_URL + "/api/menuaplikasi";
    }

    public final static String getSubmenu() {
        return BASE_URL + "/api/submenuaplikasi";
    }

    public final static String getListPerusahaan() {
        return BASE_URL + "/api/listperusahaan";
    }

    public final static String getPusatContent() {
        return BASE_URL + "/api/pusatmenu";
    }

    public final static String getCabangContent() {
        return BASE_URL + "/api/cabangmenu";
    }

    public final static String getPenghargaan() {
        return BASE_URL + "/api/penghargaan";
    }

    public final static String getPersonalInfo() {
        return BASE_URL + "/api/personalinfo";
    }

    public final static String getAtasanInfo() {
        return BASE_URL + "/api/getatasan";
    }

    public final static String getPeerList() {
        return BASE_URL + "/api/getpeer";
    }

    public final static String getBawahanList() {
        return BASE_URL + "/api/getbawahan";
    }

    public final static String getArticle() {
        return BASE_URL + "/api/article";
    }

    public final static String getLogin() {
        return BASE_URL + "/api/login";
    }

}
