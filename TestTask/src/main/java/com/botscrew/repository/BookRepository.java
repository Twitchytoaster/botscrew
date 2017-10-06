package com.botscrew.repository;

import com.botscrew.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findALlByName(String name);

    void deleteByName(String name);

    void deleteByNameAndAuthor(String name, String author);

    Book findBookByName(String name);
}
