package dev.juninho16.MagicFridgeAI.Repository;

import dev.juninho16.MagicFridgeAI.Model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
