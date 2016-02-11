package com.tzak.other;

public class KlasyNiezmienneTest {

	public static void main(String[] args) {
		
		Integer testowaLiczbaGlowna = 0;
		
/*		KlasaTestowa[] tablicaKlasTestowych = new KlasaTestowa[100];
		for (int i = 0; i<10; i++) {
			testowaLiczbaGlowna = i;
			KlasaTestowa obiektKlasy = new KlasaTestowa(testowaLiczbaGlowna);
			tablicaKlasTestowych[testowaLiczbaGlowna] = obiektKlasy;
			System.out.println("Wartość " + i + " elementu tablicy" + tablicaKlasTestowych[i].getLiczbaTestowa());
			System.out.println("Wartość 0 elementu z tablicy: " + tablicaKlasTestowych[0].getLiczbaTestowa());
		}*/
		
		System.out.println("1 Stan testowej liczby głównej: " + testowaLiczbaGlowna);
		KlasaTestowaPodstawowa obiektKlasy = new KlasaTestowaPodstawowa(testowaLiczbaGlowna);
		System.out.println(obiektKlasy.getLiczbaTestowa());
		
		testowaLiczbaGlowna++;
		System.out.println("1 Stan testowej liczby głównej: " + testowaLiczbaGlowna);
		System.out.println(obiektKlasy.getLiczbaTestowa());

		
		
		
		
		
		//###############
/*		System.out.println();
		 // Wymiary pudełka na płyty
	    Dimension d = new Dimension(50, 50);
	    // ... tworzymy pudełka na płyty
	    // ...
	    Box b10 = new Box(d, "Płyty 10");
	    b10.show();
	    // pudełko na książki będzie szersze o 20 cm
	    d.width += 20;
	    Box bk = new Box(d, "Ksiązki");
	    bk.show();
	    System.out.println("Co się stało z pudełkiem na płyty?");
	    b10.show();
		
		*/
		
		System.out.println("### Nowy test");
		System.out.println("Tworzenie klasy podstawowej");
		KlasaTestowaPodstawowa klasaPodstawowa = new KlasaTestowaPodstawowa(0);
		System.out.println("1 Wartosc liczby testowej - klasaPodstawowa: " + klasaPodstawowa.getLiczbaTestowa());
		
		System.out.println("Tworzenie klasy glownej");
		KlasaTestowaGlowna klasaGlowna = new KlasaTestowaGlowna(klasaPodstawowa);
		System.out.println("2 Wartosc liczby testowej - klasaGlowna: " + klasaGlowna.pobierzLiczbeTestowaZKlasyPodstawowej());
		
		System.out.println("Zmiana wartosci liczby testowej w klasie podstawowej");
		klasaPodstawowa.liczbaTestowa++;
		
		System.out.println("3 Wartosc liczby testowej - klasaPodstawowa: " + klasaPodstawowa.getLiczbaTestowa());
		System.out.println("4 Wartosc liczby testowej - klasaGlowna: " + klasaGlowna.pobierzLiczbeTestowaZKlasyPodstawowej());
		System.out.println("4 Wartosc liczby testowej - liczbaTestowaWKlasieGlownej: " + klasaGlowna.liczbaTestowaWKlasieGlownej);
		
		
	}

	
}

class KlasaTestowaPodstawowa {
	int liczbaTestowa;

	public KlasaTestowaPodstawowa(int liczbaTest) {
		liczbaTestowa = liczbaTest;
	}

	public int getLiczbaTestowa() {
		return liczbaTestowa;
	}

	public void setLiczbaTestowa(int liczbaTestowa) {
		this.liczbaTestowa = liczbaTestowa;
	}
}

class KlasaTestowaGlowna {
	KlasaTestowaPodstawowa klasaPodst;
	int liczbaTestowaWKlasieGlownej;

	public KlasaTestowaGlowna(KlasaTestowaPodstawowa parametrKlasa) {
		klasaPodst = parametrKlasa;
		liczbaTestowaWKlasieGlownej = parametrKlasa.getLiczbaTestowa();
	}

	public void show() {
		System.out.println("Pudełko: " + klasaPodst.getLiczbaTestowa());
	}
	
	public int pobierzLiczbeTestowaZKlasyPodstawowej() {
		return klasaPodst.getLiczbaTestowa();
	}
}







class Dimension {
	  public int width;
	  public int height;
	  public Dimension(int w, int h) {
	    width = w;
	    height = h;
	  }
	}

class Box {
	  Dimension dim;
	  String cont;

	  public Box(Dimension d, String c) {
	    dim = d;
	    cont = c;
	  }

	  public void show() {
	    System.out.println("Pudełko: " + dim.width + "x" + dim.height +
	                       " Zawartość: " + cont);
	  }
	}


