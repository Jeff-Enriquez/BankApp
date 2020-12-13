package com.bank;

import java.util.regex.Pattern;

public class Validate {
	
	public static boolean isNameValid(String name) {
		boolean isValid = true;
		if(name.length() == 0) {
			System.out.println(ANSI.RED + "Error: you must enter a name." + ANSI.RESET);
			isValid = false;
		}else if(name.length() > 50) {
			System.out.println(ANSI.RED + "Error: name must be less than 50 characters." + ANSI.RESET);
			isValid = false;
		}else if(!Pattern.matches("[a-zA-Z]+", name)) {
			System.out.println(ANSI.RED + "Error: name must only include alphabetic characters." + ANSI.RESET);
			isValid = false;
		}
		return isValid;
	}
	
	public static boolean isSSNValid(String SSN) {
		boolean isValid = true;
		String[] numbers = SSN.split("-");
		if(numbers.length != 3 || numbers[0].length() != 3 || numbers[1].length() != 2 || numbers[2].length() != 4) {
			System.out.println(ANSI.RED + "Error: SSN must follow this pattern -> XXX-XX-XXXX" + ANSI.RESET);
			isValid = false;
		} else if(!Pattern.matches("\\d{3}", numbers[0]) || !Pattern.matches("\\d{2}", numbers[1]) || !Pattern.matches("\\d{4}", numbers[2])) {
			System.out.println(ANSI.RED + "Error: SSN must only contain numbers and '-'" + ANSI.RESET);
			isValid = false;
		}
		return isValid;
	}
	
	public static boolean isPasswordValid(String password) {
		boolean isValid = true;
		if(password.length() < 6) {
			System.out.println(ANSI.RED + "Error: password must contain at least 6 characters" + ANSI.RESET);
			isValid = false;
		} else if(!Pattern.matches(".*[a-z]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 lowercase character" + ANSI.RESET);
			isValid = false;
		} else if(!Pattern.matches(".*[A-Z]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 uppercase character" + ANSI.RESET);
			isValid = false;
		} else if(!Pattern.matches(".*\\d+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 number" + ANSI.RESET);
			isValid = false;
		} else if(!Pattern.matches(".*[^\\w\\s]+.*", password)) {
			System.out.println(ANSI.RED + "Error: password must contain at least 1 special character" + ANSI.RESET);
			isValid = false;
		}
		return isValid;
	}
}
