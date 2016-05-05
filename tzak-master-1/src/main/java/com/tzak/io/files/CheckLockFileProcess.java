package com.tzak.io.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckLockFileProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		CheckLockFileProcess check1 = new CheckLockFileProcess();
		check1.wypiszPlikNaConsole();

		
	}

	public void sprawdzCzyZajetyPrzezInnyProces() {
		System.out.println("Pobrano plik");
	}
	
	//java 8
	public void wypiszPlikNaConsole() {
		
		 String workingDir = System.getProperty("user.dir");
		 String fileName = "//files//testTextFile.txt";
		 String path = workingDir + fileName;
		
		List<String> list = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(path))) {

			//1. filter linijka 3
			//2. convert all content to upper case
			//3. convert it into a List
			list = stream
					.filter(line -> !line.startsWith("linijka 3"))  //usuwa z wyników wiersz zaczynajacy się od String = "linijka 3"
					.map(String::toUpperCase)
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		list.forEach(System.out::println);
	}
	
	
	public String fuknkcjaTestowa(String parametr) {
		return "funkcja testowa + parametr:" + parametr;
	}
	
}
