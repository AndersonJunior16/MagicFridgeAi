package dev.juninho16.MagicFridgeAI.Controller.UI;

import dev.juninho16.MagicFridgeAI.DTO.FoodItemDTO;
import dev.juninho16.MagicFridgeAI.Mapper.FoodItemMapper;
import dev.juninho16.MagicFridgeAI.Model.FoodCategory;
import dev.juninho16.MagicFridgeAI.Model.FoodItem;
import dev.juninho16.MagicFridgeAI.Service.ChatGptService;
import dev.juninho16.MagicFridgeAI.Service.FoodItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ui/food")
public class FoodItemUIController {

    private final FoodItemService foodItemService;
    private final ChatGptService chatGptService;
    private final FoodItemMapper mapper;

    public FoodItemUIController(FoodItemService foodItemService,
                                ChatGptService chatGptService,
                                FoodItemMapper mapper) {
        this.foodItemService = foodItemService;
        this.chatGptService = chatGptService;
        this.mapper = mapper;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("foodItems", foodItemService.listar());
        model.addAttribute("foodItem", new FoodItemDTO()); // Para o formulário
        model.addAttribute("categories", FoodCategory.values());
        return "food/dashboard";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute FoodItemDTO foodItemDTO,
                           RedirectAttributes redirectAttributes) {
        foodItemService.salvar(foodItemDTO);
        redirectAttributes.addFlashAttribute("success", "Item salvo com sucesso!");
        return "redirect:/ui/food";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            FoodItemDTO item = foodItemService.listarPorId(id);
            foodItemService.deletar(id);
            redirectAttributes.addFlashAttribute("success",
                    "Item '" + item.getNome() + "' deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erro ao deletar item: " + e.getMessage());
        }
        return "redirect:/ui/food";
    }

    @PostMapping("/generate-recipe-with-details")
    public String generateRecipeWithDetails(@RequestParam(value = "recipeDetails", required = false) String recipeDetails,
                                            RedirectAttributes redirectAttributes) {
        List<FoodItem> items = foodItemService.listar();
        String recipe = chatGptService.generateRecipeWithDetails(items, recipeDetails).block();
        redirectAttributes.addFlashAttribute("recipe", recipe);
        return "redirect:/ui/food";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FoodItemDTO foodItemDTO = foodItemService.listarPorId(id);
        model.addAttribute("foodItem", foodItemDTO);
        model.addAttribute("categories", FoodCategory.values());
        return "food/edit-form"; // Nova página de edição
    }

    @GetMapping("/generate-recipe")
    public String generateRecipe(RedirectAttributes redirectAttributes) {
        List<FoodItem> items = foodItemService.listar();
        String recipe = chatGptService.generateRecipe(items).block();
        redirectAttributes.addFlashAttribute("recipe", recipe);
        return "redirect:/ui/food";
    }
}