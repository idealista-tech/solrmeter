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
package com.idealista.solrmeter.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.idealista.solrmeter.model.OptimizeExecutor;
import com.idealista.solrmeter.util.SolrMeterThreadFactory;
import com.idealista.solrmeter.view.Refreshable;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class OptimizeExecutorController {
    
    private static final Logger logger = Logger.getLogger(OptimizeExecutorController.class);
    
    private final ExecutorService pool = Executors.newCachedThreadPool(new SolrMeterThreadFactory("optimize-executor"));
	
	private OptimizeExecutor executor;
	
	private Refreshable panel;
	
	@Inject
	public OptimizeExecutorController(
			@Named("optimizeConsolePanel")Refreshable optimizeConsolePanel, 
			OptimizeExecutor optimizeExecutor) {
		this.executor = optimizeExecutor;
		this.panel = optimizeConsolePanel;
	}

	public void onOptimize() {
	    pool.execute(new Runnable() {
	        @Override
            public void run() {
	            logger.info("Optimizing...");
                executor.execute();
                try {
                    Thread.sleep(100);
                    while(executor.isOptimizing()) {
                        panel.refreshView();
                    }
                    panel.refreshView();
                } catch (InterruptedException e) {
                    logger.info(e);
                    Thread.currentThread().interrupt();
                }
            }
	    });
		
	}

}
