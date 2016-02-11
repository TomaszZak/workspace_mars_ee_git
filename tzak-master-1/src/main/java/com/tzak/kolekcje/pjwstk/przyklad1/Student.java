package com.tzak.kolekcje.pjwstk.przyklad1;

import java.util.*;

public class Student implements CHelper<Student>, Comparable<Student> {
  private String name;
  private double mark;             // ocena
  private static double minMark;   // minimalna ocena

  public Student() {}
  public Student(String nam, double m) {
    name = nam;
    mark = m;
  }

  public void setMark(double m) { mark = m; }

  public boolean isNotUpToDate() {
    return mark < minMark;
  }

  // Tworzy obiekt Student i dodaje do kolekcji
  // format napisy: nazwisko tab ocena
  public Student makeObject(String s) {
    Scanner sc = new Scanner(s).useDelimiter("\t");
    String name = sc.next();
    double mark = 0;
    try {
      mark = sc.nextDouble();
    } catch (Exception exc) {
      exc.printStackTrace();
    }
    Student stud = new Student(name, mark);
    return stud;

  }
  
  @Override
  public String makeString() {
    return name + '\t' + mark;
  }

  // Ustalenie minimalnej oceny.
  // Studenci z niższą oceną mogą być usunięci z kolekcji.  
  public static void setLimitMark(double m) {
    minMark = m;
  }

  public boolean equals(Object obj) {
    return name.equals(obj);
  }

  public int compareTo(Student s) {
    return this.toString().compareTo(s.toString());
  }

  public int hashCode() { return this.toString().hashCode(); }

  public String toString() { return name + " " + mark; }
}