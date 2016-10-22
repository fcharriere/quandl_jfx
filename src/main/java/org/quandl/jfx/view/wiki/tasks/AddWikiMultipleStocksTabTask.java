package org.quandl.jfx.view.wiki.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private final TableView<DataSetFX> table;
    private final TabPane tabPane;
    private final List<DataSetFX> dataSets;

    public AddWikiMultipleStocksTabTask(TableView<DataSetFX> table, TabPane tabPane, List<DataSetFX> dataSets) {
        this.table = table;
        this.tabPane = tabPane;
        this.dataSets = dataSets;
    }

    @Override
    protected Void call() throws Exception {

        System.out.println("Starting Multiple Stock from thread " + Thread.currentThread().getName());

        final WIKIConnector connector = new WIKIConnector();
        final Map<DataSetFX, List<Stock>> map = new HashMap<>();
        final Map<DataSetFX, List<WIKIStockFX>> mapfx = new HashMap<>();
        
//        Create map
        try {
            for (DataSetFX dsfx : dataSets) {
                List<Stock> tmp = connector.getStocks(dsfx.getCode());
                if (tmp != null) {
                    Collections.sort(tmp);
                    map.put(dsfx, tmp);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

//        Create mapfx
        Set<DataSetFX> keys = map.keySet();
        for (DataSetFX key : keys) {
            List<WIKIStockFX> wikisfxs = new ArrayList<>();
            List<Stock> stocks = map.get(key);
            for (Stock stock : stocks) {
                WIKIStockFX wikisfx = new WIKIStockFX(stock);
                wikisfxs.add(wikisfx);
            }
            mapfx.put(key, wikisfxs);
        }

        System.out.println("Nothing to do");

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
