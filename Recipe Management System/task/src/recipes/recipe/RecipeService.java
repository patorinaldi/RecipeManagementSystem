package recipes.recipe;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository repository;
    private RecipeMapper mapper;

    public RecipeService(RecipeRepository repository, RecipeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public RecipeDTO getRecipeById(long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public long addRecipe(RecipeDTO dto, long userId) {
        dto.setDate(LocalDateTime.now());
        dto.setUserId(userId);
        return repository.save(mapper.toRecipe(dto)).getId();
    }

    public boolean removeRecipeById(long id, long userId) {
        Optional<Recipe> recipe = repository.findById(id);
        if (recipe.isPresent() && recipe.get().getUserId().equals(userId)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateRecipeById(long id, RecipeDTO dto) {
        Optional<Recipe> recipe = repository.findById(id);
        if(recipe.isPresent() && recipe.get().getUserId().equals(dto.getUserId())) {
            Recipe recipeToUpdate = mapper.toRecipe(dto);
            recipeToUpdate.setId(id);
            recipeToUpdate.setDate(LocalDateTime.now());
            repository.save(recipeToUpdate);
            return true;
        }
        return false;
    }

    public List<?> getRecipesByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<?> getRecipesByName(String name) {
        return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public boolean existsById(long id) {
        return repository.existsById(id);
    }
}
