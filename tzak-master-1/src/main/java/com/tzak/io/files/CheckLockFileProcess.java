package com.tzak.io.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckLockFileProcess {

	  String workingDir = System.getProperty("user.dir");
	  String fileName = "testTextFile.txt";
	  String pathFiles = workingDir + "//files//";
	  File file = new File(pathFiles + fileName);
	 
	 String nameOfLockingProcess;
	 String nameOfLocikngUser;
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		CheckLockFileProcess check1 = new CheckLockFileProcess();
		check1.wypiszPlikNaConsole();
		if (check1.isFileUnlocked(check1.file)) System.out.println("Plik nie jest zablokowany");
		else System.out.println("Plik jest zablokowany: nazwa procesu: " + check1.nameOfLockingProcess + " nazwa uzytkownika: " + check1.nameOfLocikngUser);
		
	}

	public boolean isFileUnlocked(File file) {

		FileChannel channel;
		try {
			channel = new RandomAccessFile(file, "rw").getChannel();
		} catch (FileNotFoundException e) {
			System.out.println("#### Plik jest zablokowany przez inny proces !!!!");
			e.printStackTrace();
			nazwaBlokujacegoProcesuWindows(file);
			return false;
		}

		// Use the file channel to create a lock on the file. This method blocks until it can retrieve the lock.
		FileLock lock;
		try {
			lock = channel.lock();
			lock = channel.tryLock();

			// Release the lock - if it is not null!
			if (lock != null) {
				lock.release();
			}
			// Close the file
			channel.close();

		} catch (OverlappingFileLockException e) {
			// File is already locked in this thread or virtual machine
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Metoda uzupełnia nazwę procesu i uzytkownika blokujacego plik
	 * Metoda wykorzystuje aplikacje Windowsową Handle.exe i następnie filtruje wynik i wyciąga nazwę procesu i usera
	 * @param plik - obiekt File który jest blokowany przez inny proces
	 */
	public void nazwaBlokujacegoProcesuWindows(File plik) {
		try {
			String line;
			// program handle.exe sprawdza jaki proces blokuje wskazany plik
			Process p = Runtime.getRuntime().exec(pathFiles + "Handle.exe -a -u " + plik.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

			int numerLiniWyniku = 1;
			while ((line = input.readLine()) != null) {
				// System.out.println(line); //<-- Parse data here.
				StringTokenizer tokens = new StringTokenizer(line);
				if (numerLiniWyniku == 6) {  //w tej lini program Handle.exe zwraca informacje dotyczące nazwy parocesu, PID, ściezki, usera itd
					System.out.println(line);
					int numerTokena = 1;
					while (tokens.hasMoreTokens()) {

						String token = tokens.nextToken();
						if (numerTokena == 1) {  //numer tokena odpowiedzialny za nazwę blokującego procesu
							nameOfLockingProcess = token;
							 
						} else if (numerTokena == 6) { //numer tokena odpowiedzialny za nazwę uzytkownika blokujacego procesu
							nameOfLocikngUser = token;
						}
						numerTokena++;
					}
				}
				numerLiniWyniku++;
			}

			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
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
