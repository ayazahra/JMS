package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 7/29/16.
 */
public class BocData implements Serializable {

    private int id;
    private int mainMenuId;
    private int subMenuId;
    private int kantorId;
    private String nama;
    private String jabatan;
    private String url;

    public BocData() {

    }

    public BocData(int id, int mainMenuId, int subMenuId, int kantorId, String nama, String jabatan, String url) {
        this.id = id;
        this.mainMenuId = mainMenuId;
        this.subMenuId = subMenuId;
        this.kantorId = kantorId;
        this.nama = nama;
        this.jabatan = jabatan;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMainMenuId(int mainMenuId) {
        this.mainMenuId = mainMenuId;
    }

    public void setSubMenuId(int subMenuId) {
        this.subMenuId = subMenuId;
    }

    public void setKantorId(int kantorId) {
        this.kantorId = kantorId;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public int getMainMenuId() {
        return mainMenuId;
    }

    public int getSubMenuId() {
        return subMenuId;
    }

    public int getKantorId() {
        return kantorId;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getUrl() {
        return url;
    }

}
