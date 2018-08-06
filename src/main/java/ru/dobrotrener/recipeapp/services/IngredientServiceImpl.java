package ru.dobrotrener.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;
import ru.dobrotrener.recipeapp.converters.IngredientCommandToIngredient;
import ru.dobrotrener.recipeapp.converters.IngredientToIngredientCommand;
import ru.dobrotrener.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ru.dobrotrener.recipeapp.domain.Ingredient;
import ru.dobrotrener.recipeapp.domain.Recipe;
import ru.dobrotrener.recipeapp.repositories.IngredientRepository;
import ru.dobrotrener.recipeapp.repositories.RecipeRepository;

import java.util.Optional;


@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;

        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Override
    public IngredientCommand findByRecipeIdAnIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found with id: " + recipeId);
            return null;
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found with id: " + ingredientId);
            return null;
        }
        return ingredientCommandOptional.get();
    }
}
