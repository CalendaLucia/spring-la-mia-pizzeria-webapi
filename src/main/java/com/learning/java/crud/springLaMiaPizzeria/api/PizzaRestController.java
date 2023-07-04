package com.learning.java.crud.springLaMiaPizzeria.api;

import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.IngredientRepository;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    IngredientRepository ingredientRepository;

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

    @GetMapping("{pizzaId}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Integer pizzaId) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);

        if (result.isPresent()) {
            Pizza pizza = result.get();
            List<Ingredient> ingredients = pizza.getIngredients();
            pizza.setIngredients(ingredients);
            return ResponseEntity.ok(pizza);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //creare una nuova pizza
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        Pizza newPizza = new Pizza();
        newPizza.setCreatedAt(LocalDateTime.now());
        newPizza.setName(pizza.getName());
        newPizza.setPrice(pizza.getPrice());
        newPizza.setDescription(pizza.getDescription());
        newPizza.setIngredients(pizza.getIngredients());
        newPizza.setPhoto(pizza.getPhoto());


        return pizzaRepository.save(newPizza);
    }

    // modificare una pizza esistente

    @PutMapping("/{pizzaId}")
    public Pizza update(
            @Valid
            @RequestBody Pizza pizza,
            @PathVariable Integer pizzaId) {
        pizza.setId(pizzaId);
        return pizzaRepository.save(pizza);

    }

    @DeleteMapping("/{pizzaId}")
    public void delete(@PathVariable Integer pizzaId) {
        pizzaRepository.deleteById(pizzaId);
    }
}
