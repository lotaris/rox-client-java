package com.lotaris.rox.core.serializer;

import com.lotaris.rox.common.model.RoxPayload;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Serializer interface
 *
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public interface RoxSerializer {

	/**
	 * Serialize a payload
	 *
	 * @param osw Output stream
	 * @param payload The payload to serialize
	 * @param pretty Whether to indent the output
	 * @exception IOException
	 */
	void serializePayload(OutputStreamWriter osw, RoxPayload payload, boolean pretty) throws IOException;

	/**
	 * Deserialize a payload
	 *
	 * @param <T> The type of payload to deserialize
	 * @param isr Input stream
	 * @param clazz The type to deserialize
	 * @return Payload The payload deserialized
	 * @throws IOException I/O Errors
	 */
	<T extends RoxPayload> T deserializePayload(InputStreamReader isr, Class<T> clazz) throws IOException;
}
