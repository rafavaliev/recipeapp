package ru.dobrotrener.recipeapp.services;

import org.springframework.stereotype.Service;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;

@Service
public interface IngredientService {
    IngredientCommand findByRecipeIdAnIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteByRecipeIdAndId(Long recipeId, Long id);
}
