package ru.dobrotrener.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dobrotrener.recipeapp.domain.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
