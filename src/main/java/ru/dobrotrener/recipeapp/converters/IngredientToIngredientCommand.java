package ru.dobrotrener.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;
import ru.dobrotrener.recipeapp.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientCommand destination = new IngredientCommand();
        destination.setId(source.getId());
        destination.setAmount(source.getAmount());
        destination.setDescription(source.getDescription());
        destination.setUom(unitOfMeasureConverter.convert(source.getUom()));
        if (source.getRecipe() != null) {
            destination.setRecipeId(source.getRecipe().getId());
        }
        return destination;
    }
}
