package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.tzak.date_time.java8.AktualnaDataSformatowana;

public class SamplePjwstkConcurrentMain {

	// reprezentuje zadanie do wykonania
	// static Future<String> task;
	static int uspienieWykonywaniaWMiliSekundach;
	static Callable<String> pojedynczeZadanieZWynikiemCallable;
	static Runnable pojedynczeZadanieBezWynikuRunnable;
	static ExecutorService exec;
	static FutureTask<String> pojedynczeZadanieFutureTaskCallableDone;
	static KlasaZadaniaDoWykonaniaCallable pojedynczaKlasaZadanieDoWykonaniaCallable;
	static ZadanieZObslugaPrzerwanZadan<String> klasaFutureTaskZObslugaPrzerwanZadan;
	static ScheduledExecutorService scheduledExecutorService;   //wykorzystywany do harmonogramowego uruchamiania zadan

	// ###################### MAIN
	public static void main(String[] args) {

		uspienieWykonywaniaWMiliSekundach = 10; //3600000; // uśpienie na godzinę

		// ####### tworzenie przykładow obiektow do wykonania w watkach
		/**
		 * przykladowe zadanie do wykonania z wynikiem - watek Callable zapewniajacy zwrot wyniku - Runnable tego nie zapewnia
		 */
		pojedynczeZadanieZWynikiemCallable = new Callable<String>() {
			public String call() throws Exception { // metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted())
					return null; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("\n Wykonuje zadanie Callable: pojedynczeZadanieZWynikiemCallable");
				String result = null;

				przykladoweOperacjeWPetli();

/*				Thread.sleep(10000);
				// uspienie watku - sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.”
				if (Thread.currentThread().isInterrupted())
					return null; // chcemy przerwać możliwie najszybciej
*/				
//				Thread.yield();
//				wait();
				try { // sleep() jest przerywane pzrez interrupt()!
					Thread.sleep(uspienieWykonywaniaWMiliSekundach);
				} catch (InterruptedException exc) {
					System.out.println("Wątek zliczania czasu został przerwany.");
					return null;
				}
				return result;
			}
		};

		/** przykladowe zadanie do wykonania bez wyniku - Runnable */
		pojedynczeZadanieBezWynikuRunnable = new Runnable() {
			public void run() { // metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted())
					return; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("\n Wykonuje zadanie Runnable: pojedynczeZadanieBezWynikuRunnable");

//				przykladoweOperacjeDoPrzerwaniaInterrupt();
				przykladoweOperacjeWPetli();
				// uspienie watku - sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.”
/*				if (Thread.currentThread().isInterrupted())
					return; // chcemy przerwać możliwie najszybciej
				try { // sleep() jest przerywane pzrez interrupt()!
					Thread.sleep(uspienieWykonywaniaWMiliSekundach);
				} catch (InterruptedException exc) {
					System.out.println("Wątek zliczania czasu został przerwany.");
					return;
				}*/
			}
		};

		/**
		 * Przykladowe zadanie z obslyga metody done() chroniona metody done(), która jest wywoływana po zakończeniu zadania.
		 */
		pojedynczeZadanieFutureTaskCallableDone = new FutureTask<String>(new Callable<String>() {
			public String call() {
				String result = null;
				for (int i = 1; i <= 3; i++) {
					if (Thread.currentThread().isInterrupted())
						return null; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
					System.out.println("\n Wykonuje zadanie Callable: pojedynczeZadanieZWynikiemCallable");

					przykladoweOperacjeWPetli();

					// uspienie watku - sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.”
					if (Thread.currentThread().isInterrupted())
						return null; // chcemy przerwać możliwie najszybciej
					try { // sleep() jest przerywane pzrez interrupt()!
						Thread.sleep(uspienieWykonywaniaWMiliSekundach);
					} catch (InterruptedException exc) {
						System.out.println("Wątek zliczania czasu został przerwany.");
						return null;
					}
				}
				return result;
			}
		}) {
			public void done() {
				System.out.println("Done() - zadania do wykonania po zamknięciu wątku");
			}
		};

		/**
		 * Na podstawie oddzielnej klasy KlasaZadaniaDoWykonaniaCallable przykladowe zadanie do wykonania z wynikiem - watek Callable zapewniajacy zwrot wyniku - Runnable tego nie zapewnia
		 */
		pojedynczaKlasaZadanieDoWykonaniaCallable = new KlasaZadaniaDoWykonaniaCallable();
		// ####### koniec - przykładow obiektow do wykonania w watkach

		// ## Testy
//		 przykladWykorzystaniaFutureTaskow();
//		przykladGlowny();
		testShedulerTaskow();

		
	}
	// ############## Koniec MAINA

