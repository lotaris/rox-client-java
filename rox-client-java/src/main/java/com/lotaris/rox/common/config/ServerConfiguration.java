package com.lotaris.rox.common.config;

import com.lotaris.rox.common.utils.FootprintGenerator;
import java.net.URLConnection;
import java.util.Map;

/**
 * ROX Center server configuration.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public class ServerConfiguration {

	private String name;
	private String apiUrl;
	private String apiKeyId;
	private String apiKeySecret;
	private String projectApiId;
	private ProxyConfiguration proxyConfiguration;
	
	public ServerConfiguration(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public void configureWith(Map<String, Object> data) {
		this.apiUrl = configureString(apiUrl, data, "apiUrl");
		this.apiKeyId = configureString(apiKeyId, data, "apiKeyId");
		this.apiKeySecret = configureString(apiKeySecret, data, "apiKeySecret");
		this.projectApiId = configureString(projectApiId, data, "projectApiId");

		if (data.containsKey("proxy")) {
			this.proxyConfiguration = new ProxyConfiguration();
			if (data.get("proxy") instanceof Map) {
				this.proxyConfiguration.configureWith((Map) data.get("proxy"));
			}
			else {
				throw new RoxConfigurationException("Unable to parse the proxy configuration for server: " + name);
			}
		}
	}

	public void configureAuthentication(URLConnection connection) {
		connection.setRequestProperty("Authorization", "RoxApiKey id=\"" + apiKeyId + "\" secret=\"" + apiKeySecret + "\"");
	}

	public String getBaseUrlFootprint() {
		return FootprintGenerator.footprint(apiUrl);
	}

	public boolean isValid() {
		return apiUrl != null && apiKeyId != null && apiKeySecret != null;
	}

	public String getName() {
		return name;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiKeyId() {
		return apiKeyId;
	}

	public String getApiKeySecret() {
		return apiKeySecret;
	}

	public String getProjectApiId() {
		return projectApiId;
	}

	public boolean hasProxyConfiguration() {
		return proxyConfiguration != null;
	}
	
	public ProxyConfiguration getProxyConfiguration() {
		return proxyConfiguration;
	}

	private String configureString(String previousValue, Map<String, Object> data, String key) {
		final Object value = data.get(key);
		return value != null ? value.toString() : previousValue;
	}

	@Override
	public String toString() {

		final StringBuilder builder = new StringBuilder();
		builder.append("name: \"").append(name).append("\"");
		builder.append(", apiUrl: \"").append(apiUrl).append("\"");
		builder.append(", apiKeyId: \"").append(apiKeyId).append("\"");
		if (apiKeySecret != null) {
			builder.append(", apiKeySecret: \"").append(apiKeySecret.replaceAll("[.]", "*")).append("\"");
		}
		builder.append(", projectApiId: \"").append(projectApiId).append("\"");
		
		if (proxyConfiguration != null) {
			builder.append(", proxy: \"").append(proxyConfiguration).append("\"");
		}

		return builder.toString();
	}
}
