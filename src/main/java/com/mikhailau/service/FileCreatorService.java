package com.mikhailau.service;

import com.mikhailau.constants.UiFieldsConstants;
import com.mikhailau.poi.DeviceCostBillCreator;
import com.mikhailau.poi.ExpertiseCostBillCreator;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class FileCreatorService {


	public void createFiles(Map<String,String> properties){
		try {
			new ExpertiseCostBillCreator(properties).createFile();
			//new DeviceCostBillCreator(properties).createFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
