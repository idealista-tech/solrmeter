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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;

/**
 * This registry holds all the created solr servers. It will be one for each different url 
 * and it wont change between tests.
 * @author tflobbe
 *
 */
public class SolrClientRegistry {

	private static final Logger logger = Logger.getLogger(SolrClientRegistry.class);

	private static final Map<SolrClientConfiguration, SolrClient> servers = new HashMap<>();

	public static synchronized SolrClient getSolrClient(SolrClientConfiguration solrClientSettings) {
		SolrClient server = servers.get(solrClientSettings);
		
		if(server == null) {
			logger.info("Connecting to Solr: " + solrClientSettings);
						
			final SolrClient solrClient = solrClientSettings.buildSolrClient();
			servers.put(solrClientSettings, solrClient);
			
			return solrClient;
		}
		
		return server;
	}

	public static void dropSolrClients() {
		for(SolrClient server : servers.values()) {
			try {
				server.close();
			} catch (IOException e) {
				logger.error("error closing client", e);
			}
		}
		
		servers.clear();
	}
}