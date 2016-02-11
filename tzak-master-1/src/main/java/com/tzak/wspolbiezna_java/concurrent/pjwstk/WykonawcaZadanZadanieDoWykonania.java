package com.tzak.wspolbiezna_java.concurrent.pjwstk;

//klasa reprezentująca pojedyncze zadanie
public class WykonawcaZadanZadanieDoWykonania implements Runnable {

	private String name;

	public WykonawcaZadanZadanieDoWykonania(String name) {
		this.name = name;
	}

	// operacje wykonywane przez wątek - zadania do wykonania
	public void run() {
		for (int i = 1; i <= 4; i++) {
			if (Thread.currentThread().isInterrupted())
				return;	    //przerwanie wątku jeśli przyjdzie sygnał interrupt - np executor.shutdownNow(); wysyłający interrupt() Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.

			System.out.println(name + "   Numer kroku zadania: " + i);
			Thread.yield();
		}
	}
}
