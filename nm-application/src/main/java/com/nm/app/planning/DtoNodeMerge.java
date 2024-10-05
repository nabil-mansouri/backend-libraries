package com.nm.app.planning;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.Interval;

import com.google.common.collect.Lists;
import com.nm.utils.ListUtils;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class DtoNodeMerge implements Serializable, IGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date end;
	protected Date start;
	protected boolean root = true;
	protected String uuid = UUID.randomUUID().toString();
	protected List<DtoNodeMerge> merged = Lists.newArrayList();

	public DtoNodeMerge clone() {
		DtoNodeMerge clone = SerializationUtils.clone(this);
		clone.setUuid(UUID.randomUUID().toString());
		return clone;
	}

	public boolean root() {
		return root;
	}

	public Date getEnd() {
		return end;
	}

	public Date getStart() {
		return start;
	}

	public abstract String getType();

	public String getUuid() {
		return uuid;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return toInterval().toDurationMillis() == 0;
	}

	public boolean overlaps(DtoNodeMerge other) {
		Interval i1 = toInterval();
		Interval i2 = other.toInterval();
		Interval i3 = i1.overlap(i2.toInterval());
		return i3 != null;
	}

	public boolean abuts(DtoNodeMerge other) {
		Interval i1 = toInterval();
		Interval i2 = other.toInterval();
		return i1.abuts(i2.toInterval());
	}

	public boolean overlapsOrAbuts(DtoNodeMerge other) {
		return overlaps(other) || abuts(other);
	}

	public void pushMerged(DtoNodeMerge child) {
		this.merged.add(child);
		child.setRoot(false);
	}

	public DtoNodeMerge setEnd(Date end) {
		this.end = end;
		return this;
	}

	public DtoNodeMerge setStart(Date start) {
		this.start = start;
		return this;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<? extends IGraph> childrens() {
		return ListUtils.cast(this.merged);
	}

	public String uuid() {
		return uuid;
	}

	public Interval toInterval() {
		return new Interval(this.start.getTime(), this.end.getTime());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DtoNodeMerge [");
		if (end != null) {
			builder.append("end=");
			builder.append(end);
			builder.append(", ");
		}
		if (start != null) {
			builder.append("start=");
			builder.append(start);
			builder.append(", ");
		}
		builder.append("root=");
		builder.append(root);
		builder.append(", ");
		if (uuid != null) {
			builder.append("uuid=");
			builder.append(uuid);
			builder.append(", ");
		}
		if (merged != null) {
			builder.append("merged=");
			builder.append(merged);
		}
		builder.append("]");
		return builder.toString();
	}

}
