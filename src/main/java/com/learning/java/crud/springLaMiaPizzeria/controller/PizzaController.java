package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String index(
            @RequestParam(name = "keyword", required = false) String search,
            Model model) {
        List<Pizza> pizzas;
        if (search == null || search.isBlank()) {
            // se non ho il parametro search faccio la query generica
            pizzas = pizzaRepository.findAll();    // recupero la lista di libri dal database
        } else {
            // se ho il parametro search faccio la query con filtro
            pizzas = pizzaRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        }
        if (pizzas.isEmpty()) {
            model.addAttribute("message", "Non ci sono pizze nel nostro catalogo");
        }
        // passo la lista delle pizze alla view
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("searchInput", search == null ? "" : search);
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
            //devi fare la pagina di errore in html
        }

    }

    //Controller che gestisce la creazione del form per l'inserimento di una nuova pizza

    @GetMapping("/create")
    public String create(Model model) {
        //aggiungo al model l'attributo pizza contenente una Pizza vuota
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create"; //template con form di creazione pizza
    }

    //Controller che gestisce la post del form coi dati della nuova Pizza

    @PostMapping("/create")
    public String store(@ModelAttribute("pizza") Pizza formPizza) {

        pizzaRepository.save(formPizza);

        return "redirect:/papas";
    }

}
