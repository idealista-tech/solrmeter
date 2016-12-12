package com.idealista.solrmeter.controller;


import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.idealista.solrmeter.model.SolrMeterConfiguration;
import com.idealista.solrmeter.model.extractor.LogExtractor;
import com.idealista.solrmeter.view.ExtractFromLogFilePanelContainer;
import com.idealista.solrmeter.view.SolrPropertyObserver;


public class ExtractFromLogFileController {
  
  private final ExtractFromLogFilePanelContainer container;
  private final Window window;
  private Properties extractProperties;
  private HashMap<String, Set<SolrPropertyObserver>> observerMap;
  
  public static final String SOLR_LOG_FILE = "tools.extract.solrLogFile";
  public static final String DESTINATION_FILENAME = "tools.extract.destinationFilename";
  public static final String REGEX = "tools.extract.regex";
  public static final String REMOVE_DUPLICATES = "tools.extract.removeDuplicates";

  public ExtractFromLogFileController(ExtractFromLogFilePanelContainer container, Window window) {
    this.container = container;
    this.window = window;
    extractProperties = new Properties();
    observerMap = new HashMap<>();

    this.setProperty(REGEX, LogExtractor.defaultRegularExpression);
    SolrMeterConfiguration.setProperty(REGEX, LogExtractor.defaultRegularExpression);
    this.setProperty(REMOVE_DUPLICATES, "true");
    SolrMeterConfiguration.setProperty(REMOVE_DUPLICATES, "true");
  }
  
  public void cancel() {
    window.dispose();
  }
  
  public void extract() {
    container.beginExtraction();
    try {

      String solrLogFile = retrieveProperty(SOLR_LOG_FILE);
      String outputFilename = retrievePropertyOrDefault(DESTINATION_FILENAME, "output-" + getFilenameFrom(solrLogFile));
      String regularExpression = retrieveProperty(REGEX);
      boolean removeDuplicates = Boolean.parseBoolean(retrieveProperty(REMOVE_DUPLICATES));

      LogExtractor logExtractor = new LogExtractor(regularExpression, removeDuplicates);
      Collection<String> queries = logExtractor.extractFromFile(solrLogFile);

      File outputFile = writeToFile(queries, outputFilename);
      container.succed(outputFile);
    } catch (IllegalArgumentException exception) {
      Logger.getLogger(this.getClass()).error("Error while extracting queries ", exception);
      container.error(exception);
    } catch (IOException exception) {
      Logger.getLogger(this.getClass()).info("Error while extracting queries ", exception);
      container.error(exception);
    }

  }


  private String retrieveProperty(String propertyName) throws IllegalArgumentException {
    String localProperty = extractProperties.getProperty(propertyName);
    if((localProperty == null) || localProperty.isEmpty()){
        throw new IllegalArgumentException("Field is empty");
    }
    return localProperty;
  }

  private String retrievePropertyOrDefault(String propertyName, String defaultValue){
    String value = "";
    try{
      value = retrieveProperty(propertyName);    
    }catch(IllegalArgumentException exception){
        value = defaultValue;
    }

    return  value;

  }

  private String getFilenameFrom(String path) {
      File file = new File(path);
      return file.getName();
  }




  private File writeToFile(Collection<String> queries, String outputFilename) throws IllegalArgumentException, IOException {
    if(queries.isEmpty()){
        throw new IllegalArgumentException("Unable to extract any query. Check your regex!");
    }
    File outputFile = new File(outputFilename);

    FileUtils.writeLines(outputFile, queries);
    return outputFile;
  }

  public void setProperty(String property, String value) {
    extractProperties.put(property, value);
    notifyObservers(property, value);
  }
  
  public void addPropertyObserver(String property, SolrPropertyObserver observer) {
    Set<SolrPropertyObserver> observers = observerMap.get(property);
    if (observers == null) {
      observers = new HashSet<SolrPropertyObserver>();
      observerMap.put(property, observers);
    }
    
    observers.add(observer);
  }
  
  protected void notifyObservers(String property, String value) {
    Set<SolrPropertyObserver> observers = observerMap.get(property);
    if (observers != null) {
      for (SolrPropertyObserver observer : observers) {
        observer.solrPropertyChanged(property, value);
      }
    }
  }
  
  
}


