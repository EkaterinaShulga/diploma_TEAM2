package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "SELECT * FROM users WHERE password = :password", nativeQuery = true)
    User getUserByPassword(String password);

    @Query(value = "SELECT * FROM users WHERE username = :userLogin", nativeQuery = true)
    Optional<User> findByEmail(String userLogin);
}