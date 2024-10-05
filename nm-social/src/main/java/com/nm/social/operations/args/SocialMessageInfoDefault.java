package com.nm.social.operations.args;

import java.util.Collection;

import com.google.inject.internal.Sets;
import com.nm.social.models.SocialNetwork;
import com.nm.social.operations.args.SocialMessageInfo;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialMessageInfoDefault implements SocialMessageInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String url;
	private String image;
	private String desc;
	private String caption;
	private String descImg;
	private boolean wall;
	private boolean direct;
	private Collection<SocialNetwork> networks = Sets.newHashSet();

	public String getCaption() {
		return caption;
	}

	public String getDescImg() {
		return descImg;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setDescImg(String descImg) {
		this.descImg = descImg;
	}

	public Collection<SocialNetwork> getNetworks() {
		return networks;
	}

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

	public SocialMessageInfoDefault() {
	}

	public SocialMessageInfoDefault(String title, String url, String image, String desc) {
		super();
		this.title = title;
		this.url = url;
		this.image = image;
		this.desc = desc;
	}

}
