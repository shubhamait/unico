package com.unico.services.jms.producer;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * MessageProducer act as JMS producer for connecting to queue and sending out
 * messages.
 * 
 * @author shubham
 *
 */
@Component("producer")
public class MessageProducer {
	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;

	/**
	 * Takes integer input and sends it out to JMS message queue
	 * 
	 * @param i
	 */
	public void convertAndSendMessage(int i) throws JMSException {
		jmsTemplate.convertAndSend(i);
	}
}