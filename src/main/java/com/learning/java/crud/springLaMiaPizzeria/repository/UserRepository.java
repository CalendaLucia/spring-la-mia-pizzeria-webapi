package com.learning.java.crud.springLaMiaPizzeria.repository;

import com.learning.java.crud.springLaMiaPizzeria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //metodo che recupero uno User a partire dall'email (ovvero username)
    Optional<User> findByEmail(String email);
}
