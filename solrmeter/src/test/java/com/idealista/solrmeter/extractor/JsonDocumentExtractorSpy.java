package com.idealista.solrmeter.extractor;

import org.apache.solr.common.SolrInputDocument;

import com.idealista.solrmeter.model.extractor.JsonDocumentExtractor;

import java.util.List;

public class JsonDocumentExtractorSpy extends JsonDocumentExtractor implements DocumentExtractorSpy {

	public JsonDocumentExtractorSpy(String inputFilePath) {
		super(inputFilePath);
	}

	public List<SolrInputDocument> getParsedDocuments() {
		return this.documents;
	}
}
