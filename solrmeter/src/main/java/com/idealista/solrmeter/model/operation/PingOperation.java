package com.idealista.solrmeter.model.operation;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrException;

import com.idealista.solrmeter.model.exception.PingNotConfiguredException;

/**
 * Operation that executes one ping to a SolrServer
 * @author tflobbe
 *
 */
public class PingOperation implements Operation {
	
	private final SolrClient client;
	
	public PingOperation(SolrClient client) {
		this.client = client;
	}

	@Override
	public boolean execute() throws PingNotConfiguredException {
		try {
			SolrPingResponse response = client.ping();
			if(response.getStatus() == 0) {
				return true;
			}
		} catch (SolrServerException | IOException e) {
			return false;
		} catch (SolrException e) {
			if(e.getMessage().startsWith("pingQuery_not_configured")) {
				throw new PingNotConfiguredException("Ping command is not configured on server.");
			}
			return false;
		}
		return false;
	}

}
