package com.tzak.wspolbiezna_java.concurrent.pjwstk.asynchroniczna_obsługa_wynikow;

import java.util.List;
import java.util.concurrent.*;

public class RepeatedTaskTest {

	public static void main(String[] args) {
		new RepeatedTaskTest();
	}
	
	//przygotowanie zadania do wykonania
	FutureTask<Object> task = new FutureTask<Object>
	(new Callable<Object>() {
		//w konstruktorze definiujemy nowy obiekt Callable i przeciążamy metodę call
		public Object call() {
			System.out.println("Zadanie START");
			for (int i = 1; i <= 3; i++) {
				System.out.println("Wywołanie metody call():" +  i);
					try {
						Thread.sleep(700);
						//wait(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return "###### Wynik metody call()";
		}
	}) 
	{
		//przeciążamy metodę done() obiektu FutureTask
		public void done() {
			System.out.println("Done. - wywołanie metody done()");
		}
	};

	
//	metoda testująca
	public RepeatedTaskTest() {
		ExecutorService exec = Executors.newSingleThreadExecutor();  //uruchamiamy pule z jendym wątkiem
		System.out.println("Uruchomienie zadania");
		exec.execute(task);
		System.out.println("Ponowne uruchomienie tego samego zadania");
		exec.execute(task);
		System.out.println("Ponowne uruchomienie tego samego zadania");
		exec.execute(task);
		
		try {
			Thread.sleep(5000);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		try {
			//ponownie sięgamy po dostępny wynik wykonanego zadania task
			System.out.println(task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("Executor shutdown now!!!! - zamykamy działanie Executora");
		List<Runnable> awaiting = exec.shutdownNow(); //sygnał przerwania działania programu
		
		System.out.println("\nLista zadań czekających w kolejce na uruchomienie:");
		for (Runnable r : awaiting) {
			System.out.println(r.getClass().getName() + '\n');
		}
		if(awaiting.isEmpty()) {
			System.out.println(
					"Brak zadań czekających na wykonanie \n"
					+ "Drugie zadanie w ogóle się nie wykonało i nie powstał żaden wyjątek! \n"
					+ "Logika jest taka - jest tylko jedno zadanie. Ono się wykonało. Wykonawca nie wykonuje już wykonanych zadań. \n"
					+ "Wynik jest gotowy - cały czas możemy po niego sięgać. Nie ma żadnego wyjątku, bo wszystko jest w porządku - zadanie zostało wykonane.\n"
					);
		}
		
		try {
/*			
 * WAŻNE:
 * Na końcu programu - za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich 
 * zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). 
 * Warto stosować metodę awaitTermination(),  kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył 
 * działanie i wyczyścił wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć 
 * wcześniej niż Wykonawcy i aplikacja nie zakończy działania).
 * */
			System.out.println("Wywołanie metody awaitTerminator - trzyma działający wątek do zakończenia wszystkich zadań albo jak upłynie 5 sec \n"
					+ " - wysyła również przerwanie (interrupt) do sleepa, żeby przyspieszyć");
			exec.awaitTermination(50, TimeUnit.SECONDS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
	}
}
