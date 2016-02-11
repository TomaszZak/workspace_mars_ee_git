package com.tzak.kolekcje.pjwstk;

import java.io.*;
import java.util.*;

class MapyPrzykladPanstwaStolice {

  Map<String, String> countryCapital = new HashMap<String, String>();

  public MapyPrzykladPanstwaStolice() {
    readInfo();
    show("\n### Mapa inicjalna - HashMap", countryCapital);
    show("\n### Mapa w porządku naturalnym - TreeMap",
          new TreeMap<String, String>(countryCapital)
         );
    // Pusta mapa z komparatorem odwracającym naturalny porządek
    Map<String, String> mapRo = new TreeMap<String, String>(Collections.reverseOrder());
    // Wpisujemy do niej pary z mapy countryCapital
    mapRo.putAll(countryCapital);
    show("\n### Mapa w porządku odwrotnym (komparator reverseOrder)",
          mapRo
         );

    // Uzyskanie iteratora po zestawie kluczy
    // Pokazanie wszystkich par, których klucze zawierają r
    // Usunięcie ich
    Iterator<String> it = countryCapital.keySet().iterator();
    while (it.hasNext()) {
      String key = it.next();
      if (key.indexOf("r") != -1) {
        System.out.println(key + " " + countryCapital.get(key));
        it.remove();
      }
    }
    show("\n### Mapa po zmianach", countryCapital);
    
    stoliceToUpperCase();
  }

  public void show(String msg, Map<String, String> map) {
    System.out.println(msg);
    Set<String> keys = map.keySet();
    System.out.println("Klucze: " + keys);
    Collection<String> vals = map.values();
    System.out.println("Wartości: " + vals);
    Set entries = map.entrySet();
    System.out.println("Pary: " + entries);
  }
  
  private void readInfo() {
    try {
      Scanner in = new Scanner(new File("C:/eclipse_mars_ee/workspace_mars_ee/tzak-master-1/src/main/java/com/tzak/kolekcje/pjwstk/stolice.txt"));
      while(in.hasNextLine()) {
        Scanner line = new Scanner(in.nextLine()).useDelimiter("/");
        // Dodawanie do mapy pary: kraj - stolica
        countryCapital.put(line.next(),line.next());
      }
      in.close();
    } catch (Exception exc) {
        exc.printStackTrace();
        System.exit(1);
      }
  }
  
  private void stoliceToUpperCase() {
	    // Zmiana wielkości liter w pisowni stolic
	    Iterator<Map.Entry<String, String>> pit = countryCapital.entrySet().iterator();
	    while (pit.hasNext()) {
	      // To jedyny sposób uzyskania dostępu do obiektu Map.Entry !!!
	      Map.Entry<String, String> entry =  pit.next();
	      String cap =  entry.getValue();
	      entry.setValue(cap.toUpperCase());
	    }
	    show("\n### Po zmianie wielkości liter", countryCapital);
	  }
  
  
  public static void main(String[] args) {
    new MapyPrzykladPanstwaStolice();
  }

}