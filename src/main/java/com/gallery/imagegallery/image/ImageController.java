package com.gallery.imagegallery.image;

import org.apache.tomcat.jni.FileInfo;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
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
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        //Date updated
        String fp = "C:\\Users\\Domlu\\imagegallerym152\\image-photos\\" + image.getId();
        System.out.println(fp);
        File f = new File(fp);

        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        image.setDate(sd.format(f.lastModified()));

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

        service.save(image);

        image.setPath(filePath + "\\" + image.getId() + "\\" + image.getPhotos());

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
