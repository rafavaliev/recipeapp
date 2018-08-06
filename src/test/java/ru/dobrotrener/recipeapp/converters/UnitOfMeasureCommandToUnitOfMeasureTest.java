package ru.dobrotrener.recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.recipeapp.commands.UnitOfMeasureCommand;
import ru.dobrotrener.recipeapp.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasureCommandToUnitOfMeasure converter;


    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void testConvert() {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setDescription(DESCRIPTION);
        command.setId(LONG_VALUE);

        //when
        UnitOfMeasure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}