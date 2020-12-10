package com.bank;

import java.util.Scanner;

public class Main {

	public static String CREATEACCOUNT = "Create Account";
	public static String LOGIN = "Login";
	public static Scanner SC = new Scanner(System.in);
	public static String input = "";
	
	public static String createOrLogin() {
		System.out.println("Would you like to '" + CREATEACCOUNT + "' or '" + LOGIN + "'?");
		input = SC.nextLine(); 
		while(!input.equals(CREATEACCOUNT) && !input.equals(LOGIN)) {
			System.out.println("Error: please be sure to type it exactly as in the quotes.");
			System.out.println("Would you like to 'Create Account' or 'Sign In'?");
			input = SC.nextLine();
		}
		return input;
	}
	
	public static String getFirstName() {
		do {				
			System.out.print("Enter your first name: ");
			input = SC.nextLine();
		} while(!Account.isNameValid(input));
		return input;
	}
	
	public static String getLastName() {
		do {				
			System.out.print("Enter your last name: ");
			input = SC.nextLine();
		} while(!Account.isNameValid(input));
		return input;
	}
	
	public static String getSSN() {
		do {				
			System.out.print("Enter your SSN: ");
			input = SC.nextLine();
		} while(!Account.isSSNValid(input));
		return input;
	}
	
	public static String getPassword() {
		String password;
		do {
			do {
				System.out.print("Enter a password: ");
				input = SC.nextLine();
			} while(!Account.isPasswordValid(input));
			System.out.print("Confirm password: ");
			password = SC.nextLine();
			if(password != input) {
				System.out.println("Error: passwords do not match");
			}
		} while(password != input);
		return password;
	}
	
	public static void main(String[] args) {
		input = createOrLogin();
		if(input.equals(LOGIN)) {
			
		} else {
			String fname = getFirstName();
			String lname = getLastName();
			String SSN = getSSN();
			String password = getPassword();
		}
		SC.close();
	}

}
