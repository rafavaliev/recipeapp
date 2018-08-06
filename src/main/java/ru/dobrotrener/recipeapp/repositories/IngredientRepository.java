package ru.dobrotrener.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dobrotrener.recipeapp.domain.Ingredient;

@Repository
public interface IngredientRepository  extends CrudRepository<Ingredient, Long> {
}
