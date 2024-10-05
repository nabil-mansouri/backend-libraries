package com.rm.contract.clients.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rm.contract.clients.constants.ClientEventType;
import com.rm.utils.dates.PeriodType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCriteriaRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private boolean enable;
	//
	private PeriodType periodDuration;
	private boolean hasDurationCountFrom;
	private boolean hasDurationCountTo;
	private Double periodCountFrom;
	private Double periodCountTo;
	private boolean hasRangeFrom;
	private boolean hasRangeTo;
	private Double rangeFrom;
	private Double rangeTo;
	private boolean hasClientIds;
	private boolean hasPosition;
	private boolean isOnBirthday;
	private Double latitude;
	private Double longitude;
	private Double radius;
	private Set<Long> clientIds = new HashSet<Long>();
	private boolean isOnUserEvent;
	private Collection<ClientEventType> events = new HashSet<ClientEventType>();

	public boolean isOnUserEvent() {
		return isOnUserEvent;
	}

	public ClientCriteriaRuleBean setOnUserEvent(boolean isOnUserEvent) {
		this.isOnUserEvent = isOnUserEvent;
		return this;
	}

	public ClientCriteriaRuleBean addEvent(ClientEventType type) {
		this.events.add(type);
		return this;
	}

	public Collection<ClientEventType> getEvents() {
		return events;
	}

	public void setEvents(Collection<ClientEventType> events) {
		this.events = events;
	}

	public boolean isOnBirthday() {
		return isOnBirthday;
	}

	public ClientCriteriaRuleBean setOnBirthday(boolean isOnBirthday) {
		this.isOnBirthday = isOnBirthday;
		return this;
	}

	public boolean isHasPosition() {
		return hasPosition;
	}

	public ClientCriteriaRuleBean setHasPosition(boolean hasPosition) {
		this.hasPosition = hasPosition;
		return this;
	}

	public Double getLatitude() {
		return latitude;
	}

	public ClientCriteriaRuleBean setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public Double getLongitude() {
		return longitude;
	}

	public ClientCriteriaRuleBean setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public Double getRadius() {
		return radius;
	}

	public ClientCriteriaRuleBean setRadius(Double radius) {
		this.radius = radius;
		return this;
	}

	public void setClientIds(Set<Long> clientIds) {
		this.clientIds = clientIds;
	}

	public boolean isHasClientIds() {
		return hasClientIds;
	}

	public ClientCriteriaRuleBean setHasClientIds(boolean hasClientIds) {
		this.hasClientIds = hasClientIds;
		return this;
	}

	public Set<Long> getClientIds() {
		return clientIds;
	}

	public ClientCriteriaRuleBean setClientIds(Collection<Long> clientIds) {
		this.clientIds = new HashSet<Long>(clientIds);
		return this;
	}

	public Double getDurationCountFrom() {
		return periodCountFrom;
	}

	public Double getDurationCountTo() {
		return periodCountTo;
	}

	public Double getPeriodCountFrom() {
		return periodCountFrom;
	}

	public Double getPeriodCountTo() {
		return periodCountTo;
	}

	public PeriodType getPeriodDuration() {
		return periodDuration;
	}

	public Double getRangeFrom() {
		return rangeFrom;
	}

	public Double getRangeTo() {
		return rangeTo;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isHasDurationCountFrom() {
		return hasDurationCountFrom;
	}

	public boolean isHasDurationCountTo() {
		return hasDurationCountTo;
	}

	public boolean isHasRangeFrom() {
		return hasRangeFrom;
	}

	public boolean isHasRangeTo() {
		return hasRangeTo;
	}

	public ClientCriteriaRuleBean setDurationCountFrom(Double periodCountFrom) {
		this.periodCountFrom = periodCountFrom;
		return this;
	}

	public ClientCriteriaRuleBean setDurationCountTo(Double periodCountTo) {
		this.periodCountTo = periodCountTo;
		return this;
	}

	public ClientCriteriaRuleBean setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	public ClientCriteriaRuleBean setHasDurationCountFrom(boolean hasDurationCountFrom) {
		this.hasDurationCountFrom = hasDurationCountFrom;
		return this;
	}

	public ClientCriteriaRuleBean setHasDurationCountTo(boolean hasDurationCountTo) {
		this.hasDurationCountTo = hasDurationCountTo;
		return this;
	}

	public ClientCriteriaRuleBean setHasRangeFrom(boolean hasRangeFrom) {
		this.hasRangeFrom = hasRangeFrom;
		return this;
	}

	public ClientCriteriaRuleBean setHasRangeTo(boolean hasRangeTo) {
		this.hasRangeTo = hasRangeTo;
		return this;
	}

	public ClientCriteriaRuleBean setPeriodCountFrom(Double periodCountFrom) {
		this.periodCountFrom = periodCountFrom;
		return this;
	}

	public ClientCriteriaRuleBean setPeriodCountTo(Double periodCountTo) {
		this.periodCountTo = periodCountTo;
		return this;
	}

	public ClientCriteriaRuleBean setPeriodDuration(PeriodType period) {
		this.periodDuration = period;
		return this;
	}

	public ClientCriteriaRuleBean setRangeFrom(Double rangeFrom) {
		this.rangeFrom = rangeFrom;
		return this;
	}

	public ClientCriteriaRuleBean setRangeTo(Double rangeTo) {
		this.rangeTo = rangeTo;
		return this;
	}

}
