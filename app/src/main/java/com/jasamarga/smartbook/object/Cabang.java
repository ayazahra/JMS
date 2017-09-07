package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 7/19/16.
 */
public class Cabang implements Serializable {

    private int cabangId;
    private int mainMenuId;
    private int kantorId;
    private String cabangDesc;
    private String cabangAlamat;
    private String cabangUrl;

    public Cabang() {

    }

    public Cabang(int id, int mainMenuId, int kantorId,String desc, String alamat, String url) {
        this.cabangId = id;
        this.mainMenuId = mainMenuId;
        this.kantorId = kantorId;
        this.cabangDesc = desc;
        this.cabangAlamat = alamat;
        this.cabangUrl = url;
    }

    public void setCabangId(int id) {
        this.cabangId = id;
    }

    public void setMainMenuId(int mainMenuId) {
        this.mainMenuId = mainMenuId;
    }

    public void setKantorId(int kantorId) {
        this.kantorId = kantorId;
    }

    public void setCabangDesc(String desc) {
        this.cabangDesc = desc;
    }

    public void setCabangAlamat(String alamat) {
        this.cabangAlamat = alamat;
    }

    public void setCabangUrl(String url) {
        this.cabangUrl = url;
    }

    public int getCabangId() {
        return this.cabangId;
    }

    public int getMainMenuId() {
        return this.mainMenuId;
    }

    public int getKantorId() {
        return this.kantorId;
    }

    public String getCabangDesc() {
        return this.cabangDesc;
    }

    public String getCabangAlamat() {
        return this.cabangAlamat;
    }

    public String getCabangUrl() {
        return this.cabangUrl;
    }

}
