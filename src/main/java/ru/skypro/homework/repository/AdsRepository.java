package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    Ads findAdsByPk(Long pk);
    int countByTitleAndUserId(String title, Long userId);

    Optional<Ads> findByPkAndUserId(Long id, Long userId);

    Optional<Ads> findByPkAndUserEmail(long adsId, String userLogin);

    @Query("SELECT a FROM Ads a WHERE a.title LIKE %:title%")
    List<Ads> searchByTitle(@Param("title") String title);

    List<Ads> findByUserEmail(String userLogin);
}
