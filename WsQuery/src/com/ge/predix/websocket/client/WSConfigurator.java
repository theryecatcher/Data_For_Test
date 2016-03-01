/**
 * 
 */
package com.ge.predix.websocket.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;

import org.apache.http.Header;

import com.ge.predix.constants.PlatformConfiguration;
import com.ge.predix.rest.client.HttpsPostClient;

/**
 * @author Ravi_Shankar10
 *
 */
public class WSConfigurator extends javax.websocket.ClientEndpointConfig.Configurator implements ClientEndpointConfig {

	/**
	 * 
	 */
	private String payload;

	/**
	 * 
	 */
	private long currentTime;

	/**
	 * 
	 */
	private List<Header> authHeaders;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.websocket.ClientEndpointConfig.Configurator#beforeRequest(java.util
	 * .Map)
	 */
	@Override
	public void beforeRequest(Map<String, List<String>> headers) {

		List<String> authHeader = new ArrayList<>();
		try {
			String bearerToken = HttpsPostClient.generateBearerToken(PlatformConfiguration.OAUTH_ISSUER_URI);
			authHeader.add("bearer " + bearerToken.substring(1, bearerToken.length() - 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> zoneHeader = new ArrayList<>();
		zoneHeader.add(PlatformConfiguration.ZONE_ID);

		headers.put("Authorization", authHeader);
		headers.put("Predix-Zone-Id", zoneHeader);

		super.beforeRequest(headers);

		System.out.println("Headers added to websocket upgrade request....");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.websocket.ClientEndpointConfig.Configurator#afterResponse(javax.
	 * websocket.HandshakeResponse)
	 */
	@Override
	public void afterResponse(HandshakeResponse response) {
		System.out.println(response.getHeaders());

	}

	@Override
	public List<Class<? extends Decoder>> getDecoders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Encoder>> getEncoders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getUserProperties() {
		Map<String, Object> userProperties = new HashMap<String, Object>();
		userProperties.put("payload", this.payload);
		return userProperties;
	}

	@Override
	public Configurator getConfigurator() {
		return this;
	}

	@Override
	public List<Extension> getExtensions() {
		return new ArrayList<Extension>();
	}

	@Override
	public List<String> getPreferredSubprotocols() {
		return new ArrayList<String>();
	}

}
