package edson.mutombene.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void itShouldSaveRecipe() {
        // Given
        Recipe recipe = createRecipe(1,
                4,
                "Pasta",
                "Pasta, Tomato, Salt",
                "Boil the pasta, add salt, add tomato",
                false);
        // When
        Recipe savedRecipe = recipeRepository.save(recipe);
        // Then
        Optional<Recipe> optionalRecipe = recipeRepository.findById(savedRecipe.getId());
        assertThat(optionalRecipe)
                .isPresent()
                .hasValueSatisfying(r -> {
                    assertThat(r).isEqualTo(recipe);
                });


    }

    @Test
    void itShouldNotSaveRecipeWhenUserIdIsNull() {

        // Given
        Recipe recipe = createRecipe(null,
                4,
                "Pasta",
                "Pasta, Tomato, Salt",
                "Boil the pasta, add salt, add tomato",
                false);
        // When
        // Then
        assertThatThrownBy(() -> recipeRepository.save(recipe))
                .hasMessageContaining("not-null property references a null or transient value : edson.mutombene.recipe.Recipe.userId")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldNotSaveRecipeWhenNameIsNull() {
        // Given
        Recipe recipe = createRecipe(1,
                4,
                null,
                "Pasta, Tomato, Salt",
                "Boil the pasta, add salt, add tomato",
                false);
        // When
        // Then
        assertThatThrownBy(() -> recipeRepository.save(recipe))
                .hasMessageContaining("not-null property references a null or transient value : edson.mutombene.recipe.Recipe.name")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void isShouldNotSaveRecipeWhenIngredientsIsNull() {
        // Given
        Recipe recipe = createRecipe(1,
                4,
                "Pasta",
                null,
                "Boil the pasta, add salt, add tomato",
                false);
        // When
        // Then
        assertThatThrownBy(() -> recipeRepository.save(recipe))
                .hasMessageContaining("not-null property references a null or transient value : edson.mutombene.recipe.Recipe.ingredients")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void isShouldNotSaveRecipeWhenInstructionsIsNull() {
        // Given
        Recipe recipe = createRecipe(1,
                4,
                "Pasta",
                "Pasta, Tomato, Salt",
                null,
                false);
        // When
        // Then
        assertThatThrownBy(() -> recipeRepository.save(recipe))
                .hasMessageContaining("not-null property references a null or transient value : edson.mutombene.recipe.Recipe.instructions")
                .isInstanceOf(DataIntegrityViolationException.class);
    }


    private Recipe createRecipe(Integer userId, int servings, String name, String ingredients, String instructions, boolean vegetarian) {
        RecipeRegistrationRequest request = new RecipeRegistrationRequest(
                userId,
                servings,
                name,
                ingredients,
                instructions,
                vegetarian
        );
        return Recipe.builder()
                .userId(request.userId())
                .servings(request.servings())
                .name(request.name())
                .ingredients(request.ingredients())
                .instructions(request.instructions())
                .vegetarian(request.vegetarian())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
