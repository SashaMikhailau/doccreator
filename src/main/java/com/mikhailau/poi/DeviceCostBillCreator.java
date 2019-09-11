package com.mikhailau.poi;

import com.mikhailau.model.InventoryItem;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.List;
import java.util.Map;

public class DeviceCostBillCreator extends FileCreator {
	public static final String FILE_NAME = "raschet_";
	public static final String TEMPLATE_NAME = "raschet";
	private final List<InventoryItem> inventoryItems;

	public DeviceCostBillCreator(Map<String, String> properties, List<InventoryItem> inventoryItems) {
		super(properties);
		this.inventoryItems = inventoryItems;
	}

	@Override
	public String getFileName() {
		return FILE_NAME;
	}

	@Override
	public String getTemplateName() {
		return TEMPLATE_NAME;
	}

	@Override
	public POIXMLDocument transformDocument() {
		document = wordReplacer.replaceWords((XWPFDocument) document, properties);
		document = wordReplacer.addInventoryItemsToFirstTable(((XWPFDocument) document), inventoryItems);
		return document;
	}
}
