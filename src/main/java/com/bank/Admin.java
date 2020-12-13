package com.bank;

public class Admin extends Account {
	Admin(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Admin");
	}
}
