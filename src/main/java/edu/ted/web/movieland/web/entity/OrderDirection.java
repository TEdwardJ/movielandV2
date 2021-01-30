package edu.ted.web.movieland.web.entity;

public enum OrderDirection {
    ASC,
    DESC;

    public static OrderDirection validateEnumAndReturn(String value){
        for (var item : values()) {
            if (item.toString().equalsIgnoreCase(value)){
                return item;
            }
        }
        return null;
    }
}
