package org.quandl.jfx.view.wiki;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.quandl.jfx.view.DataSetFX;

/**
 *
 * @author frederic
 */
public class DataSetTableViewSkin extends TableViewSkin<DataSetFX> {

    public DataSetTableViewSkin(TableView<DataSetFX> tableView) {
        super(tableView);
    }

    @Override
    public void resizeColumnToFitContent(TableColumn<DataSetFX, ?> tc, int maxRows) {
        super.resizeColumnToFitContent(tc, maxRows);
    }

    
    
}
