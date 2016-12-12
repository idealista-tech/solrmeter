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
package com.idealista.solrmeter.mock;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.idealista.solrmeter.model.executor.UpdateExecutorRandomImpl;


public class UpdateExecutorMock extends UpdateExecutorRandomImpl {
	
	private SolrClientMock client;
	
	public synchronized SolrClient getSolrClient() {
		if(client == null) {
			client = new SolrClientMock();
		}
		return client;
	}
	
	
	public SolrInputDocument getNextDocument() {
		return new SolrInputDocument();
	}

	@Override
	public void notifyAddedDocument(UpdateResponse response) { }


	@Override
	public void notifyCommitSuccessfull(UpdateResponse response) { }


	@Override
	public void start() {	}


	@Override
	public void stop() {
		super.stop();
	}

	
}
