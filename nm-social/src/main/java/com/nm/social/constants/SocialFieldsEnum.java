package com.nm.social.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface SocialFieldsEnum {
	// TODO convert to one standard user (near from facebook user?)
	// TODO save as standard json?
	// http://blog.endpoint.com/2013/06/postgresql-as-nosql-with-data-validation.html
	// TODO hibernate criteria using sql part for json
	public enum SocialFieldsDefault implements SocialFieldsEnum {
		About, //
		Address, //
		Bio, //
		Birthday, //
		Currency, //
		Devices, //
		Education, //
		Email, //
		FavoriteTeam, //
		FavoriteAthlete, //
		FirstName, //
		Gender, //
		Hometown, //
		LastName, //
		Locale, //
		Location, //
		Political, //
		RelationShipStatus, //
		Religion, //
		Sports, //
		Website, //
		Work;

	}
}
