package edu.ted.web.movieland.common;

public enum OrderByColumn {
    RATING("rating", "M_RATING"),
    PRICE("price", "M_PRICE");

    private final String dbColumnName;
    private final String name;

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
