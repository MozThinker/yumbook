package edson.mutombene.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
    private Integer id;
    private Integer userId;
    private int servings;
    private String name;
    private String ingredients;
    private String instructions;
    private boolean vegetarian;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
