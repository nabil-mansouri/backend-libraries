package com.rm.contract.statistics.beans;

import com.rm.contract.statistics.constants.StatisticOrderDirection;
import com.rm.contract.statistics.constants.StatisticOrderType;

/**
 * 
 * @author Nabil
 * 
 */
public class StatisticOrderBean {
	private StatisticOrderDirection direction;
	private StatisticOrderType type;

	public StatisticOrderBean() {
	}

	public StatisticOrderBean(StatisticOrderDirection direction, StatisticOrderType type) {
		super();
		this.direction = direction;
		this.type = type;
	}

	public StatisticOrderDirection getDirection() {
		return direction;
	}

	public void setDirection(StatisticOrderDirection direction) {
		this.direction = direction;
	}

	public StatisticOrderType getType() {
		return type;
	}

	public void setType(StatisticOrderType type) {
		this.type = type;
	}

}
