package org.quandl.jfx.view.wiki.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import org.quandl.jfx.model.wiki.Stock;
import org.quandl.jfx.utils.connectors.quandl.wiki.WIKIConnector;
import org.quandl.jfx.view.wiki.ChartStocks;
import org.quandl.jfx.view.wiki.TableStock;
import org.quandl.jfx.view.wiki.WIKIStockFX;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;

/**
 *
 * @author frederic
 */
public class AddWikiMultipleStocksTabTask extends Task<Void> {

    private final TabPane tabPane;
    private final List<DataSetFX> dataSets;

    public AddWikiMultipleStocksTabTask(TableView<DataSetFX> table, TabPane tabPane, List<DataSetFX> dataSets) {
        this.tabPane = tabPane;
        this.dataSets = dataSets;
    }

    @Override
    protected Void call() throws Exception {

        final WIKIConnector connector = new WIKIConnector();
        final Map<DataSetFX, List<Stock>> map = new HashMap<>();
        final Map<DataSetFX, List<WIKIStockFX>> mapfx = new HashMap<>();

//        Create map
        for (DataSetFX dsfx : dataSets) {
            List<Stock> tmp = connector.getStocks(dsfx.getCode());
            map.put(dsfx, tmp);
        }

//        Create mapfx
        for (DataSetFX key : dataSets) {
            List<WIKIStockFX> stockFXs = new ArrayList<>();
            List<Stock> stocks = map.get(key);

            for (Stock stock : stocks) {
                WIKIStockFX stockFX = new WIKIStockFX(stock);
                stockFXs.add(stockFX);
            }

            mapfx.put(key, stockFXs);
        }

        Collection<List<WIKIStockFX>> r = mapfx.values();
        Object[] r1 = r.toArray();

        List<Tab> tabs = new ArrayList<>();
        String tabName = "";

        for (DataSetFX dataset : dataSets) {
            tabName = tabName + " " + dataset.getCode();
            List<WIKIStockFX> list = mapfx.get(dataset);
            ObservableList<WIKIStockFX> listfx = FXCollections.observableArrayList(list);
            TableStock ts = new TableStock();
            final TableView<WIKIStockFX> table = ts.getTable();
            table.setItems(listfx);
            Tab t = new Tab(dataset.getCode(), table);
            tabs.add(t);
        }

        ChartStocks cs = new ChartStocks();
        cs.updateChart(map);
        Tab t2 = new Tab("Graph", cs.getLineChart());

        TabPane tabPane1 = new TabPane();
        tabPane1.getTabs().add(t2);
        tabPane1.getTabs().addAll(tabs);

        Tab tab = new Tab(tabName, tabPane1);

        Platform.runLater(() -> {
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        });

        return null;
    }

}
