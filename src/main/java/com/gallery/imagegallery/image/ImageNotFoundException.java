package com.gallery.imagegallery.image;

//image exception method
public class ImageNotFoundException extends Throwable {

    public ImageNotFoundException(String message) {
        super(message);
    }
}
