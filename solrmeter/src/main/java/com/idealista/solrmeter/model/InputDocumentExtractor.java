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
package com.idealista.solrmeter.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.common.SolrInputDocument;

/**
 * Manages the documents to be added/updated to Solr.
 * @author tflobbe
 *
 */
public abstract class InputDocumentExtractor {

	protected List<SolrInputDocument> documents;

	public InputDocumentExtractor(String filePath) {
		List<String> stringDocuments = readStringDocuments(filePath);
		documents = this.createDocumentList(stringDocuments);
	}

	public SolrInputDocument getRandomDocument() {
		return (SolrInputDocument) FileUtils.getNextRandomObject(documents);
	}

	List<String> readStringDocuments(String inputFilePath) {
		return FileUtils.loadStringsFromFile(inputFilePath);
	}

	List<SolrInputDocument> createDocumentList(List<String> stringDocuments){
		return stringDocuments.stream()
				.map(this::createSolrDocument)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	protected abstract SolrInputDocument createSolrDocument(String jsonString);
}
