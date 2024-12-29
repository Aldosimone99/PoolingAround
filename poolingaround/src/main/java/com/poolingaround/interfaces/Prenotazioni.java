package com.poolingaround.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazioni {
    private int id;
    private int idViaggio;
    private int idUtente;
}
