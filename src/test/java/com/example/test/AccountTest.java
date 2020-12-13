package com.example.test;

import static org.junit.Assert.*;

import org.junit.Test;
import com.bank.Database;

public class AccountTest {
	
	@Test
	public void isNameValid() {
		assertEquals(Database.isNameValid("Name"), true);
		assertEquals(Database.isNameValid("name"), true);
		assertEquals(Database.isNameValid("234"), false);
		assertEquals(Database.isNameValid("%@$^"), false);
		assertEquals(Database.isNameValid("john smith"), false);
		assertEquals(Database.isNameValid(""), false);
		assertEquals(Database.isNameValid("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"), false);
	}
	
	@Test
	public void isSSNValid() {
		assertEquals(Database.isSSNValid("123-12-1234"), true);
		assertEquals(Database.isSSNValid("123121234"), false);
		assertEquals(Database.isSSNValid("1x2-s2-g)33"), false);
		assertEquals(Database.isSSNValid("***-**-****"), false);
		assertEquals(Database.isSSNValid("123412-123412-123412"), false);
		assertEquals(Database.isSSNValid(""), false);
	}
	
	@Test
	public void isPasswordValid() {
		assertEquals(Database.isPasswordValid("J3ff)rey"), true);
		assertEquals(Database.isPasswordValid("j3ff)rey"), false);
		assertEquals(Database.isPasswordValid("J3FF)REY"), false);
		assertEquals(Database.isPasswordValid("J3ffrey"), false);
		assertEquals(Database.isPasswordValid("Jeff)rey"), false);
		assertEquals(Database.isPasswordValid(""), false);
	}

}
