package com.bank;

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
		final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
		if(name.length() > 50) {
			System.out.println("Error: name must be less than 50 characters.");
			return false;
		}
		
		String[] characters = name.toLowerCase().split("");
		 
        for (String character : characters) {
            if(!ALPHABET.contains(character)) {
            	System.out.println("Error: name must only include alphabetic characters.");
            	return false;
            }
        }
		return true;
	}
	
	static boolean isSSNValid(String SSN) {
		final String NUMBERS = "0123456789";
		String[] numbers = SSN.split("-");
		if(numbers.length != 3 || numbers[0].length() != 3 || numbers[1].length() != 2 || numbers[2].length() != 4) {
			System.out.println("Error: SSN must follow this pattern -> XXX-XX-XXXX");
			return false;
		}
		for(int i = 0; i < numbers.length; i++) {
			String[] nums = numbers[i].split("");
			for(int j = 0; j < nums.length; j++) {
				if(!NUMBERS.contains(nums[j])) {
					System.out.println("Error: SSN must only contain numbers and '-'");
					return false;
				}
			}
		}
		return true;
	}
	
	static boolean isPasswordValid(String password) {
		final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
		final String NUMBERS = "0123456789";
		final String SPECIAL = "`!@#$%^&*()_+~-=[]{}|\\:\";',./<>?";
		if(password.length() < 6) {
			System.out.println("Error: password must contain at least 6 characters");
			return false;
		} else if(!password.contains(ALPHABET)) {
			System.out.println("Error: password must contain at least 1 lowercase character");
			return false;
		} else if(!password.contains(ALPHABET.toUpperCase())) {
			System.out.println("Error: password must contain at least 1 uppercase character");
			return false;
		} else if(!password.contains(NUMBERS)) {
			System.out.println("Error: password must contain at least 1 number");
		} else if(!password.contains(SPECIAL)) {
			System.out.println("Error: password must contain at least 1 special character");
			return false;
		}
		return true;
	}
	
	public void viewAccountDetails() {
		System.out.println("Name: " + this.name);
		System.out.println("Account #: " + this.accountNumber);
	}
	void changePassword(String password) {
		if(password.length() <= 6) {
			System.out.println("Password must be longer than 6 characters");
		} else {
			this.password = password;
		}
	}

}
