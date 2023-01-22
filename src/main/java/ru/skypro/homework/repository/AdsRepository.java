package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.AdsEntity;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<AdsEntity, Long> {

    int countById(long pk);

    List<AdsEntity> findByUserId(Long l);

    int countByTitleAndUserId(String title, Long userId);

    Collection<AdsEntity> findAllByAuthorId(long id);

    Optional<AdsEntity> findByIdAndUserId(Long id, Long userId);

    Optional<AdsEntity> findByIdAndUserEmail(long adsId, String userLogin);

    List<AdsEntity> findByUserEmail(String userLogin);
}