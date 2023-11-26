package com.restBook.RestBookAPI.services;

import com.restBook.RestBookAPI.entities.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServices {

    private static List<Book> list = new ArrayList<Book>();

    static {
        list.add(new Book(12,"Javascript","XYZ"));
        list.add(new Book(32,"HTML","PQR"));
        list.add(new Book(62,"CSS","ABC"));
    }

    //get all book
    public List<Book> getBookList() {
        return list;
    }

    //get single book by id
    public Book getBookById(int id) {
        Book book=null;
         book=list.stream().filter(e->e.getId()==id).findFirst().get();
         return book;
    }
    //adding the book
    public Book addBook(Book book) {
        list.add(book);
        return book;
    }
}
