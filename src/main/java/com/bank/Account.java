package com.bank;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
	public String userName;
	public String name;
	private String SSN;
	private String password;
	private String accountType;
	
	Account(String userName, String name, String SSN, String password, String accountType){
		this.userName = userName;
		this.name = name;
		this.SSN = SSN;
		this.password = password;
		this.accountType = accountType;
	}
	
	String getAccountType() {
		return this.accountType;
	}
	
	String getPassword() {
		return this.password;
	}
	
	String getSSN() {
		return this.SSN;
	}
	
	public void printAccounts() {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
	}
	
	public void removeAccount(String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
	}
	
	public void addAccount(String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
	}
	
	public void getBalance(String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
	}
	
	public boolean deposit(Double cash, String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
		return false;
	}
	
	public boolean withdraw(Double cash, String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
		return false;
	}
	
	public boolean hasFunds(Double cash, String accountNumber) {
		System.out.println(ANSI.RED + "Not applicable for account type: " + this.accountType + ANSI.RESET);
		return false;
	}
	
	@Override
	public String toString() {
		return "User Name: " + userName + "\nAccount Type: " + accountType;
	}

	
}
