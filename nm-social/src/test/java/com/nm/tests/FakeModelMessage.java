package com.nm.tests;

import java.util.Collection;

import com.google.inject.internal.Sets;
import com.nm.social.models.SocialNetwork;
import com.nm.social.operations.args.SocialMessageInfo;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FakeModelMessage implements SocialMessageInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String url;
	private String image;
	private String desc;
	private boolean wall;
	private boolean direct;
	private Collection<SocialNetwork> networks = Sets.newHashSet();

	public void setDirect(boolean direct) {
		this.direct = direct;
	}

	public boolean sendDirectMessage() {
		return direct;
	}

	public void setNetworks(Collection<SocialNetwork> networks) {
		this.networks = networks;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public boolean postIntoWall() {
		return wall;
	}

	public Collection<SocialNetwork> networks() {
		return networks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public FakeModelMessage() {
	}

	public FakeModelMessage(String title, String url, String image, String desc) {
		super();
		this.title = title;
		this.url = url;
		this.image = image;
		this.desc = desc;
	}

}
