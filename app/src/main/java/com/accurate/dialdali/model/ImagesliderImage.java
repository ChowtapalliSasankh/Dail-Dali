package com.accurate.dialdali.model;
public class ImagesliderImage
{
    private int id;
    private String title;
    private String image;
    private String created_on;
    private String content;

    public ImagesliderImage(int id, String title, String image, String created_on, String content) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.created_on = created_on;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
