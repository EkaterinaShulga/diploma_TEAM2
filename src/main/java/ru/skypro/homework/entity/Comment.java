package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "Comments")
public class Comment {
    private Integer author;
    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pk;
    private String text;

    private Long adsPk;

}