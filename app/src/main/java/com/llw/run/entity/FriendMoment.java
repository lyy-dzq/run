package com.llw.run.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendMoment extends LitePalSupport implements Serializable {

    private String name;
    private String content;
    private long time;
    private List<Integer> images;
    private List<String> imagePath;
    private List<Replay> replays = new ArrayList<>();

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }

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

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

}
