package edson.mutombene.recipe;

import edson.mutombene.recipe.utils.PagingHeaders;
import edson.mutombene.recipe.utils.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public record RecipeService(RecipeRepository recipeRepository) {

    public Recipe registerRecipe (RecipeRegistrationRequest request) {
        Recipe recipe = Recipe.builder()
                .userId(request.userId())
                .servings(request.servings())
                .name(request.name())
                .ingredients(request.ingredients())
                .instructions(request.instructions())
                .vegetarian(request.vegetarian())
                .createdAt(LocalDateTime.now())
                .build();

        return recipeRepository.save(recipe);
    }

    Optional<Recipe> getRecipeById(Integer id){
        return recipeRepository.findById(id);
    }

    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Recipe> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Recipe> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Recipe> spec, Pageable pageable) {
        Page<Recipe> page = recipeRepository.findAll(spec, pageable);
        List<Recipe> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Recipe> get(Specification<Recipe> spec, Sort sort) {
        return recipeRepository.findAll(spec, sort);
    }

    public boolean updateRecipe(Integer id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setUserId(updatedRecipe.getUserId());
            existingRecipe.setServings(updatedRecipe.getServings());
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());
            existingRecipe.setVegetarian(updatedRecipe.isVegetarian());
            existingRecipe.setUpdatedAt(LocalDateTime.now());
            recipeRepository.save(existingRecipe);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteRecipe(Integer id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            recipeRepository.delete(optionalRecipe.get());
            return true;
        } else {
            return false;
        }
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
}