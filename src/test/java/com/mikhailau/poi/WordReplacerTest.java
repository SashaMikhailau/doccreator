package com.mikhailau.poi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordReplacerTest {
	private WordReplacer wordReplacer = new WordReplacer();
	private Map<String, String> values;
	private String source = "Today is %DATE% and we are %FIO% \n going %DATE% to write %TYPE% " +
			"expertize";
	private String expected = "Today is " +LocalDate.now().toString() + " and we are Mikhailau Aliaksandr \n going " +LocalDate.now().toString() + " " +
			"to " +
			"write " +
			"SSZ " +
			"expertize";
	private String expectedWithoutType = "Today is " +LocalDate.now().toString() + " and we are " +
			"Mikhailau Aliaksandr \n going " +LocalDate.now().toString() + " " +
			"to " +
			"write " +
			"%KEY_NOT_FOUND% " +
			"expertize";

	@BeforeEach
	void setUp() {
		values = new HashMap<>();
		values.put("DATE", LocalDate.now().toString());
		values.put("FIO", "Mikhailau Aliaksandr");
		values.put("TYPE", "SSZ");
	}

	@Test
	void replaceWords() {
	}

	@Test
	void replaceValues() {
		String result = wordReplacer.replaceValuesInRun(values, source);
		assertEquals(expected,result);
	}

	@Test
	void dontReplaceInTextWithoutMasks() {
		String result = wordReplacer.replaceValuesInRun(values, expected);
		assertEquals(expected,result);
	}

	@Test
	void replaceValuesWithAbsentType() {
		values.remove("TYPE");
		String result = wordReplacer.replaceValuesInRun(values, source);
		assertEquals(expectedWithoutType,result);
	}
}