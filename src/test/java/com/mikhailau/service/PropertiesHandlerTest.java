package com.mikhailau.service;

import com.mikhailau.data.PropertiesRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesHandlerTest {

	@Test
	void readValuesFromSettings() throws IOException {
		PropertiesRepository propertiesHandler = new PropertiesRepository();
		Map<String, String> map = propertiesHandler.getAllFromPropertyFile(DocumentService.SETTINGS_PATH);
		assertFalse(map.isEmpty());
	}
}