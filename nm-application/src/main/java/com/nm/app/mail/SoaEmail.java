package com.nm.app.mail;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface SoaEmail {

	public void send(EmailBuilderContract builder) throws EmailException;
}
