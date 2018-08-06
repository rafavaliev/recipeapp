package ru.dobrotrener.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.dobrotrener.recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);

}
