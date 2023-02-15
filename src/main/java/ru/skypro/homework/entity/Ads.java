package ru.skypro.homework.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private long price;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "ads", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Comment> comments;
    @OneToMany(mappedBy = "ads", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> image;


}
