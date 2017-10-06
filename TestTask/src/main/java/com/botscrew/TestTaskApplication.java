package com.botscrew;

import com.botscrew.cli.OptionHandler;
import org.apache.commons.cli.MissingArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {
	@Autowired
	private OptionHandler optionHandler;


	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		optionHandler.loadOptions();
		String command = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input:");
		while (!command.equals("quit")) {
			command = scanner.nextLine();
			String[] args = command.split(" ");
			try {
				optionHandler.parseOptions(args);
			} catch (MissingArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

