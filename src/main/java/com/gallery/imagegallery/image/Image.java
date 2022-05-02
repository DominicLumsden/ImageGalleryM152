package com.gallery.imagegallery.image;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String type;

    @Column(length = 145, nullable = false)
    private String path;

    @Column(length = 45, nullable = false)
    private Long size;

    @Column(length = 45, nullable = false)
    private String date;

    @Column(length = 45, nullable = false)
    private String resolution;

    @Column(length = 145, nullable = true)
    private String photos;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

        /*
                @Override
                public String toString() {
                    return "Image{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", type='" + type + '\'' +
                            ", path='" + path + '\'' +
                            ", date='" + date + '\'' +
                            ", resolution='" + resolution + '\'' +
                            '}';
                }
            */
    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/image-photos/" + id + "/" + photos;
    }
}

