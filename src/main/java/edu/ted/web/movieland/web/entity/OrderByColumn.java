package edu.ted.web.movieland.web.entity;

public enum OrderByColumn {
    RATING("M_RATING"),
    PRICE("M_PRICE");

    private String dbColumnName;

    OrderByColumn(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public static OrderByColumn validateEnumAndReturn(String value){
        for (var item : values()) {
            if (item.toString().equalsIgnoreCase(value)){
                return item;
            }
        }
        return null;
    }
}
