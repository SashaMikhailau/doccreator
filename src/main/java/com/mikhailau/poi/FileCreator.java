package com.mikhailau.poi;

import com.mikhailau.constants.PropertiesConstants;
import com.mikhailau.constants.UiFieldsConstants;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class FileCreator {
	private static final String DOCX_EXTENSION = ".docx";

	protected WordReplacer wordReplacer = new WordReplacer();
	protected POIXMLDocument document;
	protected Map<String, String> properties;

	public FileCreator(Map<String, String> properties) {
		this.properties = properties;
	}

	public void createFile() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(definePathToTemplate());
		document = new XWPFDocument(fileInputStream);
		document = transformDocument();
		document.write(new FileOutputStream(definePathToFile()));
	}

	public  String definePathToFile(){
		String pathToFolders = properties.get(UiFieldsConstants.FOLDER_TO_SAVE);
		String expertiseNumber= properties.get(UiFieldsConstants.EXPERTISE_NUMBER);
		return String.format("%s/%s%s%s", pathToFolders, getFileName(), expertiseNumber,
				DOCX_EXTENSION);
	}

	public  String definePathToTemplate(){
		String pathToTemplates = properties.get(PropertiesConstants.PATH_TO_TEMPLATES_FOLDER);
		return String.format("%s%s%s", pathToTemplates, getTemplateName(),
				DOCX_EXTENSION);
	}


	public abstract String getFileName();
	public abstract String getTemplateName();
	public abstract POIXMLDocument  transformDocument();



}
