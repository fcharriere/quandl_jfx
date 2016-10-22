package org.quandl.jfx.view.wiki;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
import org.quandl.jfx.model.wiki.Stock;

/**
 *
 * @author frederic
 */
public class ChartStock {

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series series = new XYChart.Series();

    public ChartStock() {
        yAxis.setLabel("Price");
        xAxis.setLabel("Date");
        xAxis.setForceZeroInRange(false);
        yAxis.setForceZeroInRange(false);
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        series.setName("My portfolio");
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

    public ChartStock(String def) {
        this();
        series.setName(def);
    }

    public void updateChart(List<Stock> stocks) {

        Collections.sort(stocks);

        for (Stock stock : stocks) {
            series.getData().add(new XYChart.Data(stock.convertDateToLong(), stock.getClose()));
        }
        
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke-width: 1px;");
    }

    public LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }

}
