package com.tzak.kolekcje.pjwstk.przyklad1;

import java.io.*;
import java.util.*;



/**
 * Warto zwrócić uwagę, że metody klasy MKolekt dzialają dla różnego rodzaju kolekcji (ArrayList i HashSet) elementów dwóch bardzo róznych klas (Student i Journal).
Będą też działać dla dowolnych  innych rodzajów kolekcji (byleby implementowaly interfejs Collection) i dowolnych innych klas elementów (byleby implementowaly interfejs CHelper) i definiowały konstruktor bezparametrowy.
 * @author tzak
 *
 */
public class MKolekt {

  // Tworzy obiekty klasy
  // określonej przez klasę klasOb
  // na podstawie informacji z pliku tekstowego o nazwie fname
  // i dodaje je do kolekcji col

  public static <T extends CHelper<T>> 
         void makeCollectionFromFile(Collection<T> col, String fname,
                                     Class<T> klasaOb) {
    try {
    	System.out.println("### Nazwa klasy: " + klasaOb.toString());
      CHelper<T> mgr =  klasaOb.newInstance();

      Scanner in = new Scanner(new File(fname));
      while(in.hasNextLine()) {
        T obj = mgr.makeObject(in.nextLine());
        col.add(obj);
      }
      in.close();
    } catch (Exception exc) {
        exc.printStackTrace();
        System.exit(1); 
    }
  }

  // Usuwa z kolekcji c obiekty przeznaczone do usunięcia
  // na podstawie wyniku metodu isNotUpToDate

  public static <T extends CHelper<T>> void iterRemove(Collection<T> c) {
    Iterator<T> iter = c.iterator();
    while (iter.hasNext()) {
      CHelper<T> elt = iter.next();
      if (elt.isNotUpToDate()) iter.remove();
    }
  }

  // Zapisuje do pliku fname, w kolejnych wierszach,
  // kolejne elementy kolekcji col
  public static <T extends CHelper<T>>
    void writeToFile(Collection<T> col, String fname) throws Exception {
      Formatter out = null;
      try {
        out = new Formatter(new File(fname));
        for (T obj : col) {
          String line = obj.makeString();
          out.format("%s%n", line);
        }
      } finally {
          if (out != null) out.close();
      }
    
  }
  
  public static void show(Collection<?> col) {
    for (Object o : col) System.out.println(o);
  }

}