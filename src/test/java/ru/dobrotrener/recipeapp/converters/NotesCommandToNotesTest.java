package ru.dobrotrener.recipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.recipeapp.commands.NotesCommand;
import ru.dobrotrener.recipeapp.domain.Notes;
import ru.dobrotrener.recipeapp.testPatterns.ConverterTestable;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest implements ConverterTestable {


    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();

    }

    @Test
    @Override
    public void testConvertNull()  {
        assertNull(converter.convert(null));
    }

    @Test
    @Override
    public void testConvertEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    @Override
    public void testConvert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}