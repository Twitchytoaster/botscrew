package com.botscrew.cli;

import com.botscrew.repository.service.BookJpaService;
import com.botscrew.model.Book;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OptionHandler {
    private BookJpaService bookJpaService;
    private List<Book> booksWithSameName;
    private Options options;

    @Autowired
    public OptionHandler(BookJpaService bookJpaService) {
        this.bookJpaService = bookJpaService;
    }

    public void parseOptions(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cmd = commandLineParser.parse(options, args);
        OutputManager outputManager = new OutputManager();
        if (Arrays.stream(args).anyMatch((e) -> e.equals("add"))) {
            if (!(cmd.hasOption("author") && cmd.hasOption("bookName"))) {
                throw new MissingArgumentException("for insert statement you need next parameters -author <author name> -bookName <book name>");
            } else {
                Book book = new Book();
                String[] name = cmd.getOptionValues("bookName");
                String[] author = cmd.getOptionValues("author");
                book.setName(getValue(name));
                book.setAuthor(getValue(author));
                bookJpaService.saveBook(book);
                outputManager.outputAddedBook(book);
            }
        } else if (Arrays.stream(args).anyMatch((e) -> e.equals("remove"))) {
            if (cmd.hasOption("bookName") && !cmd.hasOption("author")) {
                String bookName = getValue(cmd.getOptionValues("bookName"));
                booksWithSameName = bookJpaService.findBooksByName(bookName);
                if (booksWithSameName.size() > 1) {
                    outputManager.outputBooksWithSameName(booksWithSameName);
                } else {
                    outputManager.outputDeletedBook(booksWithSameName.get(0));
                    bookJpaService.delete(booksWithSameName.get(0).getId());
                }
            } else if (cmd.hasOption("id")) {
                outputManager.outputDeletedBook(bookJpaService.findById(Integer.parseInt(cmd.getOptionValue("id"))));
                bookJpaService.delete(Integer.parseInt(cmd.getOptionValue("id")));
            } else if (cmd.hasOption("bookName") && cmd.hasOption("author")) {
                outputManager.outputDeletedBook(bookJpaService.findByName(getValue(cmd.getOptionValues("bookName"))));
                bookJpaService.deleteByNameAndAuthor(getValue(cmd.getOptionValues("bookName")), getValue(cmd.getOptionValues("author")));
            } else if (!(cmd.hasOption("bookName") && cmd.hasOption("author") && cmd.hasOption("id"))) {
                throw new MissingArgumentException("to remove entity you need to input it's id or name");
            }
        } else if (Arrays.stream(args).anyMatch((e) -> e.equals("update"))) {
            if (!(cmd.hasOption("id") && cmd.hasOption("author") && cmd.hasOption("bookName"))) {
                throw new MissingArgumentException("for update you need :-id <id> -author <author name> -bookName <book name>");
            } else {
                int id = Integer.parseInt(cmd.getOptionValue("id"));
                Book book = new Book();
                book.setId(id);
                book.setName(getValue(cmd.getOptionValues("bookName")));
                book.setAuthor(getValue(cmd.getOptionValues("author")));
                bookJpaService.saveBook(book);
                outputManager.outputUpdatedBook(book);
            }

        }  else if (Arrays.stream(args).anyMatch(e -> e.equals("all"))) {
            List<Book> allBooks = bookJpaService.findAllBooks();
            outputManager.outputAllBooks(allBooks);
        }
    }

    public void loadOptions() {
        options = new Options();
        options.addOption(Option.builder("author").hasArgs().desc("input author name").build());
        options.addOption(Option.builder("bookName").hasArgs().desc("input book name").build());
        options.addOption(Option.builder("id").numberOfArgs(1).hasArg().desc("input book id").build());
    }

    private String getValue(String[] values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            builder.append(values[i]);
            if (i != values.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

}
