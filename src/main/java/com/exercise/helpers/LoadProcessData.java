package com.exercise.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import com.exercise.model.User;

/**
 * This class contains the methods to load and process the data, according to
 * the user's requests
 * 
 * @author VIPR
 *
 */

public class LoadProcessData {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";

	/**
	 * This method loads jsonArray data from the txt file and passes it to a list of
	 * java objects
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public List<User> loadData() throws FileNotFoundException, IOException, ParseException {
		try {
			List<User> users = new ArrayList<User>();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("src/main/resources/db.txt"));
			JSONArray jsonArray = (JSONArray) obj;

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonUser = (JSONObject) jsonArray.get(i);
				User user = new Gson().fromJson(jsonUser.toString(), User.class);
				users.add(user);
			}

			return users;
		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Loading data ***** \n\n" + ANSI_RESET);
			pressAnyKeyToContinue();
			return null;
		}
	}

	/**
	 * This method searches the active users among all the users that we have in the
	 * list of users.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public void searchActive() throws FileNotFoundException, IOException, ParseException {
		try {
			List<User> users = loadData();
			List<User> usersActive = new ArrayList<User>();
			if (!users.isEmpty()) {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).isActive() == true) {
						usersActive.add(users.get(i));
					}
				}
				if (usersActive.isEmpty())
					System.out.println(ANSI_RED + "No Found Users Active" + ANSI_RESET);
				else
					printUsers(usersActive);
			} else
				System.out.println(ANSI_RED + "Not Users in the DataBase" + ANSI_RESET);
			pressAnyKeyToContinue();
		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Searching Users ***** \n\n" + ANSI_RESET);
			pressAnyKeyToContinue();
		}
	}

	/**
	 * This method asks the user for the characters they want to search the cities
	 * for and searches for the cities that begin with that chain (Distinguish
	 * between uppercase and lowercase).
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public void searchCity() throws FileNotFoundException, IOException, ParseException {
		try {
			List<User> users = loadData();
			List<String> searchedCities = new ArrayList<String>();
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(System.in);
			System.out.print("Search City By: ");
			String select = reader.nextLine();
			if (!users.isEmpty()) {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getCity().startsWith(select)
							&& (!searchedCities.contains(users.get(i).getCity()))) {

						searchedCities.add(users.get(i).getCity());
					}
				}
				if (searchedCities.isEmpty())
					System.out.println(ANSI_RED + "No Found Cities" + ANSI_RESET);
				else
					printCities(searchedCities);
			} else
				System.out.println(ANSI_RED + "No Cities in the DataBase" + ANSI_RESET);

			pressAnyKeyToContinue();
		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Searching City ***** \n\n" + ANSI_RESET);
			pressAnyKeyToContinue();
		}

	}

	/**
	 * This method asks the user if he wants to order by ascending or descending
	 * creation date and orders the list of users according to the request.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public void orderByDate() throws FileNotFoundException, IOException, ParseException {

		try {
			List<User> users = loadData();
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(System.in);
			System.out.print("**** Select Option ***** \n\n");
			System.out.print(" 1   Order Ascent  \n");
			System.out.print(" 2   Order Descent  \n\n");
			System.out.print("     Your Option :       \n");
			String select = reader.next();
			int selectOption = Integer.parseInt(select);
			if (!users.isEmpty()) {
				switch (selectOption) {
				case 1:
					Collections.sort(users);
					break;
				case 2:
					Collections.sort(users);
					Collections.reverse(users);
					break;
				}
				if (selectOption == 1 || selectOption == 2)
					printUsers(users);
				else
					System.out.println(ANSI_RED + "Introduced Incorrect Option" + ANSI_RESET);

			} else
				System.out.println(ANSI_RED + "Not Users in the DataBase" + ANSI_RESET);

			pressAnyKeyToContinue();
		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Order By Date ***** \n\n" +  ANSI_RESET);
			pressAnyKeyToContinue();
		}
	}

	/**
	 * This method asks the user for the data of the new user he wants to create.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void newUser() throws FileNotFoundException, IOException, ParseException {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		try {
			System.out.print("	**** User Data ***** \n\n");
			System.out.print("	Name	: ");
			String name = reader.nextLine();
			System.out.print("	Surname	: ");
			String surname = reader.nextLine();
			System.out.print("	Email	: ");
			String email = reader.nextLine();
			System.out.print("	City	: ");
			String city = reader.nextLine();
			System.out.print("	Active(true or false): ");
			boolean active = reader.nextBoolean();
			Date date = new Date();

			User user = new User();

			user.setName(name);
			user.setSurname(surname);
			user.setCity(city);
			user.setEmail(email);
			user.setActive(active);
			user.setCreationDate(date);
			List<User> users = loadData();
			users.add(user);
			saveFile(users);

		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Introducing data ***** \n\n" + ANSI_RESET);
			pressAnyKeyToContinue();
		}

	}

	/**
	 * This method stores the list of users in the txt file in jsonArray format.
	 * 
	 * @param users
	 */
	@SuppressWarnings("unchecked")
	public void saveFile(List<User> users) {
		try {
			JSONArray usersJson = new JSONArray();
			for (int i = 0; i < users.size(); i++) {
				JSONObject userJson = new JSONObject();
				userJson.put("name", users.get(i).getName());
				userJson.put("surname", users.get(i).getSurname());
				userJson.put("active", users.get(i).isActive());
				userJson.put("email", users.get(i).getEmail());
				userJson.put("city", users.get(i).getCity());
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				String creationDate = formatter.format(users.get(i).getCreationDate());
				userJson.put("creationDate", creationDate);
				usersJson.add(userJson);
			}

			FileWriter file = new FileWriter("src/main/resources/db.txt", false);

			JSONArray.writeJSONString(usersJson, file);
			file.close();
			System.out.println(ANSI_GREEN + "New User Created" + ANSI_RESET);
			pressAnyKeyToContinue();

		} catch (Exception e) {
			System.out.print(ANSI_RED + "**** Error Saving data ***** \n\n" + ANSI_RESET);
			pressAnyKeyToContinue();

		}
	}

	/**
	 * This method prints the list of users in the console.
	 * 
	 * @param users
	 */
	public void printUsers(List<User> users) {
		for (int i = 0; i < users.size(); i++) {

			System.out.println("*************************** USER **********************************\n");
			System.out.println(ANSI_GREEN + "	Name : " + users.get(i).getName() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "	SurName : " + users.get(i).getSurname() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "	City : " + users.get(i).getCity() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "	Email : " + users.get(i).getEmail() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "	Creation Date : " + users.get(i).getCreationDate() + ANSI_RESET);
			System.out.println("************************************************************************\n\n");

		}
	}

	/**
	 * This method prints the list of cities in the console.
	 * 
	 * @param searchedCities
	 */
	public void printCities(List<String> searchedCities) {

		for (int i = 0; i < searchedCities.size(); i++) {
			System.out.println("*************************** City **********************************\n");
			System.out.println("	City : " + ANSI_GREEN + searchedCities.get(i) + ANSI_RESET);
		}
	}

	/**
	 * This method stops the execution until the user press enter.
	 */
	private void pressAnyKeyToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}
}
