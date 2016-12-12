package com.idealista.solrmeter.view.statistic;

import java.util.List;

import com.google.inject.Inject;
import com.idealista.solrmeter.model.exception.OperationException;
import com.idealista.solrmeter.model.statistic.ErrorLogStatistic;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessStatisticPanel;
import com.idealista.solrmeter.view.HeadlessUtils;
import com.idealista.solrmeter.view.I18n;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class HeadlessErrorLogPanel extends HeadlessStatisticPanel {

    private String PREFIX = "statistic.errorLogPanel.";
    private ErrorLogStatistic statistic;

    @Inject
    public HeadlessErrorLogPanel(ErrorLogStatistic statistic) {
        super();
        this.statistic = statistic;
    }

    @Override
    public String getStatisticName() {
        return I18n.get(PREFIX + "title");
    }

    @Override
    public void refreshView() {
        List<OperationException> errors = statistic.getLastErrors(true, true, true, true);
        HeadlessUtils.outputData(PREFIX + "title", HeadlessConsoleFrame.getStatisticsOutputDirectory(), errors);
    }

}
