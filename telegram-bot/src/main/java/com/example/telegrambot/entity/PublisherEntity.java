package com.example.telegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "publishings")
public class PublisherEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Издательство должно иметь имя")
    private String publisher;
    @NotBlank(message = "Издательство должно именть адрес")
    private String city;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publishing")
    private List<BookEntity> book;
}
