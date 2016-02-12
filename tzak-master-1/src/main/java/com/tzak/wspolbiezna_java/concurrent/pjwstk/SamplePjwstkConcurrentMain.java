package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class SamplePjwstkConcurrentMain {

	//reprezentuje zadanie do wykonania
	//static Future<String> task;
	static Callable<String> pojedynczeZadanieZWynikiemCallable;
	static Runnable pojedynczeZadanieBezWynikuRunnable;
	static ExecutorService exec;
	static FutureTask<String> pojedynczeZadanieFutureTaskCallableDone;
	static KlasaZadaniaDoWykonaniaCallable pojedynczaKlasaZadanieDoWykonaniaCallable;
	static ZadanieZObslugaPrzerwanZadan<String> klasaFutureTaskZObslugaPrzerwanZadan;
	
	
	//###################### MAIN
	public static void main(String[] args) {
	
		// wykonywanie zadan - przyklad

		// ####### tworzenie przykładow obiektow do wykonania w watkach

		/** przykladowe zadanie do wykonania z wynikiem - watek Callable
		zapewniajacy zwrot wyniku - Runnable tego nie zapewnia 
		*/
		pojedynczeZadanieZWynikiemCallable = new Callable<String>() {
			public String call() throws Exception {		//metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted()) return null; //Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("Wykonuje zadanie Callable: pojedynczeZadanieZWynikiemCallable");
				String result = null;
				przykladoweOperacjeDoPrzerwaniaInterrupt();

				return result;
			}
		};
		
		
		/**przykladowe zadanie do wykonania bez wyniku - Runnable */
		pojedynczeZadanieBezWynikuRunnable = new Runnable() {
			public void run() {			//metoda wywolywana w watku - z wynikiem String
				if (Thread.currentThread().isInterrupted()) return; //Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
				System.out.println("Wykonuje zadanie Runnable: pojedynczeZadanieBezWynikuRunnable");		
				przykladoweOperacjeDoPrzerwaniaInterrupt();
				
				//uspienie watku - sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.”
/*		        if (Thread.currentThread().isInterrupted()) break;  // chcemy przerwać możliwie najszybciej
		        try {                                               // sleep() jest przerywane pzrez interrupt()!
		          Thread.sleep(1000);
		        } catch (InterruptedException exc) { break; }
				*/ 
				}
			};
			
			
		/** Przykladowe zadanie z obslyga metody done() 
		 * chroniona metody done(), która jest wywoływana po
		 * zakończeniu zadania.*/	
		pojedynczeZadanieFutureTaskCallableDone = new FutureTask<String>(new Callable<String>() {
			public String call() {
				for (int i = 1; i <= 3; i++) {
					System.out.println(i);

				}
				return null;
			}
		}) {
			public void done() {
				System.out.println("Done.");
			}
		};

		
		/**Na podstawie oddzielnej klasy KlasaZadaniaDoWykonaniaCallable przykladowe zadanie do wykonania z wynikiem - watek Callable	zapewniajacy zwrot wyniku - Runnable tego nie zapewnia 
		*/
		pojedynczaKlasaZadanieDoWykonaniaCallable = new KlasaZadaniaDoWykonaniaCallable();
			// ####### koniec - przykładow obiektow do wykonania w watkach
		
		
		//## Testy		
		//przykladWykorzystaniaFutureTaskow();
		przykladGlowny();
		
	}
	// ############## Koniec MAINA
	
	
	public static void przykladGlowny() {

		System.out.println("Liczba dostepnych procesorow: " + Runtime.getRuntime().availableProcessors());
//		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		exec = Executors.newSingleThreadExecutor();
//		exec = Executors.newCachedThreadPool(); //Wykonawca,prowadzący pulę wątków o dynamicznych rozmiarach
		
		//###### tworzenie zadan FutureTaskow - przyklad kiedy nie korzystamy z Future
		
		/** tworzymy zadania wspolbierzne do wykonania - z obslugą przerwania z metodą done*/
		ZadanieZObslugaPrzerwanZadan<String> zadanieCallableZPrzerwaniem1 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczeZadanieZWynikiemCallable);
		ZadanieZObslugaPrzerwanZadan<String> zadanieCallableZPrzerwaniem2 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczaKlasaZadanieDoWykonaniaCallable);
		
		/** tworzymy zadania wspolbierzne do wykonania - bez obslugi przerwania z metodą done*/
		FutureTask<String> zadanieCallableBezPrzerwania1 = new FutureTask<String>(pojedynczeZadanieZWynikiemCallable);
		FutureTask<Boolean> zadanieBezPrzerwania2 = new FutureTask<Boolean>(pojedynczeZadanieBezWynikuRunnable, true);  //obiekt runnable z przekazanym na sztywno wynikiem true
