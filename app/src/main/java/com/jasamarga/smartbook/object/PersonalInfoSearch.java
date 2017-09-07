package com.jasamarga.smartbook.object;

import android.graphics.Bitmap;

/**
 * Created by apridosandyasa on 8/11/16.
 */
public class PersonalInfoSearch {

    private String personNpp;
    private String personName;
    private String personJabatan;
    private String personUrl;
    private byte[] personImage;
    private int isImageLoaded;

    public PersonalInfoSearch() {

    }

    public PersonalInfoSearch(String npp, String name, String jabatan, String url, byte[] bitmap, int isLoaded) {
        this.personNpp = npp;
        this.personName = name;
        this.personJabatan = jabatan;
        this.personUrl = url;
        this.personImage = bitmap;
        this.isImageLoaded = 0;
    }

    public void setPersonNpp(String npp) {
        this.personNpp = npp;
    }

    public void setPersonName(String name) {
        this.personName = name;
    }

    public void setPersonJabatan(String jabatan) {
        this.personJabatan = jabatan;
    }

    public void setPersonUrl(String url) {
        this.personUrl = url;
    }

    public void setPersonImage(byte[] bitmap) {
        this.personImage = bitmap;
    }

    public void setIsImageLoaded(int isLoaded) {
        this.isImageLoaded = isLoaded;
    }

    public String getPersonNpp() {
        return this.personNpp;
    }

    public String getPersonName() {
        return this.personName;
    }

    public String getPersonJabatan() {
        return this.personJabatan;
    }

    public String getPersonUrl() {
        return this.personUrl;
    }

    public byte[] getPersonImage() {
        return this.personImage;
    }

    public int getIsImageLoaded() {
        return this.isImageLoaded;
    }
}
