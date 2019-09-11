package com.mikhailau.poi;

import com.mikhailau.constants.UiFieldsConstants;
import com.mikhailau.constants.UiFieldsConstants.InvestigationType;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.Map;
import java.util.Optional;

public class ExpertiseCostBillCreator extends FileCreator {

	@Override
	public POIXMLDocument transformDocument() {
		return wordReplacer.replaceWords((XWPFDocument) document, properties);
	}

	public static final String FILE_NAME = "spravka_";
	public static final String TEMPLATE_NAME_MATERIAL = "material";
	public static final String TEMPLATE_NAME_UD = "ud";

	@Override
	public String getTemplateName() {
		InvestigationType investigationType =
				InvestigationType.getByName(properties.get(UiFieldsConstants.MATERIAL_TYPE));
		return investigationType == InvestigationType.MAT_ADMINISTRATIVE?
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
