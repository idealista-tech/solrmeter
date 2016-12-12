package com.idealista.solrmeter.model.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealista.solrmeter.model.InputDocumentExtractor;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.Map;

public class JsonDocumentExtractor extends InputDocumentExtractor {

	private static final Logger logger = Logger.getLogger(JsonDocumentExtractor.class);

	public JsonDocumentExtractor(String filePath) {
		super(filePath);
	}

	@Override
	protected SolrInputDocument createSolrDocument(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		SolrInputDocument document = new SolrInputDocument();
		
		try {
			for (Object field: mapper.readValue(jsonString, Map.class).entrySet()) {
				Map.Entry f = (Map.Entry) field;
				document.addField((String) f.getKey(),f.getValue());
			}
		} catch (IOException e) {
			logger.error("error creating solr document", e);
		}
		
		return document;
	}
}
