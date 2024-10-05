package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.NavigationState;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private NavigationBody body = new NavigationBody();
	private NavigationHead head = new NavigationHead();
	private NavigationState state;

	public NavigationState getState() {
		return state;
	}

	public void setState(NavigationState state) {
		this.state = state;
	}

	public NavigationBody getBody() {
		return body;
	}

	public void setBody(NavigationBody body) {
		this.body = body;
	}

	public NavigationHead getHead() {
		return head;
	}

	public void setHead(NavigationHead head) {
		this.head = head;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NavigationNode cloneHead() {
		NavigationNode clone = SerializationUtils.clone(this);
		clone.setBody(new NavigationBody());
		return clone;
	}

	public NavigationNode clone() {
		NavigationNode clone = SerializationUtils.clone(this);
		return clone;
	}

	public NavigationPath find(NavigationHeadState state) {
		NavigationPath path = new NavigationPath();
		int j = 0;
		for (NavigationStack stack : this.head.getStacks()) {
			for (int i = 0; i < stack.getItems().size(); i++) {
				NavigationHeadItem item = stack.getItems().get(i);
				if (item.getState().equals(state)) {
					path.push(i, item);
					path.push(j, stack);
				}
			}
			j++;
		}
		return path;
	}

	public NavigationPathList findAll(NavigationHeadState... state) {
		List<NavigationHeadState> ss = Arrays.asList(state);
		NavigationPathList path = new NavigationPathList();
		int j = 0;
		for (NavigationStack stack : this.head.getStacks()) {
			for (int i = 0; i < stack.getItems().size(); i++) {
				NavigationHeadItem item = stack.getItems().get(i);
				if (ss.contains(item.getState())) {
					NavigationPath p = path.create();
					p.push(i, item);
					p.push(j, stack);
				}
			}
			j++;
		}
		return path;
	}
}
