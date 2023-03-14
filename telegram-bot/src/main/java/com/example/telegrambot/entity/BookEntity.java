package com.example.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name ="books")
public class BookEntity {
    @Column(name = "book_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotBlank
    private String title;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "publishing_id")
    private PublisherEntity publishing;
    private int year;
    private String kind;

    @Override
    public String toString() {
        return "Название: " + title + '\n' +
                "Автор: " + author + '\n' +
                "Издательство: " + publishing + '\n' +
                "Год издания: " + year +
                " Жанр: " + kind + '\n' +
                '\n';
    }
}
