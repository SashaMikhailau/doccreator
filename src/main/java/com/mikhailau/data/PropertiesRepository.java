package com.mikhailau.data;

import org.apache.commons.io.input.BOMInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesRepository{
	public Map<String,String> getAllFromPropertyFile(String settingsPath){
		Properties properties = new Properties();
		try {
			BOMInputStream bomInputStream = new BOMInputStream(new FileInputStream(settingsPath));
			properties.load(new InputStreamReader(bomInputStream,
					Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> map = properties.entrySet()
				.stream()
				.collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()),
						entry -> String.valueOf(entry.getValue())));
		return map;
	}

}
