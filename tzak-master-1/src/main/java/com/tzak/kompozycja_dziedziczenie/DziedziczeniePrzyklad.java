package com.tzak.kompozycja_dziedziczenie;

public class DziedziczeniePrzyklad {
	public static void main(String[] args) {
		BeerBoxDziedziczenie bb = new BeerBoxDziedziczenie(12);
		int n = bb.getCount();  //wywoływana metoda klasy głównej
		System.out.println("Liczba butelek: " + n);
	}
}

class BottleContainerDziedziczenie {

	private int bottlesCount;

	public BottleContainerDziedziczenie(int n) {
		bottlesCount = n;
	}

	public int getCount() {
		return bottlesCount;
	}

	public String toString() {
		return "BottleContainer, bottles = " + bottlesCount;
	}
}

class BeerBoxDziedziczenie extends BottleContainerDziedziczenie {

	public BeerBoxDziedziczenie(int n) {
		super(n);
	}

	public String toString() {
		return "BeerBox, bottles = " + getCount();
	}
}