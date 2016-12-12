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

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;

import com.idealista.solrmeter.model.OptimizeExecutor;
import com.idealista.solrmeter.model.OptimizeStatistic;
import com.idealista.solrmeter.model.SolrClientFactory;
import com.idealista.solrmeter.model.exception.OptimizeException;
import com.idealista.stressTestScope.StressTestScope;
/**
 * Executes an optimize only when the "execute" method is invoked
 * @author tflobbe
 *
 */
@StressTestScope
public class OnDemandOptimizeExecutor implements OptimizeExecutor {
	
	protected static final Logger logger = Logger.getLogger(OnDemandOptimizeExecutor.class);
	
	/**
	 * The Solr client were the optimize is going to run.
	 */
	protected SolrClient client = null;
	
	/**
	 * Indicates whether the index is being optimized or not at this time
	 */
	private boolean isOptimizing = false;
	
	/**
	 * List of Statistics observing the operation
	 */
	protected List<OptimizeStatistic> optimizeObservers;
	
	public OnDemandOptimizeExecutor() {
		this(SolrClientFactory.getSolrClientForUpdate());
	}
	
	public OnDemandOptimizeExecutor(SolrClient client) {
		optimizeObservers = new LinkedList<>();
		this.client = client;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public synchronized void execute() {
		if(isOptimizing) {
			logger.warn("Trying to optimize while already optimizing");
			return;
		}
		Thread optimizeThread = new Thread() {
			@Override
			public void run() {
				try {
					isOptimizing = true;
					long init = System.nanoTime();
					notifyOptimizeStatred(init);
					client.optimize();
					notifyOptimizeFinished((System.nanoTime() - init)/1000000);
					isOptimizing = false;
				} catch (Exception e) {
					logger.error(e);
					notifyErrorOnOptimize(new OptimizeException(e));
				}
			}
		};
		optimizeThread.start();
	}
	/**
	 * Notifies observers when the optimization started
	 * @param initTime
	 */
	private void notifyOptimizeStatred(long initTime) {
		for(OptimizeStatistic observer:optimizeObservers) {
			observer.onOptimizeStared(initTime);
		}
	}

	/**
	 * Notifies observers when an error ocurrs
	 * @param exception
	 */
	private void notifyErrorOnOptimize(OptimizeException exception) {
		for(OptimizeStatistic observer:optimizeObservers) {
			observer.onOptimizeError(exception);
		}
		this.isOptimizing = false;
	}

	/**
	 * Notifies observers when the operation finishes
	 * @param delay
	 */
	private void notifyOptimizeFinished(long delay) {
		for(OptimizeStatistic observer:optimizeObservers) {
			observer.onOptimizeFinished(delay);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addStatistic(OptimizeStatistic observer) {
		this.optimizeObservers.add(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOptimizing() {
		return isOptimizing;
	}

}
