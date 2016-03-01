/**
 * 
 */
package com.ge.predix.websocket.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * @author Ravi_Shankar10
 *
 */
@ClientEndpoint(subprotocols = { "xsCrossfire" }, configurator = WSConfigurator.class)
public class PredixWSClient {

	/**
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}

	/**
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Session connected!!! " + session.getId());
	}
	
	/**
	 * @param error
	 */
	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
	}
}
