package org.quandl.jfx.utils.connectors.quandl.wiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quandl.jfx.model.wiki.Stock;

/**
 *
 * @author frederic
 */
public class WIKIConnector {

    static final String API_KEY = "jh9kAu1y8MB-UZSuXTCy";

    public List<Stock> getStocks(String code) throws IOException{
        
        String request = "https://www.quandl.com/api/v3/datasets/WIKI/" + code + ".csv?api_key=" + WIKIConnector.API_KEY;
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(request);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        List<Stock> result = new ArrayList<>();
        
        String all = "";
        
        try {
            HttpEntity entity1 = response1.getEntity();
            all = EntityUtils.toString(entity1);
        } finally {
            response1.close();
        }
        
        String [] r = all.split("\\n");
        
        for (int i=1;i<r.length;i++){ // zapping the first line;
            Stock stock = Stock.stockFromString(r[i]);
            result.add(stock);
        }
        
        Collections.sort(result);
        
        return result;
    }

}
