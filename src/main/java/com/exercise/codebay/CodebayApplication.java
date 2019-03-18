package com.exercise.codebay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.exercise.helpers.*;
import com.exercise.model.User;

/**
 * In this class is the main method that contains the start menu for the user
 * 
 * @author VIPR
 *
 */
@SpringBootApplication
public class CodebayApplication {
	private static final Logger logger = LogManager.getLogger(CodebayApplication.class);

	/**
	 * This method contain the principal menu.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

		new SpringApplicationBuilder(CodebayApplication.class).headless(false).run(args);

		boolean start = true;
		LoadProcessData dataOption = new LoadProcessData();
		List<User> users = dataOption.loadData();
		while (start) {
			try {
				clearScreen();
				@SuppressWarnings("resource")
				Scanner reader = new Scanner(System.in);

				System.out.print("      **** Select Option ***** \n\n");
				System.out.print("	1   Search Active Users  \n\n");
				System.out.print("	2   Search City  \n\n");
				System.out.print("	3   Order Users By Creation Date \n\n");
				System.out.print("	4   Create New User  \n\n");
				System.out.print("	5   EXIT     \n\n");
				System.out.print("     Your Option :       \n");
				String select = reader.next();
				int selectOption = Integer.parseInt(select);
				//LoadProcessData dataOption = new LoadProcessData();
				switch (selectOption) {

				case 1:
					dataOption.searchActive(users);
					break;
				case 2:
					dataOption.searchCity(users);
					break;
				case 3:
					dataOption.orderByDate(users);
					break;
				case 4:
					dataOption.newUser();
					users=dataOption.loadData();
					break;
				case 5:
					start = false;
					System.out.println("GoodBye. The Application was Closed");
					break;
				}

			} catch (Exception e) {
				logger.info("Error Introducing Data");
			}
		}

	}

	/**
	 * This method simulates that you clear console.
	 */

	public static void clearScreen() {
		final int PAGE_SIZE = 50;
		for (int i = 0; i < PAGE_SIZE; i++) {
			System.out.println();
		}
	}

}
