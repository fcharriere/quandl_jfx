package org.quandl.jfx.view.wiki;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.quandl.jfx.model.wiki.Stock;

/**
 *
 * @author frederic
 */
public class WIKIStockFX implements Comparable<WIKIStockFX> {

    private final SimpleStringProperty date;
    private final SimpleDoubleProperty open;
    private final SimpleDoubleProperty high;
    private final SimpleDoubleProperty low;
    private final SimpleDoubleProperty close;
    private final SimpleDoubleProperty volume;
    private final SimpleDoubleProperty exDividend;
    private final SimpleDoubleProperty splitRatio;
    private final SimpleDoubleProperty adjOpen;
    private final SimpleDoubleProperty adjHigh;
    private final SimpleDoubleProperty adjLow;
    private final SimpleDoubleProperty adjClose;
    private final SimpleDoubleProperty adjVolume;

    public WIKIStockFX(String date, Double open, Double high, Double low,
            Double close, Double volume, Double exDividend, Double splitRatio,
            Double adjOpen, Double adjHigh, Double adjLow, Double adjClose,
            Double adjVolume) {
        this.date = new SimpleStringProperty(date);
        this.open = new SimpleDoubleProperty(open);
        this.high = new SimpleDoubleProperty(high);
        this.low = new SimpleDoubleProperty(low);
        this.close = new SimpleDoubleProperty(close);
        this.volume = new SimpleDoubleProperty(volume);
        this.exDividend = new SimpleDoubleProperty(exDividend);
        this.splitRatio = new SimpleDoubleProperty(splitRatio);
        this.adjOpen = new SimpleDoubleProperty(adjOpen);
        this.adjHigh = new SimpleDoubleProperty(adjHigh);
        this.adjLow = new SimpleDoubleProperty(adjLow);
        this.adjClose = new SimpleDoubleProperty(adjClose);
        this.adjVolume = new SimpleDoubleProperty(adjVolume);
    }

    public WIKIStockFX(Stock stock) {
        this(stock.getDate(), stock.getOpen(), stock.getHigh(), stock.getLow(),
                stock.getClose(), stock.getVolume(), stock.getExDividend(), stock.getSplitRatio(),
                stock.getAdjOpen(), stock.getAdjHigh(), stock.getAdjLow(), stock.getAdjClose(),
                stock.getAdjVolume());
    }

    @Override
    public int compareTo(WIKIStockFX o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(this.date.get(), formatter);
        LocalDate foreignDate = LocalDate.parse(o.getDate(), formatter);

        return localDate.compareTo(foreignDate);
    }

    @Override
    public boolean equals(Object obj) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(this.date.get(), formatter);
        LocalDate foreignDate = LocalDate.parse(((WIKIStockFX)obj).getDate(), formatter);

        return localDate.compareTo(foreignDate) == 0;
    }

    @Override
    public String toString() {
        return "WIKIStockFX{" + "date=" + date + ", close=" + close + '}';
    }

    public String getDate() {
        return date.get();
    }

    public Double getOpen() {
        return open.get();
    }

    public Double getHigh() {
        return high.get();
    }

    public Double getLow() {
        return low.get();
    }

    public Double getClose() {
        return close.get();
    }

    public Double getVolume() {
        return volume.get();
    }

    public Double getExDividend() {
        return exDividend.get();
    }

    public Double getSplitRatio() {
        return splitRatio.get();
    }

    public Double getAdjOpen() {
        return adjOpen.get();
    }

    public Double getAdjHigh() {
        return adjHigh.get();
    }

    public Double getAdjLow() {
        return adjLow.get();
    }

    public Double getAdjClose() {
        return adjClose.get();
    }

    public Double getAdjVolume() {
        return adjVolume.get();
    }

}
