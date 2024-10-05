package com.nm.tests.bridges;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.contract.PriceFormConverter;
import com.nm.prices.contract.PriceViewConverter;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.soa.computers.PriceComputerFilterBuilder;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceAdapterImpl implements PriceAdapterContract {
	private PriceFormConverter formConverter;
	private PriceViewConverter viewConverter;

	public void setFormConverter(PriceFormConverter formConverter) {
		this.formConverter = formConverter;
	}

	public void setViewConverter(PriceViewConverter viewConverter) {
		this.viewConverter = viewConverter;
	}

	public static class CustomPriceQueryBuilder extends PriceQueryBuilder {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomPriceQueryBuilder() {
			super(Price.class);
		}

		public static CustomPriceQueryBuilder get() {
			return new CustomPriceQueryBuilder();
		}

	}

	public static class CustomPriceSubjectQueryBuilder extends PriceSubjectQueryBuilder {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomPriceSubjectQueryBuilder() {
			super(PriceSubject.class);
		}

	}

	public static class CustomPriceValueQueryBuilder extends PriceValueQueryBuilder {

		private static final long serialVersionUID = 1L;

		protected CustomPriceValueQueryBuilder() {
			super();
		}

		public static CustomPriceValueQueryBuilder get() {
			return new CustomPriceValueQueryBuilder();
		}
	}

	public OptionsList getAllOptions() {
		return new OptionsList().withOption(PriceFormOptions.values());
	}

	public PriceFilterDto createFilter() {
		PriceFilterDto filter = new PriceFilterDto();
		return filter;
	}

	public PriceQueryBuilder buildQuery() {
		return new CustomPriceQueryBuilder();
	}

	public PriceSubjectQueryBuilder buildSubjectQuery() {
		return new CustomPriceSubjectQueryBuilder();
	}

	public PriceAdapterImpl() {
	}

	public PriceValueQueryBuilder buildValueQuery() {
		return CustomPriceValueQueryBuilder.get();
	}

	public PriceComputerFilterBuilder buildComputer() {
		return new PriceComputerFilterBuilder() {
			public PriceFilterDto create() {
				return new PriceFilterDto();
			}

			public boolean hasNext() {
				return false;
			}

			public PriceFilterDto next(PriceFilterDto base) {
				return base.clone();
			}

			public void reset(PriceFilterDto filter) {
			}
		};
	}

	public PriceFormConverter getFormConverter() {
		return formConverter;
	}

	public PriceViewConverter getViewConverter() {
		return viewConverter;
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceQueryBuilder query) {
		return new ArrayList<ContractPriceFilterViewModel>();
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceValueQueryBuilder query) {
		return new ArrayList<ContractPriceFilterViewModel>();
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceFilterDto filter) {
		return new ArrayList<ContractPriceFilterViewModel>();
	}

	public Collection<ContractPriceFilterViewModel> fetch(Long id) {
		return new ArrayList<ContractPriceFilterViewModel>();
	}
}
