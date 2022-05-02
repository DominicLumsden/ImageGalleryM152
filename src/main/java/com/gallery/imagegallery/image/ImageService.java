package com.gallery.imagegallery.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//service
@Service
public class ImageService {
    @Autowired private ImageRepository repo;

    public List<Image> listAll() {
        return (List<Image>) repo.findAll();
    }

    public void save(Image image) {
        repo.save(image);
    }

    public Image get(Integer id) throws ImageNotFoundException {
        Optional<Image> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ImageNotFoundException("Could not find any images with ID " + id);
    }

    public void delete(Integer id) throws ImageNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ImageNotFoundException("Could not find any images with ID " + id);
        }
        repo.deleteById(id);
    }

}
