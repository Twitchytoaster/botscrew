package com.botscrew.db.repository;

import com.botscrew.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b order by b.name")
    List<Book> findAll();

    List<Book> findBooksByName(String name);
}
