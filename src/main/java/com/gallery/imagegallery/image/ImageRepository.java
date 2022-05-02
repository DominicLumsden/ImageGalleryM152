package com.gallery.imagegallery.image;

import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    public Long countById(Integer id);
}
