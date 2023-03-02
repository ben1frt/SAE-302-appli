package com.example.ecoapp;

import java.util.Date;

public class EcowattData {
    private Date timestamp;
    private int[] pas;

    public EcowattData(Date timestamp, int[] pas) {
        this.timestamp = timestamp;
        this.pas = pas;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getPas(int pasNumber) {
        return pas[pasNumber - 1]; // le tableau commence à l'index 0, pas1 correspond à l'index 0
    }

    public int getTotalPas() {
        int total = 0;
        for (int p : pas) {
            total += p;
        }
        return total;
    }
}

