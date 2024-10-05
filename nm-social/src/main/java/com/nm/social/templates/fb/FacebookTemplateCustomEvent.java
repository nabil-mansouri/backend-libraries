package com.nm.social.templates.fb;

import static org.springframework.social.facebook.api.impl.PagedListUtils.getPagingParameters;

import org.springframework.social.facebook.api.Event;
import org.springframework.social.facebook.api.EventInvitee;
import org.springframework.social.facebook.api.EventOperations;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @See org.springframework.social.facebook.api.impl.EventTemplate
 * @author Nabil MANSOURI
 *
 */
public class FacebookTemplateCustomEvent implements EventOperations {

	private final GraphApi graphApi;

	public FacebookTemplateCustomEvent(GraphApi graphApi) {
		this.graphApi = graphApi;
	}

	public Event getEvent(String eventId) {
		return graphApi.fetchObject(eventId, Event.class, ALL_FIELDS);
	}

	public byte[] getEventImage(String eventId) {
		return getEventImage(eventId, ImageType.NORMAL);
	}

	public byte[] getEventImage(String eventId, ImageType imageType) {
		return graphApi.fetchImage(eventId, "picture", imageType);
	}

	public PagedList<EventInvitee> getInvited(String eventId) {
		return graphApi.fetchConnections(eventId, "invited", EventInvitee.class);
	}

	public PagedList<Invitation> getCreated() {
		return getEventsForUserByStatus("me", "created", new PagingParameters(25, 0, null, null));
	}

	public PagedList<Invitation> getCreated(PagingParameters pagingParams) {
		return getEventsForUserByStatus("me", "created", pagingParams);
	}

	public PagedList<Invitation> getAttending() {
		return getEventsForUserByStatus("me", "attending", new PagingParameters(25, 0, null, null));
	}

	public PagedList<Invitation> getAttending(PagingParameters pagingParams) {
		return getEventsForUserByStatus("me", "attending", pagingParams);
	}

	public PagedList<EventInvitee> getAttending(String eventId) {
		return graphApi.fetchConnections(eventId, "attending", EventInvitee.class);
	}

	public PagedList<Invitation> getMaybeAttending() {
		return getEventsForUserByStatus("me", "maybe", new PagingParameters(25, 0, null, null));
	}

	public PagedList<Invitation> getMaybeAttending(PagingParameters pagingParams) {
		return getEventsForUserByStatus("me", "maybe", pagingParams);
	}

	public PagedList<EventInvitee> getMaybeAttending(String eventId) {
		return graphApi.fetchConnections(eventId, "maybe", EventInvitee.class);
	}

	public PagedList<Invitation> getNoReplies() {
		return getEventsForUserByStatus("me", "not_replied", new PagingParameters(25, 0, null, null));
	}

	public PagedList<Invitation> getNoReplies(PagingParameters pagingParams) {
		return getEventsForUserByStatus("me", "not_replied", pagingParams);
	}

	public PagedList<EventInvitee> getNoReplies(String eventId) {
		return graphApi.fetchConnections(eventId, "noreply", EventInvitee.class);
	}

	public PagedList<Invitation> getDeclined() {
		return getEventsForUserByStatus("me", "declined", new PagingParameters(25, 0, null, null));
	}

	public PagedList<Invitation> getDeclined(PagingParameters pagingParams) {
		return getEventsForUserByStatus("me", "declined", pagingParams);
	}

	public PagedList<EventInvitee> getDeclined(String eventId) {
		return graphApi.fetchConnections(eventId, "declined", EventInvitee.class);
	}

	public void acceptInvitation(String eventId) {
		graphApi.post(eventId, "attending", new LinkedMultiValueMap<String, Object>());
	}

	public void maybeInvitation(String eventId) {
		graphApi.post(eventId, "maybe", new LinkedMultiValueMap<String, Object>());
	}

	public void declineInvitation(String eventId) {
		graphApi.post(eventId, "declined", new LinkedMultiValueMap<String, Object>());
	}

	public PagedList<Event> search(String query) {
		return search(query, new PagingParameters(25, 0, null, null));
	}

	public PagedList<Event> search(String query, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> queryMap = getPagingParameters(pagedListParameters);
		queryMap.add("q", query);
		queryMap.add("type", "event");
		return graphApi.fetchConnections("search", null, Event.class, queryMap);
	}

	// private helpers

	private PagedList<Invitation> getEventsForUserByStatus(String userId, String status,
			PagingParameters pagingParams) {
		MultiValueMap<String, String> parameters = getPagingParameters(pagingParams);
		return graphApi.fetchConnections(userId, "events/" + status, Invitation.class, parameters);
	}

	// TODO NEW PERMISSIONS:
	// attending_count,can_guests_invite,category,cover,declined_count,description,end_time,
	// guest_list_enabled,id,interested_count,is_page_owned,is_viewer_admin,maybe_count,name,
	// noreply_count,owner,parent_group,place,start_time,ticket_uri,timezone,type,updated_time,
	// admins{id},attending{context},comments,declined{context},feed,interested{context},photos,
	// live_videos,maybe{context},noreply{context},picture,posts,roles,videos
	private static final String[] ALL_FIELDS = { "id", "cover", "description", "end_time", //
			"name", "owner", "parent_group", "start_time", "ticket_uri", "timezone", //
			"updated_time", "place" };
}
