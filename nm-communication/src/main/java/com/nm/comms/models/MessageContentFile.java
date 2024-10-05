package com.nm.comms.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nm.datas.models.AppData;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_content_file")
public class MessageContentFile extends MessageContent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private AppData data;

	public AppData getData() {
		return data;
	}

	public MessageContentFile setData(AppData data) {
		this.data = data;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageContentFile other = (MessageContentFile) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
