package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 6/27/16.
 */
public class MainContentData implements Serializable {
    private int thumb;
    private int id;
    private String title;
    private String url;
    private String parent;

    public MainContentData() {

    }

    public MainContentData(int th, int id, String tl, String ur, String pt) {
        this.thumb = th;
        this.id = id;
        this.title = tl;
        this.url = ur;
        this.parent = pt;
    }

    public int getThumb() {
        return this.thumb;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getParent() {
        return this.parent;
    }
}
