package ru.dobrotrener.recipeapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
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
import java.util.stream.Collectors;

@Component
@Slf4j
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;
    private IngredientRepository ingredientRepository;

    private Map<String, UnitOfMeasure> unitOfMeasureMap = new HashMap<>(10);

    public RecipeBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
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
        log.debug("Init recipes");
        checkIfPredefinedExist();
        List<Recipe> recipes = new ArrayList<>(2);
        recipes.add(initGuacamole());
        recipes.add(initChicken());

    }


    private Recipe initChicken() {
        return null;
    }

    private Recipe initGuacamole() {

        Category americanCategory = categoryRepository.findByDescription("American").get();
        Category mexicanCategory = categoryRepository.findByDescription("Mexican").get();

        Recipe guacamole = new Recipe();
        guacamole.getCategories().add(americanCategory);
        guacamole.getCategories().add(mexicanCategory);
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.MODERATE);
        guacamole.setServings(4);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. " +
                "Remove seed. Score the inside of the avocado with a blunt knife and " +
                "scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. " +
                "(Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with " +
                "salt and lime (or lemon) juice. The acid in the lime juice will provide some balance " +
                "to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. " +
                "Chili peppers vary individually in their hotness. So, start with a " +
                "half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the " +
                "variability in the fresh ingredients. Start with this recipe and " +
                "adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic " +
                "wrap on the surface of the guacamole cover it and to prevent air " +
                "reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to " +
                "add chopped tomato to your guacamole, add it just before serving.");

        guacamole.addIngredient(
                new Ingredient("ripe avocado", BigDecimal.valueOf(2L), unitOfMeasureMap.get("Piece")));
        guacamole.addIngredient(
                new Ingredient("Kosher salt", BigDecimal.valueOf(0.5d), unitOfMeasureMap.get("Teaspoon")));


        Notes note = new Notes(guacamole, "For a very quick guacamole just take a 1/4 cup of " +
                "salsa and mix it in with your mashed avocados.\n" +
                "\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and " +
                "chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with " +
                "added pineapple, mango, or strawberries (see our Strawberry Guacamole).\n" +
                "\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the " +
                "lack of availability of other ingredients stop you from making guacamole.\n" +
                "\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to " +
                "your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!");
        guacamole.setNotes(note);
        recipeRepository.save(guacamole);
        log.debug("Created guacamole" + guacamole.toString());
        return guacamole;
    }


    private void checkIfPredefinedExist() {
        List<String> unitsOfMeasure = Arrays.asList("Teaspoon", "Tablespoon", "Cup", "Pinch",
                "Pint", "Piece", "Pound", "Dash", "Qunce");
        List<String> categories = Arrays.asList("American", "Italian", "Mexican", "Fast food");

        unitsOfMeasure.stream().forEach(description -> {
            checkIfUnitExist(description);
            unitOfMeasureMap.put(description, unitOfMeasureRepository.findByDescription(description).get());
        });
        categories.stream().forEach(description -> checkIfCategoryExist(description));

    }

    private void checkIfCategoryExist(String description) {
        if (!categoryRepository.findByDescription(description).isPresent()) {
            throw new RuntimeException(String.format("Category with description '%s' doesn't exist", description));
        }
    }

    private void checkIfUnitExist(String description) {
        if (!unitOfMeasureRepository.findByDescription(description).isPresent()) {
            throw new RuntimeException(String.format("UoM with description %s doesn't exist", description));
        }
    }
}
