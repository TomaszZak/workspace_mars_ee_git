package com.tzak.wspolbiezna_java.concurrent.pjwstk.asynchroniczna_obsługa_wynikow;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Klasa abstrakcyjnego zadania, która - dla konkretnych instancjacji poprzez dziedziczenie - wymaga tylko implementacji metody call():
 * @author tzak
 *
 * @param <V> - the result type of method call()
 */
public abstract class AbstractTask<V> implements Callable<V> {

	  private String   name;
	  private FutureTask<V> task;

	  public AbstractTask(String name, Object resultHandler, String handlerMethodName) throws Exception {
	    this.name = name;
	    task = new Ftask<V>(this, resultHandler, handlerMethodName);
	  }

	  public FutureTask<V> getTask() { return task; }
	  public String getName() { return name; }

	}