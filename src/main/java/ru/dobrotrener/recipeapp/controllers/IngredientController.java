package ru.dobrotrener.recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;
import ru.dobrotrener.recipeapp.commands.RecipeCommand;
import ru.dobrotrener.recipeapp.commands.UnitOfMeasureCommand;
import ru.dobrotrener.recipeapp.domain.UnitOfMeasure;
import ru.dobrotrener.recipeapp.services.IngredientService;
import ru.dobrotrener.recipeapp.services.RecipeService;
import ru.dobrotrener.recipeapp.services.UnitOfMeasureService;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Get ingredients list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipeId") String recipeId,
                                   @PathVariable("id") String id, Model model) {
        log.debug("Show ingredient for recipe: " + recipeId + ", with id: " + id);
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAnIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String createIngredient(@PathVariable("recipeId") String recipeId, Model model) {
        log.debug("Create ingredient for recipe: " + recipeId);

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception

        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(Long.valueOf(recipeId));
        command.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/new";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable("recipeId") String recipeId,
                                 @PathVariable("id") String id, Model model) {
        log.debug("Update ingredient for recipe: " + recipeId + ", with id: " + id);
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAnIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/new";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("Save or update ingredient for recipe: " + savedCommand.getRecipeId() + ", with id: " + savedCommand.getId());

        if (savedCommand != null && savedCommand.getRecipeId() != null && savedCommand.getId() != null) {
            return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable("recipeId") String recipeId,
                                   @PathVariable("id") String id, Model model) {
        log.debug("Delete idngredient with id: " + id + " from recipe with id: " + recipeId);
        ingredientService.deleteByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(id));
        return "redirect:/recipe/" + recipeId + "/show";

    }


}
