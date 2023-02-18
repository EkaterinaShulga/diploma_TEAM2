package ru.skypro.homework.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Comment - entity <br>
 * linked by foreign keys with {@code User(Entity)} and {@code Ads(Entity)}
 */

@Entity
@Table(name = "Comments")
public class Comment {

    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ads_id")
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
        return id;
    }

    public void setPk(Integer id) {
        this.id = id;
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
        return Objects.equals(createdAt, comment.createdAt) && Objects.equals(id, comment.id) && Objects.equals(text, comment.text) && Objects.equals(user, comment.user) && Objects.equals(ads, comment.ads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, id, text, user, ads);
    }
}