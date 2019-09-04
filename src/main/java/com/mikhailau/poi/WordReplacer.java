package com.mikhailau.poi;

import org.apache.poi.xwpf.usermodel.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordReplacer {

	public static final String REGEX = "%(\\w+)%";
	public final Pattern pattern = Pattern.compile(REGEX);


	public XWPFDocument replaceWords(XWPFDocument document, Map<String, String> valuesToReplace) {
		replaceTextInParagraphs(document.getParagraphs(), valuesToReplace);
		for (XWPFTable tbl : document.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					replaceTextInParagraphs(cell.getParagraphs(), valuesToReplace);
				}
			}
		}
		return document;
	}

	private void replaceTextInParagraphs(List<XWPFParagraph> paragraphs,
										 Map<String, String> valuesToReplace) {
		for (XWPFParagraph p : paragraphs) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = replaceValuesInRun(valuesToReplace, r.getText(0));
					r.setText(text, 0);
				}
			}
		}
	}

	public String replaceValuesInRun(Map<String, String> valuesToReplace, String text) {
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			StringBuffer sb = new StringBuffer();
			do {
				String value = getValueByKey(matcher.group(1), valuesToReplace);
				matcher.appendReplacement(sb, value);
			} while (matcher.find());
			matcher.appendTail(sb);
			return sb.toString();
		}
		return text;
	}

	private String getValueByKey(String key, Map<String, String> valuesToReplace) {
		return Optional.ofNullable(valuesToReplace.get(key))
				.orElse("%KEY_NOT_FOUND%");
	}
}



