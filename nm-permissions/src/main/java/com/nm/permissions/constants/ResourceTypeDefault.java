package com.nm.permissions.constants;

import java.util.Collection;

import com.nm.utils.ListUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public enum ResourceTypeDefault implements ResourceType {
	Default() {
		@Override
		public Collection<Action> lower() {
			return ListUtils.build(ActionDefault.List);
		}

		@Override
		public Collection<Action> master() {
			return ListUtils.build(ActionDefault.List, ActionDefault.Read, ActionDefault.Export);
		}

	},
	DefaultChild() {
		@Override
		public Collection<Action> lower() {
			return ListUtils.build(ActionDefault.List);
		}

		@Override
		public Collection<Action> master() {
			return ListUtils.build(ActionDefault.List, ActionDefault.Read, ActionDefault.Export);
		}

		@Override
		public ResourceType parent() {
			return ResourceTypeDefault.Default;
		}
	};
	//
	public ResourceType parent() {
		return null;
	}

	public Collection<Action> selectable() {
		return master();
	}

	public abstract Collection<Action> lower();

	public Collection<Action> master() {
		return lower();
	}
}