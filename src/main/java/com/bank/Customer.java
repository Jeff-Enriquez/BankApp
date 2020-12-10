package com.bank;

public class Customer extends Account {
	private Double balance = 0.0;
	// private transaction history
	
	Customer(String name, Integer accountNumber, String SSN, String password){
		super(name, accountNumber, SSN, password);
	}
	
	public void deposit(Double cash) {
		if(cash <= 0) {
			System.out.println("Error: deposit must be greater than 0");
		} else {
			this.balance += cash;
			// add to transaction history
		}
	}
	
	public void withdraw(Double cash) {
		if(cash > this.balance) {
			System.out.println("Insuffienct Funds: Your balance is " + this.balance);
		} else {
			this.balance -= cash;
			// add to transaction history
		}
	}
	
	public void transfer(Double cash, Integer accountNumber) {
		if(cash <= 0) {
			System.out.println("Error: transfer must be greater than 0");
		}
		if(cash < this.balance) {
			System.out.println("Insuffienct Funds: Your balance is " + this.balance);
		} 
		// else if - check that accountNumber exists
		// else - 
			// remove the cash from this account
			// add the cash to the other account
	}
}
