package com.exercise.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.exercise.codebay.CodebayApplication;
import com.exercise.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
		// FileReader reader = new FileReader("src/main/resources/db.txt");
		// System.out.println(reader);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/dbw.txt"));
		JSONArray jsonArray = (JSONArray) obj;
		System.out.println(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonUser = (JSONObject) jsonArray.get(i);
			System.out.println(jsonUser);

			User user = new Gson().fromJson(jsonUser.toString(), User.class);

			users.add(user);
		}
		System.out.println("users" + users.get(0).getCity());

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
		int cont = 0;
		List<String> searched = new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Search City By: ");
		String select = reader.next();
		if (select != "") {
			if (users != null) {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getCity().startsWith(select) && (!searched.contains(users.get(i).getCity()))) {
						cont++;
						searched.add(users.get(i).getCity());
						System.out.println("*************************** City **********************************");
						System.out.println("users" + users.get(i).getCity());
						System.out.println("************************************************************************");
					}

				}
				if (cont == 0)
					System.out.println("No Found Users Active");
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
		List<User> users = loadData();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Order Ascent press 1: ");
		System.out.print("Order Descent press 2: ");
		String select = reader.next();
		int selectCase = Integer.parseInt(select);
		if (select != "") {
			if (users != null) {
				switch (selectCase) {
				case 1:
					Collections.sort(users);
				case 2:
					Collections.sort(users);
					Collections.reverse(users);
				}

				printUsers(users);

			} else
				System.out.println("Not Users in the DataBase");
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
	public void newUser() throws FileNotFoundException, IOException, ParseException {
		Scanner reader = new Scanner(System.in);
		System.out.print("name: ");
		String name = reader.next();
		System.out.print("surname: ");
		String surname = reader.next();
		System.out.print("email: ");
		String email = reader.next();
		System.out.print("City: ");
		String city = reader.next();
		Date date = new Date();
		
		User user = new User();

		user.setName(name);
		user.setSurname(surname);
		user.setCity(city);
		user.setEmail(email);
		user.setActive(true);
		user.setCreationDate(date);
		List<User> users = loadData();
		users.add(user);
		saveFile(users);
		System.out.println(users.get(15).getName());
	}

	
	/**
	 * 
	 * @param users
	 */
	@SuppressWarnings("unchecked")
	public void saveFile(List<User> users) {
		
	    JSONArray usersJson = new JSONArray();
	    for (int i=0;i<users.size();i++) {
	    	JSONObject userJson = new JSONObject();
	    	userJson.put("name", users.get(i).getName());
	    	userJson.put("surname", users.get(i).getSurname());
	    	userJson.put("active", users.get(i).isActive());
	    	userJson.put("email", users.get(i).getEmail());
	    	userJson.put("city", users.get(i).getCity());
	    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); 
			String creationDate= formatter.format(users.get(i).getCreationDate());
	    	userJson.put("creationDate",creationDate );
	    	usersJson.add(userJson);
	    }
	    try{
            FileWriter  file = new FileWriter( "src/main/resources/dbw.txt",false);

            JSONArray.writeJSONString(usersJson, file);
            file.close();
        }
        catch(Exception e  ){

            e.getMessage();

        }
	}

	/**
	 * 
	 * @param users
	 */
	public void printUsers(List<User> users) {
		for (int i = 0; i < users.size(); i++) {

			System.out.println("*************************** USER **********************************");
			System.out.println("users" + users.get(i).getName());
			System.out.println("users" + users.get(i).getSurname());
			System.out.println("users" + users.get(i).getCity());
			System.out.println("users" + users.get(i).getEmail());
			System.out.println("users" + users.get(i).getCreationDate());
			System.out.println("************************************************************************");

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
