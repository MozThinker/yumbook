import edson.mutombene.recipe.Recipe
import edson.mutombene.recipe.RecipeApplication
import edson.mutombene.recipe.RecipeController
import edson.mutombene.recipe.RecipeRegistrationRequest
import edson.mutombene.recipe.RecipeRepository
import edson.mutombene.recipe.RecipeService
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@EnableSharedInjection
@SpringBootTest(classes = RecipeApplication.class, webEnvironment = RANDOM_PORT)
abstract class BaseSpeificationTest extends Specification {

    @Autowired (required = false)
    public RecipeController recipeController

    @Autowired (required = false)
    public Recipe recipe

    @Autowired (required = false)
    public RecipeService recipeService

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        recipeController
    }
}