	public static void przykladGlowny() {
		executorCreate(); // tworzymy wykonawcę zadań

		// ###### tworzenie zadan FutureTaskow - przyklad kiedy nie korzystamy z Future
		/** tworzymy zadania wspolbierzne do wykonania - z obslugą przerwania z metodą done */
		ZadanieZObslugaPrzerwanZadan<String> zadanieCallableZPrzerwaniem1 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczeZadanieZWynikiemCallable);
		ZadanieZObslugaPrzerwanZadan<String> zadanieCallableZPrzerwaniem2 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczaKlasaZadanieDoWykonaniaCallable);

		/** tworzymy zadania wspolbierzne do wykonania - bez obslugi przerwania z metodą done */
		FutureTask<String> zadanieCallableBezPrzerwania1 = new FutureTask<String>(pojedynczeZadanieZWynikiemCallable);
		FutureTask<Boolean> zadanieBezPrzerwania2 = new FutureTask<Boolean>(pojedynczeZadanieBezWynikuRunnable, true); // obiekt runnable z przekazanym na sztywno wynikiem true
		FutureTask<Boolean> zadanieBezPrzerwania3 = new FutureTask<Boolean>(pojedynczeZadanieBezWynikuRunnable, true); // obiekt runnable z przekazanym na sztywno wynikiem true
		// FutureTask<Object> zadanieBezPrzerwania3 = new FutureTask<Object>(Executors.callable(pojedynczeZadanieBezWynikuRunnable)); //wrzucenie na sztywno do Runnable wyniku NULL

		ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem1 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczeZadanieZWynikiemCallable);
		ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem2 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczaKlasaZadanieDoWykonaniaCallable);

		
//		taskStartCallableFutureTask(zadanieCallableZPrzerwaniem1);
//		taskStartCallableFutureTask(zadanieCallableZPrzerwaniem2);
//		taskStartCallableFutureTask(zadanieCallableBezPrzerwania1);
		

		taskStartCallableFutureTask(zadanieZPrzerwaniem1);
		taskStartCallableFutureTask(zadanieZPrzerwaniem2);

