package com.nb.org.testapp;

import org.junit.Test;

import com.nb.org.util.AppValidator;

public class testValidate {
	@Test
	public void testValidator(){
		
		String url = "htts://a";
		System.out.println(AppValidator.isURL(url));
		
		String issuer = "abbcd==";
		System.out.println(AppValidator.issuerValue("abcc.-_+t"));
		
		
		
	}
	
	
}
