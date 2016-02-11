package com.tzak.main.klasy_abstrakcyjne.przyklad1;

public class Test_Klasa_Abstrakcyjna{

	public static void main(String[] args) {


		Dziadek dziadek = new Dziadek();

		//z klasy dziadek
		dziadek.krzyczNaDzieci();
		//Dziadek.biegnijDoSklepu(10, 4);

		//z klasy abstrakcyjnej Emeryt
		System.out.println("Ilosc oczu dziadka: " + Emeryt.ILOSC_OCZU);
		Emeryt.biegnijDoSklepu(10, 4);
	}



}