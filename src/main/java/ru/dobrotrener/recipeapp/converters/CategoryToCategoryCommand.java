package ru.dobrotrener.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.commands.CategoryCommand;
import ru.dobrotrener.recipeapp.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        final CategoryCommand destination = new CategoryCommand();
        destination.setId(source.getId());
        destination.setDescription(source.getDescription());
        return destination;
    }
}

