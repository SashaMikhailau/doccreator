package com.mikhailau.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileControllerTest {

	@Test
	void createFiles() {
		FileController fileController = new FileController();
		fileController.createFiles();
	}
}