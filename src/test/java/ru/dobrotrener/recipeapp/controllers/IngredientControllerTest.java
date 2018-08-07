package ru.dobrotrener.recipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dobrotrener.recipeapp.commands.IngredientCommand;
import ru.dobrotrener.recipeapp.commands.RecipeCommand;
import ru.dobrotrener.recipeapp.domain.Ingredient;
import ru.dobrotrener.recipeapp.domain.Recipe;
import ru.dobrotrener.recipeapp.services.IngredientService;
import ru.dobrotrener.recipeapp.services.RecipeService;
import ru.dobrotrener.recipeapp.services.UnitOfMeasureService;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {


    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredients() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void showIngredientTest() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        when(ingredientService.findByRecipeIdAnIngredientId(anyLong(), anyLong())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        //then
        verify(ingredientService, times(1))
                .findByRecipeIdAnIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();

        //when
        when(ingredientService.findByRecipeIdAnIngredientId(anyLong(), anyLong())).thenReturn(command);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/new"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(2L);
        command.setId(3L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());


        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(
                post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some str")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void createIngredient() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/new"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void deleteIngredient() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        //when


        mockMvc.perform(
                get("/recipe/1/ingredient/2/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","1")
                .param("description", "some str")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));
        //then
        verify(ingredientService, times(1)).deleteByRecipeIdAndId(anyLong(), anyLong());


    }
}