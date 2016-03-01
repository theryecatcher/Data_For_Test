/**
 * 
 */
package com.ge.predix.websocket.test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.ge.predix.websocket.client.PredixWSClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Ravi_Shankar10
 *
 */
public class LaunchClient {

	private static final String WEBSOCKET_URI = "wss://gateway-predix-data-services.run.aws-usw02-pr.ice.predix.io/v1/stream/messages";

	public static void main(String[] args) {

		System.out.println(Double.parseDouble("NaN"));
		
		System.out.println("*************** Trying websocket method ******************");
		Session session = null;

		JsonObject payload = new JsonObject();

		JsonArray body = new JsonArray();
		
		JsonArray datapoints = new JsonArray();
		
		List<Object> dataPoint = new ArrayList<>();
		dataPoint.add(System.currentTimeMillis());
		dataPoint.add(23.89);
		dataPoint.add(3);
		String strData = new Gson().toJson(dataPoint);
		datapoints.add(new JsonParser().parse(strData).getAsJsonArray());
		
		JsonObject bodyObject = new JsonObject();
		
		bodyObject.addProperty("name", "EQUIP3-AD");
		bodyObject.add("dataPoints", datapoints);
		
		body.add(bodyObject);
		
		payload.addProperty("messageId", "469324632946");
		
		payload.add("body", body);

		System.out.println(payload.toString());
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();

		URI uri = URI.create(WEBSOCKET_URI);

		try {
			session = container.connectToServer(PredixWSClient.class, uri);
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			session.getAsyncRemote().sendText(new Gson().toJson(payload));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
