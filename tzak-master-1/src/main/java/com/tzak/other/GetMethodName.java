package com.tzak.other;

public class GetMethodName {

	public static void main(String args[]) {
		getMethodNameUsingObject();
		getMethodNameUsingThrowable();
		getMethodNameUsingThread();
	}

	public static void getMethodNameUsingObject() {
		String methodNameUsingObject = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Get Currently Method Execution Name Using Object Class - " + methodNameUsingObject);
	}

	public static void getMethodNameUsingThrowable() {
		StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
		System.out.println("Get Currently Method Execution Name From Stack Trace - " + stackTraceElements[0].getMethodName());
	}

	public static void getMethodNameUsingThread() {
		System.out.println("Get Currently Method Execution Name From Thread - " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}