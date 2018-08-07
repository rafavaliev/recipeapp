package ru.dobrotrener.recipeapp.converters;


import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.commands.RecipeCommand;
import ru.dobrotrener.recipeapp.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand destination = new RecipeCommand();
        destination.setId(source.getId());
        destination.setDescription(source.getDescription());
        destination.setCookTime(source.getCookTime());
        destination.setPrepTime(source.getPrepTime());
        destination.setDifficulty(source.getDifficulty());
        destination.setDirections(source.getDirections());
        destination.setServings(source.getServings());
        destination.setSource(source.getSource());
        destination.setImage(source.getImage());
        destination.setUrl(source.getUrl());
        destination.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(cat -> destination.getCategories().add(categoryConverter.convert(cat)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingr -> destination.getIngredients().add(ingredientConverter.convert(ingr)));
        }
        return destination;
    }
}
