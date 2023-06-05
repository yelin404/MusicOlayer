package com.example.musicplayer.Bean;

import java.io.Serializable;

public class SongBean implements Serializable {
    public String name;
    public int imgId;

    public SongBean(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public SongBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
