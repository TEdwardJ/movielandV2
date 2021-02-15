package edu.ted.web.movieland.web.entity;

public enum OrderByColumn {
    RATING("RATING", "M_RATING"),
    PRICE("PRICE", "M_PRICE");

    private String dbColumnName;
    private String name;

    OrderByColumn(String name,String dbColumnName) {
        this.dbColumnName = dbColumnName;
        this.name = name;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public String getName() {
        return name;
    }


    public static boolean isValid(String value){
        return validateEnumAndReturn(value) != null;
    }

    public static OrderByColumn validateEnumAndReturn(String value){
        for (var item : values()) {
            if (item.getName().equalsIgnoreCase(value)){
                return item;
            }
        }
        return null;
    }
}
