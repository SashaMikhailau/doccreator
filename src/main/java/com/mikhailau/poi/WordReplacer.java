package com.mikhailau.poi;

import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordReplacer {

	public static final String REGEX = "\\[(\\w+)\\]";
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
					Optional.ofNullable(r.getText(0))
							.map(text -> replaceValuesInRun(valuesToReplace, text))
							.ifPresent(text -> r.setText(text, 0));
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

	public static void replaceString(XWPFDocument doc, String search, String replace) throws Exception {
		for (XWPFParagraph p : doc.getParagraphs()) {
			TextSegment abc = p.searchText("abc", new PositionInParagraph());
			List<XWPFRun> runs = p.getRuns();
			List<Integer> group = new ArrayList<Integer>();
			if (runs != null) {
				String groupText = search;
				for (int i = 0; i < runs.size(); i++) {
					XWPFRun r = runs.get(i);
					String text = r.getText(0);
					if (text != null)
						if (text.contains(search)) {
							String safeToUseInReplaceAllString = Pattern.quote(search);
							text = text.replaceAll(safeToUseInReplaceAllString, replace);
							r.setText(text, 0);
						} else if (groupText.startsWith(text)) {
							group.add(i);
							groupText = groupText.substring(text.length());
							if (groupText.isEmpty()) {
								runs.get(group.get(0)).setText(replace, 0);
								for (int j = 1; j < group.size(); j++) {
									p.removeRun(group.get(j));
								}
								group.clear();
								groupText = search;
							}
						} else {
							group.clear();
							groupText = search;
						}
				}
			}
		}
		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						for (XWPFRun r : p.getRuns()) {
							String text = r.getText(0);
							if (text.contains(search)) {
								String safeToUseInReplaceAllString = Pattern.quote(search);
								text = text.replaceAll(safeToUseInReplaceAllString, replace);
								r.setText(text);
							}
						}
					}
				}
			}
		}
	}
}




