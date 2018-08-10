package ru.dobrotrener.recipeapp.bootstrap;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.domain.*;
import ru.dobrotrener.recipeapp.repositories.CategoryRepository;
import ru.dobrotrener.recipeapp.repositories.IngredientRepository;
import ru.dobrotrener.recipeapp.repositories.RecipeRepository;
import ru.dobrotrener.recipeapp.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;


@Component
@Slf4j
@Profile({"dev", "prod"})
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;
    private IngredientRepository ingredientRepository;


    public BootstrapMySQL(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                          CategoryRepository categoryRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initRecipes();
    }

    private void initRecipes() {
        log.debug("Loading Bootstrap MySQL data");
        if (categoryRepository.count() == 0L) {
            log.debug("Load categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L) {
            log.debug("Load UoMs");
            loadUoM();
        }

    }

    private void loadUoM() {
        List<String> unitsOfMeasure = Arrays.asList("Teaspoon", "Tablespoon", "Cup", "Pinch",
                "Pint", "Piece", "Pound", "Dash", "Qunce");
        unitsOfMeasure.forEach(description -> {
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setDescription(description);
            unitOfMeasureRepository.save(unitOfMeasure);
        });
    }

    private void loadCategories() {
        List<String> categories = Arrays.asList("American", "Italian", "Mexican", "Fast food");

        categories.forEach(description -> {
            Category cat = new Category();
            cat.setDescription(description);
            categoryRepository.save(cat);
        });

    }

}
