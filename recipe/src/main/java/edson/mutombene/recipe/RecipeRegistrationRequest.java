package edson.mutombene.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecipeRegistrationRequest(
        @Schema(name = "userId", example = "1")
        Integer userId,
        @Schema(name = "servings", example = "4")
        int servings,
        @Schema(name = "name", example = "Margherita Pizza")
        String name,
        @Schema(name = "ingredients", example = "Mozzarella 100gr, 1 Onion")
        String ingredients,
        @Schema(name = "instruction", example = "Roll out the pizza dough on a lightly floured surface to your desired thickness. Transfer the dough to a baking sheet or pizza stone...")
        String instructions,
        @Schema(name = "vegetarian", example = "false")
        boolean vegetarian) {
}