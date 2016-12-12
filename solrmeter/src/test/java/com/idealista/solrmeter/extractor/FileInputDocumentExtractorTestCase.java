/**
 * Copyright Plugtree LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealista.solrmeter.extractor;

import com.idealista.solrmeter.BaseTestCase;
import com.idealista.solrmeter.model.FileUtils;

import org.apache.solr.common.SolrInputDocument;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileInputDocumentExtractorTestCase extends DocumentExtractorTestCase {

	public void testSingleDoc() throws FileNotFoundException {
		FileInputDocumentExtractorSpy extractor = new FileInputDocumentExtractorSpy(FileUtils.findFileAsString("FileInputDocumentExtractorTestCase1.txt"));
		assertEquals(1, extractor.getParsedDocuments().size());
		testSingleDoc(extractor);
	}

	public void testManyDocs() throws FileNotFoundException {
		FileInputDocumentExtractorSpy extractor = new FileInputDocumentExtractorSpy(FileUtils.findFileAsString("FileInputDocumentExtractorTestCase2.txt"));
		assertEquals(21, extractor.getParsedDocuments().size());
		testManyDocs(extractor);
	}

	public void testLoadDocuments() throws FileNotFoundException {
		FileInputDocumentExtractorSpy executor = new FileInputDocumentExtractorSpy(FileUtils.findFileAsString("FileInputDocumentExtractorTestCase3.txt"));
		testLoadDocuments(executor);
	}
	
	public void testEscapedChars() throws FileNotFoundException {
		FileInputDocumentExtractorSpy executor = new FileInputDocumentExtractorSpy(FileUtils.findFileAsString("FileInputDocumentExtractorTestCase4.txt"));
		testEscapedChars(executor);
	}
}
