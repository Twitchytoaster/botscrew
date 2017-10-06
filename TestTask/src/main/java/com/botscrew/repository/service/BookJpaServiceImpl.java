package com.botscrew.repository.service;

import com.botscrew.repository.BookRepository;
import com.botscrew.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookJpaServiceImpl implements BookJpaService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(Integer id) {
        bookRepository.delete(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.getBooksOrderedByName();
    }

    @Override
    public List<Book> findBooksByName(String name) {
        return bookRepository.findALlByName(name);
    }

    @Override
    public Book findById(Integer id) {
        return bookRepository.getOne(id);
    }

    @Override
    @Transactional
    public void deleteByNameAndAuthor(String name, String author) {
        bookRepository.deleteByNameAndAuthor(name, author);
    }

    @Override
    public Book findByName(String name) {
        return bookRepository.findBookByName(name);
    }
}
