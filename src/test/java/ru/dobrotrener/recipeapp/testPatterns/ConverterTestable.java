package ru.dobrotrener.recipeapp.testPatterns;

import org.junit.Before;
import org.junit.Test;

public interface ConverterTestable {

    Long LONG_VALUE = new Long(1L);
    String DESCRIPTION = "description";

    @Before
    void setUp() throws Exception;
    @Test
    void testConvertNull();
    @Test
    void testConvertEmptyObject();
    @Test
    void testConvert();
}
