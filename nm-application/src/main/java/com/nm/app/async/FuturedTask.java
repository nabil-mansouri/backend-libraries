package com.nm.app.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FuturedTask {
	private Map<String, List<FutureTaskContract<?>>> futures = Maps.newHashMap();

	public void push(FutureTaskContract<?> f) {
		List<FutureTaskContract<?>> futures = this.futures.getOrDefault(f.uuid(),
				new ArrayList<FutureTaskContract<?>>());
		futures.add(f);
		this.futures.put(f.uuid(), futures);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start() {
		for(String key : futures.keySet()){
			List<FutureTaskContract<?>> futures = this.futures.get(key);
			List<Object> all = Lists.newArrayList();
			for (FutureTaskContract<?> f :futures) {
				all.add(f.getModel());
			}
			for(int i=0;i<futures.size();i++){
				FutureTaskContract f= futures.get(i);
				if(f.each()){
					f.execute(all);
				}else if(i==0){
					f.execute(all);
				}
			}
		}
	}

}
