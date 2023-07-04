package com.learning.java.crud.springLaMiaPizzeria.api;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;

    // ottenere la lista di pizze (dobbiamo anche poterle filtrare per titolo)
    @GetMapping
    public List<Pizza> getPizzas(@RequestParam(name = "keyword", required = false) String search) {

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

    //leggere i dettagli di una singola pizza

    @GetMapping("/{pizzaId}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Integer pizzaId) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
