package logic;

import java.util.*;

/**
 *
 * @author FINDarkside
 */
public class FieldList {

    private String objectName;
    private List<String> fieldNames = new ArrayList<>();

    public FieldList(String name) {
        this.objectName = name;
    }
    
    public void add(String name){
        fieldNames.add(name);
    }

    public String getObjectName() {
        return objectName;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }
    
    
    

}
