package com.unico.services;


public class GCDCalculator {

	public Integer getGCD(Integer a, Integer b) {
		return findGCD(a, b);
	}

	private int findGCD(int number1, int number2) {
		// base case
		if (number2 == 0) {
			return number1;
		}
		return findGCD(number2, number1 % number2);
	}
}
