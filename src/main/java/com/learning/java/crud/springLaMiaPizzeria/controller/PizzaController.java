package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class PizzaController {

    //dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/our-pizzas")
    public String index(Model model) {  // Model è la mappa di attributi che il controller passa alla view

        // recupero la lista delle pizze dal database
        List<Pizza> pizzas = pizzaRepository.findAll();
        // passo la lista delle pizze alla view
        model.addAttribute("pizzas", pizzas);
        return "pizzas/our-pizzas";
    }
}
