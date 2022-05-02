package com.gallery.imagegallery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//controller
@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

}
