package edson.mutombene.recipe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/recipe",
        produces = "application/json")
public record RecipeController(RecipeService recipeService) {

    @PostMapping()
    ResponseEntity<?> registerRecipe(@RequestBody RecipeRegistrationRequest recipeRegistrationRequest) {
        log.info("new recipe registration {}", recipeRegistrationRequest);
        return new ResponseEntity<>(recipeService.registerRecipe(recipeRegistrationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable ("id") Integer recipeId) {
        Optional<Recipe> recipe = recipeService.getRecipeById(recipeId);

        if(recipe.isPresent())
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        return new ResponseEntity<>("Recipe doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") Integer recipeId, @RequestBody Recipe updateRecipe) {
        boolean recipeUpdated = recipeService.updateRecipe(recipeId, updateRecipe);

        if (recipeUpdated) {
            return new ResponseEntity<>("Recipe updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Integer recipeId) {
        boolean recipeDeleted = recipeService.deleteRecipe(recipeId);

        if (recipeDeleted) {
            return new ResponseEntity<>("Recipe deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }
    }


}
