package com.gallery.imagegallery;

import com.gallery.imagegallery.image.Image;
import com.gallery.imagegallery.image.ImageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ImageRepositoryTests {

    @Autowired
    private ImageRepository repo;

    @Test
    public void testAddNew() {
        Image image = new Image();
        image.setName("img1");
        image.setType("jpg");
        image.setPath("C:/user/dom");
        image.setSize((long) 677);
        image.setDate("12.12.2022");
        image.setResolution("10x10");

        Image savedImage = repo.save(image);

        Assertions.assertThat(savedImage).isNotNull();
        Assertions.assertThat(savedImage.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Image> images = repo.findAll();
        Assertions.assertThat(images).hasSizeGreaterThan(0);

        for (Image image : images) {
            System.out.println(image);
        }
    }

    @Test
    public void testUpdate() {
        Integer imageId = 1;
        Optional<Image> optionalImage = repo.findById(imageId);
        Image image = optionalImage.get();
        image.setName("nimage.png");
        repo.save(image);

        Image updatedImage = repo.findById(imageId).get();
        Assertions.assertThat(updatedImage.getName()).isEqualTo("nimage.png");
    }

    @Test
    public void testGet() {
        Integer imageId = 1;
        Optional<Image> optionalImage = repo.findById(imageId);
        Assertions.assertThat(optionalImage).isPresent();
        System.out.println(optionalImage.get());
    }

    @Test
    public void testDelete() {
        Integer imageId = 1;
        repo.deleteById(imageId);

        Optional<Image> optionalImage = repo.findById(imageId);
        Assertions.assertThat(optionalImage).isNotPresent();
    }
}

