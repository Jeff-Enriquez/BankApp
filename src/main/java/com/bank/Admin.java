package com.bank;

import java.io.Serializable;

public class Admin extends Account implements Serializable  {
	Admin(String userName, String name, String SSN, String password){
		super(userName, name, SSN, password, "Admin");
	}
}
