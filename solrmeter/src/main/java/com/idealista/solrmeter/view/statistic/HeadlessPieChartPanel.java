package com.idealista.solrmeter.view.statistic;

import java.util.ArrayList;
import java.util.Map;

import com.google.inject.Inject;
import com.idealista.solrmeter.model.statistic.TimeRange;
import com.idealista.solrmeter.model.statistic.TimeRangeStatistic;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessStatisticPanel;
import com.idealista.solrmeter.view.HeadlessUtils;
import com.idealista.solrmeter.view.I18n;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class HeadlessPieChartPanel extends HeadlessStatisticPanel {

    private final String PREFIX = "statistic.pieChartPanel.";
    private TimeRangeStatistic timeRangeStatistic;

    @Inject
    public HeadlessPieChartPanel(TimeRangeStatistic statistic) {
        super();
        timeRangeStatistic = statistic;
    }

    @Override
    public String getStatisticName() {
        return I18n.get(PREFIX + "title");
    }

    @Override
    public void refreshView() {
        ArrayList<String> lines = new ArrayList<String>();
		Map<TimeRange, Integer> percentages = timeRangeStatistic.getActualPercentage();
		for(TimeRange range: percentages.keySet()) {
            lines.add(range.toString() + ":\t" + percentages.get(range));
		}
        HeadlessUtils.outputData(PREFIX + "title", HeadlessConsoleFrame.getStatisticsOutputDirectory(), lines);
    }
}
