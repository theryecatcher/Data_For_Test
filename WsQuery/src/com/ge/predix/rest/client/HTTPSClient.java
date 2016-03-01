/**
 * 
 */
package com.ge.predix.rest.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;

import com.ge.predix.constants.PlatformConfiguration;
import com.ge.predix.exceptions.PredixException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author Ravi_Shankar10
 *
 */
public class HTTPSClient {

	/**
	 * @param urlString
	 * @param clientId
	 * @param clientSecret
	 * @param tenantId
	 * @return
	 * @throws PredixException
	 */
	public static String generateBearerToken(String urlString, String clientId, String clientSecret, String tenantId)
			throws PredixException {
		System.out.println("************** Generating bearer token from url : " + urlString + " **************");

		// Declarations.
		String basicAuthString = null;
		String query = null;
		String token = null;

		HttpsURLConnection connection = null;
		URL url = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		StringBuffer buffer = null;

		try {
			url = new URL(urlString);

			connection = (HttpsURLConnection) url.openConnection();

			// Encode client id and client secret.
			basicAuthString = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()).toString();

			connection.setRequestMethod(PlatformConfiguration.METHOD_POST);

			connection.setRequestProperty("Authorization", basicAuthString);

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("x-tenant", tenantId);

			connection.setDoOutput(true);

			connection.setDoInput(true);

			query = "client_id=" + URLEncoder.encode(clientId, "UTF-8") + "&" + "grant_type="
					+ URLEncoder.encode("client_credentials", "UTF-8") + "&client_secret="
					+ URLEncoder.encode(clientSecret, "UTF-8");

			dos = new DataOutputStream(connection.getOutputStream());

			dos.writeBytes(query);

			dos.flush();

			dos.close();

			dis = new DataInputStream(connection.getInputStream());

			buffer = new StringBuffer();

			for (int c = dis.read(); c != -1; c = dis.read())
				buffer.append((char) c);
			dis.close();

			System.out.println("********** Bearer token generated **********");

			JsonElement obj = new JsonParser().parse(buffer.toString());

			System.out.println(obj.getAsJsonObject().get("access_token").toString());

			token = (obj.getAsJsonObject().get("access_token")).toString();

		} catch (MalformedURLException e) {
			throw new PredixException("Malformed url. Error: " + e.getMessage(), e);
		} catch (UnsupportedEncodingException uee) {
			throw new PredixException("Encoding failed. Error: " + uee.getMessage(), uee);
		} catch (IOException ioe) {
			throw new PredixException("Failed to connect to url. Error: " + ioe.getMessage(), ioe);
		}

		return token.substring(1, token.length() - 1);
	}

	/**
	 * @param urlString
	 * @param headers
	 * @param payload
	 * @return
	 * @throws PredixException
	 */
	public static String executeHttpsPost(String urlString, List<Header> headers, String payload)
			throws PredixException {

		// Declarations.
		HttpsURLConnection connection = null;
		URL url = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		StringBuffer buffer = null;

		try {

			url = new URL(urlString);

			connection = (HttpsURLConnection) url.openConnection();

			// Add headers to the request.
			for (int i = 0; i < headers.size(); i++) {
				Header h = headers.get(i);
				connection.setRequestProperty(h.getName(), h.getValue());
			}

			connection.setDoOutput(true);

			connection.setDoInput(true);

			dos = new DataOutputStream(connection.getOutputStream());

			dos.writeBytes(payload);

			dos.flush();

			dos.close();

			dis = new DataInputStream(connection.getInputStream());

			buffer = new StringBuffer();

			for (int c = dis.read(); c != -1; c = dis.read())
				buffer.append((char) c);
			dis.close();

			System.out.println("Https Resp Code:" + connection.getResponseCode());

			System.out.println("Https Resp Message:" + connection.getResponseMessage());

			System.out.println("Https Resp Body: " + buffer.toString());

		} catch (MalformedURLException e) {
			throw new PredixException("Malformed url " + urlString, e);
		} catch (IOException e) {
			throw new PredixException("Failed to execute https post. Error: " + e.getMessage(), e);
		}

		return buffer.toString();
	}
	
	public static String executeHttpsGet(String urlString, List<Header> headers) throws PredixException {
		HttpsURLConnection connection = null;
		URL url = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		StringBuffer buffer = null;

		try {

			url = new URL(urlString);

			connection = (HttpsURLConnection) url.openConnection();

			// Add headers to the request.
			connection.setRequestMethod("GET");
			
			for (int i = 0; i < headers.size(); i++) {
				Header h = headers.get(i);
				connection.setRequestProperty(h.getName(), h.getValue());
			}

			connection.setDoOutput(true);

			connection.setDoInput(true);

			dos = new DataOutputStream(connection.getOutputStream());

			dos.writeBytes("");

			dos.flush();

			dos.close();

			dis = new DataInputStream(connection.getInputStream());

			buffer = new StringBuffer();

			for (int c = dis.read(); c != -1; c = dis.read())
				buffer.append((char) c);
			dis.close();

			System.out.println("Https Resp Code:" + connection.getResponseCode());

			System.out.println("Https Resp Message:" + connection.getResponseMessage());

			System.out.println("Https Resp Body: " + buffer.toString());

		} catch (MalformedURLException e) {
			throw new PredixException("Malformed url " + urlString, e);
		} catch (IOException e) {
			throw new PredixException("Failed to execute https post. Error: " + e.getMessage(), e);
		}

		return buffer.toString();
	}

}
