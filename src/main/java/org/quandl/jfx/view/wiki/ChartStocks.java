package org.quandl.jfx.view.wiki;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
import org.quandl.jfx.model.wiki.Stock;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;

/**
 *
 * @author frederic
 */
public class ChartStocks {

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart;

    public ChartStocks() {
        yAxis.setLabel("Price");
        xAxis.setLabel("Date");
        xAxis.setForceZeroInRange(false);
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        setTickLabelFormatter(xAxis);
        xAxis.setTickLabelRotation(60);
    }

    private void setTickLabelFormatter(NumberAxis xAxis) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        StringConverter converter = new StringConverter() {
            @Override
            public String toString(Object object) {
                Long obj = (long) ((Double) object).longValue();
                LocalDate localDate = LocalDate.ofEpochDay(obj);
                return localDate.format(formatter);
            }

            @Override
            public Object fromString(String string) {
                LocalDate localDate = LocalDate.parse(string, formatter);
                return (double) localDate.toEpochDay();
            }
        };

        xAxis.setTickLabelFormatter(converter);
    }

    public void updateChart(Map<DataSetFX, List<Stock>> map) {

        Set<DataSetFX> keys = map.keySet();
        for (DataSetFX key : keys) {
            XYChart.Series series = new XYChart.Series();
            series.setName(key.getCode());
            List<Stock> ls = map.get(key);
            Collections.sort(ls);
            for (Stock stock : ls) {
                series.getData().add(new XYChart.Data(stock.convertDateToLong(), stock.getClose()));
            }
            lineChart.getData().add(series);
            series.getNode().setStyle("-fx-stroke-width: 1px;");
        }

    }

    public LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }

}
