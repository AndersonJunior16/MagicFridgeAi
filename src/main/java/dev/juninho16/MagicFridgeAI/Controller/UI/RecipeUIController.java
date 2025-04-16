package dev.juninho16.MagicFridgeAI.Controller.UI;

import dev.juninho16.MagicFridgeAI.Model.FoodItem;
import dev.juninho16.MagicFridgeAI.Service.ChatGptService;
import dev.juninho16.MagicFridgeAI.Service.FoodItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/recipes")
public class RecipeUIController {

    private final FoodItemService foodItemService;
    private final ChatGptService chatGptService;

    public RecipeUIController(FoodItemService foodItemService, ChatGptService chatGptService) {
        this.foodItemService = foodItemService;
        this.chatGptService = chatGptService;
    }

    // Página para gerar receitas
    @GetMapping("/generate")
    public String generateRecipe(Model model) {
        model.addAttribute("foodItems", foodItemService.listar());
        return "recipes/generate";
    }

    // Exibir receita gerada
    @GetMapping("/result")
    public String showRecipe(Model model) {
        String recipe = chatGptService.generateRecipe(foodItemService.listar()).block(); // Usando block() para simplificar (em produção, use reactive templates)
        model.addAttribute("recipe", recipe);
        return "recipes/result";
    }
}