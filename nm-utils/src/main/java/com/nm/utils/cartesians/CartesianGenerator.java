package com.nm.utils.cartesians;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CartesianGenerator {
	public <T> CartesianResults<T> generate(CartesianArguments<T> args) {
		List<List<T>> all = doGenerate(args.getValues());
		CartesianResults<T> res = new CartesianResults<T>();
		res.getRows().addAll(all);
		return res;
	}

	protected <T> List<List<T>> doGenerate(List<List<T>> lists) {
		List<List<T>> resultLists = new ArrayList<List<T>>();
		if (lists.size() == 0) {
			resultLists.add(new ArrayList<T>());
			return resultLists;
		} else {
			List<T> firstList = lists.get(0);
			List<List<T>> remainingLists = doGenerate(lists.subList(1, lists.size()));
			for (T condition : firstList) {
				for (List<T> remainingList : remainingLists) {
					ArrayList<T> resultList = new ArrayList<T>();
					resultList.add(condition);
					resultList.addAll(remainingList);
					resultLists.add(resultList);
				}
			}
		}
		return resultLists;
	}
}
