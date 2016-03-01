/**
 * 
 */
package com.ge.predix.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.ge.predix.rest.client.HTTPSClient;

/**
 * @author Ravi_Shankar10
 *
 */
public class AssetService {
	private static final String ASSET_URI = "https://predix-asset.run.aws-usw02-pr.ice.predix.io/feed-water-pump";
	private static final String ASSET_CLIENT_ID = "my_client";
	private static final String ASSET_CLIENT_SECRET = "my_client";
	private static final String ASSET_ZONE_ID = "b8131245-6190-4b28-86e3-d1098f1ce200";
	private static final String ASSET_ISSUER_ID = "https://7b4ee61c-9a6d-4cee-8166-dc23306e1440.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/token";

	public static void createAssetService() {
		try {
			String payload = "[{\"uri\":\"/feed-water-pump/1\",\"pump_name\":\"feed water pump\",\"equipment\":\"/equip/1\",\"equipment_name\":\"EQUIP1\",\"sensor\":\"/sensor/1\",\"sensor_name\":\"AI\",\"tag_name\":\"EQUIP1-AI\",\"sensor_attributes\":{\"min\":0.0,\"max\":400.0}}]";

			List<Header> headerList = new ArrayList<Header>();

			BasicHeader authHeader = new BasicHeader("Authorization", "Bearer " + HTTPSClient
					.generateBearerToken(ASSET_ISSUER_ID, ASSET_CLIENT_ID, ASSET_CLIENT_SECRET, ASSET_ZONE_ID));

			BasicHeader zoneHeader = new BasicHeader("Predix-Zone-Id", ASSET_ZONE_ID);

			BasicHeader contentType = new BasicHeader("Content-Type", "application/json");

			headerList.add(authHeader);
			headerList.add(zoneHeader);
			headerList.add(contentType);

			String resp = HTTPSClient.executeHttpsPost(ASSET_URI, headerList, payload);

			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createAssetService();
	}

}
