package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.User.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    Collection<User> getAll();

    @Query(value = "SELECT * FROM users WHERE password = :password", nativeQuery = true)
    User getUserByPassword(String password);
    
    public Optional<UserEntity> findByEmail(String userLogin);
}
