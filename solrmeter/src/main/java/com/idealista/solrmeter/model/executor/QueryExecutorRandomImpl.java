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
package com.idealista.solrmeter.model.executor;

import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.idealista.solrmeter.model.QueryExecutor;
import com.idealista.solrmeter.model.QueryStatistic;
import com.idealista.solrmeter.model.SolrClientFactory;
import com.idealista.solrmeter.model.SolrMeterConfiguration;
import com.idealista.solrmeter.model.exception.QueryException;
import com.idealista.solrmeter.model.generator.QueryGenerator;
import com.idealista.solrmeter.model.operation.QueryOperation;
import com.idealista.solrmeter.model.operation.RandomOperationExecutorThread;
import com.idealista.stressTestScope.StressTestScope;

/**
 * Creates and manages query execution Threads. The queries are executed with 
 * RandomOperationExectionThread.
 * @see com.idealista.solrmeter.model.operation.RandomOperationExecutorThread
 * @author tflobbe
 *
 */
@StressTestScope
public class QueryExecutorRandomImpl extends AbstractRandomExecutor implements QueryExecutor {
	
	/**
	 * Solr Client for strings
	 * TODO implement provider
	 */
	private SolrClient client;
	
	/**
	 * List of Statistics observing this Executor.
	 */
	private List<QueryStatistic> statistics;
	
    /**
     * The generator that creates a query depending on the query mode selected
     */
    private QueryGenerator queryGenerator;

    @Inject
	public QueryExecutorRandomImpl(@Named("queryGenerator") QueryGenerator queryGenerator) {
		super();
        this.queryGenerator = queryGenerator;
		this.statistics = new LinkedList<QueryStatistic>();
		this.operationsPerSecond = Integer.parseInt(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.QUERIES_PER_SECOND));
		super.prepare();
	}



	public QueryExecutorRandomImpl() {
		super();
		this.statistics = new LinkedList<QueryStatistic>();
	}
	
	

	@Override
	protected RandomOperationExecutorThread createThread() {
		return new RandomOperationExecutorThread(new QueryOperation(this, queryGenerator), 1000);
	}

	/**
	 * Logs strings time and all statistics information.
	 */
	@Override
	protected void stopStatistics() {
		for(QueryStatistic statistic:statistics) {
			statistic.onFinishedTest();
		}
	}

	@Override
	public synchronized SolrClient getSolrClient() {
		if(client == null) {
			client = SolrClientFactory.getSolrClientForQuery();
		}
		return client;
	}
	
	@Override
	public void notifyQueryExecuted(QueryResponse response, long clientTime) {
		for (QueryStatistic statistic:statistics) {
			statistic.onExecutedQuery(response, clientTime);
		}
	}
	
	@Override
	public void notifyError(QueryException exception) {
		for (QueryStatistic statistic:statistics) {
			statistic.onQueryError(exception);
		}
	}
	
	
	@Override
	protected String getOperationsPerSecondConfigurationKey() {
		return "solr.load.queriespersecond";
	}
	
	@Override
	public void addStatistic(QueryStatistic statistic) {
		this.statistics.add(statistic);
	}

	@Override
	public int getQueriesPerSecond() {
		return operationsPerSecond;
	}

}
