package com.example.kanzler.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, subtitle, description, category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "blog")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Image> getImages() {
        return images;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public void addImageToBlog(Image image) {
        image.setBlog(this);
        images.add(image);
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Blog() {}

    public Blog(String title, String subtitle, String description, String category, List<Image> images, Long previewImageId) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.images = images;
        this.previewImageId = previewImageId;
    }

    public Blog(String title, String subtitle, String description, String category, List<Image> images) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.images = images;
    }
}
