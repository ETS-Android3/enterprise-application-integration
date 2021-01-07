package com.example.cassandradatabase.springbootwebdemo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {

    @PrimaryKey
    private int id;
    private String status;

    private String timestamp;
    private int error_code;
    private String error_message;
    private double lan;
    private double lon;


}
