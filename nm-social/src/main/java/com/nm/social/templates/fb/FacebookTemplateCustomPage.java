package com.nm.social.templates.fb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PageAdministrationException;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.PageUpdate;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.impl.PagedListUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @See org.springframework.social.facebook.api.impl.PageTemplate
 * @author Nabil MANSOURI
 *
 */
public class FacebookTemplateCustomPage implements PageOperations {

	private final GraphApi graphApi;

	public FacebookTemplateCustomPage(GraphApi graphApi) {
		this.graphApi = graphApi;
	}

	public Page getPage(String pageId) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("fields", StringUtils.join(FULL_PROFILE_FIELDS, ","));
		return graphApi.fetchObject(pageId, Page.class);
	}

	public void updatePage(PageUpdate pageUpdate) {
		String pageId = pageUpdate.getPageId();
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> map = pageUpdate.toRequestParameters();
		map.add("access_token", pageAccessToken);
		graphApi.post(pageId, map);
	}

	public boolean isPageAdmin(String pageId) {
		return getAccount(pageId) != null;
	}

	public PagedList<Account> getAccounts() {
		return graphApi.fetchConnections("me", "accounts", Account.class);
	}

	public PagedList<Account> getAccounts(PagingParameters p) {
		MultiValueMap<String, String> queryMap = PagedListUtils.getPagingParameters(p);
		return graphApi.fetchConnections("me", "accounts", Account.class, queryMap);
	}

	public String post(String pageId, String message) {
		return post(new PagePostData(pageId).message(message));
	}

	public String post(String pageId, String message, FacebookLink link) {
		PagePostData postData = new PagePostData(pageId).message(message).link(link.getLink(), link.getPicture(),
				link.getName(), link.getCaption(), link.getDescription());
		return post(postData);
	}

	public String post(PagePostData post) {
		String pageId = post.getPageId();
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> map = post.toRequestParameters();
		map.set("access_token", pageAccessToken);
		return graphApi.publish(pageId, "feed", map);
	}

	public String postPhoto(String pageId, String albumId, Resource photo) {
		return postPhoto(pageId, albumId, photo, null);
	}

	public String postPhoto(String pageId, String albumId, Resource photo, String caption) {
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		if (caption != null) {
			parts.set("message", caption);
		}
		parts.set("access_token", pageAccessToken);
		return graphApi.publish(albumId, "photos", parts);
	}

	public PagedList<Page> search(String query) {
		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
		queryMap.add("q", query);
		queryMap.add("type", "page");
		return graphApi.fetchConnections("search", null, Page.class, queryMap);
	}

	public PagedList<Page> searchPlaces(String query, double latitude, double longitude, long distance) {
		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
		queryMap.add("q", query);
		queryMap.add("type", "place");
		queryMap.add("center", latitude + "," + longitude);
		queryMap.add("distance", String.valueOf(distance));
		return graphApi.fetchConnections("search", null, Page.class, queryMap);
	}

	public String getAccessToken(String pageId) {
		Account account = getAccount(pageId);
		if (account == null) {
			throw new PageAdministrationException(pageId);
		}
		return account.getAccessToken();
	}

	public Account getAccount(String pageId) {
		if (!accountCache.containsKey(pageId)) {
			// only bother fetching the account data in the event of a cache
			// miss
			List<Account> accounts = getAccounts();
			for (Account account : accounts) {
				accountCache.put(account.getId(), account);
			}
		}
		return accountCache.get(pageId);
	}

	public Facebook facebookOperations(String pageId) {
		return new FacebookTemplate(getAccessToken(pageId));
	}

	// private helper methods

	private Map<String, Account> accountCache = new HashMap<String, Account>();
	private static final String[] FULL_PROFILE_FIELDS = { "artists_we_like", "about", "access_token", "ad_campaign",
			"affiliation", "app_id", "app_links", "attire", "awards", "band_interests", "band_members", "best_page",
			"bio", "birthday", "booking_agent", "built", "business", "can_checkin", "can_post", "category",
			"category_list", "checkins", "company_overview", "contact_address", "context", "country_page_likes",
			"cover", "culinary_team", "current_location", "description", "description_html", "directed_by",
			"display_subtext", "emails", "engagement", "fan_count", "featured_video", "features", "food_styles",
			"founded", "general_info", "general_manager", "genre", "global_brand_page_name", "global_brand_root_id",
			"has_added_app", "hometown", "hours", "id", "impressum", "influences", "instant_articles_review_status",
			"is_always_open", "is_community_page", "is_permanently_closed", "is_published", "is_unclaimed",
			"is_verified", "last_used_time", "leadgen_tos_accepted", "link", "location", "members", "mission", "mpg",
			"name", "name_with_location_descriptor", "network", "new_like_count", "offer_eligible", "owner_business",
			"parent_page", "parking", "payment_options", "personal_info", "personal_interests", "pharma_safety_info",
			"phone", "place_type", "plot_outline", "press_contact", "price_range", "produced_by", "products",
			"promotion_eligible", "promotion_ineligible_reason", "public_transit", "record_label", "release_date",
			"restaurant_services", "restaurant_specialties", "schedule", "screenplay_by", "season",
			"single_line_address", "starring", "start_info", "store_location_descriptor", "store_number", "studio",
			"supports_instant_articles", "talking_about_count", "unread_message_count", "unread_notif_count",
			"unseen_message_count", "username", "verification_status", "voip_info", "website", "were_here_count",
			"written_by", "admin_notes" };
}
