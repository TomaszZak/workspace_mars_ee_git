package com.tzak.other.lambda;

public class LabmbdaTest1 {

	public static void main(String[] args) {

		LabmbdaTest1 scenariuszTestowy1 = new LabmbdaTest1();
		scenariuszTestowy1.testLambda1();
		scenariuszTestowy1.testLambda2();
		scenariuszTestowy1.testLambda3BlokLambda();

	}

	
	
	public void testLambda1() {
		System.out.println("\n" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		LambdaInterfaceWartosc testowaLiczba;
		
		// przypisujemy wyrazenie lambda do interfejsu
		testowaLiczba = (a) -> 1987 * 10;
		
		// odwolujemy sie do naszego wyrazenia lambda
		System.out.println(testowaLiczba.wartosc(5));
		
		//przypisujemy inne wyrazenie lambda do tego samego interfejsu
		testowaLiczba = (a) -> 12 * 34;
		System.out.println(testowaLiczba.wartosc(5));
		
		LambdaInterfaceWartosc silnia = (n) -> {
			int r = 1;
			for( int i=1; i<=n; i++) {
				r = i*r;
			}
			return r;
		};
		System.out.println("Silnia z 5 to: " + silnia.wartosc(5));

	}
	
	public void testLambda2() {
		System.out.println("\n" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		LambdaInterface2Argumenty dzialanieMatematyczne;
		
		//przypisanie wyrazenia lambda
		dzialanieMatematyczne = (liczba1, liczba2) -> (liczba1 / liczba2);
		System.out.println(dzialanieMatematyczne.wynik(1000, 17));

		dzialanieMatematyczne = (liczba3, liczba4) -> ((liczba3 + liczba4) * liczba3);
		System.out.println(dzialanieMatematyczne.wynik(17, 34));
		
	}
	
	public void testLambda3BlokLambda() {
		System.out.println("\n" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		LambdaInterface2Argumenty blokDzialanMatematycznych;
		
		//Czasami potrzebujamy złożonego ciała lambdy, którą nazywamy "blok lambdy" i używamy do tego nawiasów klamrowych.
		//Ważne jest aby użyć słowa kluczowego return na końcu wyrażenia.
		blokDzialanMatematycznych = (liczba1, liczba2) -> {
			double wynik = 0;
			for (int i=1; i<=100; i++) {
				wynik = wynik + 1 + liczba1 / liczba2;
				wynik = wynik * 1.5;
			}
			
			return wynik;
		};
		
		System.out.println(blokDzialanMatematycznych.wynik(23, 12));
		
	}

}
