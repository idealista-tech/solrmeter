package com.idealista.solrmeter.model;

import org.apache.solr.client.solrj.SolrClient;

public interface SolrClientConfiguration {

	SolrClient buildSolrClient();

}