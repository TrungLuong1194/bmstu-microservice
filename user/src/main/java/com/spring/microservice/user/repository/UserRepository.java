package com.spring.microservice.user.repository;

import com.spring.microservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(long id);

    User findAllByUsername(String username);
}
