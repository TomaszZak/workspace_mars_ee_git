package com.tzak.kompozycja_dziedziczenie;

/*
 * Mamy tu dwie klasy, które tworzą "zestaw ponownego użycia": klasę BottleContainer i BeerBox. 
 * Przez inne klasy (np. Compos1) bezpośrednio używana jest  klasa BeerBox. Klasa BottleContainer używana jest pośrednio. 
 * I teraz, jeśli  - podobnie jak w przykładzie z dziedziczeniem - w  klasie BottleContainer zmienimy interfejs 
 * (czyli np. zmienimy typ zwracanego przez metodę getCount() wyniku na double), 
 * to - będziemy musieli zmienić coś w implementacji klas bezpośrednio wykorzystywanych przez inne klasy 
(tu: BeerBox), ale ich interfejsy pozostaną bez zmian i w związku z tym nie trzeba będzie  nic zmieniać w klasach wykorzystujących nasz zestaw klas pojemników na butelki:
 */

/*
 * Jest to sytuacja korzystniejsza w stosunku do "słabej hermetyzacji" kodu przy dziedziczeniu, bowiem - w końcu - zwykle klasy takie jak BottleContainer i BeerBox są kontrolowane przez twórców jakiegoś API, zaś użytkownicy API zwykle korzystają z klas bezpośredniego ponownego użycia (w naszym przykładzie BeerBox).

Oczywiście, przy dziedziczeniu łatwiejsze niż przy kompozycji jest dodawanie nowych klas jakiegoś API (w naszym  przykładzie np. lodówek, półek z wodą mineralną, pojemników z Colą itp.). Łatwiejsze i bardziej naturalne jest też zastosowanie polimorfizmu.

No, właśnie, czy kompozycja w ogóle  umożliwia wykorzystanie odwołań polimorficznych?
Na pierwszy rzut oka - nie bardzo.
 */

public class KompozycjaPrzyklad {

	public static void main(String[] args) {
		BeerBoxKompozycja bb = new BeerBoxKompozycja(17);
		int n = bb.getCount();  //wywoływana metoda klasy pochodnej
		System.out.println("Liczba butelek: " + n);
	}
}

class BottleContainerKompozycja {

	private double bottlesCount;

	public BottleContainerKompozycja(int n) {
		bottlesCount = n;
	}

	public double getCount() {
		return bottlesCount;
	}

}

class BeerBoxKompozycja {

	// wykorzystanie klasy BottleContainer przez kompozycję

	private BottleContainerKompozycja cont;

	public BeerBoxKompozycja(int n) {
		cont = new BottleContainerKompozycja(n);
	}

	public int getCount() {
		return (int) cont.getCount();
	}

}