//		FutureTask<Object> zadanieBezPrzerwania3 = new FutureTask<Object>(Executors.callable(pojedynczeZadanieBezWynikuRunnable));  //wrzucenie na sztywno do Runnable wyniku NULL

		taskStartCallable(zadanieCallableZPrzerwaniem1);
		taskStartCallable(zadanieCallableZPrzerwaniem2);
		taskStartCallable(zadanieCallableBezPrzerwania1);
		
	}
	
	
	/**
	 * Metoda z przykladem zastosowania FutureTaskow
	 * z obsluga anulowania i metoda done()
	 */
		public static void przykladWykorzystaniaFutureTaskow() {

			//###### tworzenie zadan FutureTaskow - przyklad kiedy nie korzystamy z Future
			
			/** tworzymy zadania wspolbierzne do wykonania - z obslugą przerwania z metodą done*/
			ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem1 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczeZadanieZWynikiemCallable);
			ZadanieZObslugaPrzerwanZadan<String> zadanieZPrzerwaniem2 = new ZadanieZObslugaPrzerwanZadan<String>(pojedynczaKlasaZadanieDoWykonaniaCallable);
			
			/** tworzymy zadania wspolbierzne do wykonania - bez obslugi przerwania z metodą done*/
			FutureTask<String> zadanieBezPrzerwania1 = new FutureTask<String>(pojedynczeZadanieZWynikiemCallable);
			FutureTask<Boolean> zadanieBezPrzerwania2 = new FutureTask<Boolean>(pojedynczeZadanieBezWynikuRunnable, true);  //obiekt runnable z przekazanym na sztywno wynikiem true
//			FutureTask<Object> zadanieBezPrzerwania3 = new FutureTask<Object>(Executors.callable(pojedynczeZadanieBezWynikuRunnable));  //wrzucenie na sztywno do Runnable wyniku NULL

			/** tworzymy wykonawce zdan */
//			exec = Executors.newCachedThreadPool(); //Wykonawca,prowadzący pulę wątków o dynamicznych rozmiarach
			exec = Executors.newSingleThreadExecutor();  //jeden wątek

			exec.execute(zadanieZPrzerwaniem1);
			exec.execute(zadanieZPrzerwaniem2);
			
			/**Kończenie pracy wykonawcy zadan - executora */
			//  Thread.yield(); //dobrowolnego oddania czasuprocesora - Wykonujący się wątek (będący w stanie Running) może dobrowolnie oddać czas procesora za pomocą wywolania metody yield() . Przy tym nie następuje jednak zwolnienie rygla. Wątek pozostaje w stanie Runnable, ale czeka na ponowne przydzielenie czasu procesora.
			  exec.shutdown();
		
		
		}
	

	
	public static void przykladoweOperacjeDoPrzerwaniaInterrupt() {
		String name = "przykladoweOperacjeDoPrzerwaniaInterrupt";
	    for (byte i=1; i <= 128 ; i++) {
	        if (Thread.currentThread().isInterrupted()) return;
	     //   System.out.println(name + " " + i);
	      }
	}

	
	
	
	
	//Metody obslugujace uruchamianie i zatrzymywanie zadan
	
	  public static <T> void taskStartCallable(FutureTask<T> callableFutureTask) {
		    try {
		   exec.execute(callableFutureTask);
		    } catch(RejectedExecutionException exc) {
		        System.out.println("Task after shutdown");
		        return;
		    }
		    System.out.println("Uruchomiono zadanie z metody taskStartCallable");
		  }
	  
/*	  public void taskStartRunnable(Runnable runnableTask) {
		    try {
		    	exec.execute(runnableTask, true);
		    } catch(RejectedExecutionException exc) {
		        System.out.println("Task after shutdown");
		        return;
		    }
		    System.out.println("Uruchomiono zadanie");
		  }*/

