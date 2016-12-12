package com.idealista.solrmeter.extractor;

import java.io.FileNotFoundException;

import com.idealista.solrmeter.model.FileUtils;

public class JsonDocumentExtractorTestCase extends DocumentExtractorTestCase {

	public void testSingleDoc() throws FileNotFoundException {
		JsonDocumentExtractorSpy extractor = new JsonDocumentExtractorSpy(FileUtils.findFileAsString("JsonDocumentExtractorTestCase1.txt"));
		testSingleDoc(extractor);
	}

	public void testManyDocs() throws FileNotFoundException {
		JsonDocumentExtractorSpy extractor = new JsonDocumentExtractorSpy(FileUtils.findFileAsString("JsonDocumentExtractorTestCase2.txt"));
		testManyDocs(extractor);
	}
}
