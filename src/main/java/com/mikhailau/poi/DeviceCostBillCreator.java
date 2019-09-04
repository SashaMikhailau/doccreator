package com.mikhailau.poi;

import java.util.Map;

public class DeviceCostBillCreator extends FileCreator {
	public static final String FILE_NAME = "рассчет_";
	public static final String TEMPLATE_NAME = "рассчет_шаблон";


	public DeviceCostBillCreator(Map<String, String> properties) {
		super(properties);
	}

	@Override
	public String getFileName() {
		return FILE_NAME;
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE_NAME;
	}
}
