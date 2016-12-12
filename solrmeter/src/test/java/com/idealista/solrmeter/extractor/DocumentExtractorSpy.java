package com.idealista.solrmeter.extractor;

import org.apache.solr.common.SolrInputDocument;

import java.util.List;

public interface DocumentExtractorSpy {

	List<SolrInputDocument> getParsedDocuments();

	SolrInputDocument getRandomDocument();
}

