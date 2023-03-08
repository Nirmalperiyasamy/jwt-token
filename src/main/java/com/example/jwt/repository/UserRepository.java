package com.example.jwt.repository;

import com.example.jwt.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
    UserDao findByUsername(String username);

    boolean existsByUsername(String username);

}