/*	  
		  public void taskResult() {
		    String msg = "";
		    if (task.isCancelled()) msg = "Task cancelled.";
		    else if (task.isDone()) {
		      try {
		        msg = "Ready. Result = " + task.get();
		      } catch(Exception exc) {
		          msg = exc.getMessage();
		      }
		    }
		    else msg = "Task is running or waiting for execution";
		    JOptionPane.showMessageDialog(null, msg);
		  }

		  public void taskStop() {
		    task.cancel(true);
		  }
*/
		  public void executorShutdown() {
		    exec.shutdown();
		    System.out.println("Executor shutdown\n");    

		 // Na końcu programu - za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). Warto stosować metodę awaitTermination(),  kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył działanie i wyczyścił  wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć wcześniej niż Wykonawcy i aplikacja nie zakończy działania).
	    try {
	        exec.awaitTermination(5, TimeUnit.SECONDS);
	      } catch(InterruptedException exc) { exc.printStackTrace(); }
	      System.out.println("Terminated: " + exec.isTerminated()); 
		  }

		  public void executorShutdownNow() {
			  		  
		    List<Runnable> awaiting = exec.shutdownNow();
		    System.out.println("Eeecutor shutdown now - awaiting tasks:\n");
		    for (Runnable r : awaiting) {
		      System.out.println(r.getClass().getName()+'\n');
		    }
			 // Na końcu programu - za pomocą metody awaitTermination(...) wstrzymujemy bieżący wątek dopóki Wykonawca nie zakończy wszystkich zadań (albo dopóki nie minie 5 sekund lub też nie wystąpi przerwanie bieżącego wątku za pomocą metody interrupt). Warto stosować metodę awaitTermination(),  kiedy chcemy mieć pewność, że Wykonawaca naprawdę zakończył działanie i wyczyścił  wszystkie swoje zajęte zasoby (np. bez tego nasz głowny wątek może sie skończyć wcześniej niż Wykonawcy i aplikacja nie zakończy działania).
		    try {
		        exec.awaitTermination(5, TimeUnit.SECONDS);
		      } catch(InterruptedException exc) { exc.printStackTrace(); }
		      System.out.println("Terminated: " + exec.isTerminated()); 
		  }
}

/** Przykladowa klasa zadania do wykonania w watku 
 * Callable call()
 * bez obslugi done()*/
class KlasaZadaniaDoWykonaniaCallable implements Callable<String> {

	//przykladowe pole klasy - moze byc wynikowe
	private String result;
	
	public KlasaZadaniaDoWykonaniaCallable() {}
	 
	//metoda wywolywana w watku - z wyniikiem String
	@Override
	public String call() throws Exception {
		if (Thread.currentThread().isInterrupted()) return null; //Metoda interrupt() ustala jedynie status wątku jako przerwany, a zakończenie pracy wątku odbywa się zawsze przez zakończenie jego kodu.
		System.out.println("Wykonuje zadanie Callable: KlasaZadaniaDoWykonaniaCallable");
		result = "wynik" + " temp";
		//Przykladowe operacje --------
		String name = "KlasaZadaniaDoWykonaniaCallable";
	    for (byte i=1; i <= 128 ; i++) {
	        if (Thread.currentThread().isInterrupted()) return null;
	     //   System.out.println(name + " " + i);
	      }
	    //koniec operacji -------
		
		return result;
	}

}

/** 
 * Klasa oblsuguje przerwanie dla wywolanego zadania - metoda done() dla obiektow Callable - wykonywanych zadań
 * Tworzymy zadanie za pomoca tej klasy jezeli chcemy obsluzyc przerwanie
 * @author tzak
 *
 * @param <V>
 */
 class ZadanieZObslugaPrzerwanZadan<V> extends FutureTask<V> {

	/**
	 * Konstruktor - w parametrze podajemy zadanie do wykonania
	 * @param callable - podajemy zadanie do wykonania - musi buc callable bo obslugujemy zwrot wynikow
	 */
	public ZadanieZObslugaPrzerwanZadan(Callable<V> callable) {
		super(callable);  //Tworzymy obiekt FutureTask i nadpisujemy metode done()
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

 //sleep(n) says “I’m done with my timeslice, and please don’t give me another one for at least n milliseconds.” The OS doesn’t even try to schedule the sleeping thread until requested time has passed.
 //yield() says “I’m done with my timeslice, but I still have work to do.” The OS is free to immediately give the thread another timeslice, or to give some other thread or process the CPU the yielding thread just gave up.
 //wait() says “I’m done with my timeslice. Don’t give me another timeslice until someone calls notify().” As with sleep(), the OS won’t even try to schedule your task unless someone calls notify() (or one of a few other wakeup scenarios occurs).

