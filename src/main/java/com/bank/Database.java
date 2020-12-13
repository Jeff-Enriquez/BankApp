package com.bank;

import java.math.BigInteger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Database implements Serializable {
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
	
	public boolean login(String userName, String password) {
		boolean isLogin = false;
		if(!this.userByUserName.containsKey(userName)) {
			System.out.println(ANSI.RED + "The User Does Not Exist." + ANSI.RESET);
		} else {
			Account account = this.userByUserName.get(userName);
			if(!account.getPassword().equals(password)) {
				System.out.println(ANSI.RED + "The Password Is Incorrect." + ANSI.RESET);
			} else {
				this.currentUser = account;
				System.out.println("You have successfully logged in.");
				isLogin = true;
			}
		}
		return isLogin;
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
			System.out.println(ANSI.PURPLE + "Your application has been submitted and will be reviewed. Thank you!" + ANSI.RESET);
		} else {
			System.out.println(ANSI.RED + "Request denied because username is already taken." + ANSI.RESET);
		}
		return isValid;
	}
	
	public boolean isRequest() {
		boolean isRequest = false;
		if(currentUser.getAccountType().equals("Admin")) {
			if(this.accountRequests.isEmpty()) {
				System.out.println("There are no applications pending.");
			} else {				
				System.out.println("There are currently " + this.accountRequests.size() + " applications pending. The current pending application is: ");
				System.out.println(this.accountRequests.peek().toString());
				isRequest = true;
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
		return isRequest;
	}
	
	public void approveRequest() {
		if(currentUser.getAccountType().equals("Admin")) {
			if(this.accountRequests.isEmpty()) {
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
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void denyRequest() {
		if(currentUser.getAccountType().equals("Admin")) {
			if(this.accountRequests.isEmpty()) {
				System.out.println(ANSI.YELLOW + "Error: There are no requests to be denied." + ANSI.RESET);
			} else {
				Account approvedAccount = this.accountRequests.pop();
				System.out.println(ANSI.BLUE_BACKGROUND + "The account for " + 
						ANSI.WHITE + approvedAccount.toString() + ANSI.BLACK +
						" has been denied." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	private void addUser(Account account) {
		if(account.getAccountType().equals("Customer")) {
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
