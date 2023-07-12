package edson.mutombene.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @SequenceGenerator(
            name = "recipe_id_sequence",
            sequenceName = "recipe_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_id_sequence"
    )
    @Schema(name = "Recipe ID", example = "1")
    private Integer id;
    @Schema(name = "User ID", example = "1")
    private Integer userId;
    @Schema(name = "Servings number", example = "4")
    private int servings;
    @Schema(name = "Recipe Name", example = "Margherita Pizza")
    private String name;
    @Schema(name = "Ingredients", example = "Mozzarella 100gr, 1 Onion")
    private String ingredients;
    @Schema(name = "Instruction of how to make", example = "Roll out the pizza dough on a lightly floured surface to your desired thickness. Transfer the dough to a baking sheet or pizza stone...")
    private String instructions;
    @Schema(name = "Is the recipe vegetarian?", example = "false")
    private boolean vegetarian = false;
    @Schema(name = "Date of Recipe creation", example = "2023-07-06 12:10:30")
    private LocalDateTime createdAt;
    @Schema(name = "Date of Recipe updated", example = "2023-07-11 10:30:15")
    private LocalDateTime updatedAt;
}