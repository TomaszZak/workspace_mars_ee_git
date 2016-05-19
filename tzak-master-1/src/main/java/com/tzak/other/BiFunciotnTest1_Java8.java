package com.tzak.other;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.BiFunction;

@FunctionalInterface
interface BigDecimalFunction extends BiFunction<BigDecimal, BigDecimal, BigDecimal> {
}

public class BiFunciotnTest1_Java8 {
	    private final HashMap<String, BigDecimalFunction> methods = new HashMap<>();
	 
	    
	/**
	 * Jak koniecznie chcemy mieć te metody w swojej klasie to musimy potem zamienić 
	 * methods.put("-", BigDecimal::subtract); 
	 * na 
	 * methods.put("-", this::subtract);
	 */
	    private BiFunciotnTest1_Java8() {
	        methods.put("-", BigDecimal::subtract);
	        methods.put("+", BigDecimal::add);
	        methods.put("*", BigDecimal::multiply);
	        methods.put("/", BigDecimal::divide);
	    }
	 
	    private String doBiFunciotnTest1(String arg) {
	        String[] items = arg.split("\\s+");  //rozdziela znaki po regexie \s - który oznacza wszystkie nie biale znaki
	        BigDecimal arg1 = new BigDecimal(items[0]);
	        BigDecimal arg2 = new BigDecimal(items[2]);
	        BigDecimalFunction method = methods.get(items[1]);
	        return method.apply(arg1, arg2).toString();
	    }
	 
	    public static void main(String[] args){
	        BiFunciotnTest1_Java8 c = new BiFunciotnTest1_Java8();
	        System.out.println(c.doBiFunciotnTest1("3 * 2"));  //musi byc oddzielone białymi znakami zeby arg.split("\\s+") wyciagnal argumenty bez bledu
	    }
	}