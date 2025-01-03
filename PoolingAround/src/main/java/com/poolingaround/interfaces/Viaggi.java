package com.poolingaround.interfaces;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viaggi {
    private int id;
    private LocalDate data;
    private int durata; // hours
    private String partenza;
    private String arrivo;
    private boolean disponibile;
}
