package com.learning.java.crud.springLaMiaPizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity                         //con @entity sto dicendo che questa classe è una tabella
@Table(name = "pizzas")        //con @table sto dando il nome al plurale alla tabella
public class Pizza {

    @Id                                                     //@id stiamo generando una chiave primaria required
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // vanno scritte insieme a @id
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100)
    private String name;

    @Column(length = 500) // Imposta la lunghezza massima della colonna a 500 caratteri
    @NotBlank(message = "Description is required. Max lenght = 500")
    private String description;
    @NotBlank(message = "Photo is required")
    private String photo;


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;

    private LocalDateTime createdAt;

    //relazione uno a molti con offerte speciali
    @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})
    private List<SpecialOffer> specialOffers = new ArrayList<>();

    //relazione molti a molti con ingredienti
    @ManyToMany
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Pizza() {    //costruttore vuoto
    }

    //getter e setter

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }
}
