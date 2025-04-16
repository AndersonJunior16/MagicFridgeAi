package dev.juninho16.MagicFridgeAI.Controller;

import dev.juninho16.MagicFridgeAI.DTO.FoodItemDTO;
import dev.juninho16.MagicFridgeAI.Mapper.FoodItemMapper;
import dev.juninho16.MagicFridgeAI.Service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService foodItemService;
    private final FoodItemMapper mapper;

    public FoodItemController(FoodItemService foodItemService, FoodItemMapper mapper) {
        this.foodItemService = foodItemService;
        this.mapper = mapper;
    }

    // POST - Criar item
    @PostMapping("/criar")
    public ResponseEntity<FoodItemDTO> criar(@RequestBody FoodItemDTO foodItemDTO) {
        FoodItemDTO salvoDTO = foodItemService.salvar(foodItemDTO);
        return ResponseEntity.ok(salvoDTO);
    }

    // GET - Listar todos (convertendo para DTO)
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItemDTO>> listar() {
        List<FoodItemDTO> foodItems = foodItemService.listar().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
        return ResponseEntity.ok(foodItems);
    }

    // GET - Listar por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        FoodItemDTO foodItemDTO = foodItemService.listarPorId(id);
        if (foodItemDTO != null) {
            return ResponseEntity.ok(foodItemDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A comida de id " + id + " não existe");
        }
    }

    // DELETE - Remover item
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        FoodItemDTO foodItem = foodItemService.listarPorId(id);
        if (foodItem != null) {
            foodItemService.deletar(id);
            return ResponseEntity.ok("Item: " + foodItem.getNome() + " deletado!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A comida de id " + id + " não existe.");
        }
    }

    // PATCH - Atualização parcial
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarParcialmente(@PathVariable Long id,
                                                   @RequestBody FoodItemDTO foodItemDTO) {
        FoodItemDTO atualizado = foodItemService.atualizarParcialmente(id, foodItemDTO);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A comida de id " + id + " não existe");
        }
    }
}