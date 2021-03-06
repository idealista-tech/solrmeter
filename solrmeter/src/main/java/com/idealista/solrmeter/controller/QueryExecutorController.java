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

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.idealista.solrmeter.model.QueryExecutor;
import com.idealista.solrmeter.view.ConsolePanel;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class QueryExecutorController {
    
	private ConsolePanel panel;
	
	private QueryExecutor executor;
	
	private Timer timer = null;

	@Inject
	public QueryExecutorController(@Named("queryConsolePanel") ConsolePanel queryConsolePanel,
			QueryExecutor queryExecutor) {
		this.panel = queryConsolePanel;
		this.executor = queryExecutor;
	}

	public void onStart() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				panel.refreshView();
			}
		};
		timer.schedule(task, new Date(), 1000);
		getExecutor().prepare();
		getExecutor().start();
		panel.started();
	}

	public void onStop() {
		timer.cancel();
		getExecutor().stop();
		panel.stopped();
	}

	public void onConcurrentQueriesValueChange(Integer value) {
	    getExecutor().setOperationsPerSecond(value);
	}

	private QueryExecutor getExecutor() {
		return executor;
	}

}
