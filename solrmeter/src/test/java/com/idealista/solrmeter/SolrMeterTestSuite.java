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

import com.idealista.solrmeter.controller.FullQueryStatisticControllerTestCase;
import com.idealista.solrmeter.controller.StatisticsRepositoryTestCase;
import com.idealista.solrmeter.extractor.FileInputDocumentExtractorTestCase;
import com.idealista.solrmeter.extractor.FileStringExtractorTestCase;
import com.idealista.solrmeter.model.generator.ComplexQueryGeneratorTestCase;
import com.idealista.solrmeter.model.generator.ExternalFileQueryGeneratorTestCase;
import com.idealista.solrmeter.statistic.ErrorLogStatisticTestCase;
import com.idealista.solrmeter.statistic.FullQueryStatisticTestCase;
import com.idealista.solrmeter.statistic.HistogramQueryStatisticTestCase;
import com.idealista.solrmeter.statistic.OperationRateStatisticTestCase;
import com.idealista.solrmeter.statistic.OperationTimeHistoryTestCase;
import com.idealista.solrmeter.statistic.QueryLogStatisticTestCase;
import com.idealista.solrmeter.statistic.QueryTimeHistoryTestCase;
import com.idealista.solrmeter.statistic.RequestHandlerConnectionTestCase;
import com.idealista.solrmeter.statistic.SimpleQueryStatisticTestCase;
import com.idealista.solrmeter.statistic.TimeRangeStatisticTestCase;
import com.idealista.solrmeter.statistic.TimeRangeTestCase;
import com.idealista.solrmeter.statistic.parser.StatisticsParserCastorImplTestCase;
import com.idealista.solrmeter.task.AbstractOperationThreadTestCase;
import com.idealista.solrmeter.task.ConstantOperationExecutorThreadTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SolrMeterTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.idealista.solrmeter");
		
		// com.idealista.solrmeter.*
		suite.addTestSuite(AbstractExecutorTestCase.class);
		suite.addTestSuite(ExpectedParameterTestCase.class);
		suite.addTestSuite(FileUtilsTest.class);
		suite.addTestSuite(OptimizeExecutorTestCase.class);
		suite.addTestSuite(QueryServiceSolrJImplTestCase.class);
		suite.addTestSuite(SolrMeterConfigurationTestCase.class);
		suite.addTestSuite(UpdateExecutorTestCase.class);
		
		// com.idealista.solrmeter.controller.*
		suite.addTestSuite(FullQueryStatisticControllerTestCase.class);
		suite.addTestSuite(StatisticsRepositoryTestCase.class);
		
		// com.idealista.solrmeter.statistics.*
		suite.addTestSuite(ErrorLogStatisticTestCase.class);
		suite.addTestSuite(FullQueryStatisticTestCase.class);
		suite.addTestSuite(HistogramQueryStatisticTestCase.class);
		suite.addTestSuite(OperationRateStatisticTestCase.class);
		suite.addTestSuite(OperationTimeHistoryTestCase.class);
		suite.addTestSuite(QueryLogStatisticTestCase.class);
		suite.addTestSuite(QueryTimeHistoryTestCase.class);
		suite.addTestSuite(RequestHandlerConnectionTestCase.class);
		suite.addTestSuite(SimpleQueryStatisticTestCase.class);
		suite.addTestSuite(TimeRangeStatisticTestCase.class);
		suite.addTestSuite(TimeRangeTestCase.class);
		suite.addTestSuite(StatisticsParserCastorImplTestCase.class);
		
		// com.idealista.solrmeter.extractor.*
		suite.addTestSuite(FileInputDocumentExtractorTestCase.class);
		suite.addTestSuite(FileStringExtractorTestCase.class);
		
		// com.idealista.solrmeter.model.generator.*
		suite.addTestSuite(ExternalFileQueryGeneratorTestCase.class);
		suite.addTestSuite(ComplexQueryGeneratorTestCase.class);

		// com.idealista.solrmeter.task.*
		suite.addTestSuite(AbstractOperationThreadTestCase.class);
		suite.addTestSuite(ConstantOperationExecutorThreadTestCase.class);
		
		return suite;
	}

}
