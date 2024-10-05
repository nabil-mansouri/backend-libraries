package com.nm.plannings.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.Interval;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.SlotType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoSlotOccurrence implements Serializable {

	@Override
	public String toString() {
		return "EventBean [uuid=" + uuid + ", eventType=" + eventType + ", id=" + id + ", title=" + title + ", allDay="
				+ allDay + ", start=" + start + ", end=" + end + ", url=" + url + ", className=" + className
				+ ", editable=" + editable + ", startEditable=" + startEditable + ", durationEditable="
				+ durationEditable + ", color=" + color + ", backgroundColor=" + backgroundColor + ", borderColor="
				+ borderColor + ", textColor=" + textColor + ", originalStartPlan=" + originalStartPlan
				+ ", originalEndPlan=" + originalEndPlan + ", originalStartHoraire=" + originalStartHoraire
				+ ", originalEndHoraire=" + originalEndHoraire + ", noEndPlan=" + noEndPlan + ", idsMerged=" + idsMerged
				+ ", type=" + type + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Used for equals and hashcode (remove , add, ...)
	 */
	private String uuid = UUID.randomUUID().toString();
	private SlotRepeatKind eventType;
	private String id;
	private String title;
	private boolean allDay;
	private Date start;
	private Date end;
	private String url;
	private String className;
	private boolean editable;
	private boolean startEditable;
	private boolean durationEditable;
	private String color;
	private String backgroundColor;
	private String borderColor;
	private String textColor;
	private Date originalStartPlan;
	private Date originalEndPlan;
	private Date originalStartHoraire;
	private Date originalEndHoraire;
	private boolean noEndPlan;
	private List<String> idsMerged = new ArrayList<String>();
	private SlotType type;

	public DtoSlotOccurrence() {
		super();
	}

	public DtoSlotOccurrence(DtoSlotOccurrence copy) {
		setEventType(copy.getEventType()).setId(copy.getId());
		setAllDay(copy.isAllDay()).setStart(copy.getStart()).setEnd(copy.getEnd());
		setOriginalStartHoraire(copy.getOriginalStartHoraire()).setOriginalEndHoraire(copy.getOriginalEndHoraire());
		setOriginalStartPlan(copy.getOriginalStartPlan()).setOriginalEndPlan(copy.getOriginalEndPlan());
		setType(copy.getType()).getIdsMerged().addAll(copy.getIdsMerged());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isNoEndPlan() {
		return noEndPlan;
	}

	public DtoSlotOccurrence setNoEndPlan(boolean noEndPlan) {
		this.noEndPlan = noEndPlan;
		return this;
	}

	public Date getOriginalStartPlan() {
		return originalStartPlan;
	}

	public DtoSlotOccurrence setOriginalStartPlan(Date originalStartPlan) {
		this.originalStartPlan = originalStartPlan;
		return this;
	}

	public Date getOriginalEndPlan() {
		return originalEndPlan;
	}

	public DtoSlotOccurrence setOriginalEndPlan(Date originalEndPlan) {
		this.originalEndPlan = originalEndPlan;
		return this;
	}

	public Date getOriginalStartHoraire() {
		return originalStartHoraire;
	}

	public DtoSlotOccurrence setOriginalStartHoraire(Date originalStartHoraire) {
		this.originalStartHoraire = originalStartHoraire;
		return this;
	}

	public Date getOriginalEndHoraire() {
		return originalEndHoraire;
	}

	public DtoSlotOccurrence setOriginalEndHoraire(Date originalEndHoraire) {
		this.originalEndHoraire = originalEndHoraire;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public String getClassName() {
		return className;
	}

	public String getColor() {
		return color;
	}

	public Date getEnd() {
		return end;
	}

	public SlotRepeatKind getEventType() {
		return eventType;
	}

	public String getId() {
		return id;
	}

	public Date getStart() {
		return start;
	}

	public String getTextColor() {
		return textColor;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public boolean isDurationEditable() {
		return durationEditable;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isStartEditable() {
		return startEditable;
	}

	public DtoSlotOccurrence setAllDay(boolean allDay) {
		this.allDay = allDay;
		return this;
	}

	public DtoSlotOccurrence setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public DtoSlotOccurrence setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public DtoSlotOccurrence setClassName(String className) {
		this.className = className;
		return this;
	}

	public DtoSlotOccurrence setColor(String color) {
		this.color = color;
		return this;
	}

	public DtoSlotOccurrence setDurationEditable(boolean durationEditable) {
		this.durationEditable = durationEditable;
		return this;
	}

	public DtoSlotOccurrence setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	public DtoSlotOccurrence setEnd(Date end) {
		this.end = end;
		return this;
	}

	public DtoSlotOccurrence setEventType(SlotRepeatKind eventType) {
		this.eventType = eventType;
		return this;
	}

	public DtoSlotOccurrence setId(String id) {
		this.id = id;
		return this;
	}

	public DtoSlotOccurrence setStart(Date start) {
		this.start = start;
		return this;
	}

	public DtoSlotOccurrence setStartEditable(boolean startEditable) {
		this.startEditable = startEditable;
		return this;
	}

	public DtoSlotOccurrence setTextColor(String textColor) {
		this.textColor = textColor;
		return this;
	}

	public DtoSlotOccurrence setTitle(String title) {
		this.title = title;
		return this;
	}

	public DtoSlotOccurrence setUrl(String url) {
		this.url = url;
		return this;
	}

	public List<String> getIdsMerged() {
		return idsMerged;
	}

	public DtoSlotOccurrence setIdsMerged(List<String> idsMerged) {
		this.idsMerged = idsMerged;
		return this;
	}

	public SlotType getType() {
		return type;
	}

	public DtoSlotOccurrence setType(SlotType type) {
		this.type = type;
		return this;
	}

	public Interval toInterval() {
		return new Interval(this.start.getTime(), this.end.getTime());
	}

	public boolean isEmpty() {
		return toInterval().toDurationMillis() == 0;
	}

	public boolean overlaps(DtoSlotOccurrence event) {
		Interval i1 = toInterval();
		Interval i2 = event.toInterval();
		Interval i3 = i1.overlap(i2.toInterval());
		return i3 != null;
	}

	public boolean overlapsOrAbuts(DtoSlotOccurrence event) {
		Interval i1 = toInterval();
		Interval i2 = event.toInterval();
		Interval i3 = i1.overlap(i2.toInterval());
		return i3 != null || i1.abuts(i2.toInterval());
	}

	public DtoSlotOccurrence clone() {
		DtoSlotOccurrence clone = SerializationUtils.clone(this);
		clone.setUuid(UUID.randomUUID().toString());
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtoSlotOccurrence other = (DtoSlotOccurrence) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
