package com.tzak.main.klasy_abstrakcyjne.przyklad1;

public class Dziadek extends Emeryt{

	String str = "Dziadek krzyczy";
	
	@Override
	public String krzyczNaDzieci() {
		System.out.println(str);
		return str;
	}
	
	//można przeciązyć metodę klasy abstrakcyjnej
/*	public static void biegnijDoSklepu(int odleglosc, int predkosc) {
        double czas = (double)odleglosc/predkosc;
        System.out.println("DZIADEK biegnie po kiełbase i będzie za "+czas);
    }*/

}
