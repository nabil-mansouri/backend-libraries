package com.nm.geo.providers;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nm.config.SoaModuleConfig;
import com.nm.geo.constants.ModuleConfigKeyAddress;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.dtos.AddressDto;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.http.HttpResponseDto;
import com.nm.utils.http.HttpUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AddressProviderGoogle implements AddressProvider {
	protected static String getKey() {
		SoaModuleConfig config = ApplicationUtils.getBean(SoaModuleConfig.class);
		return config.getText(ModuleConfigKeyAddress.GoogleKey);
	}

	public AddressDto geocode(String address, OptionsList options) throws Exception {
		URIBuilder u = new URIBuilder("https://maps.google.com/maps/api/geocode/json");
		u.addParameter("address", address);
		u.addParameter("key", getKey());
		u.addParameter("sensor", "true");
		HttpResponseDto r = HttpUtilsExt.getHttp(u.build().toString());
		//
		if (r.getStatus() == 200) {
			JSONObject o = new JSONObject(r.getBody());
			String status = o.getString("status");
			JSONArray results = o.getJSONArray("results");
			//
			if (status.equals("OK")) {
				JSONObject details = results.getJSONObject(0);
				JSONObject geometry = details.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				AddressDtoImpl dto = new AddressDtoImpl().setGeocode(address).setDetails(details);
				dto.getComponents().setLatitude(location.getDouble("lat"));
				dto.getComponents().setLongitude(location.getDouble("lng"));
				JSONArray arr = details.getJSONArray("address_components");
				String street = "";
				for (int i = 0; i < arr.length(); i++) {
					JSONObject comp = arr.getJSONObject(i);
					JSONArray types = comp.getJSONArray("types");
					for (int j = 0; j < types.length(); j++) {
						String val = types.getString(j);
						if (StringUtils.equalsIgnoreCase(val, "country")) {
							dto.getComponents().setCountry(comp.getString("long_name"));
						} else if (StringUtils.equalsIgnoreCase(val, "postal_code")) {
							dto.getComponents().setPostal(comp.getString("long_name"));
						} else if (StringUtils.equalsIgnoreCase(val, "locality")) {
							dto.getComponents().setLocality(comp.getString("long_name"));
						} else if (StringUtils.equalsIgnoreCase(val, "route")) {
							street = street + " " + comp.getString("long_name");
						} else if (StringUtils.equalsIgnoreCase(val, "street_address")) {
							street = street + " " + comp.getString("long_name");
						} else if (StringUtils.equalsIgnoreCase(val, "street_number")) {
							street = comp.getString("long_name") + " " + street;
						}
					}
				}
				dto.getComponents().setStreet(street);
				return dto;
			} else {
				throw new Exception("Error from the API - response status: " + status);
			}
		}
		throw new Exception("Not found");
	}

}
