package com.idealista.solrmeter.runMode;

import javax.swing.JMenuBar;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.idealista.solrmeter.SolrMeterMain;
import com.idealista.solrmeter.controller.StatisticDescriptor;
import com.idealista.solrmeter.controller.StatisticType;
import com.idealista.solrmeter.controller.StatisticsRepository;
import com.idealista.solrmeter.model.OptimizeExecutor;
import com.idealista.solrmeter.model.OptimizeStatistic;
import com.idealista.solrmeter.model.QueryExecutor;
import com.idealista.solrmeter.model.QueryStatistic;
import com.idealista.solrmeter.model.UpdateExecutor;
import com.idealista.solrmeter.model.UpdateStatistic;
import com.idealista.solrmeter.view.CommitConsolePanel;
import com.idealista.solrmeter.view.ConsoleFrame;
import com.idealista.solrmeter.view.I18n;
import com.idealista.solrmeter.view.OptimizeConsolePanel;
import com.idealista.solrmeter.view.QueryConsolePanel;
import com.idealista.solrmeter.view.QueryPanel;
import com.idealista.solrmeter.view.StatisticPanel;
import com.idealista.solrmeter.view.StatisticsContainer;
import com.idealista.solrmeter.view.SwingUtils;
import com.idealista.solrmeter.view.UpdateConsolePanel;
import com.idealista.stressTestScope.StressTestRegistry;

public class SolrMeterRunModeGUI extends AbstractSolrMeterRunMode {

    public static final String RUN_MODE_NAME = "gui";

    private void addQueryPanel() {
		mainFrame.getStatisticsContainer().addStatistic(injector.getInstance(QueryPanel.class));
	}

	private void initView(Injector injector) {
		mainFrame = injector.getInstance(ConsoleFrame.class);
		mainFrame.setTitle(I18n.get("mainFrame.title"));
	}

	private void showView() {
		mainFrame.pack();
		SwingUtils.centerWindow(mainFrame);
		mainFrame.setVisible(true);
	}

	private void loadLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Logger.getLogger(SolrMeterMain.class).error("Error loading look and feel. Will Continue with default.", e);
		}
	}

    private void addStatistics(Injector injector) {
		QueryExecutor queryExecutor = injector.getInstance(QueryExecutor.class);
		UpdateExecutor updateExecutor = injector.getInstance(UpdateExecutor.class);
		OptimizeExecutor optimizeExecutor = injector.getInstance(OptimizeExecutor.class);
		StatisticsRepository repository = injector.getInstance(StatisticsRepository.class);
		
		for(StatisticDescriptor stat:repository.getActiveStatistics()) {
			Logger.getLogger("boot").info("Adding Statistic " + stat.getName());
			if(stat.isHasView()) {
				addStatictic(mainFrame.getStatisticsContainer(), injector, stat.getViewName());
			}
			if(stat.getTypes().contains(StatisticType.QUERY)) {
				addStatistic(queryExecutor, injector, stat.getModelName());
			}
			if(stat.getTypes().contains(StatisticType.UPDATE)) {
				addStatistic(updateExecutor, injector, stat.getModelName());
			}
			if(stat.getTypes().contains(StatisticType.OPTIMIZE)) {
				addStatistic(optimizeExecutor, injector, stat.getModelName());
			}
		}

	}

	private void addStatistic(OptimizeExecutor optimizeExecutor,
			Injector injector, String modelName) {
		Key<OptimizeStatistic> injectorKey = Key.get(OptimizeStatistic.class, Names.named(modelName));
		optimizeExecutor.addStatistic(injector.getInstance(injectorKey));
	}

	private void addStatistic(UpdateExecutor updateExecutor,
			Injector injector, String modelName) {
		Key<UpdateStatistic> injectorKey = Key.get(UpdateStatistic.class, Names.named(modelName));
		updateExecutor.addStatistic(injector.getInstance(injectorKey));
	}

	private void addStatictic(StatisticsContainer statisticsContainer,
			Injector injector, String viewName) {
		Key<StatisticPanel> injectorKey = Key.get(StatisticPanel.class, Names.named(viewName));
		statisticsContainer.addStatistic(injector.getInstance(injectorKey));

	}

	private void addStatistic(QueryExecutor queryExecutor, Injector injector,
			String modelName) {
		Key<QueryStatistic> injectorKey = Key.get(QueryStatistic.class, Names.named(modelName));
		queryExecutor.addStatistic(injector.getInstance(injectorKey));
	}

    public void main(Injector injector) {
        super.main(injector);
		StressTestRegistry.start();
		loadLookAndFeel();
		initView(injector);
		addStatistics(injector);
		addQueryPanel();
		showView();
    }

    public void restartApplication() {
		StressTestRegistry.restart();
		I18n.onConfigurationChange();
		mainFrame.setQueryPanel(injector.getInstance(QueryConsolePanel.class));
		mainFrame.setUpdatePanel(injector.getInstance(UpdateConsolePanel.class));
		mainFrame.setOptimizePanel(injector.getInstance(OptimizeConsolePanel.class));
		mainFrame.setCommitPanel(injector.getInstance(CommitConsolePanel.class));
		mainFrame.setJMenuBar(injector.getInstance(JMenuBar.class));
		mainFrame.onConfigurationChanged();
		addStatistics(injector);
		addQueryPanel();
    }

}
