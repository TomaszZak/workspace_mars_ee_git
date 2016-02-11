package com.tzak.main.klasy_abstrakcyjne.przyklad1;

public abstract class Emeryt {
    public static final int ILOSC_OCZU = 2; //stałe są ok
 
    //metoda abstrakcyjna
    public abstract String krzyczNaDzieci();
 
    //zwykła metoda z implementacją
    public static void biegnijDoSklepu(int odleglosc, int predkosc) {
        double czas = (double)odleglosc/predkosc;
        System.out.println("EMERYT biegnie po kiełbase i bedzie za "+czas);
    }
}
