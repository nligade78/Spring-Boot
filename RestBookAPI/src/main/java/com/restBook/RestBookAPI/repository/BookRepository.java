package com.restBook.RestBookAPI.repository;

import com.restBook.RestBookAPI.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Integer> {
    public Book findById(int id);
}
