package com.tzak.immutable;

public class MainKlasTest1 {

	public static void main(String[] args) {


		int liczba1 = 5;
		int liczba2 = liczba1;
		liczba1++;
		System.out.println(liczba1);
		System.out.println(liczba2);
		
		System.out.println("########");
		
		Integer liczba4 = new Integer(3);
		Integer liczba5 = liczba4;
		System.out.println("Jest to ten sam obiekt");
		System.out.println(liczba4 + " ID obiektu: " + Integer.toHexString(System.identityHashCode(liczba4)));
		System.out.println(liczba5 + " ID obiektu: " + Integer.toHexString(System.identityHashCode(liczba5)));
		liczba4++;
		System.out.println("Teraz są to już inne obiekty");
		System.out.println(liczba4 + " ID obiektu: " + Integer.toHexString(System.identityHashCode(liczba4)));
		System.out.println(liczba5 + " ID obiektu: " + Integer.toHexString(System.identityHashCode(liczba5)));
		

	}

}
