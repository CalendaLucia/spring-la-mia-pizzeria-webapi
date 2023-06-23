package com.learning.java.crud.springLaMiaPizzeria.repository;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//definisce un'interfaccia chiamata PizzaRepository
//che estende l'interfaccia JpaRepository di Spring Data JPA
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    // metodo per cercare i libri il cui titolo o autore contiene una stringa
    List<Pizza> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name,
                                                                                String description);
}
