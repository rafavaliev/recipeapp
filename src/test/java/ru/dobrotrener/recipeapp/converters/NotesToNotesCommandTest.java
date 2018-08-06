package ru.dobrotrener.recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.recipeapp.commands.NotesCommand;
import ru.dobrotrener.recipeapp.domain.Notes;
import ru.dobrotrener.recipeapp.testPatterns.ConverterTestable;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest implements ConverterTestable {

    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }


    @Test
    @Override
    public void testConvert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertNotNull(notesCommand);
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

    @Test
    @Override
    public void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    @Override
    public void testConvertEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }
}