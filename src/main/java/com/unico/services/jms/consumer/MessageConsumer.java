package com.unico.services.jms.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component("consumer")
public class MessageConsumer {
	@Autowired
	private JmsTemplate jmsTemplate;

	public Integer receive() {
		return (Integer) jmsTemplate.receiveAndConvert("sync.queue");
	}
}