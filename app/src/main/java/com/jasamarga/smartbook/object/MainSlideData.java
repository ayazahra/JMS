package com.jasamarga.smartbook.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 7/27/16.
 */
public class MainSlideData implements Serializable {

    private String header;
    private String title;
    private String content;

    public MainSlideData() {

    }

    public MainSlideData(String header, String title, String content) {
        this.header = header;
        this.title = title;
        this.content = content;
    }

    public String getHeader() {
        return this.header;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

}
