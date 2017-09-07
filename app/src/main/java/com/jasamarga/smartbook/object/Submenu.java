package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 3/21/17.
 */

public class Submenu implements Serializable {

    private int subMenuId;
    private int mainMenuId;
    private String subMenuNama;
    private String subMenuUrl;

    public Submenu() {

    }

    public Submenu(int subMenuId, int mainMenuId, String subMenuNama, String subMenuUrl) {
        this.subMenuId = subMenuId;
        this.mainMenuId = mainMenuId;
        this.subMenuNama = subMenuNama;
        this.subMenuUrl = subMenuUrl;
    }

    public void setSubMenuId(int subMenuId) {
        this.subMenuId = subMenuId;
    }

    public void setMainMenuId(int mainMenuId) {
        this.mainMenuId = mainMenuId;
    }

    public void setSubMenuNama(String subMenuNama) {
        this.subMenuNama = subMenuNama;
    }

    public void setSubMenuUrl(String subMenuUrl) {
        this.subMenuUrl = subMenuUrl;
    }

    public int getSubMenuId() {
        return this.subMenuId;
    }

    public int getMainMenuId() {
        return this.mainMenuId;
    }

    public String getSubMenuNama() {
        return this.subMenuNama;
    }

    public String getSubMenuUrl() {
        return this.subMenuUrl;
    }

}
