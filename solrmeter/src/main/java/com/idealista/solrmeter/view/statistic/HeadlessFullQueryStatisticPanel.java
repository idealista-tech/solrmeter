package com.idealista.solrmeter.view.statistic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.idealista.solrmeter.model.statistic.FullQueryStatistic;
import com.idealista.solrmeter.model.statistic.QueryLogStatistic;
import com.idealista.solrmeter.view.HeadlessConsoleFrame;
import com.idealista.solrmeter.view.HeadlessStatisticPanel;
import com.idealista.solrmeter.view.HeadlessUtils;
import com.idealista.solrmeter.view.I18n;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class HeadlessFullQueryStatisticPanel extends HeadlessStatisticPanel {

    private static final Logger logger = Logger.getLogger(HeadlessFullQueryStatisticPanel.class);
    private final String PREFIX = "statistic.fullQueryStatistic.";
    private static final int DOUBLE_SCALE = 2;
	private FullQueryStatistic fullQueryStatistic;
	private QueryLogStatistic queryLogStatistic;

    @Inject
    public HeadlessFullQueryStatisticPanel(FullQueryStatistic fullQueryStatistic, QueryLogStatistic queryLogStatistic) {
        super();
        this.fullQueryStatistic = fullQueryStatistic;
        this.queryLogStatistic = queryLogStatistic;
    }

    private String getString(Double number) {
		return new BigDecimal(number).setScale(DOUBLE_SCALE, BigDecimal.ROUND_HALF_DOWN).toString();
	}

    private String prepareData(String str) {
        return str.replace(",", "\t")
                  .replace("false", "ok")
                  .replace("true", "error");
    }

    @Override
	public String getStatisticName() {
		return I18n.get(PREFIX + "title");
	}

    @Override
    public void refreshView() {
        logger.debug("refreshing Full Query Statistics");

        ArrayList<String> lines = new ArrayList<String>();
        lines.add("median:\t" + getString(fullQueryStatistic.getMedian()));
        lines.add("mode:\t" + fullQueryStatistic.getMode().toString());
        lines.add("variance:\t" + getString(fullQueryStatistic.getVariance()));
        lines.add("standard deviation:\t" + getString(fullQueryStatistic.getStandardDeviation()));
        lines.add("total average:\t" + fullQueryStatistic.getTotaAverage().toString());
        lines.add("last minute average:\t" + fullQueryStatistic.getLastMinuteAverage().toString());
        lines.add("last ten minute average:\t" + fullQueryStatistic.getLastTenMinutesAverage().toString());
		if(fullQueryStatistic.getLastErrorTime() != null) {
            lines.add("last error time:\t" + SimpleDateFormat.getInstance().format(fullQueryStatistic.getLastErrorTime()));
		} else {
            lines.add("last error time:\t-");
		}
        lines.add("status\tquery\tfilter query\tfacet query\tquery time\tresult count");
        List<QueryLogStatistic.QueryLogValue> queries = queryLogStatistic.getLastQueries();
        for(QueryLogStatistic.QueryLogValue value:queries) {
            lines.add(prepareData(value.getCSV()));
        }
        HeadlessUtils.outputData(PREFIX + "title", HeadlessConsoleFrame.getStatisticsOutputDirectory(), lines);
    }

}
