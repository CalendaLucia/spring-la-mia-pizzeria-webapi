package com.learning.java.crud.springLaMiaPizzeria.service;

import com.learning.java.crud.springLaMiaPizzeria.dto.PizzaDto;
import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import com.learning.java.crud.springLaMiaPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


//questa classe serve a scrivere tutti i metodi che trasporteremo nel PizzaController
// si fa per pulizia del codice refactoring del codice
@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepository; // dovremo poi nel controller passare pizzaservice


    //metodo che restituisce una pizza preso per id
    public Pizza getById(Integer id) throws ResponseStatusException {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()) {
            return pizzaOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    //metodo per creare una nuova pizza

    public Pizza create(Pizza pizza) {

        //creo la pizza da salvare
        Pizza pizzaToPersist = new Pizza();
        pizzaToPersist.setCreatedAt(LocalDateTime.now());
        pizzaToPersist.setName(pizza.getName());
        pizzaToPersist.setDescription(pizza.getDescription());
        pizzaToPersist.setPrice(pizza.getPrice());
        pizzaToPersist.setPhoto(pizza.getPhoto());
        pizzaToPersist.setIngredients(pizza.getIngredients());

        return pizzaRepository.save(pizzaToPersist);

    }

    //metodo che crea una nuova pizza a partire da un PizzaDto
    public Pizza create(PizzaDto formPizza) {
        //coverto il pizzadto in una pizza
        Pizza pizza = mapFormPizzaToPizza(formPizza);
        return create(pizza); //salvo la pizza
    }

    private Pizza mapFormPizzaToPizza(PizzaDto formPizza) {
        //creo una nuova pizza vuota
        Pizza pizza = new Pizza();
        //copio i campi con corrispondenza uguale
        pizza.setId(formPizza.getId());
        pizza.setName(formPizza.getName());
        pizza.setDescription(formPizza.getDescription());
        pizza.setPrice(formPizza.getPrice());
        pizza.setIngredients(formPizza.getIngredients());
        //converto il campo cover
       
        pizza.setPhoto(multipartFileToByteArray(formPizza.getCoverFile()));


        return pizza;
    }


    // Metodo privato per convertire un oggetto MultipartFile in un array di byte
    private byte[] multipartFileToByteArray(MultipartFile mpf) {
        byte[] bytes = null;
        if (mpf != null && !mpf.isEmpty()) {
            try {
                // Ottiene l'array di byte dal MultipartFile
                bytes = mpf.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bytes;
    }

}
