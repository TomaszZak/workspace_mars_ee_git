package com.tzak.wspolbiezna_java.concurrent.pjwstk.asynchroniczna_obsługa_wynikow;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/** Klasa Ftask dziedziczy FutureTask i przedefiniowuje metodę done w której wywołuje metodę obsługi.
 * Klasa FutureTask dostarcza bowiem chronionej metody done(), która jest wywoływana po zakończeniu zadania.
 * @author tzak
 *
 * @param <V> - typ zwracanego rezultatu
 */
public class Ftask<V> extends FutureTask<V> {

	private Method handlerMethod;
	private Object handlerObject;

	public Ftask(Callable<V> callable, Object handler, String mname) throws Exception {
		super(callable);
		handlerObject = handler;
		handlerMethod = handler.getClass().getDeclaredMethod(mname, Object.class, Exception.class);
	}

	//chronionej metody done(), która jest wywoływana po zakończeniu zadania.
	public void done() {
		V result = null;
		try {
			result = (V) this.get();
		} catch (Exception exc) {
			try {
				handlerMethod.invoke(handlerObject, null, exc);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		try {
			handlerMethod.invoke(handlerObject, result, null);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
