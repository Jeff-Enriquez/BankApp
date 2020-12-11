package com.bank;

import java.util.Scanner;

public class Main {
	private static Scanner SC = new Scanner(System.in);
	private static String input = "";
	
	public static void main(String[] args) {
		input = createOrLogin();
		if(input.equals("login")) {
			
		} else {
			String fname = getFirstName();
			String lname = getLastName();
			String SSN = getSSN();
			String password = getPassword();
		}
		SC.close();
	}
	
	private static String createOrLogin() {
		System.out.println("Would you like to:");
		System.out.println(ANSI.BLUE + "1) " + "Create Account");
		System.out.println("2) " + "Login" + ANSI.RESET);
		input = SC.nextLine();
		input = input.toLowerCase();
		if(input.equals("create account") || input.equals("1")) {
			return "create account";
		} else if(input.equals("login") || input.equals("2")) {
			return "login";
		} else {
			System.out.println(ANSI.RED + "Error: Please enter the number or phrase" + ANSI.RESET);
			return createOrLogin();
		}
	}
	
	private static String getFirstName() {
		do {				
			System.out.print("Enter your first name: ");
			input = SC.nextLine();
		} while(!Account.isNameValid(input));
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	
	private static String getLastName() {
		do {				
			System.out.print("Enter your last name: ");
			input = SC.nextLine();
		} while(!Account.isNameValid(input));
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	
	private static String getSSN() {
		do {				
			System.out.print("Enter your SSN: ");
			input = SC.nextLine();
		} while(!Account.isSSNValid(input));
		return input;
	}
	
	private static String getPassword() {
		String password;
		do {
			System.out.print("Enter a password: ");
			input = SC.nextLine();
		} while(!Account.isPasswordValid(input));
		System.out.print("Confirm password: ");
		password = SC.nextLine();
		while(!password.equals(input)) {
			System.out.println(ANSI.RED + "Error: passwords do not match" + ANSI.RESET);
			password = getPassword();
		}
		return password;
	}

}
