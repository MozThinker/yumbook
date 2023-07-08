package edson.mutombene.recipe;

public record RecipeRegistrationRequest(
    Integer userId,
    int servings,
    String name,
    String ingredients,
    String instructions,
    boolean vegetarian) {
}