//		exec.shutdown();


	}
	
	/** Metoda uruchamia wątek w pętli co określone przerwy czasowe
	 * ScheduledExecutorService nie obsługuje FutureTaskow. Najlepiej wykorzystać Runnablre
	 * Wyjaśnienie: 
	 * The problem is that FutureTask is used, and as its class documentation says, "Once the computation has completed, the computation cannot be restarted or cancelled."
	 */
	public static void testShedulerTaskow() {

		AktualnaDataSformatowana dataCzas = new  AktualnaDataSformatowana();

		Runnable runnable1 = new Runnable() {
			public void run() { // metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted())
					return; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("\nStart Zadanie runnable 1 " + dataCzas.wyswietlDateSformatowana());
				przykladoweOperacjeWPetli();
				synchronizowanaPrzykladoweOperacjeWPetli();
				System.out.println("\nKoniec zadania runnable 1 " + dataCzas.wyswietlDateSformatowana());
			}
		};
		
		Runnable runnable2 = new Runnable() {
			public void run() { // metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted())
					return; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("\nStart Zadanie runnable 2 " + dataCzas.wyswietlDateSformatowana());
				przykladoweOperacjeWPetli();
				synchronizowanaPrzykladoweOperacjeWPetli();
				System.out.println("\nKoniec zadania runnable 2 " + dataCzas.wyswietlDateSformatowana());
			}
		};
		
		scheduledExecutorServiceCreate();
		
		scheduledExecutorService.scheduleAtFixedRate(
				runnable1  //sheduled dziala z Runnable a nie z FutureTask
				, 0, 10, TimeUnit.SECONDS);  //opoznieie startu, przerwa od ostatniego STARTAMI, jednostka czasu
		
		scheduledExecutorService.scheduleAtFixedRate(
				runnable2  //sheduled dziala z Runnable a nie z FutureTask
				, 0, 10, TimeUnit.SECONDS);  //opoznieie startu, przerwa od ostatniego kolejnymi STARTAMI, jednostka czasu

		
		
		System.out.println("Starting...");

		try {
			scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda z przykladem zastosowania FutureTaskow z obsluga anulowania i metoda done()
	 */
	public static void przykladWykorzystaniaFutureTaskow() {
		executorCreate(); // tworzymy wykonawcę zadań

		// ###### tworzenie zadan FutureTaskow - przyklad kiedy nie korzystamy z Future
		/** tworzymy zadania wspolbierzne do wykonania - z obslugą przerwania z metodą done */
		ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem1 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczeZadanieZWynikiemCallable);
		ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem2 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczaKlasaZadanieDoWykonaniaCallable);

		/** tworzymy zadania wspolbierzne do wykonania - bez obslugi przerwania z metodą done */
		FutureTask<String> zadanieBezPrzerwania1 = new FutureTask<String>(pojedynczeZadanieZWynikiemCallable);
		FutureTask<Boolean> zadanieBezPrzerwania2 = new FutureTask<Boolean>(pojedynczeZadanieBezWynikuRunnable, true); // obiekt runnable z przekazanym na sztywno wynikiem true
		// FutureTask<Object> zadanieBezPrzerwania3 = new FutureTask<Object>(Executors.callable(pojedynczeZadanieBezWynikuRunnable)); //wrzucenie na sztywno do Runnable wyniku NULL

		exec.execute(zadanieZPrzerwaniem1);
		exec.execute(zadanieZPrzerwaniem2);

		/** Kończenie pracy wykonawcy zadan - executora */
		// Thread.yield(); //dobrowolnego oddania czasuprocesora - Wykonujący się wątek (będący w stanie Running) może dobrowolnie oddać czas procesora za pomocą wywolania metody yield() . Przy tym nie następuje jednak zwolnienie rygla. Wątek pozostaje w stanie Runnable, ale czeka na ponowne przydzielenie czasu procesora.
		exec.shutdown();

	}

	// metoda jest synchronizowana- zapewnia, że kilka wykonujących się wątków nie będzie równocześnie wykonywać tego samego kodu, w szczególności - działać na tym samym obiekcie.
	synchronized public static void przykladoweOperacjeDoPrzerwaniaInterrupt() {
		String name = "przykladoweOperacjeDoPrzerwaniaInterrupt";
		for (byte i = 1; i <= 128; i++) {
			if (Thread.currentThread().isInterrupted())
				return;
			System.out.print(i + " ");
			//Thread.yield(); //yield - pozwala wejść do kolejki innym wątkom jezeli ten jest bezczynny
			
			
			//opuznienie w zadaniu
			if (Thread.currentThread().isInterrupted())
				return; // chcemy przerwać możliwie najszybciej
			try { // sleep() jest przerywane pzrez interrupt()!
				Thread.sleep(700);
			} catch (InterruptedException exc) {
				System.out.println("Wątek zliczania czasu został przerwany.");
				return;
			}
			Thread.yield(); // ddanie czasu procesora innemu oczekującemu wątkowi, który ma wyższy lub taki sam priorytet
		}
	}

	/**Przykładowa petla operacji - BEZ SYNCHRONIZACJI
	 * 
	 */
	public static void przykladoweOperacjeWPetli() {
		String name = "przykladoweOperacjeWPetli";
		
		
		for (int i = 1; i <= 10; i++) {
			if (Thread.currentThread().isInterrupted())
				return;
			System.out.print(i + " ");
			//Thread.yield(); //yield - pozwala wejść do kolejki innym wątkom jezeli ten jest bezczynny
		
			//opuznienie w zadaniu
			if (Thread.currentThread().isInterrupted())
				return; // chcemy przerwać możliwie najszybciej
			try { // sleep() jest przerywane pzrez interrupt()!
				Thread.sleep(700);
			} catch (InterruptedException exc) {
				System.out.println("Wątek zliczania czasu został przerwany.");
				return;
			}
		}
	}
	
	
	/** SYNCHRONIZOWANA
	 * To samo co metoda: przykladoweOperacjeWPetli() tylko, ze SYNCHRONIZOWANA
	 * metoda jest synchronizowana- zapewnia, że kilka wykonujących się wątków nie będzie równocześnie wykonywać tego samego kodu, w szczególności - działać na tym samym obiekcie.
	 */
	synchronized public static void synchronizowanaPrzykladoweOperacjeWPetli() {
		przykladoweOperacjeWPetli();
	}

	/**
	 * Metody obslugujace uruchamianie i zatrzymywanie zadan
	 * 
	 * @param callableFutureTask
	 *            - dzięki patametrowi FutureTask obsluguje metode done()
	 */

	public static <T> void taskStartCallableFutureTask(FutureTask<T> callableFutureTask) {
		try {
			exec.execute(callableFutureTask);
		} catch (RejectedExecutionException exc) {
			System.err.println("Wyjątek: RejectedExecutionException - próba uruchomienia zadania po zamknięciu executora (metoda shutdown()), lub z innego powodu ExecutorService odmawia wykonanie zadania");
			return;
		}
		System.out.println("Uruchomiono zadanie z metody taskStartCallable");
	}

	/*
	 * public void taskStartRunnable(Runnable runnableTask) { try { exec.execute(runnableTask, true); } catch(RejectedExecutionException exc) { System.out.println("Task after shutdown"); return; } System.out.println("Uruchomiono zadanie"); }
	 */

	/*
	 * public void taskResult() { String msg = ""; if (task.isCancelled()) msg = "Task cancelled."; else if (task.isDone()) { try { msg = "Ready. Result = " + task.get(); } catch(Exception exc) { msg = exc.getMessage(); } } else msg = "Task is running or waiting for execution"; JOptionPane.showMessageDialog(null, msg); }
	 */

	/**
	 * Cancel - Próbuje anulować wykonanie zadania (argument mówi o tym, czy można przerwać wykonujące się zadanie). Nie wykonujące się (jeszcze) zadania są usuwane z listy zadań Wykonawcy.
	 */
	public void taskStop() {
		klasaFutureTaskZObslugaPrzerwanZadan.cancel(true); // anulować mozna obiekty FutureTask - one obsluguja cancel
	}

	/**
	 * Metoda tworzy wykonawce zadan - obsluga watkow Bez ustawie czasowych, opóźnień i terminów
	 */
	public static void executorCreate() {

		System.out.println("Liczba dostepnych procesorow: " + Runtime.getRuntime().availableProcessors());
		// exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		 exec = Executors.newCachedThreadPool(); //Wykonawca,prowadzący pulę wątków o dynamicznych rozmiarach
//		exec = Executors.newFixedThreadPool(2);
//		exec = Executors.newSingleThreadExecutor(); // jeden wątek - pula z obsluga jednego watku na raz
	}
	
	public static void scheduledExecutorServiceCreate() {
		System.out.println("Liczba dostepnych procesorow: " + Runtime.getRuntime().availableProcessors());
		scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		
		//opcja z kontrola tworzonego nowego wotku
/*		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println("Tworzę nowy wątek");
                return new Thread(r);
            }
        }); 
		*/
	}

	public void executorShutdown() {
		exec.shutdown();
		System.out.println("Executor shutdown\n");

		// Na końcu programu - za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). Warto stosować metodę awaitTermination(), kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył działanie i wyczyścił wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć wcześniej niż Wykonawcy i aplikacja nie zakończy działania).
		try {
			exec.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
		System.out.println("Terminated: " + exec.isTerminated());
	}

	public void executorShutdownNow() {

		List<Runnable> awaiting = exec.shutdownNow();
		System.out.println("Eeecutor shutdown now - awaiting tasks:\n");
		for (Runnable r : awaiting) {
			System.out.println(r.getClass().getName() + '\n');
		}
		// Na końcu programu - za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). Warto stosować metodę awaitTermination(), kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył działanie i wyczyścił wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć wcześniej niż Wykonawcy i aplikacja nie zakończy działania).
		try {
			exec.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
		System.out.println("Terminated: " + exec.isTerminated());
	}
}

/**
 * Przykladowa klasa zadania do wykonania w watku Callable call() bez obslugi done()
 */
class KlasaZadaniaDoWykonaniaCallable implements Callable<String> {

	// przykladowe pole klasy - moze byc wynikowe
	private String result;

	int uspienieWykonywaniaWMiliSekundach = 10; //3600000;

	public KlasaZadaniaDoWykonaniaCallable() {
	}

	// metoda wywolywana w watku - z wyniikiem String
	public String call() throws Exception {
		if (Thread.currentThread().isInterrupted())
			return null; // Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
		System.out.println("Wykonuje zadanie Callable: KlasaZadaniaDoWykonaniaCallable");
		result = "wynik" + " temp";

		synchronized (result) { // zakładamy rygiel na zmienna - Bloki synchronizowane wprowadzane są instrukcją synchronized z podaną w nawiasie referencją do obiektu, który ma być zaryglowany.
			result = result + " operacje synchronizowane. Zmienna zaryglowana";
		}

		// Przykladowe operacje --------
		String name = "KlasaZadaniaDoWykonaniaCallable";
		for (int i = 1; i <= 5; i++) {
			if (Thread.currentThread().isInterrupted())
				return null;
			System.out.print(i + " ");
			//Thread.yield(); // ddanie czasu procesora innemu oczekującemu wątkowi, który ma wyższy lub taki sam priorytet
			
			//opuznienie w zadaniu
			if (Thread.currentThread().isInterrupted())
				return null; // chcemy przerwać możliwie najszybciej
			try { // sleep() jest przerywane pzrez interrupt()!
				Thread.sleep(700);
			} catch (InterruptedException exc) {
				System.out.println("Wątek zliczania czasu został przerwany.");
				return null;
			}
		}

		// uspienie watku - sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.”
		if (Thread.currentThread().isInterrupted())
			return null; // chcemy przerwać możliwie najszybciej
		try { // sleep() jest przerywane pzrez interrupt()!
			Thread.sleep(uspienieWykonywaniaWMiliSekundach);
		} catch (InterruptedException exc) {
			System.out.println("Wątek zliczania czasu został przerwany.");
			return null;
		}

		// koniec operacji -------

		return result;
	}

}

/**
 * Klasa oblsuguje przerwanie dla wywolanego zadania - metoda done() dla obiektow Callable - wykonywanych zadań Tworzymy zadanie za pomoca tej klasy jezeli chcemy obsluzyc przerwanie
 * 
 * @author tzak
 *
 * @param <V>
 */
class ZadanieZObslugaPrzerwanZadan<V> extends FutureTask<V> {

	/**
	 * Konstruktor - w parametrze podajemy zadanie do wykonania
	 * 
	 * @param callable
	 *            - podajemy zadanie do wykonania - musi buc callable bo obslugujemy zwrot wynikow
	 */
	public ZadanieZObslugaPrzerwanZadan(Callable<V> callable) {
		super(callable); // Tworzymy obiekt FutureTask i nadpisujemy metode done()
	}

	// Bezpośrednia konstrukcja (za pomocą konstruktorów klasy) zadań jako
	// FutureTask ma pewną zaletę wobec submit(Callable) czy
	// submit(Runnable): w łatwy sposób możemy dostarczać odwołań zwrotnych
	// (callback) reagujących na zakończenie zadania. Klasa FutureTask
	// dostarcza bowiem chronionej metody done(), która jest wywoływana po
	// zakończeniu zadania.
	public void done() {
		String result = "Wynik: ";
		if (isCancelled())
			result += "Cancelled - Anulowano wykonanie zadania";
		else
			try {
				result += get();
			} catch (Exception exc) {
				result += exc.toString();
			}
		System.out.println("Zakończono wzadanie / watek");
	}

}

// sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.” The OS doesn’t even try to schedule the sleeping thread until requested time has passed.
// yield() says “I’m done with my timeslice, but I still have work to do.” The OS is free to immediately give the thread another timeslice, or to give some other thread or process the CPU the yielding thread just gave up.
// wait() says “I’m done with my timeslice. Don’t give me another timeslice until someone calls notify().” As with sleep(), the OS won’t even try to schedule your task unless someone calls notify() (or one of a few other wakeup scenarios occurs).
