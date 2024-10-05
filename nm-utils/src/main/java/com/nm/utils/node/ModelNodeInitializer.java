package com.nm.utils.node;

import java.util.Collection;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.collect.Maps;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.hibernate.impl.TransactionWrapper;
import com.nm.utils.hibernate.impl.TransactionWrapper.TransactionHandler;

/**
 * 
 * @author Nabil
 * 
 */
public class ModelNodeInitializer implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		TransactionWrapper wrapper = ApplicationUtils.getBean(TransactionWrapper.class);
		wrapper.requireIfNotExists(new TransactionHandler<Void>() {

			public Void process() {
				Collection<SessionFactory> factories = ApplicationUtils.getBeansCollection(SessionFactory.class);
				for (final SessionFactory f : factories) {

					IGenericDao<ModelNodeType, Long> dao = AbstractGenericDao.get(ModelNodeType.class);
					Collection<ModelNodeType> types = dao.findAll();
					Map<String, ModelNodeType> typesSorted = Maps.newHashMap();
					for (ModelNodeType y : types) {
						typesSorted.put(y.getName(), y);
					}
					for (ClassMetadata m : f.getAllClassMetadata().values()) {
						if (!typesSorted.containsKey(m.getMappedClass().getName())) {
							ModelNodeType type = new ModelNodeType();
							type.setName(m.getMappedClass().getName());
							type.setSimple(m.getMappedClass().getSimpleName());
							dao.save(type);
						}
					}
				}
				return null;
			}
		});
		// TODO modifier Graph pour integrer uuid
		// TODO metter en cache une map des type
	}

}
