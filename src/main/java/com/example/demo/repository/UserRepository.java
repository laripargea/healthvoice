package com.example.demo.repository;

import com.sun.istack.NotNull;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailAndPassword(@NotNull String email, @NotNull String password);

    User findUserByEmail(@NotNull String email);
}