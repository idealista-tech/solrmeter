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
import com.idealista.solrmeter.model.operation.ConstantOperationExecutorThread;
import com.idealista.solrmeter.model.operation.QueryOperation;
import com.idealista.stressTestScope.StressTestScope;

/**
 * This query executor calculates the interval between queries to achieve
 * the specified number of queries per minute and tries to execute them in
 * constant time.
 * @see com.idealista.solrmeter.model.operation.ConstantOperationExecutorThread
 * @author tflobbe
 *
 */
@StressTestScope
public class QueryExecutorConstantImpl implements QueryExecutor{
	
	/**
	 * Solr client for strings
	 */
	private SolrClient client;
	
	/**
	 * List of Statistics observing this Executor.
	 */
	private List<QueryStatistic> statistics;
	
	/**
	 * Indicates wether the Executor is running or not
	 */
	private boolean running;
	
	private int operationsPerSecond;
	
	/**
	 * Thread that execute queries periodically
	 */
	private ConstantOperationExecutorThread executerThread;

    /**
     * The generator that creates a query depending on the query mode selected 
     */
    private QueryGenerator queryGenerator;

    @Inject
	public QueryExecutorConstantImpl(@Named("queryGenerator") QueryGenerator queryGenerator) {
        this.queryGenerator = queryGenerator;
        statistics = new LinkedList<>();
		this.operationsPerSecond = Integer.valueOf(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.QUERIES_PER_SECOND)).intValue();
	}

	@Override
	public int getQueriesPerSecond() {
		return operationsPerSecond;
	}

	@Override
	public synchronized SolrClient getSolrClient() {
		if(client == null) {
			client = SolrClientFactory.getSolrClientForQuery();
		}
		
		return client;
	}
	
	private void updateThreadWaitTime() {
		if(executerThread != null) {
			executerThread.setTimeToWait(1000/operationsPerSecond);
		}
	}

	@Override
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public void prepare() {

	}

	@Override
	public void start() {
		running = true;
		executerThread = new ConstantOperationExecutorThread(new QueryOperation(this, queryGenerator));
		this.updateThreadWaitTime();
		executerThread.start();
	}

	@Override
	public void stop() {
		running = false;
		executerThread.destroy();
		this.stopStatistics();
	}
	
	/**
	 * Logs strings time and all statistics information.
	 */
	protected void stopStatistics() {
		for(QueryStatistic statistic:statistics) {
			statistic.onFinishedTest();
		}
	}

	@Override
	public void notifyQueryExecuted(QueryResponse response, long clientTime) {
		for(QueryStatistic statistic:statistics) {
			statistic.onExecutedQuery(response, clientTime);
		}
	}

	@Override
	public void notifyError(QueryException exception) {
		for(QueryStatistic statistic:statistics) {
			statistic.onQueryError(exception);
		}
	}
	
	@Override
	public void addStatistic(QueryStatistic statistic) {
		this.statistics.add(statistic);
	}



    @Override
    public void setOperationsPerSecond(int newOperationsPerSecond) {
        this.operationsPerSecond = newOperationsPerSecond;
        updateThreadWaitTime();
    }

}
