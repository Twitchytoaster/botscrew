package com.botscrew.db.service;

import com.botscrew.cli.command.Command;
import com.botscrew.cli.exception.UnknownCommandException;

public interface BookService {
    void save(Command command) throws UnknownCommandException;

    void delete(Command command) throws UnknownCommandException;

    void update(Command command) throws UnknownCommandException;

    void list(Command command) throws UnknownCommandException;
}
