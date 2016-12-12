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
package com.idealista.solrmeter.model.extractor;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

import com.idealista.solrmeter.model.InputDocumentExtractor;

import java.util.LinkedList;
import java.util.List;
/**
 * Extracts documents from text files. The file must be fieldName:fieldValue;fieldName2:fieldValue2;...
 * and all required fields must be added.
 * if a ";" character is part of some value (and it is not a field separator) it must be escaped with a "\" character.
 * All "\" on a value must be escaped as "\\"
 * @see com.idealista.solrmeter.extractor.FileInputDocumentExtractorTestCase.testEscapedChars()
 * @author tflobbe
 *
 */
public class FileInputDocumentExtractor extends InputDocumentExtractor {
	
	private static final Logger logger = Logger.getLogger(FileInputDocumentExtractor.class);

	public FileInputDocumentExtractor(String filePath) {
		super(filePath);
	}

	@Override
	protected SolrInputDocument createSolrDocument(String documentString) {
		SolrInputDocument document = new SolrInputDocument();
		List<String> fields = this.split(documentString);
		try {
			for(String field:fields) {
				try {
					int idx = field.indexOf(":");
					document.addField(field.substring(0, idx), field.substring(idx + 1));
				}catch(RuntimeException e) {
					logger.error("Error Loading documents, on field " + field);
					throw e;
				}
			}
		} catch(RuntimeException e) {
			logger.error("Error Loading documents, on document line: " + documentString);
			throw e;
		}
		
		return document;
	}
	
	private List<String> split(String documentString) {
		List<String> strings = new LinkedList<String>();
		int lastSplitIndex = 0;
		int nextSplitIndex;
		while(lastSplitIndex < documentString.length()) {
			nextSplitIndex = findNextSplitIndex(documentString, lastSplitIndex);
			String splittedString = documentString.substring(lastSplitIndex, nextSplitIndex);
			strings.add(removeEscapeCharacters(splittedString));
			lastSplitIndex = nextSplitIndex + 1;
		}
		return strings;
	}
	
	private String removeEscapeCharacters(String splittedString) {
		return splittedString.replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\");
	}

	/**
	 * Returns the next Index to Split the String
	 * @param documentString
	 * @param lastSplitIndex
	 * @return
	 */
	private int findNextSplitIndex(String documentString, int lastSplitIndex) {
		for(int i = lastSplitIndex; i < documentString.length(); i++) {
			if(documentString.charAt(i) == '\\') {
				if(documentString.charAt(i + 1) == '\\' || documentString.charAt(i + 1) == ';') {
					i++;
				}
			}else {
				if(documentString.charAt(i) == ';') {
					return i;
				}
			}
		}
		return documentString.length();
	}
}
