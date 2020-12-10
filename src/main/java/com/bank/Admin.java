package com.bank;

public class Admin extends Account{
	
	Admin(String name, Integer accountNumber, String SSN, String password){
		super(name, accountNumber, SSN, password);
	}
	
	public void transferFromTo(Double cash, Integer accountNumber1, Integer accountNumber2) {
		if(cash <= 0) {
			System.out.println("Error: transfer must be greater than 0");
		}
		// Check if account 1 exists
		// Check if account 1 has sufficient funds
		// Check if account 2 exists
		// Transfer funds
	}
	
}
