package com.afifa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    public User(String email, String password, List<Booking> bookings, String name, Long id,String role) {
        this.email = email;
        this.password = password;
        this.bookings = bookings;
        this.name = name;
        this.id = id;
        this.role = role;
    }
    public User(){

    }

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "Enter a valid email")
   private String email;
    @NotBlank(message = "Password is required ")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();
    private String role;
}
