package com.idealista.solrmeter.view.statistic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.inject.Inject;
import com.idealista.solrmeter.model.statistic.QueryTimeHistoryStatistic;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessStatisticPanel;
import com.idealista.solrmeter.view.HeadlessUtils;
import com.idealista.solrmeter.view.I18n;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class HeadlessQueryTimeHistoryPanel extends HeadlessStatisticPanel {

    private final String PREFIX = "statistic.queryTimeHistoryPanel.";
    private QueryTimeHistoryStatistic statistic;

    @Inject
    public HeadlessQueryTimeHistoryPanel(QueryTimeHistoryStatistic statistic) {
        super();
        this.statistic = statistic;
    }

    @Override
    public String getStatisticName() {
        return I18n.get(PREFIX + "title");
    }

    @Override
    public void refreshView() {
        if(!statistic.getCurrentHistory().isEmpty()) {
            try {
                File outFile = HeadlessUtils.getOutputFile(PREFIX + "title", HeadlessConsoleFrame.getStatisticsOutputDirectory());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
                statistic.printQueriesTimeToStream(outputStream);
                outputStream.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
