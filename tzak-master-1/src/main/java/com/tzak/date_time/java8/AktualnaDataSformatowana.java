package com.tzak.date_time.java8;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AktualnaDataSformatowana {

	public static void main(String[] args) {
		LocalDateTime today = LocalDateTime.now();
		String todaySformatowane;
		todaySformatowane = today.format(DateTimeFormatter.ofPattern("d:MMM:uuuu HH:mm:ss"));
		System.out.println(todaySformatowane);
		todaySformatowane = today.format(DateTimeFormatter.ofPattern("dd:MM:uuuu HH:mm:ss"));
		System.out.println(todaySformatowane);

		System.out.println(" Koniec działania programu");
	}
}
