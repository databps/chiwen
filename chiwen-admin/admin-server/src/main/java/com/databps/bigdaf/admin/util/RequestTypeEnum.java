package com.databps.bigdaf.admin.util;

/**
 * @author haipeng
 * @create 17-9-29 上午9:35
 */
public enum RequestTypeEnum {
    GET("get"),
    POST("post"),
    DELETE("delete"),
    UPDATE("update"),
    ;

    private String name ;

    RequestTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
