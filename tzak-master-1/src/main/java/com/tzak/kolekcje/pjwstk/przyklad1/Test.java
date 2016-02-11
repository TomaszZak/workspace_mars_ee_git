package com.tzak.kolekcje.pjwstk.przyklad1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

	  public static void main(String[] args) {
	    List<Journal> gazety = new ArrayList<Journal>();
	    Set<Student>  studenci = new HashSet<Student>();
	    MKolekt.makeCollectionFromFile(gazety, "C:/eclipse_mars_ee/workspace_mars_ee/tzak-master-1/src/main/java/com/tzak/kolekcje/pjwstk/przyklad1/Journal.in", Journal.class);
	    MKolekt.makeCollectionFromFile(studenci, "C:/eclipse_mars_ee/workspace_mars_ee/tzak-master-1/src/main/java/com/tzak/kolekcje/pjwstk/przyklad1/Student.in", Student.class);
	    System.out.println("Oryginalna zawartość kolekcji"); 
	    MKolekt.show(gazety);
	    MKolekt.show(studenci);
	    // ustalenie minimalnego roku wydania
	    // gazety wydane wczesniej będą usunięte z kolekcji
	    Journal.setLimitYear(2004);
	    MKolekt.iterRemove(gazety);
	    // ustalenie minimalnej oceny
	    // studenci z oceną niższą będa usunięci z kolekcji   
	    Student.setLimitMark(3);
	    MKolekt.iterRemove(studenci);
	    // Pokazujemy po usunięciu
	    System.out.println("Nowa zawartość kolekcji");
	    MKolekt.show(gazety);
	    MKolekt.show(studenci);
	    // I zapisujemy do pliku

	    try {
	      MKolekt.writeToFile(gazety, "Journal.out");
	      MKolekt.writeToFile(studenci, "Student.out");
	    } catch (Exception exc) {
	      exc.printStackTrace();
	    }
	  }

	}