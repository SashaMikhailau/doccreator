package com.mikhailau.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FilesManipulator {
	public static final String TEMPLATE_FILE_PATH = "C:\\Users\\Aliaksandr_Mikhaila1\\Documents\\test.docx";
	public static final String PATH_TO_SAVE = "C:\\Users\\Aliaksandr_Mikhaila1\\Documents" +
			"\\output2" +
			".docx";

	private WordReplacer wordReplacer = new WordReplacer();
	private XWPFDocument document;
	private Map<String, String> valuesToReplace = new HashMap<>();

	public void manipulate() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(TEMPLATE_FILE_PATH);
		document = new XWPFDocument(fileInputStream);
		document = wordReplacer.replaceWords(document,valuesToReplace);
		document.write(new FileOutputStream(PATH_TO_SAVE));
	}

}
