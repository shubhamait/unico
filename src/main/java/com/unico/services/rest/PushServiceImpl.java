package com.unico.services.rest;

import java.util.List;

import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.unico.services.dao.GCDDao;
import com.unico.services.jms.producer.MessageProducer;

/**
 * PushServiceImpl exposes two GET REST methods push and list.
 * 
 * @author shubham
 *
 */
@Path("/gcd")
public class PushServiceImpl implements PushService {

	@Autowired
	private MessageProducer producer;

	@Autowired
	private GCDDao gcdDao;

	/* (non-Javadoc)
	 * @see com.unico.services.rest.PushService#push(int, int)
	 */
	@GET
	@Path("/push/{a}/{b}")
	@Produces("text/plain")
	public String push(@PathParam("a") int i1, @PathParam("b") int i2) {
		try {
			producer.convertAndSendMessage(i1);
			producer.convertAndSendMessage(i2);
		} catch (JMSException jms) {
			// we can log the error message with suitable logging enabled.
			return "Not able to send input to JMS queue " + jms.getMessage();
		}
		gcdDao.addInputToList(i1, i2);
		return "Input sent to queue:" + i1 + " and " + i2;
	}

	/* (non-Javadoc)
	 * @see com.unico.services.rest.PushService#list()
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Integer> list() {
		return gcdDao.getListofNumbers();
	}
}
