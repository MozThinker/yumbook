package edson.mutombene.recipe;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public record RecipeService(RecipeRepository recipeRepository) {

    public Recipe registerRecipe (RecipeRegistrationRequest request) {
        Recipe recipe = Recipe.builder()
                .userId(request.userId())
                .servings(request.servings())
                .name(request.name())
                .ingredients(request.ingredients())
                .instructions(request.instructions())
                .vegetarian(request.vegetarian())
                .createdAt(LocalDateTime.now())
                .build();

        return recipeRepository.save(recipe);
    }

    Optional<Recipe> getRecipeById(Integer id){
        return recipeRepository.findById(id);
    }

    public boolean updateRecipe(Integer id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setUserId(updatedRecipe.getUserId());
            existingRecipe.setServings(updatedRecipe.getServings());
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());
            existingRecipe.setVegetarian(updatedRecipe.isVegetarian());
            existingRecipe.setUpdatedAt(LocalDateTime.now());
            recipeRepository.save(existingRecipe);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteRecipe(Integer id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            recipeRepository.delete(optionalRecipe.get());
            return true;
        } else {
            return false;
        }
    }
}
