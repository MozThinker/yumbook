import edson.mutombene.recipe.Recipe
import edson.mutombene.recipe.RecipeRegistrationRequest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class RecipeControllerTest extends BaseSpeificationTest{



    def "registerRecipe should return created recipe"() {
        given:
        def request = new RecipeRegistrationRequest(
                4,
                5,
                "Omelete specal",
                "5 Ovos, 50g de queijo mozzarela",
                "Em um recipiente...",
                true)

        when:
        def registeredRecipe = recipeService.registerRecipe(request)

        then:
        registeredRecipe.userId == 4
        registeredRecipe.servings == 5
        registeredRecipe.name == "Omelete specal"
        registeredRecipe.ingredients == "5 Ovos, 50g de queijo mozzarela"
        registeredRecipe.instructions == "Em um recipiente..."
        registeredRecipe.isVegetarian()

    }

    def "getRecipeById should return a specific recipe"() {
        given:
        def request = new RecipeRegistrationRequest(
                4,
                5,
                "Omelete specal",
                "5 Ovos, 50g de queijo mozzarela",
                "Em um recipiente...",
                true)

        when:
        def registeredRecipe = recipeService.registerRecipe(request)
        def result = recipeService.getRecipeById(registeredRecipe.id)

        then:
        result.get().id == 2
    }

    def "updateRecipe should update a specific recipe"() {
        given:
        def recipe = new Recipe(id: 2,
                userId: 4,
                servings: 5,
                name: "Omelete special",
                ingredients: "5 Ovos, 50g de queijo mozzarela",
                instructions: "Em um recipiente...",
                vegetarian: false)

        when:
        def registeredRecipe = recipeService.updateRecipe(2,recipe)

        then:
        registeredRecipe
        !recipeService.getRecipeById(2).get().vegetarian
    }

    def "deleteRecipeById should delete a specific recipe"() {
        given:
        def recipeId = 2

        when:
        def registeredRecipe = recipeService.deleteRecipeById(recipeId)

        then:
        registeredRecipe
        !recipeService.getRecipeById(2).isPresent()
    }

}