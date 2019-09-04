package com.mikhailau.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesHandlerTest {

	@Test
	void readValuesFromSettings() throws IOException {
		PropertiesHandler propertiesHandler = new PropertiesHandler();
		Map<String, String> map = propertiesHandler.readValuesFromSettings();
		assertFalse(map.isEmpty());
	}
}