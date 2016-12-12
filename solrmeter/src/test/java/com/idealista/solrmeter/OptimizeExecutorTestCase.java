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
package com.idealista.solrmeter;

import java.net.MalformedURLException;

import com.idealista.solrmeter.mock.OptimizeExecutorSpy;
import com.idealista.solrmeter.mock.SolrClientMock;
import com.idealista.solrmeter.model.statistic.OptimizationResult;
import com.idealista.solrmeter.model.statistic.SimpleOptimizeStatistic;

public class OptimizeExecutorTestCase extends BaseTestCase {

	public void test() throws MalformedURLException, InterruptedException {
		OptimizeExecutorSpy executor = new OptimizeExecutorSpy();
		assertEquals(0, ((SolrClientMock)executor.getClient()).getNumberOfOptimize());
		executor.execute();
		Thread.sleep(100);
		assertEquals(1, ((SolrClientMock)executor.getClient()).getNumberOfOptimize());
	}
	
	public void testObservers() throws MalformedURLException, InterruptedException {
		OptimizeExecutorSpy executor = new OptimizeExecutorSpy();
		SimpleOptimizeStatistic statistic = new SimpleOptimizeStatistic();
		executor.addStatistic(statistic);
		executor.execute();
		Thread.sleep(100);
		assertEquals(1, statistic.getOptimizationCount());
		assertEquals(OptimizationResult.OK, statistic.getLastOptimizationResult());
		assertEquals(0, statistic.getOptimizeErrorCount());
	}
}
