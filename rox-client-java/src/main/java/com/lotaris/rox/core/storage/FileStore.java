package com.lotaris.rox.core.storage;

import com.lotaris.rox.common.config.Configuration;
import com.lotaris.rox.common.model.RoxPayload;
import com.lotaris.rox.common.utils.Constants;
import com.lotaris.rox.core.serializer.RoxSerializer;
import com.lotaris.rox.core.serializer.json.JsonSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File store to keep the result between runs
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class FileStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStore.class);
	
	private Configuration configuration;
	
	private RoxSerializer serializer;
	
	/**
	 * Constructor
	 * 
	 * @param configuration The configuration
	 */
	public FileStore(Configuration configuration) {
		this.configuration = configuration;
		
		if (configuration.getSerializer() == null) {
			LOGGER.info("Default serializer {} will be used.", JsonSerializer.class.getName());
			serializer = new JsonSerializer();
		}
		else {
			try {
				serializer = (RoxSerializer) getClass().getClassLoader().loadClass(configuration.getSerializer()).newInstance();
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				LOGGER.warn("Unable to create the serializer {}, default one will be used.", configuration.getSerializer(), e);
			}
			finally {
				serializer = new JsonSerializer();
			}
		}
	}
	
	/**
	 * Save a payload
	 * 
	 * @param roxPayload The payload to save
	 * @throws IOException I/O Errors
	 */
	public void save(RoxPayload roxPayload) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(
			new FileOutputStream(new File(getTmpDir(roxPayload), UUID.randomUUID().toString())), 
			Charset.forName(Constants.ENCODING).newEncoder()
		);
		
		serializer.serializePayload(osw, roxPayload, true);
	}
	
	/**
	 * Load a payload
	 * 
	 * @param <T> The payload type
	 * @param name The name of the payload file to load
	 * @param clazz The class of the payload
	 * @return The payload loaded
	 * @throws IOException I/O Errors
	 */
	public <T extends RoxPayload> T load(String name, Class<T> clazz) throws IOException {
		InputStreamReader isr = new InputStreamReader(
			new FileInputStream(new File(getTmpDir(clazz), name)),
			Charset.forName(Constants.ENCODING).newDecoder()
		);
		
		return serializer.deserializePayload(isr, clazz);
	}
	
	/**
	 * Load a list of payload
	 * 
	 * @param <T> The payload type
	 * @param clazz The class of the payload
	 * @return The list of payloads loaded
	 * @throws IOException 
	 */
	public <T extends RoxPayload> List<T> load(Class<T> clazz) throws IOException {
		List<T> payloads = new ArrayList<>();
		
		for (File f : getTmpDir(clazz).listFiles()) {
			if (f.isFile()) {
				InputStreamReader isr = new InputStreamReader(
					new FileInputStream(f),
					Charset.forName(Constants.ENCODING).newDecoder()
				);

				payloads.add(serializer.deserializePayload(isr, clazz));
			}
		}
		
		return payloads;
	}
	
	/**
	 * Clear the directory where payloads are stored
	 * 
	 * @param clazz The class of the payload
	 * @throws IOException I/O Errors
	 */
	public void clear(Class<? extends RoxPayload> clazz) throws IOException {
		FileUtils.cleanDirectory(getTmpDir(clazz));
	}
	
	/**
	 * Retrieve the temporary directory where to store/load payloads
	 * 
	 * @param payload The payload to retrieve the temp directory
	 * @return The temp directory, new one if the directory does not exist
	 */
	private File getTmpDir(RoxPayload payload) {
		File tmpDir = new File(configuration.getWorkspace() + "/tmp/" + payload.getVersion());
		
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		
		return tmpDir;
	}
	
	/**
	 * Retrieve the temporary directory where to store/load payloads
	 * 
	 * @param clazz The class of the payload to load
	 * @return The temp directory, null if not able to create a new instance of the payload class
	 */
	private File getTmpDir(Class<? extends RoxPayload> clazz) {
		try {
			return getTmpDir(clazz.newInstance());
		}
		catch (IllegalAccessException | InstantiationException iae) {
			return null;
		}
	}
}
