package org.quandl.jfx.utils.connectors.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.quandl.jfx.model.wiki.DataSet;
import org.quandl.jfx.model.wiki.Stock;

/**
 *
 * @author frederic
 */
public class DBConnector {

    private final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String dbName = "QUANDL";
    private final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    private final Connection connection;

    public DBConnector() throws SQLException {
        connection = DriverManager.getConnection(connectionURL);
    }

    public Boolean createTables() {
        String request = "create table WIKI_DATASET("
                + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                + "CODE VARCHAR(10),"
                + "DESCRIPTION VARCHAR(150))";

        String createStockTableRequest = "CREATE TABLE WIKI_STOCK("
                + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                + "DATASET_ID INT NOT NULL,"
                + "DATE VARCHAR(10),"
                + "OPENS DOUBLE,"
                + "HIGH DOUBLE,"
                + "LOW DOUBLE,"
                + "CLOSES DOUBLE,"
                + "VOLUME DOUBLE,"
                + "EX_DIVIDEND DOUBLE,"
                + "SPLIT_RATIO DOUBLE,"
                + "ADJ_OPEN DOUBLE,"
                + "ADJ_HIGH DOUBLE,"
                + "ADJ_LOW DOUBLE,"
                + "ADJ_CLOSE DOUBLE,"
                + "ADJ_VOLUME DOUBLE)";

        try {

            PreparedStatement statement = connection.prepareStatement(request);
            statement.execute();
            Statement createStockTableStatement = connection.createStatement();
            createStockTableStatement.execute(createStockTableRequest);

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
//            System.out.println(sqle.getSQLState());
            return false;
        }
        return true;
    }

    public void insertDataSet(List<DataSet> dataSets) {

        try {

            String request = "insert into WIKI_DATASET(CODE,DESCRIPTION) values (?,?)";
            PreparedStatement statement = connection.prepareStatement(request);

            for (DataSet ds : dataSets) {
                statement.setString(1, ds.getCode());
                statement.setString(2, ds.getDescription());
                statement.executeUpdate();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public List<DataSet> uploadDataSet() {

        List<DataSet> result = new ArrayList<>();

        try {
            
            String request = "select * from WIKI_DATASET";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);

            while (rs.next()) {
                String code = rs.getString("CODE");
                String description = rs.getString("DESCRIPTION");
                DataSet ds = new DataSet(code, description);
                result.add(ds);
            }

        } catch (SQLException sqle) {

        }

        return result;
    }

    public void insertStock(List<Stock> stocks, DataSet ds) {

        String request = "SELECT ID FROM WIKI_DATASET WHERE CODE='" + ds.getCode() + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            rs.next();
            Integer datasetId = rs.getInt("ID");
            insertStock(stocks, datasetId);
        } catch (SQLException sqle) {
            System.out.println(request);
            System.out.println(sqle.getMessage());
        }

    }

    private void insertStock(List<Stock> stocks, Integer datasetId) {
        
        String request = "insert into WIKI_STOCK(DATASET_ID,"
                + "DATE,"
                + "OPENS,"
                + "HIGH,"
                + "LOW,"
                + "CLOSES,"
                + "VOLUME,"
                + "EX_DIVIDEND,"
                + "SPLIT_RATIO,"
                + "ADJ_OPEN,"
                + "ADJ_HIGH,"
                + "ADJ_LOW,"
                + "ADJ_CLOSE,"
                + "ADJ_VOLUME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        
        try {

            PreparedStatement statement = connection.prepareStatement(request);

            for(Stock stock: stocks){
                statement.setInt(1, datasetId);
                statement.setString(2, stock.getDate());
                statement.setDouble(3, stock.getOpen());
                statement.setDouble(4, stock.getHigh());
                statement.setDouble(5, stock.getLow());
                statement.setDouble(6, stock.getClose());
                statement.setDouble(7, stock.getVolume());
                statement.setDouble(8, stock.getExDividend());
                statement.setDouble(9, stock.getSplitRatio());
                statement.setDouble(10, stock.getAdjOpen());
                statement.setDouble(11, stock.getAdjHigh());
                statement.setDouble(12, stock.getAdjLow());
                statement.setDouble(13, stock.getAdjClose());
                statement.setDouble(14, stock.getAdjVolume());                
                statement.executeUpdate();
            }
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
