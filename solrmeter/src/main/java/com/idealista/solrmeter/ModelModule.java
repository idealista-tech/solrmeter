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

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.idealista.solrmeter.model.FieldExtractor;
import com.idealista.solrmeter.model.InputDocumentExtractor;
import com.idealista.solrmeter.model.OptimizeExecutor;
import com.idealista.solrmeter.model.QueryExecutor;
import com.idealista.solrmeter.model.QueryExtractor;
import com.idealista.solrmeter.model.SolrMeterConfiguration;
import com.idealista.solrmeter.model.UpdateExecutor;
import com.idealista.solrmeter.model.executor.OnDemandOptimizeExecutor;
import com.idealista.solrmeter.model.executor.QueryExecutorConstantImpl;
import com.idealista.solrmeter.model.executor.QueryExecutorRandomImpl;
import com.idealista.solrmeter.model.executor.UpdateExecutorConstantImpl;
import com.idealista.solrmeter.model.executor.UpdateExecutorRandomImpl;
import com.idealista.solrmeter.model.extractor.FileFieldExtractor;
import com.idealista.solrmeter.model.extractor.FileInputDocumentExtractor;
import com.idealista.solrmeter.model.extractor.FileQueryExtractor;
import com.idealista.solrmeter.model.extractor.JsonDocumentExtractor;
import com.idealista.solrmeter.model.generator.ComplexQueryGenerator;
import com.idealista.solrmeter.model.generator.ExternalFileQueryGenerator;
import com.idealista.solrmeter.model.generator.QueryGenerator;
import com.idealista.solrmeter.model.service.QueryService;
import com.idealista.solrmeter.model.service.impl.QueryServiceSolrJImpl;
import com.idealista.solrmeter.model.statistic.AbstractStatisticConnection;
import com.idealista.solrmeter.model.statistic.RequestHandlerConnection;

/**
 * 
 * @author tflobbe
 *
 */
public class ModelModule extends AbstractModule {
  
  @Override
  protected void configure() {
    configureQueryExecutors();
    configureUpdateExecutors();
    configureOptimizeExecutors();
    configureQueryGenerators();
    bind(QueryService.class).to(QueryServiceSolrJImpl.class);
    bind(AbstractStatisticConnection.class).to(RequestHandlerConnection.class);
    
  }
  
  private void configureQueryGenerators() {
    Map<String, Class<? extends QueryGenerator>> map = getQueryGeneratorMap();
    for(String generatorName:map.keySet()) {
      bind(QueryGenerator.class).annotatedWith(Names.named(generatorName)).to(map.get(generatorName));
    }
  }
  
  private void configureQueryExecutors() {
    Map<String, Class<? extends QueryExecutor>> map = getQueryExecutorsMap();
    for(String executorName:map.keySet()) {
      bind(QueryExecutor.class).annotatedWith(Names.named(executorName)).to(map.get(executorName));
    }
  }
  
  private void configureUpdateExecutors() {
    Map<String, Class<? extends UpdateExecutor>> map = getUpdateExecutorsMap();
    for(String executorName:map.keySet()) {
      bind(UpdateExecutor.class).annotatedWith(Names.named(executorName)).to(map.get(executorName));
    }
  }
  
  private void configureOptimizeExecutors() {
    Map<String, Class<? extends OptimizeExecutor>> map = getOptimizeExecutorsMap();
    for(String executorName:map.keySet()) {
      bind(OptimizeExecutor.class).annotatedWith(Names.named(executorName)).to(map.get(executorName));
    }
  }
  /**
   * @return The map with name and class of optimize executors to be added to SolrMeter. Override
   * this method to add plugin optimize executors.
   */
  protected Map<String, Class<? extends OptimizeExecutor>> getOptimizeExecutorsMap() {
    Map<String, Class<? extends OptimizeExecutor>> map = new HashMap<String, Class<? extends OptimizeExecutor>>();
    map.put("ondemand", OnDemandOptimizeExecutor.class);
    return map;
  }
  
  protected Map<String, Class<? extends UpdateExecutor>> getUpdateExecutorsMap() {
    Map<String, Class<? extends UpdateExecutor>> map = new HashMap<String, Class<? extends UpdateExecutor>>();
    map.put("constant", UpdateExecutorConstantImpl.class);
    map.put("random", UpdateExecutorRandomImpl.class);
    return map;
  }
  
  protected Map<String, Class<? extends QueryExecutor>> getQueryExecutorsMap() {
    Map<String, Class<? extends QueryExecutor>> map = new HashMap<String, Class<? extends QueryExecutor>>();
    map.put("constant", QueryExecutorConstantImpl.class);
    map.put("random", QueryExecutorRandomImpl.class);
    return map;
  }
  
  protected Map<String, Class<? extends QueryGenerator>> getQueryGeneratorMap() {
    Map<String, Class<? extends QueryGenerator>> map = new HashMap<String, Class<? extends QueryGenerator>>();
    map.put("standard", ComplexQueryGenerator.class);
    map.put("external", ExternalFileQueryGenerator.class);
    return map;
  }
  
  
  @Provides
  public QueryExecutor getQueryExecutor(Injector injector) {
    final String name = SolrMeterConfiguration.getProperty("executor.queryExecutor");
    final Key<QueryExecutor> key = Key.get(QueryExecutor.class,	Names.named(name));
    return injector.getInstance(key);
  }
  
  @Provides
  public UpdateExecutor getUpdateExecutor(Injector injector) {
    final String name = SolrMeterConfiguration.getProperty("executor.updateExecutor");
    final Key<UpdateExecutor> key = Key.get(UpdateExecutor.class, Names.named(name));
    return injector.getInstance(key);
  } 
  
  @Provides
  public OptimizeExecutor getOptimizeExecutor(Injector injector) {
    final String name = SolrMeterConfiguration.getProperty("executor.optimizeExecutor");
    final Key<OptimizeExecutor> key = Key.get(OptimizeExecutor.class, Names.named(name));
    return injector.getInstance(key);
  }
  
  @Provides @Named("queryGenerator")
  public QueryGenerator createQueryGenerator(Injector injector){
    String name = SolrMeterConfiguration.getProperty("solr.query.queryMode");
    final Key<QueryGenerator> key = Key.get(QueryGenerator.class,	Names.named(name));
    return injector.getInstance(key);
  }
  
  
  @Provides @Named("queryExtractor")
  public QueryExtractor createQueryExtractor() {
    return new FileQueryExtractor(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.QUERIES_FILE_PATH));
  }
  
  @Provides @Named("filterQueryExtractor")
  public QueryExtractor createFilterQueryExtractor() {
    return new FileQueryExtractor(SolrMeterConfiguration.getProperty("solr.query.filterQueriesFile"));
  }
  
  @Provides @Named("extraParamExtractor")
  public QueryExtractor createExtraParamExtractor() {
    return new FileQueryExtractor(SolrMeterConfiguration.getProperty("solr.query.extraParams"));
  }
  
  @Provides
  public FieldExtractor createFieldExtractor() {
    return new FileFieldExtractor(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.FIELDS_FILE_PATH));
  }
  
  @Provides @Named("fileUpdateExtractor")
  public InputDocumentExtractor createFileInputExtractor() {
    return new FileInputDocumentExtractor(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.UPDATES_FILE_PATH));
  }

  @Provides @Named("jsonUpdateExtractor")
  public InputDocumentExtractor createJsonInputExtractor() {
    return new JsonDocumentExtractor(SolrMeterConfiguration.getProperty(SolrMeterConfiguration.UPDATES_FILE_PATH));
  }
  
}

