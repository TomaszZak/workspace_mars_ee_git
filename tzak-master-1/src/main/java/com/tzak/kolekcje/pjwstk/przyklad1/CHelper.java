package com.tzak.kolekcje.pjwstk.przyklad1;

public interface CHelper<T>  {
	  
	  // Tworzy obiekt z podanego napisu s i zwraca go
	  T makeObject(String s);
	  
	  // Tworzy String z obiektu - możliwe, że w altrnatywny sposób niż toString
	  String makeString(); 

	  // Zwraca true, jeśli dane obiektu są uznane za nieaktualne
	  boolean isNotUpToDate();

	}