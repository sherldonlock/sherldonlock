package com.nicechat.pojo;

import java.io.Serializable;

public class Image implements Serializable {

    private String id;

    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
