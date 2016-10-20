package org.quandl.jfx.view.wiki.dataset;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author frederic
 */
public class DataSetFX {

    private final SimpleStringProperty code;
    private final SimpleStringProperty description;

    public DataSetFX(String code, String description) {
        this.code = new SimpleStringProperty(code);
        this.description = new SimpleStringProperty(description);
    }

    @Override
    public String toString() {
        return "DataSetFX{" + "code=" + code.get() + ", description=" + description.get() + '}';
    }
    
    public void setCode(String code){
        this.code.set(code);
    }
    
    public void setDescription(String description){
        this.description.set(description);
    }
    
    public String getCode() {
        return code.get();
    }

    public String getDescription() {
        return description.get();
    }
    
}
