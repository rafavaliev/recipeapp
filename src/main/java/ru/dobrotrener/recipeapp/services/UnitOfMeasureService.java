package ru.dobrotrener.recipeapp.services;


import ru.dobrotrener.recipeapp.commands.UnitOfMeasureCommand;
import ru.dobrotrener.recipeapp.domain.UnitOfMeasure;

import java.util.List;
import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
