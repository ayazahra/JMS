package com.jasamarga.smartbook.object;

/**
 * Created by apridosandyasa on 8/14/16.
 */
public class Highlight {

    private int id;
    private String nama;
    private int catId;
    private String fullText;
    private String introText;
    private String date;

    public Highlight() {

    }

    public Highlight(int id, String nama, int catId, String fullText, String introText, String date) {
        this.id = id;
        this.nama = nama;
        this.catId = catId;
        this.fullText = fullText;
        this.introText = introText;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getIntroText() {
        return introText;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
