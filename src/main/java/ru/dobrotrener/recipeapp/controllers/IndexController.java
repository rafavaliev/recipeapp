package ru.dobrotrener.recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dobrotrener.recipeapp.domain.Category;
import ru.dobrotrener.recipeapp.domain.UnitOfMeasure;
import ru.dobrotrener.recipeapp.repositories.CategoryRepository;
import ru.dobrotrener.recipeapp.repositories.RecipeRepository;
import ru.dobrotrener.recipeapp.repositories.UnitOfMeasureRepository;
import ru.dobrotrener.recipeapp.services.RecipeService;

import java.util.Optional;

@Controller
@Slf4j
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "", "/index", "/index.php"})
    public String getIndex(Model model) {
        log.debug("Current url: /");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
