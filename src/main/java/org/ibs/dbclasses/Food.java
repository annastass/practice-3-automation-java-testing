package org.ibs.dbclasses;

public class Food {

    private String name;
    private String type;
    private boolean exotic;

    public Food(String name, String type, boolean exotic){
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isExotic() {
        return exotic;
    }
}
