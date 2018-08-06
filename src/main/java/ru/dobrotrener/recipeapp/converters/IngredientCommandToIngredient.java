package ru.dobrotrener.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;
import ru.dobrotrener.recipeapp.domain.Ingredient;


@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient destination = new Ingredient();
        destination.setId(source.getId());
        destination.setAmount(source.getAmount());
        destination.setDescription(source.getDescription());
        destination.setUom(unitOfMeasureConverter.convert(source.getUom()));
        return destination;
    }
}
