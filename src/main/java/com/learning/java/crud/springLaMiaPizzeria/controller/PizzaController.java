package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.messages.AlertMessage;
import com.learning.java.crud.springLaMiaPizzeria.messages.AlertMessageType;
import com.learning.java.crud.springLaMiaPizzeria.model.Ingredient;
import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.IngredientRepository;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/papas")
public class PizzaController {

    //dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(
            @RequestParam(name = "keyword", required = false) String search,
            Authentication authentication,
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

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        // passo la lista delle pizze alla view
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("searchInput", search == null ? "" : search);

        return "pizzas/our-pizzas";
    }

    //metodo per ricerca singolo ID
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String pizzaId,
                       Model model) {
        Integer id = Integer.parseInt(pizzaId);
        //cerco su database i dettagli della pizza con quell'ID
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            Pizza pizza = result.get();

            List<Ingredient> ingredients = pizza.getIngredients();


            model.addAttribute("pizza", pizza);
            model.addAttribute("ingredients", ingredients);

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
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create"; //template con form di creazione pizza
    }

    //Controller che gestisce la post del form coi dati della nuova Pizza

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            @RequestParam("selectedIngredientIds") List<Integer> ingredientIds,
            BindingResult bindingResult
    ) {
        //verifico se ci sono stati degli errori
        if (bindingResult.hasErrors()) {
            //se ci sono stati errori allora
            return "pizzas/create"; //ritorno il tamplate del form ma con la pizza precaricata
        }
        //gestisco il timestamp di creazione
        formPizza.setCreatedAt(LocalDateTime.now());

        // Recupero gli ingredienti selezionati dal repository degli ingredienti usando gli ID
        List<Ingredient> selectedIngredients = ingredientRepository.findAllById(ingredientIds);
        // Imposto gli ingredienti selezionati sulla pizza
        formPizza.setIngredients(selectedIngredients);

        pizzaRepository.save(formPizza); //il metodo save fa una create sql se l'oggetto con quella PK non esiste, altrimenti fa update

        return "redirect:/papas";
    }

    //EDIT Controller che gestisce la modifica dei dati del form

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        Pizza pizza = getPizzaById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "/pizzas/create";
    }

    //UPDATE Controller che gestisce la post delle modifiche nel form
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @RequestParam("selectedIngredientIds") List<Integer> ingredientIds,
                         @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        //cerco la pizza per id richiamando il metodo privato getPizzaById
        Pizza pizzaToEdit = getPizzaById(id);  //vecchia versione della pizza

        if (bindingResult.hasErrors()) {
            //mando un messaggio di errore
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.ERROR, "Sorry, but we couldn't apply the modification"));
            return "/pizzas/create";
        }

        //salvo i valori iniziali di creazione per non perderli nella nuova modifica
        formPizza.setId(pizzaToEdit.getId());
        formPizza.setCreatedAt(pizzaToEdit.getCreatedAt());

        // Recupero gli ingredienti selezionati dal repository degli ingredienti usando gli ID
        List<Ingredient> selectedIngredients = ingredientRepository.findAllById(ingredientIds);
        // Imposto gli ingredienti selezionati sulla pizza
        formPizza.setIngredients(selectedIngredients);
        pizzaRepository.save(formPizza);
        //mando una conferma di successo
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza updated!"));
        return "redirect:/papas";

    }

    //Controller Delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Pizza pizzaToDelete = getPizzaById(id);
        pizzaRepository.delete(pizzaToDelete);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza" + pizzaToDelete.getName() + " deleted"));
        return "redirect:/papas";
    }


    //METODO per selezionare l oggetto da database e tirare un eccezione
    private Pizza getPizzaById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza " + id + " not found");
        }
        return result.get();
    }
}
