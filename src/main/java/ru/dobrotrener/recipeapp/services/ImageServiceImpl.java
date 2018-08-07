package ru.dobrotrener.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dobrotrener.recipeapp.domain.Recipe;
import ru.dobrotrener.recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("Save image for recipe with id: " + recipeId);

        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

            if (!recipeOptional.isPresent()) {
                log.debug("For uploading image, recipe not found for id: " + recipeId);

            } else {
                Recipe recipe = recipeOptional.get();
                Byte[] bytesObjects= new Byte[file.getBytes().length];

                int i = 0;
                for (byte b : file.getBytes()) {
                    bytesObjects[i++] = b;
                }

                recipe.setImage(bytesObjects);
                recipeRepository.save(recipe);
            }
        } catch (IOException e) {
            log.error("Error occured ", e);
            e.printStackTrace();
        }
    }
}
