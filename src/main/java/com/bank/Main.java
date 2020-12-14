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
					adminActions();
				} else if(database.currentUser.getAccountType().equals("Customer")) {
					customerActions();
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
	
	private static void customerActions() {
		String accountNum;
		Double cash;
		while(!input.equals("LOG OUT")) {
			database.currentUser.getInstructions();
			input = getInput();
			if(input.equals("1")) {
				//apply for joint account
			} else if(input.equals("2")) {
				database.currentUser.printAccounts();
				do {					
					System.out.print("Input the account number you would like to withdraw from: ");
					accountNum = getInput();
					System.out.print("Enter the amount you would like to withdraw: ");
					cash = getInputTypeDouble();
				} while(!database.currentUser.withdraw(cash, accountNum));
			} else if(input.equals("3")) {
				database.currentUser.printAccounts();
				do {					
					System.out.print("Input the account number you would like to deposit into: ");
					accountNum = getInput();
					System.out.print("Enter the amount you would like to deposit: ");
					cash = getInputTypeDouble();
				} while(!database.currentUser.deposit(cash, accountNum));
			} else if(input.equals("4")) {
				database.currentUser.printAccounts();
				String accountNum2;
				do {					
					System.out.print("Input the account number you would like to transfer from: ");
					accountNum = getInput();
					System.out.print("Input the account number you would like to transfer to: ");
					accountNum2 = getInput();
					System.out.print("Enter the amount you would like to transfer: ");
					cash = getInputTypeDouble();
				} while(!database.transfer(cash, accountNum, accountNum2));
			} else if(input.equals("5")) {
				database.currentUser.toString();
				System.out.println("SSN: " + database.currentUser.getSSN());
				System.out.println("Password: " + database.currentUser.getPassword());
				database.currentUser.printAccounts();
			} else {
				System.out.println(ANSI.YELLOW + "Input was incorrect" + ANSI.RESET);
			}
			System.out.println("Type 'LOG OUT' to log out or press 'enter' to continue.");
			input = getInput();
		}
	}

	private static void adminActions() {
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
	
	private static Double getInputTypeDouble() {
		Double greenInput;
		System.out.print(ANSI.GREEN);
		greenInput = SC.nextDouble();
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
