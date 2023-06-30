package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.IngredientRepository;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model,
                        @RequestParam("edit") Optional<Integer> ingredientId,
                        @RequestParam(value = "showIngredientForm", defaultValue = "false")
                        boolean showIngredientForm) {

        List<Ingredient> ingredients;
        ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);

        Ingredient ingredientObj;
        if (ingredientId.isPresent()) {
            Optional<Ingredient> ingredientDb = ingredientRepository.findById(ingredientId.get());
            if (ingredientDb.isPresent()) {
                ingredientObj = ingredientDb.get();
            } else {
                ingredientObj = new Ingredient();
            }
        } else {
            ingredientObj = new Ingredient();
        }

        model.addAttribute("ingredientObj", ingredientObj);
        model.addAttribute("showIngredientForm", showIngredientForm);
        return "ingredients/ingredients";
    }

    //controller per gestire la creazione dei nuovi ingredienti

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/ingredients/ingredients";
        }

        ingredientRepository.save(formIngredient);


        return "redirect:/ingredients";
    }

    //Controller per eliminazione ingredienti

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ingredient ingredientToDelete = result.get();
        for (Pizza pizza : ingredientToDelete.getPizzas()) {
            pizza.getIngredients().remove(ingredientToDelete);
        }
        ingredientRepository.deleteById(id);
        return "redirect:/ingredients";
    }
}
