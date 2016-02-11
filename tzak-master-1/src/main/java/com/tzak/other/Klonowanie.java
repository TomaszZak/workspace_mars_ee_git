package com.tzak.other;

class E  {

	  private final int[] arr = {1, 2, 3};

	  public E() { }

	  // statyczna metoda fabryczna
	  public static E getInstance(E orgObj) {
	    // nowy obiekt
	    E newInst = new E();
	    // skopiowanie zawartości tablicy
	    // - wugodna metoda arraycopy (zob. dokumentacje)  
	    System.arraycopy(orgObj.arr, 0, newInst.arr, 0, orgObj.arr.length);
	    // zwrot wyniku  
	    return newInst;
	  }

	  // konstruktor kopiujący
	  public E( E orgObj ) {
	    for (int i=0; i<orgObj.arr.length; i++) arr[i] = orgObj.arr[i];
	  }

	  public int[] get() { return arr; }
	  public void set(int i, int val) { arr[i] = val; }

	}

	class Klonowanie {

	  static void show(String nam, int[] arr) {
	    System.out.println(nam);
	    for (int i=0; i<arr.length; i++) System.out.print(" " + arr[i]);
	    System.out.println("");
	  }

	  public static void main(String[] args) {
	    E x = new E();
	    E x1 = new E(x);
	    show("x", x.get());
	    show("x1 - nowy obiekt kopia x" , x1.get());
	    System.out.println("#Zmiana wartości obiektu x. Wstawiamy wartość 10 do drugiej kolumny");
	    x.set(1, 10);
	    show("x1 - głęboka kopia, jest niezależnym obiektem! Wartość x1 nie zmieniła się", x1.get());
	    show("x zmienione jest teraz", x.get());
	    
	    System.out.println("#Teraz tworzymy x2");
	    E x2 = E.getInstance(x);
	    x.set(2, 11);
	    show("x2 - głęboka kopia, jest niezależnym obiektem!", x2.get());
	    show("a x jest teraz", x.get());
	  }


	}
