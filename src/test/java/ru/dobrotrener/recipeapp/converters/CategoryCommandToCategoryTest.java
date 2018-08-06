package ru.dobrotrener.recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.recipeapp.commands.CategoryCommand;
import ru.dobrotrener.recipeapp.domain.Category;
import ru.dobrotrener.recipeapp.testPatterns.ConverterTestable;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest implements ConverterTestable {

    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = new Long(1L);

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Override
    @Test
    public void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Override
    @Test
    public void testConvertEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Override
    @Test
    public void testConvert() {
        //given
        CategoryCommand command = new CategoryCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(command);

        //then
        assertNotNull(category);
        assertEquals(LONG_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}