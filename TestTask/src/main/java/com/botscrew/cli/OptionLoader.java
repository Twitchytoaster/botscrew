package com.botscrew.cli;

import org.apache.commons.cli.*;

public class OptionLoader {
    private Options options;

    public Options loadOptions() {
        options = new Options();
        options.addOption(Option.builder("author").hasArgs().desc("input author name").build());
        options.addOption(Option.builder("bookName").hasArgs().desc("input book name").build());
        options.addOption(Option.builder("id").numberOfArgs(1).hasArg().desc("input book id").build());
        return options;
    }
}
