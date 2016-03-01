/**
 * 
 */
package com.ge.predix.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.ge.predix.rest.client.HTTPSClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Ravi_Shankar10
 *
 */
public class TimeseriesService {

	private static final String OAUTH_ISSUER_URI = "https://7b4ee61c-9a6d-4cee-8166-dc23306e1440.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/token";
	private static final String OAUTH_CLIENT_ID = "my_client";
	private static final String OAUTH_CLIENT_SECRET = "my_client";
	private static final String QUERY_URI = "https://time-series-store-predix.run.aws-usw02-pr.ice.predix.io/v1/datapoints";
	private static final String PREDIX_ZONE_ID = "305c175e-726d-430d-883d-198be621c0a2";

	public static void getTimeSeriesData() {
		JsonObject jsonPayload = new JsonObject();
		try {
			jsonPayload.addProperty("start", "9y-ago");
			jsonPayload.addProperty("end", "1mi-ago");
			JsonArray arr = new JsonArray();
			JsonObject tagName = new JsonObject();
			// JsonObject order = new JsonObject();
			tagName.addProperty("name", "EQUIP1-AI");
			tagName.addProperty("order", "desc");
			JsonObject aggObj = new JsonObject();

			aggObj.addProperty("type", "avg");
			aggObj.addProperty("interval", "1h");
			JsonArray aggArray = new JsonArray();
			aggArray.add(aggObj);
			// aggList.add(aggObj.toString());
			// tagName.addProperty("aggregations", aggArray.getAsJsonObject());
			// JsonArray aggregationsArray = new JsonArray();

			// aggregationsArray.add();
			// tagName.add("aggregations", aggObj);
			// tagName.addProperty("limit", "200");
			arr.add(tagName);
			jsonPayload.add("tags", arr);

			List<Header> headerList = new ArrayList<Header>();
			BasicHeader authHeader = new BasicHeader("Authorization", "Bearer " + HTTPSClient
					.generateBearerToken(OAUTH_ISSUER_URI, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, PREDIX_ZONE_ID));
			BasicHeader zoneHeader = new BasicHeader("Predix-Zone-Id", PREDIX_ZONE_ID);
			
			headerList.add(authHeader);
			headerList.add(zoneHeader);
			
			String payload = "{\"start\": 9y-ago,\"end\": 1mi-ago,\"tags\":" + "[{\"name\":"
					+ "\"EQUIP1-AI\""
					/*+ ",\"aggregations\":[{\"type\":interpolate,\"interval\":"
					+ "\"1h\"}]*/
					+ ",\"order\": \"desc\"}]}";
			System.out.println(payload);
			String resp = HTTPSClient.executeHttpsPost(QUERY_URI, headerList, payload);
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getTimeSeriesData();
	}

}
