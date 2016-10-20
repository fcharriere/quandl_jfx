package org.quandl.jfx.model.wiki;

/**
 *
 * @author frederic
 */
public class DataSet {

    private final String code;
    private final String description;

    public DataSet(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "DataSet{" + "code=" + code + ", description=" + description + '}';
    }
    
    public static DataSet datasetFromString(String str){
        int index = str.indexOf(",");
        String code = str.substring(0,index).split("/")[1];
        String definition = str.substring(index+1).replaceAll("\"", "");
        
        index = definition.indexOf("(");
        definition = definition.substring(0,index);

        DataSet ds = new DataSet(code, definition);
        return ds;
    }
    
}
