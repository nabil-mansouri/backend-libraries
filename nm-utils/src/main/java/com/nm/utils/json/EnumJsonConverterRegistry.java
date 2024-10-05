package com.nm.utils.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;
import net.sf.corn.cps.PackageNameFilter;

/**
 * 
 * @author nabilmansouri
 *
 */
public class EnumJsonConverterRegistry {
	private String base;
	private Map<String, Enum<?>> registry = new HashMap<String, Enum<?>>();
	private static EnumJsonConverterRegistry instance;

	public static EnumJsonConverterRegistry getOrCreateInstance() {
		if (instance == null) {
			instance = new EnumJsonConverterRegistry();
		}
		return instance;
	}

	public static EnumJsonConverterRegistry getInstance() {
		return instance;
	}

	protected EnumJsonConverterRegistry() {
		instance = this;
	}

	public Set<Enum<?>> findByInterface(Class<?> ee) {
		Assert.notNull(ee);
		Set<Enum<?>> founded = Sets.newHashSet();
		for (String key : registry.keySet()) {
			if (ee.isInstance(registry.get(key))) {
				founded.add(registry.get(key));
			}
		}
		return founded;
	}

	public Set<String> findKeysByInterface(Class<?> ee) {
		Assert.notNull(ee);
		Set<String> founded = Sets.newHashSet();
		for (String key : registry.keySet()) {
			if (ee.isInstance(registry.get(key))) {
				founded.add(key);
			}
		}
		return founded;
	}

	public static String key(Object ee) {
		Assert.notNull(ee);
		return key(ee.getClass(), (Enum<?>) ee);
	}

	public static String key(Enum<?> ee) {
		Assert.notNull(ee);
		return key(ee.getClass(), ee);
	}

	public static String key(Class<?> clazz, Enum<?> ee) {
		return String.format("%s.%s", clazz.getSimpleName(), ee.name());
	}

	private void load() {
		List<Class<?>> classes = CPScanner.scanClasses(new PackageNameFilter(base),
				new ClassFilter().enumOnly().appendAnnotation(EnumJsonRegistryAnnotation.class));
		for (Class<?> clazz : classes) {
			put(clazz);
		}
	}

	public void put(Class<?> clazz) {
		Object[] enums = clazz.getEnumConstants();
		if (enums != null) {
			for (Object e : enums) {
				Enum<?> ee = (Enum<?>) e;
				String key = key(clazz, ee);
				if (this.registry.containsKey(key)) {
					Enum<?> exi = this.registry.get(key);
					if (!exi.equals(ee)) {
						throw new IllegalArgumentException("Key already exists for enum :" + key);
					}
				}
				this.registry.put(key, ee);
			}
		}
	}

	public Enum<?> find(String text) {
		return registry.get(text);
	}

	public void setBase(String base) {
		this.base = base;
		if (!Strings.isNullOrEmpty(base)) {
			load();
		}
	}

	@Override
	public String toString() {
		return "JsonEnumConverterRegistry [base=" + base + ", value=" + registry + "]";
	}

}
