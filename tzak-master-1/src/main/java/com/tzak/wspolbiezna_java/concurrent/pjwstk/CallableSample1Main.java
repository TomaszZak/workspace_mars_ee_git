package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallableSample1Main {

	public static void main(String[] args) {

		Runnable r = new Runnable() {
			public void run() {
				System.out.println("Zadanie Runnable \"r\"");
			}
		};

		Callable<String> c = new Callable<String>() {
			public String call() throws Exception {
				String result;
				System.out.println("Zadanie Callable \"c\"");
				result = "Rezultat Callable c";
				return result;
			}
		};

		
		
		FutureTask<Boolean> task1 = new FutureTask<Boolean>(r, true);
		FutureTask<Object> task2 = new FutureTask<Object>(Executors.callable(r));
		FutureTask<String> task3 = new FutureTask<String>(c);

		Executor exec = Executors.newCachedThreadPool();
		exec.execute(task1);
		exec.execute(task2);
		exec.execute(task3);
		//Gdy nasze zadania zakończą się, Wykonawca nadal "działa" i jest gotowy do przyjmowania nowych zadań.
		((ExecutorService) exec).shutdown();
		
		ExecutorService exec2 = Executors.newCachedThreadPool();
		Future<Boolean> future1 = exec2.submit(task1, true);
		Future<Object> future2 = (Future<Object>) exec2.submit(task2);
		Future<String> future3 = (Future<String>) exec2.submit(task3);
		exec2.shutdown();

		LocalDateTime today = LocalDateTime.now();
		String todaySformatowane = today.format(DateTimeFormatter.ofPattern("dd:MMM:uuuu HH:mm:ss"));
		System.out.println(todaySformatowane + " Koniec działania programu");
	}
}
