package com.botscrew.cli;

import com.botscrew.cli.command.Command;
import com.botscrew.cli.exception.UnknownCommandException;
import com.botscrew.cli.parser.DefaultParser;
import org.junit.Test;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

public class OptionParserTest {
    @Test
    public void testSelectOption() throws UnknownCommandException{
        DefaultParser parser = new DefaultParser();
        String args = "-save book -a author -b name";
        Command command = parser.parse(args);
        assertEquals("-save", command.getMainArgument().getOptionName());
        assertEquals("-a", command.getOptions().get(0).getOptionName());
        assertEquals("-b", command.getOptions().get(1).getOptionName());
        assertEquals("author", command.getOptions().get(0).getOptionValues()[0]);
        assertEquals("name", command.getOptions().get(1).getOptionValues()[0]);
    }

    @Test
    public void testUpdateOption() throws UnknownCommandException{
        DefaultParser parser = new DefaultParser();
        String args = "-update book -a author -b name -id 1";
        Command command = parser.parse(args);
        assertEquals("-update", command.getMainArgument().getOptionName());
        assertEquals("-a", command.getOptions().get(0).getOptionName());
        assertEquals("-b", command.getOptions().get(1).getOptionName());
        assertEquals("-id", command.getOptions().get(2).getOptionName());
        assertEquals("author", command.getOptions().get(0).getOptionValues()[0]);
        assertEquals("name", command.getOptions().get(1).getOptionValues()[0]);
        assertEquals("1", command.getOptions().get(2).getOptionValues()[1]);
    }
    @Test
    public void testDeleteOption() throws UnknownCommandException{
        DefaultParser parser = new DefaultParser();
        String args = "-delete book -id 1";
        Command command = parser.parse(args);
        assertEquals("-delete", command.getMainArgument().getOptionName());
        assertEquals("-id", command.getOptions().get(0).getOptionName());
        assertEquals("1", command.getOptions().get(0).getOptionValues()[1]);
    }

    @Test
    public void testListOption() throws UnknownCommandException{
        DefaultParser parser = new DefaultParser();
        String args = "-list book";
        Command command = parser.parse(args);
        assertEquals("-list", command.getMainArgument().getOptionName());
    }

}
