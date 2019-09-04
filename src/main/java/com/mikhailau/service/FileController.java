package com.mikhailau.service;

import com.mikhailau.poi.DeviceCostBillCreator;
import com.mikhailau.poi.ExpertiseCostBillCreator;

import java.io.IOException;
import java.util.Map;

public class FileController {
	private PropertiesHandler propertiesHandler = new PropertiesHandler();

	public void createFiles(){
		try {
			Map<String, String> properties = propertiesHandler.readValuesFromSettings();
//			new DeviceCostBillCreator(properties).createFile();
			new ExpertiseCostBillCreator(properties).createFile();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
