package com.learning.java.crud.springLaMiaPizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity                         //con @entity sto dicendo che questa classe è una tabella
@Table(name = "pizzas")        //con @table sto dando il nome al plurale alla tabella
public class Pizza {

    @Id                                                     //@id stiamo generando una chiave primaria required
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // vanno scritte insieme a @id
    private Integer id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    private String description;
    private String photo;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime createdAt;

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
}
