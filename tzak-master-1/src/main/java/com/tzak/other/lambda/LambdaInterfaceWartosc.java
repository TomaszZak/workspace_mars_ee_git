package com.tzak.other.lambda;

/**
 * 
 * Do przypisania wyrażenia Lambda potrzebujemy interface z jedną i tylko jedną metodą (wtedy nazywamy ją "funkconalnym interfacem"), nie potrzebujemy w tej metodzie użycia słowa kluczowego "abstract". W javie 8 dodana została defaultowa metoda, lecz jej użycie nie zmienia znaczenia interfacesu funkcjonalnego czyli jedna metoda abstrakcyjna i nawet kilka metod defaultowych może być użyta jako interfaces funkcjonalny.
 * 
 * @author tzak
 *
 */
public interface LambdaInterfaceWartosc {
	int wartosc(int a);
}
