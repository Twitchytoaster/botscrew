package com.botscrew.cli.option;

public enum MainOption  {
    DB(RequestType.values());

    private MainArgument[] mainArguments;

    MainOption(RequestType[] requestTypes) {
        this.mainArguments = requestTypes;
    }

    public MainArgument[] getMainArguments() {
        return mainArguments;
    }
}
