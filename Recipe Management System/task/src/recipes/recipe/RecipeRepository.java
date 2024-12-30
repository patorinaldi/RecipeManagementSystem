package recipes.recipe;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<?> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<?> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
