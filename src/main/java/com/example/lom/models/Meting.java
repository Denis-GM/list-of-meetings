package com.example.lom.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meting {

    private Integer id;
    private String title;
    private String details;
    private Date date;
/*
    private String place;
    private int totalNumberSeats;
    private int availableSeats;*/

}
