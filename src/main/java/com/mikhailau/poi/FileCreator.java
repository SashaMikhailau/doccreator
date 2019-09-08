package com.mikhailau.poi;

import com.mikhailau.constants.PropertiesConstants;
import com.mikhailau.constants.UiFieldsConstants;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class FileCreator {
	private static final String DOCX_EXTENSION = ".docx";

	private WordReplacer wordReplacer = new WordReplacer();
	private XWPFDocument document;
	protected Map<String, String> properties;

	public FileCreator(Map<String, String> properties) {
		this.properties = properties;
	}

	public void createFile() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(definePathToTemplate());
		document = new XWPFDocument(fileInputStream);
		document = wordReplacer.replaceWords(document, properties);
		document.write(new FileOutputStream(definePathToFile()));
	}

	public  String definePathToFile(){
		String pathToFilders = properties.get(PropertiesConstants.PATH_TO_EXPERTISES_FOLDER);
		String expertiseNumber= properties.get(UiFieldsConstants.EXPERTISE_NUMBER);
		return String.format("%s%s%s%s", pathToFilders, getFileName(), expertiseNumber,
				DOCX_EXTENSION);
	}

	public  String definePathToTemplate(){
		String pathToTemplates = properties.get(PropertiesConstants.PATH_TO_TEMPLATES_FOLDER);
		return String.format("%s%s%s", pathToTemplates, getTemplateName(),
				DOCX_EXTENSION);
	}


	public abstract String getFileName();
	public abstract String getTemplateName();


}
