package org.quandl.jfx.view.wiki;

import java.util.Collections;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
        xAxis.setLabel("Number of days");
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        series.setName("My portfolio");
        lineChart.setCreateSymbols(false);
        
    }
    
    public ChartStock(String def) {
        this();
        series.setName(def);
    }
    
    public void updateChart(List<Stock> stocks) {
        
        int i = 0;
        Collections.sort(stocks);
        
        for (Stock stock : stocks) {
            series.getData().add(new XYChart.Data(i++, stock.getClose()));
        }
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke-width: 1px;");
    }
    
    public LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }
    
}
