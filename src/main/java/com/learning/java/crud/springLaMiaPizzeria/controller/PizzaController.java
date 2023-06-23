package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/papas")
public class PizzaController {

    //dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {  // Model è la mappa di attributi che il controller passa alla view
        // recupero la lista delle pizze dal database
        List<Pizza> pizzas = pizzaRepository.findAll();
        if (pizzas.isEmpty()) {
            model.addAttribute("message", "Non ci sono pizze nel nostro catalogo");
        }
        // passo la lista delle pizze alla view
        model.addAttribute("pizzas", pizzas);
        return "pizzas/our-pizzas";
    }

    //metodo per ricerca singolo ID
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer pizzaId, Model model) {
        //cerco su database i dettagli della pizza con quell'ID
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "pizzas/details";
        } else {
            //ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza" + pizzaId + "not found");
        }


    }

}
