//8. Współbieżna Java: zadania i wykonawcy
//http://edu.pjwstk.edu.pl/wyklady/zap/scb/
//http://edu.pjwstk.edu.pl/wyklady/zap/scb/
package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import java.io.Console;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WykonawcaZadanMain {

	public static void main(String[] args) {
			
		
//		W Javie mamy do dyspozycji kilka rodzajów gotowych Wykonawców, fabrykowanych przez odpowiednie metody klasy Executors m.in.: Wykonawca, prowadzący pulę wątków o zadanych maksymalnych rozmiarach (Executors.newFixedThreadPool()), Wykonawca, prowadzący pulę wątków o dynamicznych rozmiarach (Executors.newCachedThreadPool()), Wykonawcy zarządzający tworzeniem i wykonaniem wątków w określonym czasie lub z określoną periodycznością (Executors.newScheduled....())
//		Zwróćmy uwagę, że pula wątków jest ograniczona do dwóch. Zatem najpierw wspólbieżnie działają dwa pierwsze zadania, a po nich - trzecie i czwarte.
//		Executor exec = Executors.newFixedThreadPool(2);
//		Executor exec = Executors.newCachedThreadPool();
		ExecutorService exec = Executors.newFixedThreadPool(2);


	    for (int i=1; i<=6; i++) {
	      exec.execute(new WykonawcaZadanZadanieDoWykonania("Watek/Zadanie numer: " + i));
	    }
	    Thread.yield();   //yield - pozwala wejść do kolejki innym wątkom jezeli ten jest bezczynny
	    exec.shutdown(); //Gdy nasze zadania zakończą się, Wykonawca nadal "działa" i jest gotowy do przyjmowania nowych zadań. Usługę zamknięcia dostarcza interfejs ExecutorService, który jest rozszerzeniem interfejsu Executor.
//		    zatrzymajWatek(exec);
	    
	    System.err.println("Czy wątek jest zakończony: " + exec.isTerminated());   //Sprawdzenie czy wątek jest zakończony - tutaj jeszcze nie jest
	    
/*		    Za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). Warto stosować metodę awaitTermination(), kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył działanie i wyczyścił wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć wcześniej niż Wykonawcy i aplikacja nie zakończy działania).*/
	    try {
	        exec.awaitTermination(5, TimeUnit.SECONDS);
	        } catch(InterruptedException exc) { 
	    	  exc.printStackTrace(); 
	    	  }
	      System.err.println("Czy wątek jest zakończony: " + exec.isTerminated());

	  //  System.err.println("Koniec działania programu");
	}
	
	/**Wątki zostaną zatrzymane i zadania zakończone. */
	public static void zatrzymajWatek(ExecutorService exec) {
	    try {
	        Thread.sleep(1000);
	        } catch(Exception exc) {}

	    //ExecutorService dostarcza także metody shutdownNow(), która ma za zadanie zakończyć działanie wszystkich aktualnie wykonujących się zadań (wątków) i zamknąć Wykonawcę. 
	    //zadania i tak będą dzialały w nieskończoność gdyż metoda shutDownNow() kończy działające zadania poprzez użycie metody interrupt() wobec odpowiednich wątków.
	    //Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
	      exec.shutdownNow();
	}
	
	
}
