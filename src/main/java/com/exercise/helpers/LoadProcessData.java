package com.exercise.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
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
  
	public List<User> loadData() throws FileNotFoundException, IOException, ParseException {
		List<User> users = new ArrayList<User>();
		// FileReader reader = new FileReader("src/main/resources/db.txt");
		// System.out.println(reader);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/main/resources/db.txt"));
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

	public void searchActive() throws FileNotFoundException, IOException, ParseException {
		List<User> users = loadData();
		int cont = 0;
		if (users != null) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).isActive() == true) {
					cont++;
					System.out.println("*************************** USER **********************************");
					System.out.println("users" + users.get(i).getName());
					System.out.println("users" + users.get(i).getSurname());
					System.out.println("users" + users.get(i).getCity());
					System.out.println("users" + users.get(i).getEmail());
					System.out.println("users" + users.get(i).getCreationDate());
					System.out.println("************************************************************************");
				}
			}
			if (cont == 0)
				System.out.println("No Found Users Active");
		} else
			System.out.println("Not Users in the DataBase");
		pressAnyKeyToContinue();
	}

	public void searchCity() throws FileNotFoundException, IOException, ParseException {
		List<User> users = loadData();
		int cont = 0;
		List<String> searched= new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Search City By: ");
		String select = reader.next();
		if (select != "") {
			if (users != null) {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getCity().startsWith(select)&& (!searched.contains(users.get(i).getCity())) ) {
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
		}else
			System.out.println("Can´t do the search ");
		pressAnyKeyToContinue();
		
	}
	public void searchDate() throws FileNotFoundException, IOException, ParseException {
		List<User> users = loadData();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Order Ascent press 1: ");
		System.out.print("Order Descent press 2: ");
		String select = reader.next();
		int selectCase=Integer.parseInt(select);
		if (select != "") {
			if (users != null) {
				switch (selectCase) {
				case 1:
					Collections.sort(users);
				case 2:	
					Collections.sort(users);
					Collections.reverse(users);
				} 
			
				
				for (int i = 0; i < users.size(); i++) {
					
						
						System.out.println("*************************** USER **********************************");
						System.out.println("users" + users.get(i).getName());
						System.out.println("users" + users.get(i).getSurname());
						System.out.println("users" + users.get(i).getCity());
						System.out.println("users" + users.get(i).getEmail());
						System.out.println("users" + users.get(i).getCreationDate());
						System.out.println("************************************************************************");
					
				}
				
		
			} else
				System.out.println("Not Users in the DataBase");
		}else
			System.out.println("Can´t do the search ");
		pressAnyKeyToContinue();
		
	}
	private void pressAnyKeyToContinue()
	 { 
	        System.out.println("Press Enter key to continue...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
}
