package com.example.kanzler.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, subtitle, description, category;
    private Integer price, size, rating, isSmoke, isParking;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "room")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public Integer getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(Integer isSmoke) {
        this.isSmoke = isSmoke;
    }

    public void addImageToRoom(Image image) {
        image.setRoom(this);
        images.add(image);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getIsParking() {
        return isParking;
    }

    public void setIsParking(Integer isParking) {
        this.isParking = isParking;
    }

    public Room() {
    }

    public Room(String title, String subtitle, String description, String category, Integer price, Integer size, Integer rating, Integer isSmoke, Integer isParking, List<Image> images, Long previewImageId, LocalDateTime dateOfCreated) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.price = price;
        this.size = size;
        this.rating = rating;
        this.isSmoke = isSmoke;
        this.isParking = isParking;
        this.images = images;
        this.previewImageId = previewImageId;
        this.dateOfCreated = dateOfCreated;
    }

    public static Comparator<Room> SortByPriceAsc = new Comparator<Room>() {
        public int compare(Room room1, Room room2) {
            Integer Room1Price = room1.getPrice();
            Integer Room2Price = room2.getPrice();

            return Room1Price - Room2Price;
        }
    };

    public static Comparator<Room> SortByPriceDesc = new Comparator<Room>() {
        public int compare(Room room1, Room room2) {
            Integer Room1Price = room1.getPrice();
            Integer Room2Price = room2.getPrice();

            return Room2Price - Room1Price;
        }
    };

    public static Comparator<Room> SortByRating = new Comparator<Room>() {
        public int compare(Room room1, Room room2) {
            Integer Room1Rating = room1.getRating();
            Integer Room2Rating = room2.getRating();

            return Room2Rating - Room1Rating;
        }
    };
}