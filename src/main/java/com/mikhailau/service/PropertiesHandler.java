package com.mikhailau.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesHandler {
	private static final String SETTINGS_PATH = "src/main/resources/settings.properties";

	public Map<String,String> readValuesFromSettings() throws IOException {
		Properties properties = new Properties();
		properties.load(new InputStreamReader(new FileInputStream(SETTINGS_PATH),
				Charset.forName("UTF-8")));
		Map<String, String> map = properties.entrySet().stream().collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()),
				entry -> String.valueOf(entry.getValue())));
		return map;
	}

}
