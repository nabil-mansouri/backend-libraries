package com.nm.payment.finder;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.paiments.TransactionNotFoundedException;
import com.nm.paiments.models.Transaction;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.GraphFinder;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class TransactionFinderImpl implements TransactionFinder {

	public GraphFinderPath<Transaction> find(Transaction tr, final Long transactionId)
			throws TransactionNotFoundedException {
		try {
			GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
			final Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
			ids.add(new TransactionFinderIdentifier(null, transactionId));
			TransactionFinderDecorator deco = new TransactionFinderDecorator(tr);
			GraphFinderPath<IGraph> path = finder.findFirst(deco, ids);
			if (!path.founded()) {
				throw new TransactionNotFoundedException();
			}
			return new GraphFinderPath<Transaction>(path.getFounded(TransactionFinderDecorator.class).getTransaction());
		} catch (Exception e) {
			throw new TransactionNotFoundedException(e);
		}
	}

	public GraphFinderPath<Transaction> find(Transaction tr, final String transactionId)
			throws TransactionNotFoundedException {
		try {
			GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
			final Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
			ids.add(new TransactionFinderIdentifier(transactionId, null));
			TransactionFinderDecorator deco = new TransactionFinderDecorator(tr);
			GraphFinderPath<IGraph> path = finder.findFirst(deco, ids);
			if (!path.founded()) {
				throw new TransactionNotFoundedException();
			}
			return new GraphFinderPath<Transaction>(path.getFounded(TransactionFinderDecorator.class).getTransaction());

		} catch (Exception e) {
			throw new TransactionNotFoundedException(e);
		}
	}

}
