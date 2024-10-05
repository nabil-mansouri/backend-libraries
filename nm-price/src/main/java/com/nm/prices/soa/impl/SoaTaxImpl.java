package com.nm.prices.soa.impl;

import com.nm.prices.soa.SoaTax;

/**
 * TODO
 * 
 * @author Nabil
 * 
 */
public class SoaTaxImpl implements SoaTax {

	// @Autowired
	// private DaoTaxsDefinition daoTaxsDefinition;
	// @Autowired
	// private DaoPrice daoReleased;
	//
	// @Transactional(readOnly = true)
	// public PriceForm editPrice(Long id, String lang) throws
	// NoDataFoundException {
	// // Price released = daoReleased.loadById(id);
	// Collection<ModelOptions> options = new ArrayList<ModelOptions>();
	// options.addAll(Arrays.asList(PriceOptions.values()));
	// options.add(ProductOptions.Parts);
	// // return priceConverter.convert(released, lang, null, options);
	// return null;
	// }
	//
	// @Transactional(readOnly = true)
	// public PriceForm flexible(Long idProduct, String lang) throws
	// NoDataFoundException {
	// Collection<Price> prices =
	// daoReleased.find(PriceQueryBuilder.get().withOnlyCurrent(true)
	// .withSubject(PriceSubjectQueryBuilder.getProduct().withProduct(idProduct)));
	// if (prices.isEmpty()) {
	// prices = daoReleased.find(PriceQueryBuilder.get().withOrderByLast());
	// }
	// if (prices.isEmpty()) {
	// // return priceConverter.convert(new PriceSimple(), lang);
	// } else {
	// // return priceConverter.convert(prices.iterator().next(), lang);
	// }
	// return null;
	// }
	//
	// @Transactional(readOnly = true)
	// public TaxDefFormBean editTax(Long id) throws NoDataFoundException {
	// // TaxDefinition tax = daoTaxsDefinition.loadById(id);
	// // return priceConverter.convert(tax);
	// return null;
	// }
	//
	// @Transactional(readOnly = true)
	// public Collection<PriceForm> fetch(PriceFilterDto request, String lang) {
	// Collection<PriceForm> response = new ArrayList<PriceForm>();
	// // LinkedHash => Unique row
	// // PriceQueryBuilder criteria =
	// // PriceQueryBuilder.get().withFilter(request);
	// // Collection<Price> releaseds = new
	// // LinkedHashSet<Price>(daoReleased.find(criteria));
	// // for (Price released : releaseds) {
	// // response.add(priceConverter.convert(released, lang,
	// // request.getOrderType(), request.getOptions()));
	// // }
	// return response;
	// }
	//
	// @Transactional(readOnly = true)
	// public Collection<TaxDefFormBean> fetch(TaxDefFilterBean request) {
	// Collection<TaxDefFormBean> response = new ArrayList<TaxDefFormBean>();
	// // Collection<TaxDefinition> taxs;
	// if (request.getFirst() != null && request.getCount() != null) {
	// // taxs = daoTaxsDefinition.find(request.getFirst(),
	// // request.getCount());
	// } else {
	// // taxs =
	// //
	// daoTaxsDefinition.find(DetachedCriteria.forClass(TaxDefinition.class));
	// }
	// // for (TaxDefinition tax : taxs) {
	// // response.add(priceConverter.convert(tax));
	// // }
	// return response;
	// }
	//
	// @Transactional()
	// public Price removeReleased(Long id) throws NoDataFoundException {
	// Price released = daoReleased.loadById(id);
	// // Delete
	// daoReleased.delete(released);
	// return released;
	// }
	//
	// @Transactional()
	// public TaxDefinition removeTax(Long idTax) throws NoDataFoundException {
	// TaxDefinition tax = daoTaxsDefinition.loadById(idTax);
	// // Delete
	// daoTaxsDefinition.delete(tax);
	// return tax;
	// }
	//
	// @Transactional
	// public PriceForm saveOrUpdate(PriceForm request, String lang) throws
	// NoDataFoundException {
	// Price price = new PriceComposed();
	// if (request.getId() != null) {
	// price = daoReleased.loadById(request.getId());
	// price.childrens().clear();
	// price.getFilter().clear();
	// }
	// //
	// // price = priceConverter.convert(price, request);
	// daoReleased.saveOrUpdate(price);
	// //
	// request.setId(price.getId());
	// return request;
	// }
	//
	// @Transactional
	// public TaxDefFormBean saveOrUpdate(TaxDefFormBean request) throws
	// NoDataFoundException {
	// TaxDefinitionBuilder builder =
	// TaxDefinitionBuilder.get().withDenominateur(request.getDenominateur())
	// .withId(request.getId()).withNominateur(request.getNominateur()).withTaxeType(request.getType())
	// .withName(request.getName());
	// for (TaxeApplicability app : request.getApplicabilities()) {
	// builder.withApplicabilities(app);
	// }
	// for (TaxeEvents event : request.getEvents().keySet()) {
	// if (request.getEvents().get(event)) {
	// builder.withEvents(event);
	// }
	// }
	// TaxDefinition tax = builder.build();
	// if (tax.getId() != null) {
	// tax = daoTaxsDefinition.merge(tax);
	// }
	// daoTaxsDefinition.saveOrUpdate(tax);
	// //
	// request.setId(tax.getId());
	// return request;
	// }

}
