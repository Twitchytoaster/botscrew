package com.botscrew.repository.service;

import com.botscrew.model.Book;

import java.util.List;

public interface BookJpaService {
    void saveBook(Book book);

    void delete(Integer id);

    void deleteBookByName(String name);

    List<Book> findAllBooks();

    List<Book> findBooksByName(String name);

    Book findById(Integer id);

    void deleteByNameAndAuthor(String name, String author);

    Book findByName(String name);
}
