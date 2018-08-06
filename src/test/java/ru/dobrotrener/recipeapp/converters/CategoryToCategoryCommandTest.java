package ru.dobrotrener.recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.recipeapp.commands.CategoryCommand;
import ru.dobrotrener.recipeapp.domain.Category;
import ru.dobrotrener.recipeapp.testPatterns.ConverterTestable;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest implements ConverterTestable {
    private CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Override
    @Test
    public void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Override
    @Test
    public void testConvertEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Override
    @Test
    public void testConvert() {
        //given
        Category category = new Category();
        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand command = converter.convert(category);

        //then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}