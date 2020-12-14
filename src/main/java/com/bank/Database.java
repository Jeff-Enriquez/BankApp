package com.bank;

import java.math.BigInteger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jdk.internal.org.jline.utils.Log;

public class Database implements Serializable {
	private static Database instance;
	private static Logger logger = LogManager.getLogger(Database.class);
	// userByAccount: you can find the user name by account number
	private HashMap<String, String> userByAccount = new HashMap<String, String>();
	// userByUserName: you can find the user account by user name
	private HashMap<String, Account> userByUserName = new HashMap<String, Account>();
	private LinkedList<Account> accountRequests = new LinkedList<Account>();
	private LinkedList<String> jointAccountRequest = new LinkedList<String>();
	public Account currentUser;
	
	private Database(){
		//This is so the program will always have an admin and employee
		Admin admin = new Admin("Admin", "Admin", "000-00-0000", "admin1234");
		userByUserName.put(admin.userName, admin);
		Employee employee = new Employee("Employee", "Employee", "000-00-0000", "employee1234");
		userByUserName.put(employee.userName, employee);
	}
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
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
	
	public void requestJointAccount() {
		if(this.currentUser.getAccountType().equals("Customer")) {
			jointAccountRequest.add(this.currentUser.userName);
		}
	}
	
	public boolean isJointRequest() {
		boolean isRequest = false;
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(this.jointAccountRequest.isEmpty()) {
				System.out.println("There are no applications pending.");
			} else {				
				System.out.println("There are currently " + this.jointAccountRequest.size() + " applications pending.");
				System.out.println("User: " + this.jointAccountRequest.peek() + "would like a joint account.");
				isRequest = true;
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
		return isRequest;
	}
	
	public void approveJointRequest() {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(this.accountRequests.isEmpty()) {
				System.out.println(ANSI.YELLOW + "Error: There are no requests to be approved." + ANSI.RESET);
			} else {
				String userName = this.jointAccountRequest.pop();
				String newAccountNum = getNewAccountNumber();
				this.userByAccount.put(newAccountNum, userName);
				Account account = this.userByUserName.get(userName);
				account.addAccount(newAccountNum);
				System.out.println(ANSI.PURPLE + "The account '" + newAccountNum + "' has been added for " + userName + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void denyJointRequest() {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(this.accountRequests.isEmpty()) {
				System.out.println(ANSI.YELLOW + "Error: There are no requests to be approved." + ANSI.RESET);
			} else {
				String userName = this.jointAccountRequest.pop();
				System.out.println(ANSI.PURPLE + "The user: "+ userName + "has been denied a joint account." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
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
			this.userByUserName.put(account.userName, null);
			System.out.println(ANSI.PURPLE + "Your application has been submitted and will be reviewed. Thank you!" + ANSI.RESET);
		} else {
			System.out.println(ANSI.RED + "Request denied because username is already taken." + ANSI.RESET);
		}
		return isValid;
	}
	
	public boolean isRequest() {
		boolean isRequest = false;
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
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
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
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
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(this.accountRequests.isEmpty()) {
				System.out.println(ANSI.YELLOW + "Error: There are no requests to be denied." + ANSI.RESET);
			} else {
				Account account = this.accountRequests.pop();
				this.userByUserName.remove(account.userName);
				System.out.println(ANSI.BLUE_BACKGROUND + "The account for " + 
						ANSI.WHITE + account.toString() + ANSI.BLACK +
						" has been denied." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	private void addUser(Account account) {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(account.getAccountType().equals("Customer")) {
				String accountNumber = this.getNewAccountNumber();
				account.addAccount(accountNumber);
				userByAccount.put(accountNumber, account.userName);
			} 
			userByUserName.put(account.userName, account);
		} else {			
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void addAccount(String userName) {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(userByUserName.containsKey(userName)) {
				String accountNumber = this.getNewAccountNumber();
				Account account = userByUserName.get(userName);
				account.addAccount(accountNumber);
				userByAccount.put(accountNumber, userName);
				System.out.println("Account was successfully added for " + userName);
			} else {
				System.out.println(ANSI.YELLOW + "The user name does not exist" + ANSI.RESET);				
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);		
		}
	}
	
	public void withdraw(Double cash, String accountNum) {
		if(!currentUser.getAccountType().equals("Admin")) {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
			logger.warn("User: " + currentUser.userName + " tried to make a withdraw through the database instead of from personal account.");
		} else {
			if(userByAccount.containsKey(accountNum)) {				
				String userName = userByAccount.get(accountNum);
				Account userAccount = userByUserName.get(userName);
				userAccount.withdraw(cash, accountNum);
				logger.info("Admin: " + currentUser.userName + " withdrew " + cash + " from" 
						+ "\nuser: " + userName 
						+ "\naccount number: " + accountNum);
			} else {
				System.out.println(ANSI.RED + "Account number: " + accountNum + " does not exist." + ANSI.RESET);
			}
		}
	}
	
	public void deposit(Double cash, String accountNum) {
		if(currentUser.getAccountType().equals("Admin")) {
			if(userByAccount.containsKey(accountNum)) {				
				String userName = userByAccount.get(accountNum);
				Account userAccount = userByUserName.get(userName);
				userAccount.deposit(cash, accountNum);
				logger.info("Admin: " + currentUser.userName + " deposited " + cash + " to" 
						+ "\nuser: " + userName 
						+ "\naccount number: " + accountNum);
			} else {
				System.out.println(ANSI.RED + "Account number: " + accountNum + " does not exist." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
			logger.warn("User: " + currentUser.userName + " tried to make a withdraw through the database instead of from personal account.");
		}
	}
	
	public void transfer(Double cash, String accountNum1, String accountNum2) {
		if(currentUser.getAccountType().equals("Admin")) {
			if(!userByAccount.containsKey(accountNum1)) {
				System.out.println(ANSI.YELLOW + "Account number: " + accountNum1 + " does not exist." + ANSI.RESET);
			} else if(!userByAccount.containsKey(accountNum2)) {
				System.out.println(ANSI.YELLOW + "Account number: " + accountNum2 + " does not exist." + ANSI.RESET);
			} else {
				String userName1 = userByAccount.get(accountNum1);
				Account user1 = userByUserName.get(userName1);
				if(user1.hasFunds(cash, accountNum1)) {					
					String userName2 = userByAccount.get(accountNum2);
					Account user2 = userByUserName.get(userName2);
					user1.withdraw(cash, accountNum1);
					user1.getBalance(accountNum1);
					user2.deposit(cash, accountNum2);
					user2.getBalance(accountNum2);
					logger.info("Admin: " + currentUser.userName + " transferred " + cash + " from" 
							+ "\nuser: " + userName1 + " account number: " + accountNum1
							+ "\nto user: " + userName2 + " account number: " + accountNum2);
				}
			}
		} else if(userByAccount.containsKey(accountNum1)) {
			if(userByAccount.get(accountNum1).equals(currentUser.userName)) {				
				if(currentUser.hasFunds(cash, accountNum1)) {			
					if(userByAccount.containsKey(accountNum2)) {
						String userName2 = userByAccount.get(accountNum2);
						Account userAccount2 = userByUserName.get(userName2);
						userAccount2.deposit(cash, accountNum2);
						currentUser.withdraw(cash, accountNum1);
						currentUser.getBalance(accountNum1);
						logger.info("User: " + currentUser.userName + " transferred " + cash + " from own account: " + accountNum1 
								+ "\nto user: " + userName2 + " account number: " + accountNum2);
					} else {
						System.out.println(ANSI.YELLOW + "Error: the account you are transferring to does not exist." + ANSI.RESET);
					}
				}
			} else {
				System.out.println(ANSI.RED + "Error: the account you are transferring from must be your own." + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Error: the account you are transferring from does not exist." + ANSI.RESET);
		}
	}
	
	public void viewAllAccounts() {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			this.userByUserName.forEach((userName, account) -> {
				if(account.getAccountType().equals("Customer")) {					
					System.out.print(ANSI.CYAN + userName + ": " + ANSI.RESET);
					account.printAccounts();
				}
			});
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void viewPersonalAccountInfo(String userName) {
		if(currentUser.getAccountType().equals("Admin") || currentUser.getAccountType().equals("Employee")) {
			if(this.userByUserName.containsKey(userName)) {
				Account account = this.userByUserName.get(userName);
				System.out.print(ANSI.CYAN);
				account.toString();
				System.out.println("Name: " + account.name);
				System.out.println("Password: " + account.getPassword());
				System.out.println("SSN: " + account.getSSN());
				account.printAccounts();
				System.out.print(ANSI.RESET);
			} else {
				System.out.println(ANSI.YELLOW + "The user name does not exist" + ANSI.RESET);
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public void cancelAccount(String accountNum) {
		if(currentUser.getAccountType().equals("Admin")) {
			if(userByAccount.containsKey(accountNum)) {
				String userName = userByAccount.get(accountNum);
				userByAccount.remove(accountNum);
				Account userAccount = userByUserName.get(userName);
				userAccount.removeAccount(accountNum);
				System.out.println(ANSI.PURPLE + "Account: " + accountNum + " was succesfully removed" + ANSI.RESET);
			} else {
				System.out.println(ANSI.YELLOW + "Error: the account " + accountNum + " does not exist");
			}
		} else {
			System.out.println(ANSI.RED + "Permission Error: you must be admin to do this." + ANSI.RESET);
			System.out.println(ANSI.RED + "You are currently signed in as: " + currentUser.getAccountType() + ANSI.RESET);
		}
	}
	
	public boolean isUserNameValid(String username) {
		boolean isValid = true;
		if(this.userByUserName.containsKey(username)) {
			isValid = false;
		}
		return isValid;
	}
}
