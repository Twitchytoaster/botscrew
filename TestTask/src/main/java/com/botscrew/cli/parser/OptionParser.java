package com.botscrew.cli.parser;

import com.botscrew.cli.command.Command;
import com.botscrew.cli.exception.UnknownCommandException;

public interface OptionParser {
    Command parse(String args) throws UnknownCommandException;
}
