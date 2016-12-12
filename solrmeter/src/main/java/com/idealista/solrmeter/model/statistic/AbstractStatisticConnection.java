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
package com.idealista.solrmeter.model.statistic;

import java.util.Map;

import com.idealista.solrmeter.model.exception.StatisticConnectionException;

/**
 * Superclass for statistic connections with Solr. A StatisticConnection will be used for extracting
 * information from Solr, even for showing it or for analyzing it and extract conclussions.
 * @author tomas
 *
 */
public abstract class AbstractStatisticConnection {
	
	public static final String FILTER_CACHE_NAME = "filterCache";
	public static final String DOCUMENT_CACHE_NAME = "documentCache";
	public static final String FIELD_CACHE_NAME = "fieldCache";
	public static final String FIELD_VALUE_CACHE_NAME = "fieldValueCache";
	public static final String QUERY_RESULT_CACHE_NAME = "queryResultCache";
	
	public static final String CUMULATIVE_FILTER_CACHE_NAME = "cumulativeFilterCache";
	public static final String CUMULATIVE_DOCUMENT_CACHE_NAME = "cumulativeDocumentCache";
	public static final String CUMULATIVE_FIELD_VALUE_CACHE_NAME = "cumulativeFieldValueCache";
	public static final String CUMULATIVE_QUERY_RESULT_CACHE_NAME = "cumulativeQueryResultCache";
	

	public abstract Map<String, CacheData> getData() throws StatisticConnectionException;
	
	public CacheData getFilterCacheData(Map<String, CacheData> map) {
		return map.get(FILTER_CACHE_NAME);
	}
	
	public CacheData getDocumentCacheData(Map<String, CacheData> map) {
		return map.get(DOCUMENT_CACHE_NAME);
	}
	
	public CacheData getFieldValueCacheData(Map<String, CacheData> map) {
		return map.get(FIELD_VALUE_CACHE_NAME);
	}
	
	public CacheData getQueryResultCacheData(Map<String, CacheData> map) {
		return map.get(QUERY_RESULT_CACHE_NAME);
	}
	
	public CacheData getCumulativeFilterCacheData(Map<String, CacheData> map) {
		return map.get(CUMULATIVE_FILTER_CACHE_NAME);
	}
	
	public CacheData getCumulativeDocumentCacheData(Map<String, CacheData> map) {
		return map.get(CUMULATIVE_DOCUMENT_CACHE_NAME);
	}
	
	public CacheData getCumulativeFieldValueCacheData(Map<String, CacheData> map) {
		return map.get(CUMULATIVE_FIELD_VALUE_CACHE_NAME);
	}
	
	public CacheData getCumulativeQueryResultCacheData(Map<String, CacheData> map) {
		return map.get(CUMULATIVE_QUERY_RESULT_CACHE_NAME);
	}
}
