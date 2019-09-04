package com.mikhailau.poi;

import java.util.Map;

public class ExpertiseCostBillCreator extends FileCreator {

	public static final String FILE_NAME = "output";
	public static final String TEMPLATE_NAME = "test";

	@Override
	public String getTemplateName() {
		return TEMPLATE_NAME;
	}

	public ExpertiseCostBillCreator(Map<String, String> properties) {
		super(properties);
	}

	@Override
	public String getFileName() {
		return FILE_NAME;
	}
}
