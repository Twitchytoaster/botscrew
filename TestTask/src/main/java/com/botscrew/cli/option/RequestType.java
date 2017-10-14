package com.botscrew.cli.option;

public enum RequestType implements MainArgument {
    SAVE("-save"),DELETE("-delete"),UPDATE("-update"),LIST("-list");

    private String option;

    RequestType(String option) {
        this.option = option;
    }

    @Override
    public String getOptionName() {
        return option;
    }

    @Override
    public String getEnumOption() {
        return name();
    }


}
