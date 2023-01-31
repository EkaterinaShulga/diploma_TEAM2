package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long pk;
    private long price;
    private String title;
    private String description;

    @ManyToOne
    private User user;


   // @OneToMany()
  // @JsonIgnore
  //  List<Comment> comment;
}
