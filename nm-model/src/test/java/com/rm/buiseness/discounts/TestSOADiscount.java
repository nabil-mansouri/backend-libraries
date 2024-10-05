package com.rm.buiseness.discounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.commons.ContentFormDto;
import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.commons.beans.UploadViewDto;
import com.rm.contract.discounts.beans.DiscountCommunicationBean;
import com.rm.contract.discounts.beans.DiscountCommunicationContentBean;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.contract.discounts.beans.DiscountRuleBean;
import com.rm.contract.discounts.constants.CommunicationType;
import com.rm.contract.discounts.constants.DiscountOperationType;
import com.rm.contract.products.constants.ProductType;
import com.rm.contract.products.forms.ProductFormDto;
import com.rm.contract.products.forms.ProductPartFormDto;
import com.rm.dao.orders.DaoOrder;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.soa.prices.SoaPrice;
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestSOADiscount {

	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	private SoaPrice soaTarif;
	@Autowired
	private SoaDiscount soaDiscount;
	@Autowired
	private DaoOrder daoOrder;
	//
	private ProductFormDto special;
	private ProductFormDto free;
	private ProductFormDto gift;
	private ProductFormDto subprodSaved1;
	private ProductFormDto subprodSaved2;
	private ProductFormDto composed;

	// TODO relancer tout les tests ; attention au boolean dans les setter de
	// list; attention au JsonIgnore pour les setter inutile
	@Before
	public void globalSetUp() throws NoDataFoundException {
		assertNotNull("soaTarif soa can not be null.", soaTarif);
		daoOrder.deleteAll(daoOrder.findAll());
		// Product
		UploadViewDto img1View = new UploadViewDto("/test.png");
		ContentFormDto lang1Form = new ContentFormDto().setDescription("Ma description").addKeyword("a")
				.addKeyword("b").setLang("fr").setName("Nom");
		// Composed
		// SUB PRODUCTS
		{
			subprodSaved1 = new ProductFormDto().setType(ProductType.Product).add(lang1Form).setImg(img1View);
			subprodSaved2 = new ProductFormDto().setType(ProductType.Product).add(lang1Form).setImg(img1View);
			soaProductDefinition.saveOrUpdate(subprodSaved1, new OptionsList("fr"));
			soaProductDefinition.saveOrUpdate(subprodSaved2, new OptionsList("fr"));
		}
		//
		ProductPartFormDto part1 = new ProductPartFormDto().setName("PART1").add(subprodSaved1).add(subprodSaved2);
		ProductPartFormDto part2 = new ProductPartFormDto().setName("PART2").add(subprodSaved1).add(subprodSaved2);
		// Special
		{
			this.special = new ProductFormDto().setImg(img1View).add(part1).add(part2).add(lang1Form)
					.setType(ProductType.Promo);
			soaProductDefinition.saveOrUpdate(special, new OptionsList("fr"));
		}
		// Free
		{
			this.free = new ProductFormDto().setImg(img1View).add(part1).add(part2).add(lang1Form)
					.setType(ProductType.Product);
			soaProductDefinition.saveOrUpdate(free, new OptionsList("fr"));
		}
		// Gift
		{
			this.gift = new ProductFormDto().setImg(img1View).add(part1).add(part2).add(lang1Form)
					.setType(ProductType.Gift);
			soaProductDefinition.saveOrUpdate(gift, new OptionsList("fr"));
		}
		// Composed
		// SUB PRODUCTS
		{

			this.composed = new ProductFormDto().setImg(img1View).add(part1).add(part2).add(lang1Form)
					.setType(ProductType.Promo);
			;
			soaProductDefinition.saveOrUpdate(composed, new OptionsList("fr"));
		}
	}

	@Test
	@Transactional
	public void testCRUDDiscountSpecial() throws NoDataFoundException {
		// Prepare Price with additionnal
		// TODO
		// ProductDefBean node =
		// this.soaProductDefinition.fetch(this.special.getId(),new
		// OptionsList("fr"));
		// ProductNodeBean child = (ProductNodeBean)
		// node.getChildren().get(1).getChildren().get(1);
		// child.getPriceDetail().setSelected(true);
		// child.getPriceDetail().getPrices().put(OrderType.Default, new
		// Double(1d));
		// PriceFormBean bean = new
		// PriceFormBean().setProduct(node).addPrice(OrderType.Default, 10d)
		// .addPrice(OrderType.Delivered, 15d).setHasLimitDate(false);
		// System.out.println("Saving price with additionnal");
		// soaTarif.saveOrUpdate(bean,new OptionsList("fr"));
		// PriceFilterBean priFilter = new PriceFilterBean();
		// priFilter.getOptions().addAll(Arrays.asList(PriceOptions.values()));
		// priFilter.getOptions().add(ProductOptions.Parts);
		// PriceFormBean priceResponse = soaTarif.fetch(priFilter,
		// "fr").iterator().next();
		// assertNotNull(priceResponse.getId());
		// // Preparing discount
		// DiscountFormBean discountBean = new
		// DiscountFormBean().setName("DISC")
		// .addRule(new
		// DiscountRuleBean().setSpecial(true).addSpecialPrices(priceResponse));
		// DiscountFormBean response = soaDiscount.save(discountBean,new
		// OptionsList("fr"));
		// assertNotNull(response.getId());
		// // Edit
		// DiscountFormBean edit = soaDiscount.edit(response.getId(),new
		// OptionsList("fr"));
		// assertEquals(1, edit.getRules().size());
		// assertEquals(1, edit.getRules().get(0).getSpecialPrices().size());
		// assertTrue(edit.getRules().get(0).isSpecial());
		// soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testCRUDDiscountFree() throws NoDataFoundException {
		// Preparing discount
		// TODO
		// DiscountFormBean discountBean = new
		// DiscountFormBean().setName("DISC").addRule(new
		// DiscountRuleBean().setFree(true).addFreeProducts(free));
		// DiscountFormBean response = soaDiscount.save(discountBean,new
		// OptionsList("fr"));
		// assertNotNull(response.getId());
		// // Edit
		// DiscountFormBean edit = soaDiscount.edit(response.getId(),new
		// OptionsList("fr"));
		// assertEquals(1, edit.getRules().size());
		// assertEquals(1, edit.getRules().get(0).getFreeProducts().size());
		// assertTrue(edit.getRules().get(0).isFree());
		// soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testCRUDDiscountGift() throws NoDataFoundException {
		// TODO
		// Preparing discount
		// DiscountFormBean discountBean = new
		// DiscountFormBean().setName("DISC")
		// .addRule(new DiscountRuleBean().setGift(true).addGiftProducts(gift));
		// DiscountFormBean response = soaDiscount.save(discountBean,new
		// OptionsList("fr"));
		// assertNotNull(response.getId());
		// // Edit
		// DiscountFormBean edit = soaDiscount.edit(response.getId(),new
		// OptionsList("fr"));
		// assertEquals(1, edit.getRules().size());
		// assertEquals(1, edit.getRules().get(0).getGiftProducts().size());
		// assertTrue(edit.getRules().get(0).isGift());
		// soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testCRUDDiscountDecreaseGlobal() throws NoDataFoundException {
		// Preparing discount
		DiscountFormBean discountBean = new DiscountFormBean().setName("DISC").addRule(new DiscountRuleBean()
				.setOperation(true).setOperationType(DiscountOperationType.Fixe).setOperationValue(10d));
		DiscountFormBean response = soaDiscount.save(discountBean, "fr");
		assertNotNull(response.getId());
		// Edit
		DiscountFormBean edit = soaDiscount.edit(response.getId(), "fr");
		assertEquals(1, edit.getRules().size());
		// Update
		edit.setRules(new ArrayList<DiscountRuleBean>(Arrays.asList(edit.getRules().iterator().next())));
		soaDiscount.save(edit, "fr");
		edit = soaDiscount.edit(response.getId(), "fr");
		assertEquals(1, edit.getRules().size());
		assertNotNull(edit.getRules().get(0).getOperationType());
		assertNotNull(edit.getRules().get(0).getOperationValue());
		assertTrue(edit.getRules().get(0).isOperation());
		soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testCRUDDiscountDecrease() throws NoDataFoundException {
		// TODO
		// DiscountFormBean discountBean = new
		// DiscountFormBean().setName("DISC");
		// {
		// ProductDefBean bean =
		// soaProductDefinition.fetch(subprodSaved1.getId(), "fr");
		// PriceFormBean price = new PriceFormBean().addPrice(OrderType.Default,
		// 10d).setProduct(bean)
		// .setHasLimitDate(false);
		// discountBean.addRule(new
		// DiscountRuleBean().setReplacePrice(true).addReplacePrices(price));
		// }
		// {
		// ProductDefBean bean =
		// soaProductDefinition.fetch(subprodSaved2.getId(), "fr");
		// PriceFormBean price = new PriceFormBean().addPrice(OrderType.Default,
		// 10d).setProduct(bean)
		// .setHasLimitDate(false);
		// discountBean.addRule(new
		// DiscountRuleBean().setReplacePrice(true).addReplacePrices(price));
		// }
		// DiscountFormBean response = soaDiscount.save(discountBean, "fr");
		// assertNotNull(response.getId());
		// // Edit
		// DiscountFormBean edit = soaDiscount.edit(response.getId(), "fr");
		// assertEquals(1, edit.getRules().size());
		// assertEquals(2, edit.getRules().get(0).getReplacePrices().size());
		// assertTrue(edit.getRules().get(0).isReplacePrice());
		// // Update
		// PriceFormBean price =
		// edit.getRules().get(0).getReplacePrices().iterator().next();
		// edit.getRules().get(0).getReplacePrices().clear();
		// edit.getRules().get(0).getReplacePrices().add(price);
		// soaDiscount.save(edit, "fr");
		// edit = soaDiscount.edit(response.getId(), "fr");
		// assertEquals(1, edit.getRules().get(0).getReplacePrices().size());
		// soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testSaveCommunication() throws NoDataFoundException {
		// Preparing discount
		DiscountCommunicationBean email = new DiscountCommunicationBean().setShow(true)
				.addContents(new DiscountCommunicationContentBean().setContent("mon email").setLang("fr")
						.setContentText("mon email"));
		DiscountCommunicationBean sms = new DiscountCommunicationBean().setShow(true).addContents(
				new DiscountCommunicationContentBean().setContent("mon sms").setLang("fr").setContentText("mon sms"));
		DiscountFormBean discountBean = new DiscountFormBean().addCOmmunication(CommunicationType.Email, email)
				.addCOmmunication(CommunicationType.Sms, sms).setName("DISC").addRule(new DiscountRuleBean()
						.setOperation(true).setOperationType(DiscountOperationType.Fixe).setOperationValue(10d));
		discountBean = soaDiscount.save(discountBean, "fr");
		assertNotNull(discountBean.getId());
		// Edit
		DiscountFormBean edit = soaDiscount.edit(discountBean.getId(), "fr");
		for (CommunicationType type : edit.getCommunication().keySet()) {
			switch (type) {
			case Code:
			case AutoComm:
				assertFalse(edit.getCommunication().get(type).isShow());
				break;
			case Email:
			case Sms:
				assertTrue(edit.getCommunication().get(type).isShow());
			}
		}
		assertEquals(CommunicationType.values().length, edit.getCommunication().size());
		// Update
		Map<CommunicationType, DiscountCommunicationBean> pro = new HashMap<CommunicationType, DiscountCommunicationBean>();
		pro.put(CommunicationType.Email, edit.getCommunication().get(CommunicationType.Email));
		edit.setCommunication(pro);
		soaDiscount.save(edit, "fr");
		edit = soaDiscount.edit(discountBean.getId(), "fr");
		for (CommunicationType type : edit.getCommunication().keySet()) {
			switch (type) {
			case Code:
			case Sms:
			case AutoComm:
				assertFalse(edit.getCommunication().get(type).isShow());
				break;
			case Email:
				assertTrue(edit.getCommunication().get(type).isShow());
			}
		}
		assertEquals(CommunicationType.values().length, edit.getCommunication().size());
		soaDiscount.delete(edit.getId());
	}

	@Test
	@Transactional
	public void testSaveTrigger() throws NoDataFoundException {
		// Preparing discount
		// DiscountTriggerBean birthday =
		// DiscountTriggerBeanBuilder.get().withShow(true);
		// DiscountTriggerBean nbCOmmande =
		// DiscountTriggerBeanBuilder.get().withShow(true).withNumber(TriggerDataType.Count,
		// 2d);
		// DiscountFormBean discountBean =
		// DiscountFormBeanBuilder.get().setName("DISC").withDiscountType(DiscountType.Gift).withGift(this.gift)
		// .withTrigger(TriggerType.Birthday,
		// birthday).withTrigger(TriggerType.NbCommande, nbCOmmande);
		// DiscountFormBean response = soaDiscount.save(discountBean, "fr");
		// assertNotNull(response.getId());
		// // Edit
		// DiscountFormBean edit = soaDiscount.edit(response.getId(), "fr");
		// assertNull(edit.getPrice());
		// assertNull(edit.getFree());
		// assertNotNull(edit.getGift());
		// assertTrue(edit.getPrices().isEmpty());
		// for (TriggerType type : edit.getTrigger().keySet()) {
		// switch (type) {
		// case Birthday:
		// case NbCommande:
		// assertTrue(edit.getTrigger().get(type).getShow());
		// break;
		// default:
		// assertFalse(edit.getTrigger().get(type).getShow());
		// }
		// }
		// assertEquals(TriggerType.values().length, edit.getTrigger().size());
		// // Update
		// Map<TriggerType, DiscountTriggerBean> pro = new HashMap<TriggerType,
		// DiscountTriggerBean>();
		// pro.put(TriggerType.NbCommande,
		// edit.getTrigger().get(TriggerType.NbCommande));
		// edit.setTrigger(pro);
		// soaDiscount.save(edit, "fr");
		// edit = soaDiscount.edit(response.getId(), "fr");
		// for (TriggerType type : edit.getTrigger().keySet()) {
		// switch (type) {
		// case NbCommande:
		// assertTrue(edit.getTrigger().get(type).getShow());
		// break;
		// default:
		// assertFalse(edit.getTrigger().get(type).getShow());
		// }
		// }
		// assertEquals(TriggerType.values().length, edit.getTrigger().size());
		// soaDiscount.delete(edit.getId());
	}

}
