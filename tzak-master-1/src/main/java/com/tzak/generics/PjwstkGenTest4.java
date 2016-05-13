package com.tzak.generics;

import java.lang.reflect.*;

class Para<S, T> {
  S first;
  T last;

  public Para() {}

  public Para(S f, T l) {
    first = f;
    last = l;
  }

  // konstruktor kopiujący
  public Para(Para<S,T> p) {
    // nie możemy użyć new, ale możemy zastosować refleksję
    try {
      first = (S) getInstance(p.first);  // unchecked, ale jest gwarancja
      last = (T) getInstance(p.last);    // że typy będą właściwe
    } catch (Exception exc) {
        throw new UnsupportedOperationException("Copy constructor not available",
                                                 exc.getCause());
    }
  }

  private Object getInstance(Object o) throws Exception {
     Class type = o.getClass();
     Constructor c = null;
     Object arg = null;
     try {  // czy jest konstruktor kopiujący?
       c = type.getConstructor(type);
       arg = o;
     } catch (Exception exc) {  // nie ma kopiującego
          if (type.getSuperclass() == java.lang.Number.class) { // może to boxy?
             c = type.getConstructor(java.lang.String.class);
             arg = o.toString();
          }
     }
     if (c == null) { // ani kopiujący, ani ze Stringa nie jest bezpiecznie
       // utworzyć obiekt za pomocą konstruktora bezparametrowego (newInstance)
       // pobrać od klasy Properties (PropertyDescriptor[])
       // dla wszystkich setterów wywołać odpowiednie gettery na oryginale,
       // a zwrócone wartości podać jako argumenty setterom
       // (i wołać je po kolei na kopii)
       throw new UnsupportedOperationException("Valid constructor not found in" +
                                                type);  // bo tego nie robimy
     }
     else return c.newInstance(arg);
  }

  // dodawanie par
  public Para<S, T> add(Para<S, T> p) {
    Para<S, T> wynik = new Para<S, T>(); // nie można new T(), ale można new X<T>!
    try {
      wynik.first =  (S) addObjects(first, p.first); // unchecked, 
      wynik.last = (T) addObjects(last, p.last);    // ale typ jest gwarantowany
      return wynik;
    } catch(Exception exc) {
       throw new UnsupportedOperationException("Addition not allowed",
                                                exc.getCause());
    }
  }
  
  private Object addObjects(Object o1, Object o2) throws Exception {

    if (o1 instanceof String) {
    	System.out.println("Krok test1: " + (String) o1 + o2);
    	return (String) o1 + o2; // konkatenacja
    }
    
    
    if (o1 instanceof Number) { // działania na klasach opakowujących typy proste
 
      double d = ((Number) o1).doubleValue() + ((Number) o2).doubleValue();
      String s = String.valueOf(d);
      Constructor c = null;
      try {                   // wynik musi być specyficznego typu (np. Integer)
        c = o1.getClass().getConstructor(java.lang.String.class);  //tworzymy konstruktor typu String dla Object o1
        System.out.println("Krok test2: " + s);
        return c.newInstance(s);
      } catch(Exception exc) {       // np. gdy new Integer("1.0");
    	 // exc.printStackTrace();
          int l = s.indexOf('.');    // bierzemy tylko cyfry przed kropką
          s = s.substring(0, l);
          System.out.println("Krok test3: " + s);
          return c.newInstance(s);
      }
    }

    // Ani String ani Number - więc musi mieć metodę add(...)

    Class typ = o1.getClass();
    Method m = typ.getDeclaredMethod("add", typ);
    return m.invoke(o1, o2);
  }


  public S getFirst() { return first; }
  public T getLast()   { return last; }

  public void setFirst(S f) { first = f; }
  public void setLast(T l) { last = l; }

  public String toString() {
    return first + " " + last;
  }

}

class Value {
   int val;

   Value(int n) { val = n; }

   public Value add(Value v) {
      return new Value(val + v.val);
   }

   public String toString() {
     return "" + val;
   }

}


public class PjwstkGenTest4 {

  public static void main(String[] args) {
    Para<String, Integer> p1 = new Para<String, Integer>("A", 2);
    Para<String, Integer> p2 = new Para<String, Integer>(p1);
    System.out.println(p2);
    Para<String, Integer> wynik = p1.add(p2);
    System.out.println(wynik);
    
    Para<String, String> p3 = new Para<String, String>("A", "2");
    Para<String, String> p4 = new Para<String, String>(p3);
    System.out.println(p4);
    Para<String, String> wynik2 = p3.add(p4);
    System.out.println(wynik2);
    
    // Para <String, String> ps = new Para <String, String>("c", "d");
    // wynik = p1.add(ps); <=== kompilator wykrywa błędy
    Para<Value, Value> v1 = new Para<Value, Value>(new Value(1), new Value(2));
    Para<Value, Value> v2 = new Para<Value, Value>(new Value(3), new Value(4));
    Para<Value, Value> vv = v1.add(v2);
    System.out.println(vv);
  }

}