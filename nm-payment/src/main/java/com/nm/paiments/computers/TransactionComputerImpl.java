package com.nm.paiments.computers;

import com.nm.paiments.PaimentException;
import com.nm.paiments.constants.TransactionStateType;
import com.nm.paiments.contract.PaimentAdapter;
import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionSimple;
import com.nm.paiments.models.TransactionSubject;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerIGraph;

/**
 * 
 * @author Nabil
 * 
 */
public class TransactionComputerImpl implements TransactionComputer {

	public Double compute(Transaction transaction) {
		class ComputerIGraphListener implements IteratorListenerIGraph {
			private Double total = 0d;

			public void onFounded(IGraph node) {
				if (node instanceof TransactionSimple) {
					TransactionSimple simple = (TransactionSimple) node;
					if (simple.getLastState().getType().equals(TransactionStateType.Commit)) {
						// Times quantity because in bean we have detail
						switch (simple.getAction()) {
						case Credit:
							total += simple.getPayment().getTotal();
							break;
						case Debit:
							total += -simple.getPayment().getTotal();
							break;
						}

					}
				}
			}
		}
		ComputerIGraphListener list = new ComputerIGraphListener();
		GraphIteratorBuilder.buildDeep().iterate(transaction, list);
		return list.total;
	}

	public TransactionComputerResult compute(TransactionSubject order, Transaction transaction, PaimentAdapter adapter)
			throws PaimentException {
		TransactionComputerResult result = new TransactionComputerResult();
		Double value = adapter.compute(order).compute(order);
		result.setPaid(compute(transaction));
		result.setDue(value - result.getPaid());
		return result;
	}
}
