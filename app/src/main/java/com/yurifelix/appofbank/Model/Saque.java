package com.yurifelix.appofbank.Model;

import java.util.Calendar;


/**
 * Created by yurif on 28/04/2018.
 */

public class Saque {

    private double valor;

    private Calendar data;

    public Saque(double valor) {
        this.valor = valor;

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }



    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

}
