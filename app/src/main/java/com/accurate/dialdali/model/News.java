package com.accurate.dialdali.model;
public class News
{
    private int id;
    private String title;
    private String description;
    private String image;
    private String image_src;

    public News(int id, String title, String description, String image, String image_src) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.image_src = image_src;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
