package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Ads")
public class Ads {

    private long author;
    private String image;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;
    private long price;
    private String title;


 //   @OneToMany
 //   List<Comment> comment;
}
