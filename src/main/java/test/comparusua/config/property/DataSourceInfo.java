package test.comparusua.config.property;

import lombok.Data;

import java.util.HashMap;

@Data
public class DataSourceInfo {

    private String name;
    private String strategy;
    private String url;
    private String table;
    private String user;
    private String password;
    private HashMap<String, String> mapping;
}
