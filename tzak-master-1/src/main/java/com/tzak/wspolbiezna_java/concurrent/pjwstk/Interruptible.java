//http://edu.pjwstk.edu.pl/wyklady/zap/scb/
package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;


/*Możliwość wykonywania kodów jako zadań obsługiwanych przez Wykonawców powoduje, 
że poczynając od Javy 1.5 kończenie pracy wątków musimy zapewniać poprzez sprawdzanie stanu INTERRUPTED,
oraz obsługę wyjątku InterruptedException*/

public class Interruptible  {

	  Lock lock = new ReentrantLock();

	  Runnable task1 = new Runnable() {
	     public void run() {
	       System.out.println("Task 1 begins");
	       try {
	         lock.tryLock(120, TimeUnit.SECONDS);  // próba zamknięcia rygla (czeka na wolny rygiel lub 120 sekund)
	         System.out.println("Task 1 entered");
	       } catch(InterruptedException exc) {
	           System.out.println("Task 1 interrupted");
	       }
	       System.out.println("Task 1 stopped");
	     }
	  };

	  Runnable task2 = new Runnable() {
	    public void run() {
	      System.out.println("Task 2 begins");
	      int temp = 0;
	      for (int i=1; i <= 600; i++) {
	        // jakieś obliczenie
	    	  if (Thread.currentThread().isInterrupted()) break;
	        temp += temp;
	        if (Thread.currentThread().isInterrupted()) break;  // chcemy przerwać możliwie najszybciej
	        try {                                               // sleep() jest przerywane pzrez interrupt()!
	          Thread.sleep(10);
	        } catch (InterruptedException exc) { break; }
	      }
	      System.out.println("Task 2 stopped");
	    }
	  };


	  Runnable task3 = new Runnable() {
		  
		// musimy miec InterruptibleChannel, aby móc przerwać czekanie na wejściu - czekanie na wejściu po wybraniu CRTL+Z
		  //w eclipse pod windows jest problem z wysłaniem sygnału end of file przez input
		  //google: eclipse console input eof
		  Scanner scan = new Scanner(new FileInputStream(FileDescriptor.in).getChannel(), "Cp852");
	                    
	    
	    
	    public void run() {
	    	
	    //	Scanner scan2 = new Scanner(System.in);
/* 			System.out.println("Enter your username: ");
	    	Scanner scanner = new Scanner(System.in);
	    	String username = scanner.nextLine();
	    	System.out.println("Your username is " + username);*/
	    	
	      System.out.println("Task 3 begins");
	      System.out.print("Wpisz ciąg znaków i potwierdź wciskając Enter i następnie CTRL+Z \n>>");

/*	      String temp = "Test \n";
	      temp = scan.nextLine();
	      System.out.println(temp);*/
	      
	      while (scan.hasNextLine()) {
	        try {
	          String s = scan.nextLine();
	          System.out.println("\n Wpisano: " +s + "\n Wpisz kolejny ciąg znaków i potwierdź wciskając Enter i następnie CTRL+Z \n>>");
	        } catch (Exception exc) {
	            // Uwaga: scanner nie zgłasza wyjątków, ale przerywa dzialanie
	            System.out.println("Task 3 - wejscie w Exception");
	        	exc.printStackTrace();
	            break;
	        }
	      }
	      System.out.println("Task 3 stopped - " + scan.ioException());  // jaki wyjątek go przerwał?
	    }
	  };

	  Interruptible() {
	    ExecutorService exec = Executors.newCachedThreadPool();
	    //ExecutorService exec = Executors.newFixedThreadPool(3);
	    exec.execute(new Runnable() {        // wątek zamyka rygiel
	                    public void run() {
	                      lock.lock();
	                    }
	                 }
	     );
	    exec.execute(task1);
	    exec.execute(task2);
	    exec.execute(task3);
	    JOptionPane.showMessageDialog(null, "Press Ok to stop all tasks");
	    exec.shutdownNow();
	  }

	}
