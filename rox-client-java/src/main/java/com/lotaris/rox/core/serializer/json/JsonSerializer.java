package com.lotaris.rox.core.serializer.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lotaris.rox.common.model.RoxPayload;
import com.lotaris.rox.core.serializer.RoxSerializer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Json Serializer implementation of {@link RoxSerializer}
 *
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class JsonSerializer implements RoxSerializer {

	@Override
	public void serializePayload(OutputStreamWriter osw, RoxPayload roxPayload, boolean pretty) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		if (pretty) {
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		}

		mapper.writeValue(osw, roxPayload);
	}

	@Override
	public <T extends RoxPayload> T deserializePayload(InputStreamReader isr, Class<T> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(isr, clazz);
	}
}
