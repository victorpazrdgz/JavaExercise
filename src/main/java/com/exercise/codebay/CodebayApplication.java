package com.exercise.codebay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.exercise.helpers.*;

@SpringBootApplication
public class CodebayApplication {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		new SpringApplicationBuilder(CodebayApplication.class).headless(false).run(args);
		boolean init = true;

		while (init) {
			clearScreen();
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(System.in);
			LoadProcessData dataOption = new LoadProcessData();
			//SpringApplication.run(CodebayApplication.class, args);
			System.out.print("Desea utulizar " + "Colección Verano (1) o Colección Invierno (2):");
			String select = reader.next();
			if (select.equals("1")) {
				dataOption.searchActive();

			}
			if (select.equals("2")) {
				dataOption.searchCity();

			}
			if (select.equals("3")) {
				dataOption.searchDate();

			}
			if (select.equals("4")) {
				dataOption.newUser();;

			}
			if (select.equals("5")) {
				init = false;

			}
		}
	}
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	   }

}
