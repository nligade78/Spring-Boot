package com.restBook.RestBookAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int authorId;
    private String firstName;
    private String lastName;
    private String language;
    @OneToOne(mappedBy = "author")
    @JsonBackReference
    private Book book;


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Author(int id, String firstName, String lastName, String language,Book book) {
        this.authorId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.book=book;
    }

    public Author() {
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
