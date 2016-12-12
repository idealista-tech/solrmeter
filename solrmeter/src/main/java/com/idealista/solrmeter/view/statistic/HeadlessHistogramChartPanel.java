package com.idealista.solrmeter.view.statistic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.inject.Inject;
import com.idealista.solrmeter.model.statistic.HistogramQueryStatistic;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessStatisticPanel;
import com.idealista.solrmeter.view.HeadlessUtils;
import com.idealista.solrmeter.view.I18n;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class HeadlessHistogramChartPanel extends HeadlessStatisticPanel {

    private final String PREFIX = "statistic.histogramChartPanel.";
    private HistogramQueryStatistic histogram;

    @Inject
	public HeadlessHistogramChartPanel(HistogramQueryStatistic histogram) {
		super();
		this.histogram = histogram;
	}

    public String getStatisticName() {
        return I18n.get(PREFIX + "title");
    }

    public void refreshView() {
        if(!histogram.getCurrentHisogram().isEmpty()) {
            try {
                File outFile = HeadlessUtils.getOutputFile(PREFIX + "title", HeadlessConsoleFrame.getStatisticsOutputDirectory());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
                histogram.printHistogramToStream(outputStream);
                outputStream.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
