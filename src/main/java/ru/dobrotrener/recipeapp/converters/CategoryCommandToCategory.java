package ru.dobrotrener.recipeapp.converters;


import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.dobrotrener.recipeapp.commands.CategoryCommand;
import ru.dobrotrener.recipeapp.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }
        final Category destination = new Category();
        destination.setId(source.getId());
        destination.setDescription(source.getDescription());
        return destination;
    }
}
