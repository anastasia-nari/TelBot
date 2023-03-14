package com.example.telegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name="authors")
public class AuthorEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastname;
    private String surname;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<BookEntity> book;
}