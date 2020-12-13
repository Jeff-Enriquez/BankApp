package com.bank;

public class Employee extends Account{
	Employee(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Employee");
	}
}
