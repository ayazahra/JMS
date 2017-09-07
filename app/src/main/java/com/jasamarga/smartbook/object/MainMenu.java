package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 7/19/16.
 */
public class MainMenu implements Serializable {

    private int mainMenuId;
    private String mainMenuDesc;
    private String mainMenuUrl;

    public MainMenu() {

    }

    public MainMenu(int id, String desc, String url) {
        this.mainMenuId = id;
        this.mainMenuDesc = desc;
        this.mainMenuUrl = url;
    }

    public void setMainMenuId(int id) {
        this.mainMenuId = id;
    }

    public void setMainMenuDesc(String desc) {
        this.mainMenuDesc = desc;
    }

    public void setMainMenuUrl(String url) {
        this.mainMenuUrl = url;
    }

    public int getMainMenuId() {
        return this.mainMenuId;
    }

    public String getMainMenuDesc() {
        return this.mainMenuDesc;
    }

    public String getMainMenuUrl() {
        return this.mainMenuUrl;
    }

}
