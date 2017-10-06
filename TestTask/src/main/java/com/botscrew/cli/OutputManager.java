package com.botscrew.cli;

import com.botscrew.model.Book;

import java.util.List;

public class OutputManager {
    public void outputAddedBook(Book book) {
        System.out.println("added book with author: " + book.getAuthor() + " and name: " + book.getName());
    }

    public void outputAllBooks(List<Book> bookList) {
        for (Book book : bookList) {
            System.out.println("id: " + book.getId() + " author: " + book.getAuthor() + " bookName: " + book.getName());
        }
    }

    public void outputDeletedBook(Book book) {
        System.out.println("deleted book with author: " + book.getAuthor() + " and bookName: " + book.getName());
    }

    public void outputBooksWithSameName(List<Book> bookList) {
        for (Book book : bookList) {
            System.out.println("id: " + book.getId() + ". author " + book.getAuthor() + " book name" + book.getName());
        }
        System.out.println("to delete specific book enter it's id: remove -id <id>");
    }
    public void outputUpdatedBook(Book book) {
        System.out.println("book with id: " + book.getId() + " was updated" + book.getAuthor() + " and name: " + book.getName());
    }
}
