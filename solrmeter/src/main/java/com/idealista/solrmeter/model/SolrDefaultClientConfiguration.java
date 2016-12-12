package com.idealista.solrmeter.model;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrDefaultClientConfiguration implements SolrClientConfiguration {

	public final String url;
	
	public SolrDefaultClientConfiguration(String url){
		this.url = url;
	}
	
	@Override
	public SolrClient buildSolrClient() {
		HttpSolrClient httpServer = new HttpSolrClient.Builder(url).build();
		
		httpServer.setSoTimeout(Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.soTimeout", "60000"))); // socket read timeout
		httpServer.setConnectionTimeout(Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.connectionTimeout", "60000")));
		httpServer.setDefaultMaxConnectionsPerHost(Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.defaultMaxConnectionsPerHost", "100000")));
		httpServer.setMaxTotalConnections(Integer.parseInt(SolrMeterConfiguration.getProperty("solr.server.configuration.maxTotalConnections", "1000000")));
		httpServer.setFollowRedirects(Boolean.parseBoolean(SolrMeterConfiguration.getProperty("solr.server.configuration.followRedirect", "false"))); // defaults to false
		httpServer.setAllowCompression(Boolean.parseBoolean(SolrMeterConfiguration.getProperty("solr.server.configuration.allowCompression", "true")));
		setAuthentication(httpServer);
		
		return httpServer;
	}
	
	private static void setAuthentication(HttpSolrClient httpServer) {
		String user = SolrMeterConfiguration.getProperty("solr.server.configuration.httpAuthUser");
		String pass = SolrMeterConfiguration.getProperty("solr.server.configuration.httpAuthPass");
		
		if(StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pass)) {
			HttpClient client = httpServer.getHttpClient();
			((AbstractHttpClient) client).addRequestInterceptor(new PreEmptiveBasicAuthenticator(user, pass));
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		
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
		SolrDefaultClientConfiguration other = (SolrDefaultClientConfiguration) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SolrClientSettings [url=" + url + "]";
	}
	
}