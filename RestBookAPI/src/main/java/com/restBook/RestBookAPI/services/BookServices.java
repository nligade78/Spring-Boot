package com.restBook.RestBookAPI.services;

import com.restBook.RestBookAPI.entities.Book;
import com.restBook.RestBookAPI.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServices {

//    private static List<Book> list = new ArrayList<Book>();
//
//    static {
//        list.add(new Book(12,"Javascript","XYZ"));
//        list.add(new Book(32,"HTML","PQR"));
//        list.add(new Book(62,"CSS","ABC"));
//    }


    @Autowired
    private BookRepository bookRepository;

    // *************************************************
    //add Book
    public Book addBook(Book book)
    {
        Book bookSave = bookRepository.save(book);
        return bookSave;
    }
    // *************************************************

    //get allBooks
    public List<Book> getAllBooks()
    {
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        return allBooks;
    }
    // *************************************************

    //get a single book by id
    public Book getBookById(int id)
    {
        Book book=null;
        try{
            book = bookRepository.findById(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return book;
    }
    // *************************************************

    //delete Book
    public void deleteBook(int bid)
    {
        if(!bookRepository.existsById(bid))
        {
            throw new RuntimeException("Book Not Found id:"+bid);
        }
        bookRepository.deleteById(bid);
    }

    // *************************************************

    //updateBook
    public void updateBook(Book book,int bookId)
    {
        book.setId(bookId);
        bookRepository.save(book);
    }

}
