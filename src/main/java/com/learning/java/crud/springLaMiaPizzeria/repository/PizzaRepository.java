package com.learning.java.crud.springLaMiaPizzeria.repository;

import com.learning.java.crud.springLaMiaPizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

//definisce un'interfaccia chiamata PizzaRepository
//che estende l'interfaccia JpaRepository di Spring Data JPA
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
