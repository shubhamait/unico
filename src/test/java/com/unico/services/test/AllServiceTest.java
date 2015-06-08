package com.unico.services.test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unico.services.dao.GCDDao;
import com.unico.services.rest.PushService;
import com.unico.services.soap.GCDService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class AllServiceTest {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private GCDDao gcdDao;

	@Autowired
	private GCDService gcdservice;

	@Autowired
	private PushService pushservice;

	@Before
	public void init() {
		Assert.assertNotNull(jmsTemplate);
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(5);
		numbers.add(6);
		numbers.add(7);
		doReturn(numbers).when(gcdDao).getListofNumbers();
		doReturn(numbers).when(gcdDao).listofGCDs();
		doReturn(5).when(gcdDao).sumofGCDs();

		doAnswer(new Answer<Integer>() {
			private int count = 10;

			public Integer answer(InvocationOnMock invocation) {
				return count += 5;
			}
		}).when(jmsTemplate).receiveAndConvert(anyString());
	}

	@Test
	public void pushToQueue() {
		Assert.assertTrue((pushservice.push(5, 6))
				.equalsIgnoreCase("Input sent to queue:" + 5 + " and " + 6));
	}

	@Test
	public void getListOfNumbersFromDB() {
		Assert.assertNotNull(pushservice.list());
	}

	@Test
	public void getGCDOfTwoNumberFromJMSQueue() {
		Assert.assertNotNull(gcdservice.gcd());
	}

	@Test
	public void getSumOfAllGCDGeneratedTillNow() {
		Assert.assertNotNull(gcdservice.gcdSum());
	}

	@Test
	public void getListOfGCDsFromDB() {
		Assert.assertNotNull(gcdservice.gcdList());
	}
}