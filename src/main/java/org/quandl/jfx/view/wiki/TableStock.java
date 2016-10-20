package org.quandl.jfx.view.wiki;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author frederic
 */
public class TableStock {

    final TableView<WIKIStockFX> table = new TableView<>();

    public TableStock() {
        init();
    }

    private void init() {
        table.setEditable(true);

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn openColumn = new TableColumn("Open");
        openColumn.setCellValueFactory(new PropertyValueFactory<>("open"));

        TableColumn highColumn = new TableColumn("High");
        highColumn.setCellValueFactory(new PropertyValueFactory<>("high"));

        TableColumn lowColumn = new TableColumn("Low");
        lowColumn.setCellValueFactory(new PropertyValueFactory<>("low"));

        TableColumn closeColumn = new TableColumn("Close");
        closeColumn.setCellValueFactory(new PropertyValueFactory<>("close"));

        TableColumn volumnColumn = new TableColumn("Volumn");
        volumnColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));

        TableColumn exDividendColumn = new TableColumn("Ex Dividend");
        exDividendColumn.setCellValueFactory(new PropertyValueFactory<>("exDividend"));

        TableColumn splitRatioColumn = new TableColumn("Split Ratio");
        splitRatioColumn.setCellValueFactory(new PropertyValueFactory<>("splitRatio"));

        TableColumn adjOpenColumn = new TableColumn("Adj Open");
        adjOpenColumn.setCellValueFactory(new PropertyValueFactory<>("adjOpen"));

        TableColumn adjHighColumn = new TableColumn("Adj High");
        adjHighColumn.setCellValueFactory(new PropertyValueFactory<>("adjHigh"));

        TableColumn adjLowColumn = new TableColumn("Adj Low");
        adjLowColumn.setCellValueFactory(new PropertyValueFactory<>("adjLow"));

        TableColumn adjCloseColumn = new TableColumn("Adj Close");
        adjCloseColumn.setCellValueFactory(new PropertyValueFactory<>("adjClose"));

        TableColumn adjVolumnColumn = new TableColumn("Adj Volumn");
        adjVolumnColumn.setCellValueFactory(new PropertyValueFactory<>("adjVolume"));

        table.getColumns().addAll(dateColumn, openColumn, highColumn, lowColumn, closeColumn, volumnColumn, exDividendColumn,
                splitRatioColumn, adjOpenColumn, adjHighColumn, adjLowColumn, adjCloseColumn, adjVolumnColumn);
    }

    public TableView<WIKIStockFX> getTable() {
        return table;
    }
    
}
