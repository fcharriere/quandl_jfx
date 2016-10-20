package org.quandl.jfx.view.wiki.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import org.quandl.jfx.model.wiki.Stock;
import org.quandl.jfx.utils.connectors.quandl.wiki.WIKIConnector;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;
import org.quandl.jfx.view.wiki.ChartStock;
import org.quandl.jfx.view.wiki.TableStock;
import org.quandl.jfx.view.wiki.WIKIStockFX;

/**
 *
 * @author frederic
 */
public class AddWikiStockTabTask extends Task<Void> {

    private final TableView<DataSetFX> table;
    private final TabPane tabPane;
    private final DataSetFX data;

    public AddWikiStockTabTask(TableView<DataSetFX> table, TabPane tabPane, DataSetFX data) {
        this.table = table;
        this.tabPane = tabPane;
        this.data = data;
    }

    @Override
    protected Void call() throws Exception {

        WIKIConnector connector = new WIKIConnector();
        List<Stock> stocks = new ArrayList<>();

        try {
            List<Stock> tmp = connector.getStocks(data.getCode());
            if (tmp != null) {
                stocks.addAll(tmp);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        List<WIKIStockFX> wikisfxs = new ArrayList<>();

        stocks.stream().map((stock) -> new WIKIStockFX(stock)).forEachOrdered((fx) -> {
            wikisfxs.add(fx);
        });

        final ObservableList<WIKIStockFX> wikisfx = FXCollections.observableArrayList(wikisfxs);

        TableStock ts = new TableStock();
        final TableView<WIKIStockFX> table = ts.getTable();
        table.setItems(wikisfx);

        TabPane tabPane1 = new TabPane();
        Tab t = new Tab("Data", table);
        ChartStock cs = new ChartStock(data.getCode());
        cs.updateChart(stocks);

        Tab t2 = new Tab("Graph", cs.getLineChart());
        tabPane1.getTabs().addAll(t2, t);
        Tab tab = new Tab(data.getDescription(), tabPane1);

        Platform.runLater(() -> {
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        });

        return null;
    }

}
