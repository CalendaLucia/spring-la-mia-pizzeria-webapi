package com.learning.java.crud.springLaMiaPizzeria.api;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/papas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> index(@RequestParam(name = "keyword", required = false) String search) {

        List<Pizza> pizzas;
        if (search == null || search.isBlank()) {
            // se non ho il parametro search faccio la query generica
            pizzas = pizzaRepository.findAll();    // recupero la lista di libri dal database
        } else {
            // se ho il parametro search faccio la query con filtro
            pizzas = pizzaRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        }
        return pizzas;
    }

}
