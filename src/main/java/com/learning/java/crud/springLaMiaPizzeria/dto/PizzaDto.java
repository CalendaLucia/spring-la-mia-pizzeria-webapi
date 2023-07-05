package com.learning.java.crud.springLaMiaPizzeria.dto;

import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PizzaDto {

    private Integer id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100)
    private String name;


    @NotBlank(message = "Description is required. Max lenght = 500")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;

    private List<Ingredient> ingredients;
    private MultipartFile coverFile;  //qui cambiamo il tipo di dato da byte a MultipartFile

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    //GETTER E SETTER
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(MultipartFile coverFile) {
        this.coverFile = coverFile;
    }
}
