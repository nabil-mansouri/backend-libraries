package com.nm.orders.dtos.converters;

import java.util.Map;

import com.google.common.collect.Maps;
import com.nm.orders.dtos.impl.OrderDetailsDtoTree;
import com.nm.orders.models.OrderDetailsTree;
import com.nm.orders.models.OrderDetailsTreeNode;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class OrderDetailsTreeConverterImpl extends DtoConverterDefault<OrderDetailsDtoTree, OrderDetailsTree> {

	public OrderDetailsDtoTree toDto(OrderDetailsDtoTree dto, OrderDetailsTree entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		//
		// BUILD TREE
		dto.setAmount(entity.getAmount());
		final Map<String, OrderDetailsDtoTree> track = Maps.newHashMap();
		for (final OrderDetailsTreeNode childModel : entity.getNodes()) {
			GraphIteratorBuilder.buildDeep().iterate(childModel, new IteratorListenerGraphInfo() {

				public void onFounded(GraphInfo node) {
					OrderDetailsTreeNode cModel = (OrderDetailsTreeNode) node.getCurrent();
					OrderDetailsDtoTree cDto = new OrderDetailsDtoTree();
					cDto.setAmount(cModel.getAmount());
					cDto.setDetails(cModel.getName());
					cDto.setReference(cModel.getReference());
					cDto.setType(cModel.getType());
					track.put(cModel.uuid(), cDto);
					if (node.getParent() != null) {
						OrderDetailsTreeNode pModel = (OrderDetailsTreeNode) node.getParent().getCurrent();
						track.get(pModel.uuid()).getChildren().add(cDto);
					}
				}
			});
		}
		return dto;
	}

	public OrderDetailsDtoTree toDto(OrderDetailsTree entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderDetailsDtoTree(), entity, options);
	}

	public OrderDetailsTree toEntity(OrderDetailsDtoTree dto, OptionsList options) throws DtoConvertException {
		try {
			OrderDetailsTree entity = new OrderDetailsTree();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(OrderDetailsTree.class).get(dto.getId());
				// CLEAR
				for (OrderDetailsTreeNode n : entity.getNodes()) {
					GraphIteratorBuilder.buildDeep().iterate(n, new IteratorListenerGraphInfo() {

						public void onFounded(GraphInfo node) {
							if (node.getParent() != null) {
								OrderDetailsTreeNode parent = (OrderDetailsTreeNode) node.getParent().getCurrent();
								OrderDetailsTreeNode current = (OrderDetailsTreeNode) node.getCurrent();
								parent.removeChild(current);
							}
						}
					});
				}
				entity.getNodes().clear();
			}
			// BUILD TREE
			entity.setAmount(dto.getAmount());
			for (final OrderDetailsDtoTree childDto : dto.getChildren()) {
				GraphIteratorBuilder.buildDeep().iterate(childDto, new IteratorListenerGraphInfoInner(entity));
			}
			//
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	static class IteratorListenerGraphInfoInner implements IteratorListenerGraphInfo {
		final Map<String, OrderDetailsTreeNode> track = Maps.newHashMap();
		final OrderDetailsTree entity;

		public IteratorListenerGraphInfoInner(OrderDetailsTree entity) {
			super();
			this.entity = entity;
		}

		public void onFounded(GraphInfo node) {
			OrderDetailsDtoTree currentDto = (OrderDetailsDtoTree) node.getCurrent();
			OrderDetailsTreeNode currentModel = new OrderDetailsTreeNode();
			currentModel.setAmount(currentDto.getAmount());
			currentModel.setName(currentDto.getDetails());
			currentModel.setReference(currentDto.getReference());
			currentModel.setType(currentDto.getType());
			track.put(currentDto.uuid(), currentModel);
			if (node.getParent() == null) {
				entity.getNodes().add(currentModel);
			} else {
				OrderDetailsDtoTree parentDto = (OrderDetailsDtoTree) node.getParent().getCurrent();
				track.get(parentDto.uuid()).addChild(currentModel);
			}
		}
	}
}
