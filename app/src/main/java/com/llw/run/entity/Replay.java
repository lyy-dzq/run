package com.llw.run.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Replay extends LitePalSupport implements Serializable {

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
