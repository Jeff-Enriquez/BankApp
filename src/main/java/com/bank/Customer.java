package com.bank;

import java.util.HashMap;

public class Customer extends Account {
	private HashMap<String, Double> accounts = new HashMap<String, Double>();
	// private transaction history
	
	Customer(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Customer");
	}
	
	public void addAccount(String accountNumber) {
		this.accounts.put(accountNumber, 0.0);
	}
	
	public void getAccounts() {
		System.out.println("You have " + accounts.size() + " account(s)");
		this.accounts.forEach((account, balance) -> {
			System.out.println("Account: " + account + " " + "Balance: " + balance);
		});
	}
	
	public boolean deposit(Double cash, String accountNumber) {
		boolean isDeposited = false;
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else if(cash <= 0) {
			System.out.println(ANSI.RED + "Error: deposit must be greater than 0" + ANSI.RESET);
		} else {
			Double balance = this.accounts.get(accountNumber) + cash;
			this.accounts.put(accountNumber, balance);
			System.out.println("Your new account balance for '" + accountNumber + "' is: " + balance);
			isDeposited = true;
			// add to transaction history
		}
		return isDeposited;
	}
	
	public boolean withdraw(Double cash, String accountNumber) {
		boolean isWithdrawn = false;
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else {
			Double balance = this.accounts.get(accountNumber) + cash;
			if(cash > balance) {
				System.out.println(ANSI.RED + "Insufficient Funds in account " + accountNumber);
				System.out.println(ANSI.RED + "Your withdraw must be less than your balance of: " + balance + ANSI.RESET);
			} else {
				balance -= cash;
				this.accounts.put(accountNumber, balance);
				System.out.println("Your new account balance for '" + accountNumber + "' is: " + balance);
				isWithdrawn = true;
			}
			// add to transaction history
		}
		return isWithdrawn;
	}
	
	public boolean hasFunds(Double cash, String accountNumber) {
		boolean hasFunds = false;
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else if(cash >= this.accounts.get(accountNumber)) {
			hasFunds = true;
		}
		return hasFunds;
	}
}
