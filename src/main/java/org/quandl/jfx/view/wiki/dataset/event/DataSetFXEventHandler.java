package org.quandl.jfx.view.wiki.dataset.event;

import java.util.List;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;
import org.quandl.jfx.view.wiki.tasks.AddWikiStockTabTask;

/**
 *
 * @author frederic
 */
public class DataSetFXEventHandler implements EventHandler<ActionEvent> {

    private final TableView<DataSetFX> table;
    private final TabPane tabPane;

    public DataSetFXEventHandler(TableView<DataSetFX> table, TabPane tabPane) {
        this.table = table;
        this.tabPane = tabPane;
    }

    @Override
    public void handle(ActionEvent event) {

        List<DataSetFX> dss = table.getSelectionModel().getSelectedItems();
        if (dss == null || dss.size() == 0) {
            System.out.println("No stocks selected");
            return;
        }

        DataSetFX newValue = dss.get(0);

        Task task = new AddWikiStockTabTask(table, tabPane, newValue);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

}
