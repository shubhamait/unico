package com.unico.services.dao;

import java.util.List;

public interface GCDDao {

	/**
	 * Will add both the integers to DB.
	 * 
	 * @param i
	 * @param j
	 */
	public void addInputToList(Integer i, Integer j);

	/**
	 * Will add GCD to the integers to DB.
	 * 
	 * @param gcd
	 * 
	 */
	public void addGCDToList(Integer gcd);

	/**
	 * Will add both the integers to DB.
	 * 
	 */
	public List<Integer> getListofNumbers();

	/**
	 * Will add both the integers to DB.
	 *
	 */
	public List<Integer> listofGCDs();

	/**
	 * 
	 * @return
	 */
	public Integer sumofGCDs();
}
