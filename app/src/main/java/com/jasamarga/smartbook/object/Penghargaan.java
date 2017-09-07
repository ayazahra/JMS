package com.jasamarga.smartbook.object;

/**
 * Created by apridosandyasa on 9/26/16.
 */

public class Penghargaan {

    private String title;
    private String detail;

    public Penghargaan() {

    }

    public Penghargaan(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDetail() {
        return this.detail;
    }
}
