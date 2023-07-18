package edson.mutombene.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Schema(name = "id", example = "1")
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "userId", example = "1")
    private Integer userId;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "servings", example = "4")
    private int servings;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "name", example = "Margherita Pizza")
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "ingredients", example = "Mozzarella 100gr, 1 Onion")
    private String ingredients;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "instruction", example = "Roll out the pizza dough on a lightly floured surface to your desired thickness. Transfer the dough to a baking sheet or pizza stone...")
    private String instructions;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "vegetarian", example = "false")
    private boolean vegetarian = false;

    @NotBlank
    @Column(nullable = false)
    @Schema(name = "createdAt", example = "2023-07-06 12:10:30")
    private LocalDateTime createdAt;

    @Schema(name = "updatedAt", example = "2023-07-11 10:30:15")
    private LocalDateTime updatedAt;
}