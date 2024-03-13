package com.rijo.springjwtnew.jwtnew.repository;

import com.rijo.springjwtnew.jwtnew.entities.Role;
import com.rijo.springjwtnew.jwtnew.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
}
