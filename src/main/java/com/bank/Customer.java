package com.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Customer extends Account implements Serializable  {
	private HashMap<String, Double> accounts = new HashMap<String, Double>();
	private static Logger logger = LogManager.getLogger(Database.class);
	
	Customer(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Customer");
	}
	
	public void addAccount(String accountNumber) {
		this.accounts.put(accountNumber, 0.0);
	}
	
	public void removeAccount(String accountNumber) {
		this.accounts.remove(accountNumber);
	}
	
	public void printAccounts() {
		if(this.accounts.size() == 0) {
			System.out.println("No Existing Accounts");
		} else {			
			this.accounts.forEach((account, balance) -> {
				System.out.println("Account: " + account + " " + "Balance: " + balance);
			});
		}
	}
	
	public void getBalance(String accountNumber) {
		if(this.accounts.containsKey(accountNumber)) {
			System.out.println("The account balance for '" + accountNumber + "' is: " + this.accounts.get(accountNumber));
		} else {
			System.out.println(ANSI.YELLOW + "The account number does not exist" + ANSI.RESET);
		}
	}
	
	public void deposit(Double cash, String accountNumber) {
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else if(cash <= 0) {
			System.out.println(ANSI.RED + "Error: deposit must be greater than 0" + ANSI.RESET);
		} else {
			Double balance = this.accounts.get(accountNumber) + cash;
			this.accounts.put(accountNumber, balance);
			logger.info("User: " + this.userName + " deposited " + cash + " to account: " + accountNumber);
		}
	}
	
	public void withdraw(Double cash, String accountNumber) {
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else {
			Double balance = this.accounts.get(accountNumber);
			if(cash > balance) {
				System.out.println(ANSI.RED + "Insufficient Funds in account " + accountNumber);
				System.out.println(ANSI.RED + "Your withdraw must be less than your balance of: " + balance + ANSI.RESET);
			} else {
				balance -= cash;
				this.accounts.put(accountNumber, balance);
				System.out.println("The new account balance for '" + accountNumber + "' is: " + balance);
				logger.info("User: " + this.userName + " withdrew " + cash + " from account: " + accountNumber);
			}
		}
	}
	
	public boolean hasFunds(Double cash, String accountNumber) {
		boolean hasFunds = false;
		if(!this.accounts.containsKey(accountNumber)) {
			System.out.println(ANSI.RED + "Error: The account '" + accountNumber + "' does not exist." + ANSI.RESET);
		} else if (cash > this.accounts.get(accountNumber)) {
			System.out.println(ANSI.RED + "Insufficient Funds in account " + accountNumber);
		} else {
			hasFunds = true;
		}
		return hasFunds;
	}
}
