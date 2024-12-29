package com.poolingaround.interfaces;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utenti {
    private int id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String indirizzo;
    private String documentoId;
}
