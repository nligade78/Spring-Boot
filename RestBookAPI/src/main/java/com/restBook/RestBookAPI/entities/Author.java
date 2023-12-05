package com.restBook.RestBookAPI.entities;

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

    public int getId() {
        return authorId;
    }

    public void setId(int id) {
        this.authorId = id;
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

    public Author(int id, String firstName, String lastName, String language) {
        this.authorId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
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
