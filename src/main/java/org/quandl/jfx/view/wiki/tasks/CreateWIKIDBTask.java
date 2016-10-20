package org.quandl.jfx.view.wiki.tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.quandl.jfx.model.wiki.DataSet;
import org.quandl.jfx.utils.Utils;
import org.quandl.jfx.utils.connectors.db.DBConnector;
import org.quandl.jfx.view.DataSetFX;

/**
 *
 * @author frederic
 */
public class CreateWIKIDBTask extends Task<ObservableList<DataSetFX>> {

    private final ObservableList<DataSetFX> data;
    private final ObservableList<DataSetFX> completeData;

    public CreateWIKIDBTask(ObservableList<DataSetFX> data, ObservableList<DataSetFX> completeData) {
        this.data = data;
        this.completeData = completeData;
    }
    
    @Override
    protected ObservableList<DataSetFX> call() throws Exception {

//        Step 1 -- Create the database if needed,
        Utils util = new Utils();
        List<DataSet> dss = util.readWikiDataSets();

        Boolean togo = false;

        try {
            DBConnector connector = new DBConnector();
            togo = connector.createTables();
            if (togo) {
                connector.insertDataSet(dss);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

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
        
        return xs;
    }

}
