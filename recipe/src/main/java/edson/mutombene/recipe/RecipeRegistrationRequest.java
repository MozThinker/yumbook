package edson.mutombene.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecipeRegistrationRequest(
        @Schema(name = "User ID", example = "1")
        Integer userId,
        @Schema(name = "Servings number", example = "4")
        int servings,
        @Schema(name = "Recipe Name", example = "Margherita Pizza")
        String name,
        @Schema(name = "Ingredients", example = "Mozzarella 100gr, 1 Onion")
        String ingredients,
        @Schema(name = "Instruction of how to make", example = "Roll out the pizza dough on a lightly floured surface to your desired thickness. Transfer the dough to a baking sheet or pizza stone...")
        String instructions,
        @Schema(name = "Is the recipe vegetarian?", example = "false")
        boolean vegetarian) {
}