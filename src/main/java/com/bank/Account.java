package com.bank;

import java.util.regex.*;

public class Account {
	public Integer accountNumber;
	public String name;
	private String SSN;
	private String password;
	
	Account(String name, Integer accountNumber, String SSN, String password){
		this.name = name;
		this.accountNumber = accountNumber;
		this.SSN = SSN;
		this.password = password;
	}
	
	static boolean isNameValid(String name) {
		if(name.length() > 50) {
			System.out.println(ANSI.RED + "Error: name must be less than 50 characters." + ANSI.RESET);
			return false;
		}
		if(!Pattern.matches("\\w+", name)) {
			System.out.println(ANSI.RED + "Error: name must only include alphabetic characters." + ANSI.RESET);
			return false;
		}
		return true;
	}
	
	static boolean isSSNValid(String SSN) {
		String[] numbers = SSN.split("-");
		if(numbers.length != 3 || numbers[0].length() != 3 || numbers[1].length() != 2 || numbers[2].length() != 4) {
			System.out.println(ANSI.RED + "Error: SSN must follow this pattern -> XXX-XX-XXXX" + ANSI.RESET);
			return false;
		}
		if(!Pattern.matches("\\d{3}", numbers[0]) || !Pattern.matches("\\d{2}", numbers[1]) || !Pattern.matches("\\d{4}", numbers[2])) {
			System.out.println(ANSI.RED + "Error: SSN must only contain numbers and '-'" + ANSI.RESET);
			return false;
		}
		return true;
	}
	
	static boolean isPasswordValid(String password) {
		if(password.length() < 6) {
			System.out.println(ANSI.RED + "Error: password must contain at least 6 characters" + ANSI.RESET);
			return false;
		} else if(!Pattern.matches(".*[a-z]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 lowercase character" + ANSI.RESET);
			return false;
		} else if(!Pattern.matches(".*[A-Z]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 uppercase character" + ANSI.RESET);
			return false;
		} else if(!Pattern.matches(".*\\d+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 number" + ANSI.RESET);
			return false;
		} else if(!Pattern.matches(".*[^\\w\\s]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 special character" + ANSI.RESET);
			return false;
		}
		return true;
	}
	
	void viewAccountDetails() {
		System.out.println("Name: " + this.name);
		System.out.println("Account #: " + this.accountNumber);
	}

}
