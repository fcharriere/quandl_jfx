package org.quandl.jfx.view.wiki.tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.quandl.jfx.model.wiki.DataSet;
import org.quandl.jfx.utils.connectors.db.DBConnector;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;

/**
 *
 * @author frederic
 */
public class LoadDataSetTask extends Task<Void> {

    private final ObservableList<DataSetFX> data;
    private final ObservableList<DataSetFX> completeData;

    public LoadDataSetTask(ObservableList<DataSetFX> data, ObservableList<DataSetFX> completeData) {
        this.data = data;
        this.completeData = completeData;
    }

    @Override
    protected Void call() throws Exception {
//        Step 2 -- Load the DataSets from the database.
        List<DataSet> dss2 = new ArrayList<>();

        try {
            DBConnector connector = new DBConnector();
            List<DataSet> tmp = connector.uploadDataSet();
            if (tmp != null) {
                dss2.addAll(tmp);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        List<DataSetFX> dsfxs = new ArrayList<>();
        for (DataSet ds : dss2) {
            DataSetFX fx = new DataSetFX(ds.getCode(), ds.getDescription());
            dsfxs.add(fx);
        }

        ObservableList<DataSetFX> xs = FXCollections.observableArrayList(dsfxs);
        data.addAll(xs);
        completeData.addAll(xs);
        
        return null;
    }

}
