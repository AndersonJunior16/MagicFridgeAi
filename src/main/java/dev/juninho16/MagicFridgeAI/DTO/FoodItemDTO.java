package dev.juninho16.MagicFridgeAI.DTO;
import dev.juninho16.MagicFridgeAI.Model.FoodCategory;

import java.time.LocalDate;

public class FoodItemDTO {
    private Long id;
    private String nome;
    private FoodCategory categoria;
    private Integer quantidade;
    private LocalDate validade;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public FoodCategory getCategoria() {
        return categoria;
    }

    public void setCategoria(FoodCategory categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
}