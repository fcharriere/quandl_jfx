package org.quandl.jfx.view.wiki.listener;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import org.quandl.jfx.view.DataSetFX;

/**
 *
 * @author frederic
 */
public class DataSetFilterListener implements ChangeListener<String> {

    private final ObservableList<DataSetFX> data;
    private final ObservableList<DataSetFX> complete_data;

    public DataSetFilterListener(ObservableList<DataSetFX> data, ObservableList<DataSetFX> complete_data) {
        this.data = data;
        this.complete_data = complete_data;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        List<DataSetFX> dss = new ArrayList<>();

        if (newValue == null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    data.clear();
                    data.addAll(complete_data);
                }
            });
            return;
        }

        if (newValue.equalsIgnoreCase(oldValue)) {
            return;
        }

        for (DataSetFX fx : complete_data) {
            if (fx.getCode().toLowerCase().contains(newValue.toLowerCase())) {
                dss.add(fx);
            }
        }

        for (DataSetFX fx : complete_data) {
            if (fx.getDescription().toLowerCase().contains(newValue.toLowerCase())) {
                dss.add(fx);
            }
        }

        data.clear();
        data.addAll(dss);
    }

}
