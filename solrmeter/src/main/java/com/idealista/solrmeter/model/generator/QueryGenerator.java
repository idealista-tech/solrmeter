package com.idealista.solrmeter.model.generator;

import org.apache.solr.client.solrj.SolrQuery;


public interface QueryGenerator {

    public SolrQuery generate();
}
