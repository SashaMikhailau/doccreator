package com.mikhailau.poi;

import com.mikhailau.constants.UiFieldsConstants;

import java.util.Map;
import java.util.Optional;

public class ExpertiseCostBillCreator extends FileCreator {

	public static final String FILE_NAME = "spravka";
	public static final String TEMPLATE_NAME_MATERIAL = "material";
	public static final String TEMPLATE_NAME_UD = "ud";

	@Override
	public String getTemplateName() {
		UiFieldsConstants.InvestigationType investigationType = Optional.ofNullable(properties.get(UiFieldsConstants.MATERIAL_TYPE))
				.map(UiFieldsConstants.InvestigationType::valueOf)
				.orElse(UiFieldsConstants.InvestigationType.MAT_ADMINISTRATIVE);
		return investigationType == UiFieldsConstants.InvestigationType.MAT_ADMINISTRATIVE?
			TEMPLATE_NAME_MATERIAL:
				TEMPLATE_NAME_UD;
	}

	public ExpertiseCostBillCreator(Map<String, String> properties) {
		super(properties);
	}

	@Override
	public String getFileName() {
		return FILE_NAME;
	}
}
