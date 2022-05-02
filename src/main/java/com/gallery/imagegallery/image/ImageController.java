package com.gallery.imagegallery.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

//controller
@Controller
public class ImageController {
    @Autowired private ImageService service;

    @GetMapping("/images")
    public String showImageList(Model model) {
        List<Image> listImages = service.listAll();
        model.addAttribute("listImages", listImages);
        return "images";
    }

    @GetMapping("/images/new")
    public String showNewForm(Model model) {
        model.addAttribute("image", new Image());
        model.addAttribute("pageTitle", "Add New Image");
        model.addAttribute("imageText", "");
        return "image_form";
    }

    @PostMapping("/images/save")
    public String saveUser(Image image, RedirectAttributes ra, @RequestParam("img") MultipartFile multipartFile) throws IOException {


        //Original file name
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        image.setPhotos(filename);

        //Type
        String fileType = multipartFile.getContentType();
        image.setType(fileType);

        //Size (Bytes)
        Long fileSize = multipartFile.getSize();
        image.setSize(fileSize);

        //Resolution
        InputStream is = new BufferedInputStream(multipartFile.getInputStream());

        BufferedImage bimg = ImageIO.read(is);
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        System.out.println(bimg);
        System.out.println(bimg.getHeight());

        String fileResolution = width + "x" + height;
        image.setResolution(fileResolution);

        //Path
        File file = new File(multipartFile.getName());
        String filePath = file.getAbsolutePath();
        if (filePath.endsWith("img")) {
            filePath = filePath.substring(0, filePath.length() - 3) + "image-photos";
        }

        image.setPath(filePath);

        //Date modified
        String fp = "C:\\Users\\Domlu\\imagegallerym152\\image-photos";
        System.out.println(fp);
        File f = new File(fp);

        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        image.setDate(sd.format(f.lastModified()));

        //save all values
        service.save(image);

        //change values that require id
        String nfp = "C:\\Users\\Domlu\\imagegallerym152\\image-photos";
        File nf = new File(nfp);
        System.out.println(nfp);

        //set values that require id
        image.setDate(sd.format(nf.lastModified()));
        image.setPath(filePath + "\\" + image.getId() + "\\" + image.getPhotos());

        //upload images
        String uploadDir = "image-photos/" + image.getId();
        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        service.save(image);

        ra.addFlashAttribute("message", "The image has been saved successfully.");

        return "redirect:/images";
    }

    @GetMapping("/images/edit/{id}")
    public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra) {
        try {
            Image image = service.get(id);
            model.addAttribute("image", image);
            model.addAttribute("pageTitle", "Edit Image (ID: " + id + ")");
            model.addAttribute("imageText", "(Image in use)");

            return "image_form";
        } catch (ImageNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/images";
        }
    }

    @GetMapping("/images/delete/{id}")
    public String deleteUser(@PathVariable("id")Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The image ID " + id + " has been deleted.");
        } catch (ImageNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/images";

    }


    @GetMapping("/images/details/{id}")
    public String showDetails(@PathVariable("id")Integer id, Model model, RedirectAttributes ra) {
        try {
            Image image = service.get(id);
            model.addAttribute("image", image);
            model.addAttribute("pageTitle", "Image Details (ID: " + id + ")");
            return "image_details";
        } catch (ImageNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/images";
        }
    }

}
