package com.idealista.solrmeter;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.idealista.solrmeter.controller.QueryExecutorController;
import com.idealista.solrmeter.view.ConsolePanel;
import com.idealista.solrmeter.view.HeadlessCommitConsolePanel;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessOptimizeConsolePanel;
import com.idealista.solrmeter.view.HeadlessQueryConsolePanel;
import com.idealista.solrmeter.view.HeadlessStatisticsContainer;
import com.idealista.solrmeter.view.HeadlessUpdateConsolePanel;
import com.idealista.solrmeter.view.Refreshable;

public class HeadlessModule extends AbstractModule {

    @Override
    public void configure() {
        bind(HeadlessConsoleFrame.class);
        bind(HeadlessQueryConsolePanel.class);
        bind(HeadlessUpdateConsolePanel.class);
        bind(HeadlessCommitConsolePanel.class);
        bind(HeadlessOptimizeConsolePanel.class);
        bind(HeadlessStatisticsContainer.class);
        bind(QueryExecutorController.class);
        bind(ConsolePanel.class).annotatedWith(Names.named("queryConsolePanel")).to(HeadlessQueryConsolePanel.class);
		bind(Refreshable.class).annotatedWith(Names.named("statisticsContainer")).to(HeadlessStatisticsContainer.class);
		bind(Refreshable.class).annotatedWith(Names.named("optimizeConsolePanel")).to(HeadlessOptimizeConsolePanel.class);
    }
}
