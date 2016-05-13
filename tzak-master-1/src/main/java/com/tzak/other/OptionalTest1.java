package com.tzak.other;

import java.util.Optional;


/**
 * Klasa typu Optional słuzy do obslugi NullPointerException
 * @author tzak
 *
 */
public class OptionalTest1 {

	public static void main(String[] args) {
		
		OptionalTest1 optionalTest1 = new OptionalTest1();
		optionalTest1.test1();
		optionalTest1.personOptionalGettersTest();
		
	}

	public void test1(){
		PersonOptional1 personOptional1 = new PersonOptional1();
		
		//klasyczna obsługa w celu uniknięcia NullPointerException (mozna jeszcze np try catch
		if(personOptional1.getAge()!=null) 
			System.out.println("Zmienna Age nie jest nullem");
		else
			System.out.println("Konieczność obsługi null - w innym wypadku pojawi się NullPointerException");
		
		
		//ten warunek jest równoznaczny z ponizyszm
		if(personOptional1.getName()!=null) 
			System.out.println(personOptional1.getName());
		else
			System.out.println("Brak imienia - z warunku if else");
		//powyzszy warunek wykonany za pomocą Optional
		System.out.println(Optional.ofNullable(personOptional1.getName()).orElse("Brak imienia - z Optional"));  //nalezy przestrzega typu zmiennej w orElse - tutaj musi byc String
		
		
		//obsługa NullPointerException przy uzyciu klasy Optional
		System.out.println(Optional.ofNullable(personOptional1.getAge()));
		System.out.println("Aktualny wiek: " + Optional.ofNullable(personOptional1.getAge()).orElse(0)); //nalezy przestrzega typu zmiennej w orElse - tutaj musi byc Integer
		
		personOptional1.setName("Tomasz");
		System.out.println(Optional.ofNullable(personOptional1.getName()).orElse("Brak imienia - z Optional"));
		//lepiej... do println
		Optional.ofNullable(personOptional1.getName()).ifPresent(System.out::println); 
		//metoda ifPresent zostanie wykonana tylko jeśli wartość getName nie będzie nullem lub jeśli cały optional nie będzie empty, co jest tym samym.
		//Metoda ifPresent nic nie zwraca - jeśli chcemy coś zwrócić to uzywamy map
		System.out.println(Optional.ofNullable(personOptional1.getName()).map(value -> "imie1: "+value).orElse("brak Imienia!"));
		String wynikTestowy1 = Optional.ofNullable(personOptional1.getName()).map(value -> "imie2: "+value).orElse("brak Imienia!");
		System.out.println(wynikTestowy1);
		
				
		personOptional1.setAge(22);
        System.out.println(Optional.ofNullable(personOptional1.getAge())
                .filter(age -> age >= 18)   //mozna wprowadzac warunki do Optional
                .map(age -> "wiek: " + age + ". Osoba pełnoletnia")
                .orElse("Osoba niepełnoletnia"));
	}
	
	public void personOptionalGettersTest() {
		System.out.println("########################");
		PersonOptionalGetters personalTest2 = new PersonOptionalGetters();
		//dla tej klasy metody getery zostały zrobine tak ze od razu zwracaja Optional
		System.out.println("Obecnie pole imie nie jest ustawioane");

		personalTest2.getName().ifPresent(System.out::println);  //tutaj nic sie nie wydarzy
		System.out.println(personalTest2.getName().orElse("Brak imienia dla tego obiektu")); // tutaj wypisane zostanie infpo o braku imienia
		
		System.out.println("Ustawiamy imię....");
		personalTest2.setName("Tomasz 2");
		personalTest2.getName().ifPresent(System.out::println);
		System.out.println(personalTest2.getName().map(value -> "imie: "+value).orElse("brak Imienia!"));
		
		//filtry
		System.out.println(personalTest2.getName()
                .filter(zmiennaTymczasowa -> zmiennaTymczasowa == "Tomasz 2")
                .map(zmiennaTymczasowa -> "Imie: " + zmiennaTymczasowa + " - zgadza sie z filtrem")
                .orElse("imie nie zgadza sie z filtrem"));
		
	}
	
}


class PersonOptional1 {
	private String name;
    private String lastName;
    private Integer age;
 
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }
    
    /**
     * Stworzy nam Optionala, który jeśli będzie nullem, będzie miał wartość „empty”, w innym wypadku będzie miał wartość, która została do niego przypisana.
	 * metoda orElse przypisuje wartość, jaka ma być zwrócona jeśli Optional jest pusty (empty).
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + Optional.ofNullable(name).orElse("") + '\'' +
                ", lastName='" + Optional.ofNullable(lastName).orElse("") + '\'' +
                ", age=" + Optional.ofNullable(age).orElse(0) +
                '}';
    }
    
}

class PersonOptionalGetters {
	private String name;
	private String lastName;
	private Integer age;
	
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);   //zwracany jest obiekt typu Optional i będzie mozna od razu z gettera kozystać z mozliwosci Optionali
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Optional<String> getLastName() {
		return Optional.ofNullable(lastName);
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Optional<Integer> getAge() {
		return Optional.ofNullable(age);
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
}