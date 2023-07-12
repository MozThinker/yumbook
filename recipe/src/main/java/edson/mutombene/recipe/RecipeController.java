package edson.mutombene.recipe;

import edson.mutombene.recipe.utils.PagingHeaders;
import edson.mutombene.recipe.utils.PagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.NotLikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "This endpoint is to create a Recipe in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Created a new Recipe in DB",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409",
                    description = "Could not create the Recipe, this ID is already associated with other Recipe",
            content = @Content)
    })
    @Transactional
    @PostMapping()
    public ResponseEntity<?> registerRecipe(@RequestBody RecipeRegistrationRequest recipeRegistrationRequest) {
        log.info("New recipe registration {}", recipeRegistrationRequest);
        return new ResponseEntity<>(recipeService.registerRecipe(recipeRegistrationRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is to get a Recipe by Id from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Return a Recipe from DB",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Recipe doesn't exist",
            content = @Content)
    })
    @Transactional
    @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@Cacheable(value = "carSearchCache", key = "#id")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") @Parameter(name = "id", description = "Recipe id", example = "1") Integer recipeId) {
        log.info("Fetching recipe by ID: {}", recipeId);
        Optional<Recipe> recipe = recipeService.getRecipeById(recipeId);

        if (recipe.isPresent())
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        return new ResponseEntity<>("Recipe doesn't exist", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "This endpoint is to fetch a list of Recipes from DB, based on json of one or more criteria and sort")
    @ApiResponse(responseCode = "200",
            description = "Return a list of Recipes from DB",
            content = {@Content(mediaType = "application/json")})
    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "recipeSearchCache", key = "{#root.methodName, #spec, #sort, #headers}")
    public ResponseEntity<List<Recipe>> get(
            @And({
                    @Spec(path = "id", params = "id", spec = Equal.class),
                    @Spec(path = "userId", params = "userId", spec = Equal.class),
                    @Spec(path = "servings", params = "servings", spec = Equal.class),
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "ingredients", params = "ingredientsInclude", spec = LikeIgnoreCase.class),
                    @Spec(path = "ingredients", params = "ingredientsExclude", spec = NotLikeIgnoreCase.class),
                    @Spec(path = "instructions", params = "instructions", spec = LikeIgnoreCase.class),
                    @Spec(path = "vegetarian", params = "vegetarian", spec = Equal.class),
            }) @Parameter(description = "userId & Servings", example = "{\n" +
                    "   \"userId\":\"5\",\n" +
                    "   \"servings\":\"4\"\n" +
                    "}") Specification<Recipe> spec,
            @Parameter(description = "userId & Servings", example = "{\n" +
                    "  \"sort\": [\n" +
                    "    \"id,desc\"\n" +
                    "  ]\n" +
                    "}") Sort sort,
            @RequestHeader HttpHeaders headers) {
        log.info("Fetching recipes with specifications and sorting: {}, {}, {}", spec, sort, headers);
        final PagingResponse response = recipeService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

    @Operation(summary = "This endpoint is to update a Recipe on DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recipe updated successfully!",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Recipe doesn't exist",
                    content = @Content)
    })
    @Transactional
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRecipe(@PathVariable("id") @Parameter(name = "id", description = "Recipe id", example = "1") Integer recipeId, @RequestBody Recipe updateRecipe) {
        log.info("Updating recipe with ID: {}, Updated recipe: {}", recipeId, updateRecipe);
        boolean recipeUpdated = recipeService.updateRecipe(recipeId, updateRecipe);

        if (recipeUpdated) {
            return new ResponseEntity<>("Recipe updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "This endpoint is to delete a Recipe from DB by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recipe deleted from DB",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Recipe doesn't exist",
                    content = @Content)
    })
    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") @Parameter(name = "id", description = "Recipe id", example = "1") Integer recipeId) {
        log.info("Deleting recipe with ID: {}", recipeId);
        boolean recipeDeleted = recipeService.deleteRecipeById(recipeId);

        if (recipeDeleted) {
            return new ResponseEntity<>("Recipe deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }
    }

    public RecipeService recipeService() {
        return recipeService;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RecipeController) obj;
        return Objects.equals(this.recipeService, that.recipeService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeService);
    }

    @Override
    public String toString() {
        return "RecipeController[" +
                "recipeService=" + recipeService + ']';
    }
}