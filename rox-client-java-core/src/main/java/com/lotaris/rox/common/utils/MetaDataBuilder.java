package com.lotaris.rox.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class MetaDataBuilder {
	private Map<String, String> data = new HashMap<String, String>();
	
	public MetaDataBuilder add(String name, String value) {
		data.put(name, value);
		return this;
	}
	
	public MetaDataBuilder add(MetaDataBuilder metaDataBuilder) {
		data.putAll(metaDataBuilder.data);
		return this;
	}
	
	public Map<String, String> toMetaData() {
		return data;
	}
}
