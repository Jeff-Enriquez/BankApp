package com.bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
	private static Scanner SC = new Scanner(System.in);
	private static String input = "";
	private static Database database;
	public static String FILE_NAME = "Database.txt";
	static {
		deserialization();
	}
	
	public static void main(String[] args) {
		while(!input.equals("EXIT")) {			
			input = createOrLogin();
			if(input.equals("login")) {
				login();
				if(database.currentUser.getAccountType().equals("Admin")) {
					while(!input.equals("LOG OUT")) {						
						database.currentUser.getInstructions();
						input = getInput();
						if(input.equals("1")) {
							approveOrDenyApplication();
						} else if(input.equals("2")) {
							
						} else if(input.equals("3")) {
							
						} else {
							System.out.println(ANSI.YELLOW + "Input was incorrect" + ANSI.RESET);
						}
						System.out.println("Type 'LOG OUT' to log out or press 'enter' to continue.");
						input = getInput();
					}
				} else if(database.currentUser.getAccountType().equals("Customer")) {
					System.out.println("SUCCESS!!!");
				} 
			} else {
				String userName = getUserName();
				String fname = getFirstName();
				String lname = getLastName();
				String SSN = getSSN();
				String password = getPassword();
				Customer customer = new Customer(userName, fname + " " + lname, SSN, password);
				while(!database.requestAccount(customer)) {
					customer.userName = getUserName();
				}
			}
			System.out.println("Type 'EXIT' to exit the application or press 'enter' to continue.");
			input = getInput();
		}
		serialization();
		SC.close();
	}
	
	private static void deserialization() {
		try {
            FileInputStream file = new FileInputStream(FILE_NAME); 
            ObjectInputStream in = new ObjectInputStream(file);
            database = (Database)in.readObject(); 
            in.close(); 
            file.close(); 
            System.out.println(ANSI.CYAN + "Database has been loaded" + ANSI.RESET); 
        } catch(IOException ex) { 
            System.out.println("IOException is caught"); 
        } catch(ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException is caught"); 
        }
	}
	
	private static void serialization() {
		try {
			FileOutputStream file = new FileOutputStream(FILE_NAME); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
            out.writeObject(database); 
            System.out.println(ANSI.CYAN + "Database has been saved" + ANSI.RESET); 
            out.close(); 
            file.close(); 
        } catch(IOException ex) { 
            System.out.println("IOException is caught"); 
        }
	}
	
	private static void approveOrDenyApplication() {
		input = "";					
		if(database.isRequest()) {			
			System.out.println("Would you like to");
			System.out.println(ANSI.BLUE + "1) approve or");
			System.out.println("2) deny this request?" + ANSI.RESET);
			input = getInput();
			if(input.equals("1")) { 
				database.approveRequest();
			} else if(input.equals("2")) {
				database.denyRequest();
			} else {			
				System.out.println(ANSI.YELLOW + "Input was incorrect" + ANSI.RESET);
				approveOrDenyApplication();
			}
		}
	}
	
	private static void login() {
		String userName, password;
		System.out.print("Enter your user name: ");
		userName = getInput();
		System.out.print("Enter your password: ");
		password = getInput();
		if(!database.login(userName, password)) {
			login();
		}
		
	}
	
	private static String getInput() {
		String greenInput;
		System.out.print(ANSI.GREEN);
		greenInput = SC.nextLine();
		System.out.print(ANSI.RESET);
		return greenInput;
	}
	
	private static String createOrLogin() {
		System.out.println("Would you like to:");
		System.out.println(ANSI.BLUE + "1) Create Account");
		System.out.println("2) Login" + ANSI.RESET);
		input = getInput();
		input = input.toLowerCase();
		if(input.equals("create account") || input.equals("1")) {
			input = "create account";
		} else if(input.equals("login") || input.equals("2")) {
			input = "login";
		} else {
			System.out.println(ANSI.RED + "Error: Please enter the number or phrase" + ANSI.RESET);
			input = createOrLogin();
		}
		return input;
	}
	
	private static String getUserName() {
		System.out.println("Enter a user name: ");
		input = getInput();
		return input;
	}
	
	private static String getFirstName() {
		do {				
			System.out.print("Enter your first name: ");
			input = getInput();
		} while(!Validate.isNameValid(input));
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	
	private static String getLastName() {
		do {				
			System.out.print("Enter your last name: ");
			input = getInput();
		} while(!Validate.isNameValid(input));
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	
	private static String getSSN() {
		do {				
			System.out.print("Enter your SSN: ");
			input = getInput();
		} while(!Validate.isSSNValid(input));
		return input;
	}
	
	private static String getPassword() {
		String password;
		do {
			System.out.print("Enter a password: ");
			input = getInput();
		} while(!Validate.isPasswordValid(input));
		System.out.print("Confirm password: ");
		password = getInput();
		while(!password.equals(input)) {
			System.out.println(ANSI.RED + "Error: passwords do not match" + ANSI.RESET);
			password = getPassword();
		}
		return password;
	}

}
