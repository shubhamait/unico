package com.unico.services.soap;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.unico.services.GCDCalculator;
import com.unico.services.dao.GCDDao;
import com.unico.services.jms.consumer.MessageConsumer;

/**
 * GCDServiceImpl exposes SOAP services for calculating, listing and summing
 * GCD's.
 * 
 * @author shubham
 *
 */
@WebService(name = "GCDService", targetNamespace = "http://soap.services.unico.com/")
public class GCDServiceImpl implements GCDService {

	@Autowired
	private MessageConsumer consumer;

	@Autowired
	private GCDDao gcdDao;

	@Autowired
	private GCDCalculator gcdCalculator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unico.services.soap.GCDService#gcd()
	 */
	public Integer gcd() {
		Integer i1 = consumer.receive();
		Integer i2 = consumer.receive();
		if (i1 == null || i2 == null)
			return null;
		Integer gcd = gcdCalculator.getGCD(i1, i2);
		gcdDao.addGCDToList(gcd);
		return gcd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unico.services.soap.GCDService#gcdList()
	 */
	public List<Integer> gcdList() {
		return gcdDao.listofGCDs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unico.services.soap.GCDService#gcdSum()
	 */
	public int gcdSum() {
		return gcdDao.sumofGCDs();
	}
}
