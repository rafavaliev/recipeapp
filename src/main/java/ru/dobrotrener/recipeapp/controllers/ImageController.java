package ru.dobrotrener.recipeapp.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.dobrotrener.recipeapp.commands.RecipeCommand;
import ru.dobrotrener.recipeapp.domain.Recipe;
import ru.dobrotrener.recipeapp.services.ImageService;
import ru.dobrotrener.recipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;



    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImagePost(@PathVariable("id") String id,
                                  @RequestParam("file") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/image")
    public String showImageForm(@PathVariable("id") String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/image/new";
    }

    @GetMapping("/recipe/{id}/image/show")
    public void showRenderedImage(@PathVariable("id") String recipeId,
                                    HttpServletResponse response) throws Exception {
        RecipeCommand command = recipeService.findCommandById(Long.valueOf(recipeId));

        byte[] bytes = new byte[command.getImage().length];

        int i = 0;
        for (Byte wrap : command.getImage()) {
            bytes[i++] = wrap;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(bytes);
        IOUtils.copy(is, response.getOutputStream());
    }

}
