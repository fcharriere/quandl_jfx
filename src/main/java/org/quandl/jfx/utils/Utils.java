package org.quandl.jfx.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.quandl.jfx.model.wiki.DataSet;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;

/**
 *
 * @author frederic
 */
public class Utils {

    public List<DataSet> readWikiDataSets() {

        List<DataSet> result = new ArrayList<>();
        Scanner scanner = null;

        try {
            
            InputStream input = getClass().getResourceAsStream("/databases/wiki/datasets.csv");
            InputStreamReader reader = new InputStreamReader(input);
            scanner = new Scanner(reader);

            while (scanner.hasNextLine()) {
                DataSet tmp = DataSet.datasetFromString(scanner.nextLine());
                result.add(tmp);
            }

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return result;
    }
    
    public List<DataSetFX> uploadDataSets(){
        return null;
    }

}
