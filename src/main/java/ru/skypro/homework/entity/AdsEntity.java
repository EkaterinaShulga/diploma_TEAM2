package ru.skypro.homework.entity;
import lombok.Data;
import ru.skypro.homework.entity.User.User;

import javax.persistence.*;

@Entity
@Table(name = "Ads", schema = "public")
@Data
public class AdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private String image;
    private int price;
    private String title;
    private String description;
}
