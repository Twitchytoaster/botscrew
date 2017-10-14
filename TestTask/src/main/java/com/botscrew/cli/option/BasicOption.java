package com.botscrew.cli.option;

import com.botscrew.cli.option.Option;

public class BasicOption implements Option {
    private String []values;
    private String optionName;

    public BasicOption() {

    }

    public BasicOption(String[] values, String optionName) {
        this.values = values;
        this.optionName = optionName;
    }

    @Override
    public String[] getOptionValues() {
        return values;
    }

    @Override
    public boolean hasArguments() {
        return values.length > 0;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
