package com.nm.auths.constants;

import com.nm.config.constants.ModuleConfigKey;

/**
 * 
 * @author Nabil
 * 
 */
public enum ModuleConfigKeyAuthentication implements ModuleConfigKey {
	FacebookAuthURI("https://www.facebook.com/dialog/oauth"), //
	FacebookTokenURI("https://graph.facebook.com/v2.3/oauth/access_token"), //
	FacebookSecret(""), //
	FacebookClientId(""), //
	FacebookScope("email"), //
	FacebookAuthCode("code"), //
	//
	GoogleClientId(""), //
	GoogleSecret(""), //
	GoogleScope("openid,email,profile"), //
	GoogleTokenURI("https://accounts.google.com/o/oauth2/token"), //
	GoogleAuthURI("https://accounts.google.com/o/oauth2/auth"), //
	GoogleAuthCode("code"), //
	//
	TwitterAuthURI("https://api.twitter.com/oauth/authorize"), //
	TwitterAccessTokenURI("https://api.twitter.com/oauth/access_token"), //
	TwitterRequestTokenURI("https://api.twitter.com/oauth/request_token"), //
	TwitterSecret(""), //
	TwitterClientId(""), //
	TwitterScope("email"), //
	TwitterAuthCode("access_token"), //
	;
	private final String defaut;

	private ModuleConfigKeyAuthentication(String defaut) {
		this.defaut = defaut;
	}

	public boolean isPlainText() {
		return true;
	}

	public boolean isFile() {
		return false;
	}

	public String getDefaut() {
		return defaut;
	}
}
