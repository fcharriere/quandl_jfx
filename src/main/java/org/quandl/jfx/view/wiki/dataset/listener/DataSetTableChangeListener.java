package org.quandl.jfx.view.wiki.dataset.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;
import org.quandl.jfx.view.wiki.tasks.AddWikiStockTabTask;

/**
 *
 * @author frederic
 */
public class DataSetTableChangeListener implements ChangeListener<DataSetFX> {

    private final TableView<DataSetFX> table;
    private final TabPane tabPane;

    public DataSetTableChangeListener(TableView<DataSetFX> table, TabPane tabPane) {
        this.table = table;
        this.tabPane = tabPane;
    }

    @Override
    public void changed(ObservableValue<? extends DataSetFX> ov, DataSetFX oldValue, DataSetFX newValue) {
        if (table.getSelectionModel().getSelectedItem() != null) {

            Task task = new AddWikiStockTabTask(table, tabPane, newValue);
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
            
        }
    }
    
}
