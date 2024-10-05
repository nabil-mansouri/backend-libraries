package com.nm.permissions.converters;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.inject.internal.Lists;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.constants.ResourceTypeDefault;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface ResourceFetcher extends Serializable {
	public Collection<ResourceType> resource();

	public static class ResourceFetcherImpl implements ResourceFetcher {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Collection<ResourceType> resource() {
			List<ResourceType> l = Lists.newArrayList();
			l.addAll(Arrays.asList(ResourceTypeDefault.values()));
			return l;
		}
	}
}
