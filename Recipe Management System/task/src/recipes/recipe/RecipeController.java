package recipes.recipe;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import recipes.user.UserAdapter;

import java.util.Map;

@RestController
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(service.getRecipeById(id));
        } catch (EntityNotFoundException ee) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> postRecipe(@Valid @RequestBody RecipeDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAdapter userAdapter = (UserAdapter) authentication.getPrincipal();
        long id = service.addRecipe(dto, userAdapter.getId());
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAdapter userAdapter = (UserAdapter) authentication.getPrincipal();
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else if (service.removeRecipeById(id, userAdapter.getId())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> putRecipe(@PathVariable("id") long id, @Valid @RequestBody RecipeDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAdapter userAdapter = (UserAdapter) authentication.getPrincipal();
        dto.setUserId(userAdapter.getId());
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else if (service.updateRecipeById(id, dto)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<?> searchRecipes(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "name", required = false) String name) {
        if (category != null && name == null) {
            return ResponseEntity.ok(service.getRecipesByCategory(category));
        } else if (name != null && category == null) {
            return ResponseEntity.ok(service.getRecipesByName(name));
        }
        return ResponseEntity.badRequest().build();
    }
}
