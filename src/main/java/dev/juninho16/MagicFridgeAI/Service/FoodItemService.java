package dev.juninho16.MagicFridgeAI.Service;

import dev.juninho16.MagicFridgeAI.DTO.FoodItemDTO;
import dev.juninho16.MagicFridgeAI.Mapper.FoodItemMapper;
import dev.juninho16.MagicFridgeAI.Model.FoodItem;
import dev.juninho16.MagicFridgeAI.Repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private FoodItemRepository foodItemRepository;
    private FoodItemMapper foodItemMapper;

    public FoodItemService(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
    }

    //Salvar
    public FoodItemDTO salvar(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemMapper.map(foodItemDTO); // Converte DTO para Entity
        FoodItem savedItem = foodItemRepository.save(foodItem);
        return foodItemMapper.map(savedItem); // Converte de volta para DTO
    }

    //Listar todos os itens
    public List<FoodItem> listar(){
        return foodItemRepository.findAll();
    }

    //Listar po id
    public FoodItemDTO listarPorId(Long id){
        Optional<FoodItem> foodItemId = foodItemRepository.findById(id);
        return foodItemId.map(foodItemMapper::map).orElse(null);
    }

    //Deletar por id
    public void deletar(Long id){
        foodItemRepository.deleteById(id);
    }

    // Atualização parcial
    public FoodItemDTO atualizarParcialmente(Long id, FoodItemDTO foodItemDTO) {
        Optional<FoodItem> foodItemExistente = foodItemRepository.findById(id);

        if (foodItemExistente.isPresent()) {
            FoodItem foodItem = foodItemExistente.get();

            // Atualiza apenas os campos que não são nulos no DTO
            if (foodItemDTO.getNome() != null) {
                foodItem.setNome(foodItemDTO.getNome());
            }
            if (foodItemDTO.getQuantidade() != null) {
                foodItem.setQuantidade(foodItemDTO.getQuantidade());
            }
            if (foodItemDTO.getValidade() != null) {
                foodItem.setValidade(foodItemDTO.getValidade());
            }
            if (foodItemDTO.getCategoria() != null) {
                foodItem.setCategoria(foodItemDTO.getCategoria());
            }

            FoodItem foodItemAtualizado = foodItemRepository.save(foodItem);
            return foodItemMapper.map(foodItemAtualizado);
        }


        return null;
    }

}
