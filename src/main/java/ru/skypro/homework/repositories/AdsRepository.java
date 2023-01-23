package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    Ads findAdsByPk(Long pk);
}
