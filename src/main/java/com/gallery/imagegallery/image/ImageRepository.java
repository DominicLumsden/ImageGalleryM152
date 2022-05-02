package com.gallery.imagegallery.image;

import org.springframework.data.repository.CrudRepository;

//repository with crud
public interface ImageRepository extends CrudRepository<Image, Integer> {
    public Long countById(Integer id);
}
