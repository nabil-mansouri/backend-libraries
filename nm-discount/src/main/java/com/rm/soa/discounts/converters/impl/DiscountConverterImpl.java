package com.rm.soa.discounts.converters.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rm.app.ModelOptions;
import com.rm.contract.discounts.beans.DiscountCommunicationBean;
import com.rm.contract.discounts.beans.DiscountCommunicationContentBean;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleRuleBean;
import com.rm.contract.discounts.beans.DiscountLifeCycleTrackingRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleBean;
import com.rm.contract.discounts.beans.DiscountRuleSubjectBean;
import com.rm.contract.discounts.constants.CommunicationType;
import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountOptions;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.communication.DiscountCommunicationContent;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRuleCount;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRuleDate;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRulePeriod;
import com.rm.model.discounts.rules.DiscountRuleFree;
import com.rm.model.discounts.rules.DiscountRuleGift;
import com.rm.model.discounts.rules.DiscountRuleOperation;
import com.rm.model.discounts.rules.subject.DiscountRuleSubject;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectProduct;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRuleCount;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRulePeriod;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRulePeriodic;
import com.rm.soa.discounts.converters.DiscountConverter;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountConverterImpl implements DiscountConverter {

	public DiscountRuleSubject convert(DiscountRuleSubjectBean bean) {
		if (bean.isConcernedAdditionnal()) {
			// TODO
		} else if (bean.isConcernedOrder()) {

		} else if (bean.isConcernedProduct()) {

		} else if (bean.isConcernedTaxs()) {

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.discounts.converters.DiscountConverterI#convert(com.rm.model
	 * .discounts.DiscountDefinition,
	 * com.rm.contract.discounts.beans.DiscountFormBean)
	 */
	@Transactional(readOnly = true)
	public DiscountDefinition convert(final DiscountDefinition discount, final DiscountFormBean bean)
			throws NoDataFoundException {
		discount.setId(bean.getId());
		discount.setName(bean.getName());
		// Order Criteria
		for (DiscountRuleBean ruleBean : bean.getRules()) {
			if (ruleBean.isFree()) {
				DiscountRuleFree rule = new DiscountRuleFree();
				DiscountRuleSubjectProduct sub = new DiscountRuleSubjectProduct();
				sub.setAll(false);
				rule.setSubject(sub);
				discount.getRule().add(rule);
				// TODO
				// for (ProductFormDto prou : ruleBean.getFreeProducts()) {
				// sub.getProducts().add(daoProductDefinition.load(prou.getId())
				// );
				// }
			} else if (ruleBean.isGift()) {
				DiscountRuleGift rule = new DiscountRuleGift();
				DiscountRuleSubjectProduct sub = new DiscountRuleSubjectProduct();
				sub.setAll(false);
				rule.setSubject(sub);
				discount.getRule().add(rule);
				// TODO
				// for (ProductFormDto prou : ruleBean.getGiftProducts()) {
				// sub.getProducts().add(prou.getId());
				// }
			} else if (ruleBean.isOperation()) {
				DiscountRuleOperation rule = new DiscountRuleOperation();
				rule.setOperation(ruleBean.getOperationType());
				rule.setValue(ruleBean.getOperationValue());
				// TODO
				// if(ruleBean.getSubject().)
			} else if (ruleBean.isReduction()) {

			} else if (ruleBean.isReplacePrice()) {

			} else if (ruleBean.isSpecial()) {

			}
		}
		// Lifecycle
		discount.clearLifeRules();
		for (DiscountLifeCycleRuleType type : bean.getLifeRules().keySet()) {
			DiscountLifeCycleRuleBean rule = bean.getLifeRules().get(type);
			if (rule.isEnable()) {
				if (rule.isDateLimited()) {
					if (rule.isHasToDate()) {
						DiscountLifeCycleRuleDate ruleP = new DiscountLifeCycleRuleDate();
						ruleP.setFromDate(rule.getFrom());
						ruleP.setToDate(rule.getTo());
						discount.put(DiscountLifeCycleRuleType.Date, ruleP);
					} else if (rule.isExactDay()) {
						DiscountLifeCycleRuleDate ruleP = new DiscountLifeCycleRuleDate();
						ruleP.setFromDate(rule.getFrom());
						ruleP.setToDate(rule.getFrom());
						ruleP.setExact(true);
						discount.put(DiscountLifeCycleRuleType.Date, ruleP);
					} else {
						DiscountLifeCycleRuleDate ruleP = new DiscountLifeCycleRuleDate();
						ruleP.setFromDate(rule.getFrom());
						discount.put(DiscountLifeCycleRuleType.Date, ruleP);
					}

				} else if (rule.isPeriodLimited()) {
					DiscountLifeCycleRulePeriod ruleP = new DiscountLifeCycleRulePeriod();
					ruleP.setDuration(rule.getPeriodNumber());
					ruleP.setPeriod(rule.getPeriod());
					discount.put(DiscountLifeCycleRuleType.Duration, ruleP);
				} else if (rule.isCountLimited()) {
					DiscountLifeCycleRuleCount ruleP = new DiscountLifeCycleRuleCount();
					ruleP.setMax(rule.getCountMax());
					discount.put(DiscountLifeCycleRuleType.AbsoluteCount, ruleP);
				}
			}
		}
		// Tracking Lifecycle
		discount.clearTrackingRules();
		for (DiscountTrackingLifeCycleRuleType type : bean.getTrackingLifeRules().keySet()) {
			DiscountLifeCycleTrackingRuleBean rule = bean.getTrackingLifeRules().get(type);
			if (rule.isEnable()) {
				if (rule.isLimitedPeriod()) {
					DiscountTrackingLifeCycleRulePeriod ruleP = new DiscountTrackingLifeCycleRulePeriod();
					ruleP.setDuration(rule.getPeriodNumber());
					ruleP.setPeriod(rule.getPeriod());
					discount.put(DiscountTrackingLifeCycleRuleType.Duration, ruleP);
				} else if (rule.isLimitedCount()) {
					DiscountTrackingLifeCycleRuleCount ruleP = new DiscountTrackingLifeCycleRuleCount();
					ruleP.setMax(rule.getCountMax());
					discount.put(DiscountTrackingLifeCycleRuleType.RelativeCount, ruleP);
				} else if (rule.isPeriodic()) {
					DiscountTrackingLifeCycleRulePeriodic ruleP = new DiscountTrackingLifeCycleRulePeriodic();
					ruleP.setDuration(rule.getEveryPeriodNumber());
					ruleP.setPeriod(rule.getEveryPeriod());
					discount.put(DiscountTrackingLifeCycleRuleType.RecurrentPeriod, ruleP);
				}
			}
		}
		// Communication
		for (CommunicationType type : bean.getCommunication().keySet()) {
			DiscountCommunicationBean commBean = bean.getCommunication().get(type);
			if (commBean.isShow()) {
				switch (type) {
				case Email:
				case Sms:
					DiscountCommunicationContent contents = new DiscountCommunicationContent();
					discount.getCommunication().putContents(type, contents);
					for (DiscountCommunicationContentBean c : commBean.getContents()) {
						contents.addContent(c.getLang(), c.getContent());
						contents.addContentText(c.getLang(), c.getContentText());
					}
					break;
				case Code:
					discount.getCommunication().setHasCode(true);
					break;
				case AutoComm:
					discount.getCommunication().setAutoCommunicate(true);
					break;
				default:
					break;
				}
			}
		}
		return discount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.discounts.converters.DiscountConverterI#convert(com.rm.model
	 * .discounts.DiscountDefinition, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public DiscountFormBean convert(DiscountDefinition discount, String lang, Collection<ModelOptions> options)
			throws NoDataFoundException {
		final DiscountFormBean beanD = new DiscountFormBean();
		beanD.setId(discount.getId());
		beanD.setName(discount.getName());
		beanD.setCreated(discount.getCreated());
		if (options.contains(DiscountOptions.Rules)) {

		}
		if (options.contains(DiscountOptions.Communication)) {
			// Communication
			for (CommunicationType t : CommunicationType.values()) {
				DiscountCommunicationBean commBean = new DiscountCommunicationBean();
				commBean.setShow(false);
				beanD.getCommunication().put(t, commBean);
			}
			for (CommunicationType type : discount.getCommunication().getContents().keySet()) {
				DiscountCommunicationContent content = discount.getCommunication().getContents().get(type);
				DiscountCommunicationBean commBean = beanD.getCommunication().get(type);
				commBean.setShow(true);
				//
				List<String> langs = content.getLangs();
				for (String currLang : langs) {
					DiscountCommunicationContentBean contentBean = new DiscountCommunicationContentBean();
					contentBean.setLang(currLang);
					contentBean.setContent(content.getContent(currLang));
					contentBean.setContentText(content.getContentText(currLang));
					commBean.getContents().add(contentBean);
				}
			}
			if (discount.getCommunication().isHasCode()) {
				DiscountCommunicationBean commBean = beanD.getCommunication().get(CommunicationType.Code);
				commBean.setShow(true);
			}
		}
		return beanD;
	}
}
