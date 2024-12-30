package recipes.recipe;

import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public RecipeDTO toDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections(),
                recipe.getDate(),
                recipe.getUserId());
    }

    public Recipe toRecipe(RecipeDTO dto) {
        return new Recipe(
                null,
                dto.getName(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getIngredients(),
                dto.getDirections(),
                dto.getDate(),
                dto.getUserId());
    }
}
