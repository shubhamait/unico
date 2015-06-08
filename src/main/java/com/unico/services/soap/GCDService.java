package com.unico.services.soap;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface GCDService {

	/**
	 * Calculate GCD for two numbers which are retrieved from JMS queue if any
	 * of the number is Null (as queue does not have two number/messages) then
	 * we return null.
	 * 
	 * @return GCD of two numbers.
	 */
	public Integer gcd();

	/**
	 * List of all the GCD's which have been calculated and saved in the
	 * database.
	 * 
	 * @return List of GCS's calculated till now.
	 */
	public List<Integer> gcdList();

	/**
	 * Sum of all the GCD's which have been calculated and saved in the
	 * database.
	 * 
	 * @return Sum of all calculated GCS's till now.
	 */
	public int gcdSum();

}