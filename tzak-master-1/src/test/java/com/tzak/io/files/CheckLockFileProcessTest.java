package com.tzak.io.files;

import static org.junit.Assert.*;

import org.junit.Test;

public class CheckLockFileProcessTest {

	@Test
	public void testFunckjaTestowa() {
		CheckLockFileProcess check1 = new CheckLockFileProcess();
		String result = check1.fuknkcjaTestowa("-----przykładowy tekst");
		
		//wskazuje na oczekiwany wynik
		assertEquals("funkcja testowa + parametr:-----przykładowy tekst", result);
	}

}
