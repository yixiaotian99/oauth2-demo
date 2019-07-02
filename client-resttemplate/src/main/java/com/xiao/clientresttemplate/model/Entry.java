package com.xiao.clientresttemplate.model;


import lombok.Data;

/**
 * 传递参数
 */
@Data
public class Entry {

    private String value;


    public Entry(String value) {
        super();
        this.value = value;
    }
}
