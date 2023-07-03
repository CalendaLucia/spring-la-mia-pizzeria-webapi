package com.learning.java.crud.springLaMiaPizzeria.repository;

import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
