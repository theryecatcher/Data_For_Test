/**
 * 
 */
package com.ge.predix.rest.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;

import com.ge.predix.constants.PlatformConfiguration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Ravi_Shankar10
 *
 */
public class HttpsPostClient {

	private static final String HTTP_METHOD_POST = "POST";

	public static String generateBearerToken(String urlString) throws IOException {

		System.out.println("********** Generating bearer token from " + urlString + " **************");

		URL url = new URL(urlString);

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod(PlatformConfiguration.METHOD_POST);

		connection.setRequestProperty("Authorization", PlatformConfiguration.ASSET_AUTH_BASIC);

		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		connection.setRequestProperty("x-tenant", PlatformConfiguration.ASSET_TENANT_ID);

		connection.setDoOutput(true);

		connection.setDoInput(true);

		// Add any query parameters required. Use URLEncoder.encode to
		// Example:
		String query = "client_id=" + URLEncoder.encode(PlatformConfiguration.ASSET_CLIENT_ID, "UTF-8") + "&"
				+ "grant_type=" + URLEncoder.encode("client_credentials", "UTF-8") + "&client_secret="
				+ URLEncoder.encode(PlatformConfiguration.ASSET_CLIENT_SECRET, "UTF-8");

		// String query = "";

		DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

		dos.writeBytes(query);

		// dos.writeBytes(query);

		dos.flush();

		dos.close();

		DataInputStream dis = new DataInputStream(connection.getInputStream());

		StringBuffer buffer = new StringBuffer();

		for (int c = dis.read(); c != -1; c = dis.read())
			buffer.append((char) c); // System.out.print((char) c);
		dis.close();

		System.out.println("Resp Code:" + connection.getResponseCode());

		System.out.println("Resp Message:" + connection.getResponseMessage());

		System.out.println("********** Bearer token generated **********");

		JsonElement obj = new JsonParser().parse(buffer.toString());

		System.out.println(obj.getAsJsonObject().get("access_token").toString());
		String token = (obj.getAsJsonObject().get("access_token")).toString();

		return token.substring(1, token.length() - 1);
	}

	public static void main(String[] args) {
		try {
			JsonObject jsonPayload = new JsonObject();

			jsonPayload.addProperty("start", "1358294400000");
			jsonPayload.addProperty("end", "1358958900000");
			JsonArray arr = new JsonArray();
			JsonObject tagName = new JsonObject();
			// JsonObject order = new JsonObject();
			tagName.addProperty("name", "EQUIP1-AI");
			tagName.addProperty("order", "desc");
			tagName.addProperty("limit", 200);
			arr.add(tagName);
			jsonPayload.add("tags", arr);
			// jsonPayload.addProperty("tags", "[{\"name\" : \"EQUIP1-AK\",
			// \"order\": \"desc\"}]");

			System.out.println(jsonPayload.toString());
			String payload = "{\"start\": 9y-ago,\"end\": 1mi-ago,\"tags\":"+
			 "[{\"name\":"+
			 "\"EQUIP1-AK\",\"groups\":[{\"name\":\"time\",\"rangeSize\":"+
			 "\"1h\", \"groupCount\" : 10}],\"order\": \"desc\"}]}";
			// queryTimeSeriesData(PlatformConfiguration.QUERY_URI,
			// jsonPayload.toString());
			createAssetService();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void queryTimeSeriesData(String urlString, String payload) throws IOException {
		URL url = new URL(urlString);

		System.out.println("Querying data from : " + urlString);

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod(PlatformConfiguration.METHOD_POST);

		connection.setRequestProperty("Authorization",
				"Bearer " + generateBearerToken(PlatformConfiguration.OAUTH_ISSUER_URI));

		connection.setRequestProperty("Predix-Zone-Id", PlatformConfiguration.ZONE_ID);

		// connection.setRequestProperty("x-tenant",
		// PlatformConfiguration.TENANT_ID);

		connection.setDoOutput(true);

		connection.setDoInput(true);

		DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

		dos.writeBytes(payload);

		dos.flush();

		dos.close();

		DataInputStream dis = new DataInputStream(connection.getInputStream());

		StringBuffer buffer = new StringBuffer();

		for (int c = dis.read(); c != -1; c = dis.read())
			buffer.append((char) c); // System.out.print((char) c);
		dis.close();

		System.out.println("Query Resp Code:" + connection.getResponseCode());

		System.out.println("Query Resp Message:" + connection.getResponseMessage());

		System.out.println("Query Resp Body: " + buffer.toString());
	}

	public static void createAssetService() {

		try {
			String urlString = "https://predix-asset.run.aws-usw02-pr.ice.predix.io/feed-water-pump";

			URL url = new URL(urlString);

			System.out.println("Querying data from : " + urlString);

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod(PlatformConfiguration.METHOD_POST);

			connection.setRequestProperty("Authorization",
					"Bearer " + generateBearerToken(PlatformConfiguration.ASSET_ISSUER_URI));

			connection.setRequestProperty("Predix-Zone-Id", PlatformConfiguration.ASSET_INSTANCE_ID);

			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);

			connection.setDoInput(true);

			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

			String payload = "[{\"uri\":\"/feed-water-pump/1\",\"pump_name\":\"feed water pump\",\"equipment\":\"/equip/1\",\"equipment_name\":\"EQUIP1\",\"sensor\":\"/sensor/1\",\"sensor_name\":\"AI\",\"tag_name\":\"EQUIP1-AI\",\"sensor_attributes\":{\"min\":0.0,\"max\":400.0}}]";
			dos.writeBytes(payload);

			dos.flush();

			dos.close();

			DataInputStream dis = new DataInputStream(connection.getInputStream());

			StringBuffer buffer = new StringBuffer();

			for (int c = dis.read(); c != -1; c = dis.read())
				buffer.append((char) c); // System.out.print((char) c);
			dis.close();

			System.out.println("Asset Service Resp Code:" + connection.getResponseCode());

			System.out.println("Asset Service Resp Message:" + connection.getResponseMessage());

			System.out.println("Asset Service Resp Body: " + buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String executeHttpsPost(String urlString, List<Header> headersList, String payload) throws IOException {
		// Declarations
		URL url = null;
		HttpsURLConnection connection = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		StringBuffer buffer = null;

		// Construct URL
		url = new URL(urlString);
		connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod(HTTP_METHOD_POST);

		Header h = null;

		// Set headers in the request if present.
		for (int i = 0; null != headersList && i < headersList.size(); i++) {
			h = headersList.get(i);
			connection.setRequestProperty(h.getName(), h.getValue());
		}

		connection.setDoOutput(true);
		connection.setDoInput(true);

		dos = new DataOutputStream(connection.getOutputStream());

		// Post data.
		dos.writeBytes(payload);
		dos.flush();
		dos.close();

		// Response string formation.
		dis = new DataInputStream(connection.getInputStream());

		buffer = new StringBuffer();

		for (int c = dis.read(); c != -1; c = dis.read()) {
			buffer.append((char) c);
		}
		dis.close();

		// Return response string.
		return buffer.toString();
	}

	public String executeHttpsGet() {
		return null;
	}
}
