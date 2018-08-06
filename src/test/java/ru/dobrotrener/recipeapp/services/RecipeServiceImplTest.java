package ru.dobrotrener.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.recipeapp.converters.RecipeCommandToRecipe;
import ru.dobrotrener.recipeapp.converters.RecipeToRecipeCommand;
import ru.dobrotrener.recipeapp.domain.Recipe;
import ru.dobrotrener.recipeapp.repositories.RecipeRepository;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@Slf4j
public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);


        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipesData);


        Recipe recipe2 = new Recipe();
        recipe2.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe2);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
    }

    @Test
    public void testGetRecipeById() throws Exception {

        Recipe returnedRecipe = recipeService.findById(Long.valueOf(1));
        assertNotNull("Null recipe returned", returnedRecipe);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testGetRecipes() {

        //when
        Set<Recipe> recipes = recipeService.getRecipes();

        //then
        verify(recipeRepository, times(1)).findAll();
        log.info(recipes.toString());
        assertEquals( 1, recipes.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }


}