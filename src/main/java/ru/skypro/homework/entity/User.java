package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
@Data
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String regDate;
    private String city;
    private String password;
    private Role role;
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Ads> allAds;
    public User() {
    }

    public User(Long id, String firstName, String lastName, String phone, String email, String regDate, String city,  String password, Role role, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.city = city;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public User(String firstName, String lastName, String phone, String email, String regDate, String city, String password, Role role, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.city = city;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone)
                && Objects.equals(email, user.email) && Objects.equals(regDate, user.regDate)
                && Objects.equals(city, user.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, email, regDate, city);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", regDate='" + regDate + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
