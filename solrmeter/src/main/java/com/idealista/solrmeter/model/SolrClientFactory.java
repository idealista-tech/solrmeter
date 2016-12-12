package com.idealista.solrmeter.model;

import org.apache.solr.client.solrj.SolrClient;

public class SolrClientFactory {

	public static SolrClient getSolrClientForQuery(){		
		if(!Boolean.parseBoolean(SolrMeterConfiguration.getProperty("solr.query.useSolrCloudArchitecture"))){
			return SolrClientRegistry.getSolrClient(new SolrDefaultClientConfiguration(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.SOLR_SEARCH_URL)));
		} 
		
		return SolrClientRegistry.getSolrClient(new SolrCloudClientConfiguration(SolrMeterConfiguration.getProperty("solr.query.zooKeeperUrl"), SolrMeterConfiguration.getProperty("solr.query.zooKeeperChroot"), SolrMeterConfiguration.getProperty("solr.query.defaultSolrCollection")));
	}
	
	public static SolrClient getSolrClientForUpdate(){
		if(!Boolean.parseBoolean(SolrMeterConfiguration.getProperty("solr.update.useSolrCloudArchitecture"))){
			return SolrClientRegistry.getSolrClient(new SolrDefaultClientConfiguration(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.SOLR_ADD_URL)));
		} 
		
		return SolrClientRegistry.getSolrClient(new SolrCloudClientConfiguration(SolrMeterConfiguration.getProperty("solr.update.zooKeeperUrl"), SolrMeterConfiguration.getProperty("solr.update.zooKeeperChroot"), SolrMeterConfiguration.getProperty("solr.update.defaultSolrCollection")));
	}
}