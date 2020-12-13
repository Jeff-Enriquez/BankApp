package com.bank;

public class Admin extends Account {
	Admin(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Admin");
	}
	
	public void getInstructions() {
		System.out.println("Welcome " + this.userName + " would you like to: ");
		System.out.println(ANSI.BLUE + "1) Approve/Deny Accounts");
		System.out.println("2) Withdraw, deposit, transfer");
		System.out.println("3) Cancel accounts" + ANSI.RESET);
	}
}
