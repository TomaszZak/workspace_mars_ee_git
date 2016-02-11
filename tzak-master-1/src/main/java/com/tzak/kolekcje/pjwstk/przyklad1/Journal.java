package com.tzak.kolekcje.pjwstk.przyklad1;

import java.util.*;

public class Journal implements CHelper<Journal>, Comparable<Journal> {
  private String title;
  private int year;
  private static int retainAfter = 0;  // rok wydania,
                                       // od ktorego ew. zostawiać gazety

  //konstruktor brzparametrowy (wg kontraktu potrzebny do newInstance w MKolekt)
  public Journal() { }  
  
  public Journal(String t, int y) {
    title = t;
    year = y;
  }

  // Implementacja metody stwierdzającej czy Journal można uznać za aktualny
  @Override
  public boolean isNotUpToDate() {
    return year < retainAfter;
  }

  // Tworzy Journal ze Stringu, w którym tytuł jest ujęty w cudzysłow
  // a za tytułem znajduje się rok wydania. Dodaje do kolekcji.
  @Override
  public Journal makeObject(String s){
    String title = "";
    int year = -1;
    try {
      StringTokenizer st = new StringTokenizer(s, "\"");  //pobieranie tytułu - ciąg znaków znajdujący sie między cudzysłowiem
      title = st.nextToken();
      year = Integer.parseInt(st.nextToken().trim());
    } catch (Exception exc) { }
    Journal j =  new Journal(title, year);
    return j;
  }
  
  // Tworzy String z obiektu
  // inna forma niż toString (dla zapisu do pliku)
  @Override
  public String makeString() {
    return '"' + title + '"' + " " + year; 
  }

  public static void setLimitYear(int y) { retainAfter = y; }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return toString().equals(obj.toString());
  }

  @Override
  public int compareTo(Journal j) {
    return toString().compareTo(j.toString());
  }

  @Override
  public String toString() { return title + " " + year; }
}