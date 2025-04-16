package dev.juninho16.MagicFridgeAI.Mapper;

import dev.juninho16.MagicFridgeAI.DTO.FoodItemDTO;
import dev.juninho16.MagicFridgeAI.Model.FoodItem;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class FoodItemMapper {

    // Converte FoodItem para FoodItemDTO
    public FoodItemDTO map(FoodItem foodItem) {
        FoodItemDTO dto = new FoodItemDTO();
        dto.setId(foodItem.getId());
        dto.setNome(foodItem.getNome());
        dto.setCategoria(foodItem.getCategoria());
        dto.setQuantidade(foodItem.getQuantidade());
        dto.setValidade(foodItem.getValidade());
        return dto;
    }

    // Converte FoodItemDTO para FoodItem
    public FoodItem map(FoodItemDTO dto) {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(dto.getId());
        foodItem.setNome(dto.getNome());
        foodItem.setCategoria(dto.getCategoria());
        foodItem.setQuantidade(dto.getQuantidade());
        foodItem.setValidade(dto.getValidade());
        return foodItem;
    }
}
