package com.botscrew;

import com.botscrew.cli.command.Command;
import com.botscrew.cli.command.CommandExecutor;
import com.botscrew.cli.exception.UnknownCommandException;
import com.botscrew.cli.parser.DefaultParser;
import com.botscrew.cli.parser.OptionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {
    @Autowired
    private CommandExecutor commandExecutor;

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        OptionParser parser = new DefaultParser();
        Scanner scanner = new Scanner(System.in);
        String nextLine;
        while (true) {
            nextLine = scanner.nextLine();
            if (nextLine.equals("exit")) {
                break;
            }
            try {
                Command command = parser.parse(nextLine);
                commandExecutor.execute(command);
            } catch (UnknownCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

