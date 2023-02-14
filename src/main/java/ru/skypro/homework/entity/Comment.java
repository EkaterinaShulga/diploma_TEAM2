package ru.skypro.homework.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * имеет связь по внешнему ключу  с {@code User(Entity)} и с {@code Ads(Entity)}
 */

@Entity
@Table(name = "Comments")
public class Comment {

    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String text;

    @ManyToOne()
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ads_pk")
    private Ads ads;


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        LocalDateTime localDateTime = LocalDateTime.now();
        createdAt = localDateTime.toString();
        this.createdAt = createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(createdAt, comment.createdAt) && Objects.equals(pk, comment.pk) && Objects.equals(text, comment.text) && Objects.equals(user, comment.user) && Objects.equals(ads, comment.ads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, pk, text, user, ads);
    }
}