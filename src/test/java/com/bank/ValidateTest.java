package com.bank;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateTest {
	/* VALID PASSWORD TESTS*/
	@Test
	public void validPassword() {
		assertEquals(Validate.isPasswordValid("N@m3_will_pass"), true);
	}
	
	@Test
	public void shortPassword() {
		//must be at least 6 characters
		assertEquals(Validate.isPasswordValid("Nam3)"), false);
	}
	
	@Test
	public void noUppercasePassword() {
		assertEquals(Validate.isPasswordValid("n@m3_wont_pass_without_lowercase"), false);
	}
	
	@Test
	public void noLowercasePassword() {
		assertEquals(Validate.isPasswordValid("N@M3_WONT_PASS_WITHOUT_LOWERCASE"), false);
	}
	
	@Test
	public void noSpecialCharPassword() {
		assertEquals(Validate.isPasswordValid("NAM3_wont_pass_without_special_char"), false);
	}
	/* VALID SSN TESTS*/
	@Test
	public void validSSN() {
		assertEquals(Validate.isSSNValid("123-12-1234"), true);
	}
	
	@Test
	public void ssnMustHaveDash() {
		assertEquals(Validate.isSSNValid("123121234"), false);
	}
	
	@Test
	public void ssnCannotHaveChars() {
		assertEquals(Validate.isSSNValid("1x2-s2-g)33"), false);
	}
	
	@Test
	public void ssnFollowsFormat() {
		assertEquals(Validate.isSSNValid("123412-123412-123412"), false);
	}
	
	@Test
	public void ssnCannotBeBlank() {
		assertEquals(Validate.isSSNValid(""), false);
	}
	

}
