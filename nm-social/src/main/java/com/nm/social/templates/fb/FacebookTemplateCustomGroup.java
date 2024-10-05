package com.nm.social.templates.fb;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMemberReference;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.GroupOperations;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.PagedListUtils;
import org.springframework.util.MultiValueMap;

/**
 * @See org.springframework.social.facebook.api.impl.GroupTemplate
 * @author Nabil MANSOURI
 *
 */
public class FacebookTemplateCustomGroup implements GroupOperations {

	private final GraphApi graphApi;

	public FacebookTemplateCustomGroup(GraphApi graphApi) {
		this.graphApi = graphApi;
	}

	public Group getGroup(String groupId) {
		return graphApi.fetchObject(groupId, Group.class);
	}

	public byte[] getGroupImage(String groupId) {
		return getGroupImage(groupId, ImageType.NORMAL);
	}

	public byte[] getGroupImage(String groupId, ImageType imageType) {
		return graphApi.fetchImage(groupId, "picture", imageType);
	}

	public PagedList<GroupMemberReference> getMembers(String groupId) {
		return graphApi.fetchConnections(groupId, "members", GroupMemberReference.class);
	}

	public PagedList<User> getMemberProfiles(String groupId) {
		return graphApi.fetchConnections(groupId, "members", User.class, FULL_PROFILE_FIELDS);
	}

	public PagedList<GroupMembership> getMemberships(PagingParameters pagedListParameters) {
		return getMemberships("me", pagedListParameters);
	}

	public PagedList<GroupMembership> getMemberships(String groupId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> parameters = PagedListUtils.getPagingParameters(pagedListParameters);
		parameters.set("fields", StringUtils.join(FULL_PROFILE_FIELDS, ","));
		return graphApi.fetchConnections(groupId, "groups", GroupMembership.class, FULL_PROFILE_FIELDS);
	}

	public PagedList<GroupMembership> getMemberships() {
		return getMemberships("me");
	}

	public PagedList<GroupMembership> getMemberships(String userId) {
		return graphApi.fetchConnections(userId, "groups", GroupMembership.class);
	}

	public PagedList<Group> search(String query) {
		return search(query, new PagingParameters(25, 0, null, null));
	}

	public PagedList<Group> search(String query, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> queryMap = PagedListUtils.getPagingParameters(pagedListParameters);
		queryMap.add("q", query);
		queryMap.add("type", "group");
		queryMap.add("fields", "owner,name,description,privacy,icon,updated_time,email");
		return graphApi.fetchConnections("search", "", Group.class, queryMap);
	}

	private static final String[] FULL_PROFILE_FIELDS = { "id", "name", "first_name", "last_name", "gender", "locale",
			"education", "work", "email", "third_party_id", "link", "timezone", "updated_time", "verified", "about",
			"bio", "birthday", "location", "hometown", "interested_in", "religion", "political", "quotes",
			"relationship_status", "significant_other", "website" };

}
