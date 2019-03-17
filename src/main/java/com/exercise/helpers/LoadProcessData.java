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

public class LoadProcessData {
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<User> loadData() throws FileNotFoundException, IOException, ParseException {
		List<User> users = new ArrayList<User>();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/dbw.txt"));
		JSONArray jsonArray = (JSONArray) obj;
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonUser = (JSONObject) jsonArray.get(i);
			User user = new Gson().fromJson(jsonUser.toString(), User.class);
			users.add(user);
		}

		return users;
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void searchActive() throws FileNotFoundException, IOException, ParseException {
		List<User> users = loadData();
		List<User> usersActive = new ArrayList<User>();
		if (users != null) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).isActive() == true) {
					usersActive.add(users.get(i));
				}
			}
			if (usersActive.equals(null))
				System.out.println("No Found Users Active");
			else
				printUsers(usersActive);
		} else
			System.out.println("Not Users in the DataBase");
		pressAnyKeyToContinue();
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void searchCity() throws FileNotFoundException, IOException, ParseException {
		List<User> users = loadData();
		List<String> searchedCities = new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Search City By: ");
		String select = reader.next();
		if (select != "") {
			if (users != null) {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getCity().startsWith(select)
							&& (!searchedCities.contains(users.get(i).getCity()))) {

						searchedCities.add(users.get(i).getCity());

					}

				}
				if (searchedCities.equals(null))
					System.out.println("No Found Cities");
				else
					printCities(searchedCities);
			} else
				System.out.println("No Cities in the DataBase");
		} else
			System.out.println("Can´t do the search ");
		pressAnyKeyToContinue();

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void searchDate() throws FileNotFoundException, IOException, ParseException {

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
			if (users != null) {
				switch (selectOption) {
				case 1:
					Collections.sort(users);
					break;
				case 2:
					Collections.sort(users);
					Collections.reverse(users);
					break;
				}
				// if (select.equals("1"))
				// Collections.sort(users);
				// if (select.equals("2")) {
				// Collections.sort(users);
				// Collections.reverse(users);
				// }
				if (selectOption==1 || selectOption==2)
					printUsers(users);
				else
					System.out.println("Introduced Incorrect Option");

			} else
				System.out.println("Not Users in the DataBase");

			pressAnyKeyToContinue();
		} catch (Exception e) {
			System.out.print("**** Error Introducing data ***** \n\n");
			pressAnyKeyToContinue();
		}
	}

	/**
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
			System.out.print("**** Error Introducing data ***** \n\n");
			pressAnyKeyToContinue();
		}

	}

	/**
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

			FileWriter file = new FileWriter("src/main/resources/dbw.txt", false);

			JSONArray.writeJSONString(usersJson, file);
			file.close();
			System.out.println("New User Created");
			pressAnyKeyToContinue();

		} catch (Exception e) {
			System.out.print("**** Error Saving data ***** \n\n");
			pressAnyKeyToContinue();

		}
	}

	/**
	 * 
	 * @param users
	 */
	public void printUsers(List<User> users) {
		for (int i = 0; i < users.size(); i++) {

			System.out.println("*************************** USER **********************************\n");
			System.out.println("	Name : " + users.get(i).getName());
			System.out.println("	SurName : " + users.get(i).getSurname());
			System.out.println("	City : " + users.get(i).getCity());
			System.out.println("	Email : " + users.get(i).getEmail());
			System.out.println("	Creation Date : " + users.get(i).getCreationDate());
			System.out.println("************************************************************************\n\n");

		}
	}

	/**
	 * 
	 * @param searchedCities
	 */
	public void printCities(List<String> searchedCities) {

		for (int i = 0; i < searchedCities.size(); i++) {
			System.out.println("*************************** City **********************************\n");
			System.out.println("	City : " + searchedCities.get(i));
			System.out.println("************************************************************************\n\n");
		}
	}

	/**
	 * 
	 */
	private void pressAnyKeyToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}
}
