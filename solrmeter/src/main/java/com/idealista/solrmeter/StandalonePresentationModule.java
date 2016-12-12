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

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.idealista.solrmeter.controller.QueryExecutorController;
import com.idealista.solrmeter.controller.QueryPanelController;
import com.idealista.solrmeter.controller.SolrMeterMenuController;
import com.idealista.solrmeter.view.CommitConsolePanel;
import com.idealista.solrmeter.view.ConsoleFrame;
import com.idealista.solrmeter.view.ConsolePanel;
import com.idealista.solrmeter.view.OptimizeConsolePanel;
import com.idealista.solrmeter.view.QueryConsolePanel;
import com.idealista.solrmeter.view.QueryPanel;
import com.idealista.solrmeter.view.Refreshable;
import com.idealista.solrmeter.view.SolrMeterMenuBar;
import com.idealista.solrmeter.view.StatisticsContainer;
import com.idealista.solrmeter.view.UpdateConsolePanel;
/**
 * 
 * @author tflobbe
 *
 */
public class StandalonePresentationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConsoleFrame.class);
		bind(JFrame.class).annotatedWith(Names.named("mainFrame")).to(ConsoleFrame.class);
		bind(QueryConsolePanel.class);
		bind(UpdateConsolePanel.class);
		bind(CommitConsolePanel.class);
		bind(OptimizeConsolePanel.class);
		bind(StatisticsContainer.class);
		bind(JMenuBar.class).to(SolrMeterMenuBar.class);
		bind(QueryExecutorController.class);
		bind(ConsolePanel.class).annotatedWith(Names.named("queryConsolePanel")).to(QueryConsolePanel.class);
		bind(Refreshable.class).annotatedWith(Names.named("statisticsContainer")).to(StatisticsContainer.class);
		bind(Refreshable.class).annotatedWith(Names.named("optimizeConsolePanel")).to(OptimizeConsolePanel.class);
		bind(SolrMeterMenuController.class);
		bind(QueryPanel.class);
		bind(QueryPanelController.class);
	}

}
