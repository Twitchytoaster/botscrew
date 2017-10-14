package com.botscrew.cli.command;

import com.botscrew.cli.option.MainArgument;
import com.botscrew.cli.option.Option;

import java.util.List;

public class Command {
    private MainArgument mainArgument;
    private List<Option> options;

    public Command() {

    }

    public Command(MainArgument mainOption, List<Option> options) {
        this.mainArgument = mainOption;
        this.options = options;
    }

    public MainArgument getMainArgument() {
        return mainArgument;
    }

    public void setMainArgument(MainArgument mainArgument) {
        this.mainArgument = mainArgument;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
