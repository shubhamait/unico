package com.unico.services.rest;

import java.util.List;

public interface PushService {

	/**
	 * It takes two parameters which are added to the JMS queue and message is
	 * returned for the status of the request.
	 * 
	 * @param i1
	 * @param i2
	 * 
	 * @return String
	 */
	public String push(int i1, int i2);

	/**
	 * It returns the list of all the elements which were added to JMS queue.
	 * 
	 * @return List<String>
	 * 
	 */
	public List<Integer> list();

}