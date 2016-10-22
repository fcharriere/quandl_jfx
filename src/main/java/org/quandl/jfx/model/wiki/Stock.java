package org.quandl.jfx.model.wiki;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author frederic
 */
public class Stock implements Comparable<Stock> {

    private final String date;
    private final Double open;
    private final Double high;
    private final Double low;
    private final Double close;
    private final Double volume;
    private final Double exDividend;
    private final Double splitRatio;
    private final Double adjOpen;
    private final Double adjHigh;
    private final Double adjLow;
    private final Double adjClose;
    private final Double adjVolume;

    public Stock(String date, Double open, Double high, Double low,
            Double close, Double volume, Double exDividend, Double splitRatio,
            Double adjOpen, Double adjHigh, Double adjLow, Double adjClose,
            Double adjVolume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.exDividend = exDividend;
        this.splitRatio = splitRatio;
        this.adjOpen = adjOpen;
        this.adjHigh = adjHigh;
        this.adjLow = adjLow;
        this.adjClose = adjClose;
        this.adjVolume = adjVolume;
    }

    public static Stock stockFromString(String input) {
        String[] inputs = input.split(",");

        String date = inputs[0];
        Double open = inputs[1].isEmpty() ? 0D : Double.parseDouble(inputs[1]);
        Double high = inputs[2].isEmpty() ? 0D : Double.parseDouble(inputs[2]);
        Double low = inputs[3].isEmpty() ? 0D : Double.parseDouble(inputs[3]);
        Double close = inputs[4].isEmpty() ? 0D : Double.parseDouble(inputs[4]);
        Double volume = inputs[5].isEmpty() ? 0D : Double.parseDouble(inputs[5]);
        Double exDividend = inputs[6].isEmpty() ? 0D : Double.parseDouble(inputs[6]);
        Double splitRatio = inputs[7].isEmpty() ? 0D : Double.parseDouble(inputs[7]);
        Double adjOpen = inputs[8].isEmpty() ? 0D : Double.parseDouble(inputs[8]);
        Double adjHigh = inputs[9].isEmpty() ? 0D : Double.parseDouble(inputs[9]);
        Double adjLow = inputs[10].isEmpty() ? 0D : Double.parseDouble(inputs[10]);
        Double adjClose = inputs[11].isEmpty() ? 0D : Double.parseDouble(inputs[11]);
        Double adjVolume = inputs[12].isEmpty() ? 0D : Double.parseDouble(inputs[12]);

        return new Stock(date, open, high, low, close, volume, exDividend, splitRatio, adjOpen, adjHigh, adjLow, adjClose, adjVolume);
    }

    public long convertDateToLong() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(this.getDate(), formatter);
        return localDate.toEpochDay();
    }

    @Override
    public int compareTo(Stock o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(this.date, formatter);
        LocalDate foreignDate = LocalDate.parse(o.getDate(), formatter);

        return localDate.compareTo(foreignDate);
    }

    @Override
    public String toString() {
        return this.date + " - " + this.open + " - " + this.high;
    }

    public String getDate() {
        return date;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() {
        return close;
    }

    public Double getVolume() {
        return volume;
    }

    public Double getExDividend() {
        return exDividend;
    }

    public Double getSplitRatio() {
        return splitRatio;
    }

    public Double getAdjOpen() {
        return adjOpen;
    }

    public Double getAdjHigh() {
        return adjHigh;
    }

    public Double getAdjLow() {
        return adjLow;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public Double getAdjVolume() {
        return adjVolume;
    }

}
