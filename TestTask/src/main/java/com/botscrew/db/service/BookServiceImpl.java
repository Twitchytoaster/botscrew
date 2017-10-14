package com.botscrew.db.service;

import com.botscrew.cli.annotation.OptionManager;
import com.botscrew.cli.command.Command;
import com.botscrew.cli.exception.UnknownCommandException;
import com.botscrew.cli.option.Option;
import com.botscrew.cli.option.RequestType;
import com.botscrew.db.repository.BookRepository;
import com.botscrew.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Service("BookServiceImpl")
@OptionManager(argumentType = RequestType.class)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void save(Command command) throws UnknownCommandException{
        Optional<Book> book = getBook(command);
        if (book.isPresent()) {
            bookRepository.save(book.get());
        }
    }

    @Override
    public void delete(Command command) throws UnknownCommandException{
        Optional<Book> book = getBook(command);
        if (book.isPresent()) {
            bookRepository.delete(book.get().getId());
        }
    }

    @Override
    public void update(Command command) throws UnknownCommandException {
        Optional<Book> bookOptional = getBook(command);
        if (bookOptional.isPresent()) {
            List<Book> booksWithSameNames = bookRepository.findBooksByName(bookOptional.get().getName());
            if (booksWithSameNames.size() > 1) {
                for (Book book : booksWithSameNames) {
                    System.out.println("id: " + book.getId() + " author: " + book.getAuthor() + " name: " + book.getName());
                }
                System.out.println("Choose a book to update: ");
            } else {
                bookRepository.save(bookOptional.get());
            }
        }
    }

    @Override
    public void list(Command command) throws UnknownCommandException  {
        Optional<Book> bookOptional = getBook(command);
        if (!bookOptional.isPresent()) {
            List<Book> bookList = bookRepository.findAll();
            for (Book book : bookList) {
                System.out.println("id: " + book.getId() + " author: " + book.getAuthor() + " name: " + book.getName());
            }
        }
    }

    private Optional<Book> getBook(Command command) throws UnknownCommandException {
        List<Option> options = command.getOptions();
        Book book = new Book();
        if (command.getMainArgument().getOptionName().equals("-list")) {
            return Optional.ofNullable(null);
        }
        for (Option option : options) {
            if (option.getOptionName().equals("-a")) {
                book.setAuthor(Arrays.stream(option.getOptionValues()).collect(joining(" ")));
            } else if (option.getOptionName().equals("-b")) {
                book.setName(Arrays.stream(option.getOptionValues()).collect(joining(" ")));
            } else if (option.getOptionName().equals("-id")) {
                book.setId(Integer.parseInt(option.getOptionValues()[1]));
            } else {
                throw new UnknownCommandException(option.getOptionName() + " is wrong option name");
            }
        }
        return Optional.of(book);
    }
}
