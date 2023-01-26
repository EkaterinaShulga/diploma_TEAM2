package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    Ads findAdsByPk(Long pk);
    int countByTitleAndUserId(String title, Long userId);

    Optional<Ads> findByPkAndUserId(Long id, Long userId);

    Optional<Ads> findByPkAndUserEmail(long adsId, String userLogin);

    List<Ads> findByUserEmail(String userLogin);
}
