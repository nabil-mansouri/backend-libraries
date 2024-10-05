package com.nm.permissions.acl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @See org.springframework.security.acls.afterinvocation
 * @author Nabil MANSOURI
 *
 * @param <T>
 */
public class CollectionFilterer<T> implements Iterable<T> {
	// ~ Static fields/initializers
	// =====================================================================================

	protected static final Log logger = LogFactory.getLog(CollectionFilterer.class);

	// ~ Instance fields
	// ================================================================================================

	private final Collection<T> collection;

	private final Set<T> removeList;

	// ~ Constructors
	// ===================================================================================================

	CollectionFilterer(Collection<T> collection) {
		this.collection = collection;

		// We create a Set of objects to be removed from the Collection,
		// as ConcurrentModificationException prevents removal during
		// iteration, and making a new Collection to be returned is
		// problematic as the original Collection implementation passed
		// to the method may not necessarily be re-constructable (as
		// the Collection(collection) constructor is not guaranteed and
		// manually adding may lose sort order or other capabilities)
		removeList = new HashSet<T>();
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 *
	 * @see org.springframework.security.acls.afterinvocation.Filterer#getFilteredObject()
	 */
	public Collection<T> getFilteredObject() {
		// Now the Iterator has ended, remove Objects from Collection
		Iterator<T> removeIter = removeList.iterator();

		int originalSize = collection.size();

		while (removeIter.hasNext()) {
			collection.remove(removeIter.next());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Original collection contained " + originalSize + " elements; now contains " + collection.size() + " elements");
		}

		return collection;
	}

	/**
	 *
	 * @see org.springframework.security.acls.afterinvocation.Filterer#iterator()
	 */
	public Iterator<T> iterator() {
		return collection.iterator();
	}

	/**
	 *
	 * @see org.springframework.security.acls.afterinvocation.Filterer#remove(java.lang.Object)
	 */
	public void remove(T object) {
		removeList.add(object);
	}

	public void forEach(Consumer<? super T> action) {
		collection.forEach(action);
	}

	public Spliterator<T> spliterator() {
		return collection.spliterator();
	}
}
