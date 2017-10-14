package com.botscrew.cli.option;

public interface Option {
    String[] getOptionValues();

    boolean hasArguments();

    String getOptionName();
}
