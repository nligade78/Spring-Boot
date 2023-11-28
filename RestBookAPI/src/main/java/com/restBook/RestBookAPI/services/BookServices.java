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

    //get all book
    public List<Book> getBookList() {
        List<Book> allBooks= (List<Book>) bookRepository.findAll();
        return allBooks;
    }

    //get single book by id
    public Book getBookById(int id) {
        Book book=null;
        try {
          //  book = list.stream().filter(e -> e.getId() == id).findFirst().get();
           book =this.bookRepository.findById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
         return book;
    }
    //adding the book
    public Book addBook(Book book) {
        Book result=bookRepository.save(book);
        return result;
    }
    public void deleteBook(int bid) {
//        list.stream().filter(e->{
//            if(e.getId()!=bid)
//            {
//                  return true;
//            }
//            return false;
//        }).collect(Collectors.toList());

       // list=list.stream().filter(book->book.getId()!=bid).collect(Collectors.toList());

        bookRepository.deleteById(bid);
    }

    public void updateBook(Book book,int bookId)
    {
//        list=list.stream().map(b->{
//            if(b.getId()==bookId)
//            {
//                b.setTitle(book.getTitle());
//                b.setAuthor(book.getAuthor());
//            }
//            return b;
//        }).collect(Collectors.toList());
        book.setId(bookId);
        bookRepository.save(book);
    }
}
