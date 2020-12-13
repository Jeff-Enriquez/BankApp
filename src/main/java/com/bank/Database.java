package com.bank;

import java.math.BigInteger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Database extends Validate{
	private HashMap<String, Account> userByAccount = new HashMap<String, Account>();
	private HashMap<String, Account> userByUserName = new HashMap<String, Account>();
	private LinkedList<Account> accountRequests = new LinkedList<Account>();
	public Account currentUser;
	Database(){
		//This is so the program will always have an admin and employee
		Admin admin = new Admin("Admin", "Admin", "000-00-0000", "admin1234");
		userByUserName.put(admin.userName, admin);
		Employee employee = new Employee("Employee", "Employee", "000-00-0000", "employee1234");
		userByUserName.put(employee.userName, employee);
	}
	
	public void login(String userName, String password) {
		if(!this.userByUserName.containsKey(userName)) {
			System.out.println(ANSI.RED + "The User Does Not Exist." + ANSI.RESET);
		} else {
			Account account = this.userByUserName.get(userName);
			if(!account.getPassword().equals(password)) {
				System.out.println(ANSI.RED + "The Password Is Incorrect." + ANSI.RESET);
			} else {
				this.currentUser = account;
				System.out.println("You have successfully logged in.");
			}
		}
	}
	
	public void logout() {
		this.currentUser = null;
		System.out.println("You have successfully logged out.");
	}
	
	private String getNewAccountNumber() {
		int max = 5;
	    Random randNum = new Random();
	    byte[] b = new byte[max];
	    randNum.nextBytes(b);
	    BigInteger bigInt = new BigInteger(b);
	    return (String) bigInt.abs().toString().subSequence(0,10);
	}
	
	public boolean requestAccount(Account account) {
		boolean isValid = false;
		isValid = this.isUserNameValid(account.userName);
		if(isValid) {
			this.accountRequests.add(account);
			System.out.println("Your request has been submitted");
		} else {
			System.out.println(ANSI.RED + "Request denied because username is already taken." + ANSI.RESET);
		}
		return isValid;
	}
	
	public void viewRequest(Account account) {
		if(currentUser.getAccountType() == "Admin") {
			System.out.println(this.accountRequests.peek().toString());
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void approveRequest(Account account) {
		if(currentUser.getAccountType() == "Admin") {
			if(accountRequests.isEmpty()) {
				System.out.println(ANSI.YELLOW + "Error: There are no requests to be approved." + ANSI.RESET);
			} else {
				Account approvedAccount = this.accountRequests.pop();
				this.addUser(approvedAccount);
				System.out.println(ANSI.BLUE_BACKGROUND + "The account for " + 
						ANSI.WHITE + approvedAccount.toString() + ANSI.BLACK +
						" has been approved." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + account.getAccountType() + ANSI.RESET);
		}
	}
	
	private void addUser(Account account) {
		if(account.getAccountType() == "Customer") {
			String accountNumber = this.getNewAccountNumber();
			account.addAccount(accountNumber);
			userByAccount.put(accountNumber, account);
		} 
		userByUserName.put(account.userName, account);
	}

	public void addAccount() {
		String accountNumber = this.getNewAccountNumber();
		currentUser.addAccount(accountNumber);
	}
	
	public boolean deposit(Double cash, String accountNumber) {
		return currentUser.deposit(cash, accountNumber);
	}
	
	public boolean withdraw(Double cash, String accountNumber) {
		return currentUser.withdraw(cash, accountNumber);
	}
	
	public boolean hasFunds(Double cash, String accountNumber) {
		return currentUser.hasFunds(cash, accountNumber);
	}
	
	public boolean isUserNameValid(String username) {
		boolean isValid = true;
		if(this.userByUserName.containsKey(username)) {
			isValid = false;
		}
		return isValid;
	}
}
