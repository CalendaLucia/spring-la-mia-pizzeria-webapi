package com.learning.java.crud.springLaMiaPizzeria.controller;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.model.SpecialOffer;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import com.learning.java.crud.springLaMiaPizzeria.repository.SpecialOfferRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    SpecialOfferRepository specialOfferRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        // Creo una nuova offerta speciale
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setStartDate(LocalDate.now());

        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with ID " + pizzaId + " not found");
        }
        specialOffer.setPizza(pizza.get());

        model.addAttribute("specialOffer", specialOffer);

        return "specialOffer/form";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("specialOffer") SpecialOffer formSpecialOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "specialOffer/form";
        }
        specialOfferRepository.save(formSpecialOffer);

        return "redirect:/papas/" + formSpecialOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(id);
        if (specialOffer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("specialOffer", specialOffer.get());
        return "specialOffer/form";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("specialOffer") SpecialOffer formSpecialOffer, BindingResult bindingResult) {
        Optional<SpecialOffer> specialOfferToEdit = specialOfferRepository.findById(id);
        if (specialOfferToEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        formSpecialOffer.setId(id);
        specialOfferRepository.save(formSpecialOffer);
        return "redirect:/papas/" + formSpecialOffer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // verifico che il borrowing esiste
        Optional<SpecialOffer> specialOfferToDelete = specialOfferRepository.findById(id);
        if (specialOfferToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // se esiste lo cancello
        specialOfferRepository.delete(specialOfferToDelete.get());
        // faccio una redirect alla pagina di dettaglio del libro
        return "redirect:/papas/" + specialOfferToDelete.get().getPizza().getId();
    }
}



