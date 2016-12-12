package com.idealista.solrmeter.model;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrCloudClientConfiguration implements SolrClientConfiguration {

	private final String zooKeeperUrl;
	
	private final String zooKeeperChroot;
	
	private final String defaultCollection;

	public SolrCloudClientConfiguration(String zooKeeperUrl, String zooKeeperChroot, String defaultCollection) {
		this.zooKeeperUrl = zooKeeperUrl;
		this.zooKeeperChroot = zooKeeperChroot;
		this.defaultCollection = defaultCollection;
	}
		
	@Override
	public SolrClient buildSolrClient(){
		CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder()
									    		.withHttpClient(createHttpClient())
												.withZkHost(zooKeeperUrl)
												.withZkChroot(zooKeeperChroot)
									    		.build();
		
		cloudSolrClient.setDefaultCollection(defaultCollection);
		
		return cloudSolrClient;
	}
	
	private HttpClient createHttpClient() {
		ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.maxTotalConnections", "1000000")));
        params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.defaultMaxConnectionsPerHost", "100000")));
        params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, Boolean.parseBoolean(SolrMeterConfiguration.getProperty("solr.server.configuration.followRedirect", "false")));

        return HttpClientUtil.createClient(params);
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultCollection == null) ? 0 : defaultCollection.hashCode());
		result = prime * result + ((zooKeeperChroot == null) ? 0 : zooKeeperChroot.hashCode());
		result = prime * result + ((zooKeeperUrl == null) ? 0 : zooKeeperUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolrCloudClientConfiguration other = (SolrCloudClientConfiguration) obj;
		if (defaultCollection == null) {
			if (other.defaultCollection != null)
				return false;
		} else if (!defaultCollection.equals(other.defaultCollection))
			return false;
		if (zooKeeperChroot == null) {
			if (other.zooKeeperChroot != null)
				return false;
		} else if (!zooKeeperChroot.equals(other.zooKeeperChroot))
			return false;
		if (zooKeeperUrl == null) {
			if (other.zooKeeperUrl != null)
				return false;
		} else if (!zooKeeperUrl.equals(other.zooKeeperUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SolrCloudSettings [zooKeeperUrl=" + zooKeeperUrl 
				+ ", zooKeeperChroot=" + zooKeeperChroot
				+ ", defaultCollection=" + defaultCollection + "]";
	}
}