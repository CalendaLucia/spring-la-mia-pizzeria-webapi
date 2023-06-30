package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import com.learning.java.crud.springLaMiaPizzeria.repository.IngredientRepository;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {

        List<Ingredient> ingredients;

        ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);

        return "ingredients/ingredients";
    }
}
