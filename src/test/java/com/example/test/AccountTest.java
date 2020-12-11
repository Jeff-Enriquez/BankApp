package com.example.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.*;

import com.bank.Account;

public class AccountTest {
	
	@Test
	public void isNameValid() {
		assertEquals(Account.isNameValid("Name"), true);
		assertEquals(Account.isNameValid("name"), true);
		assertEquals(Account.isNameValid("234"), false);
		assertEquals(Account.isNameValid("%@$^"), false);
		assertEquals(Account.isNameValid("john smith"), false);
		assertEquals(Account.isNameValid(""), false);
		assertEquals(Account.isNameValid("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"), false);
	}
	
	@Test
	public void isSSNValid() {
		assertEquals(Account.isSSNValid("123-12-1234"), true);
		assertEquals(Account.isSSNValid("123121234"), false);
		assertEquals(Account.isSSNValid("1x2-s2-g)33"), false);
		assertEquals(Account.isSSNValid("***-**-****"), false);
		assertEquals(Account.isSSNValid("123412-123412-123412"), false);
		assertEquals(Account.isSSNValid(""), false);
	}
	
	@Test
	public void isPasswordValid() {
		assertEquals(Account.isPasswordValid("J3ff)rey"), true);
		assertEquals(Account.isPasswordValid("j3ff)rey"), false);
		assertEquals(Account.isPasswordValid("J3FF)REY"), false);
		assertEquals(Account.isPasswordValid("J3ffrey"), false);
		assertEquals(Account.isPasswordValid("Jeff)rey"), false);
		assertEquals(Account.isPasswordValid(""), false);
	}

}
