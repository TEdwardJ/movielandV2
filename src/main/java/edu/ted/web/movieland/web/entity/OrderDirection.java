package edu.ted.web.movieland.web.entity;

public enum OrderDirection {
    ASC("ASC"),
    DESC("DESC");


    private String order;

    OrderDirection(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public static OrderDirection validateEnumAndReturn(String value){
        for (var item : values()) {
            if (item.getOrder().equalsIgnoreCase(value)){
                return item;
            }
        }
        return null;
    }
}
