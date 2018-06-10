package com.yurifelix.appofbank.Model;


import java.util.Calendar;

/**
 * Created by yurif on 25/04/2018.
 */

public class Tranferencia {

    private Banco banco;

    private Conta emissor;
    private Conta receptor;

    private double valor;

    private Calendar dataTransferecia;


    public Tranferencia( Banco banco, Conta emissor, Conta receptor, double valor) {
        this.banco = banco;
        this.emissor = emissor;
        this.receptor = receptor;
        this.valor = valor;
        this.dataTransferecia = Calendar.getInstance();
    }


    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Conta getEmissor() {
        return emissor;
    }

    public void setEmissor(Conta emissor) {
        this.emissor = emissor;
    }

    public Conta getReceptor() {
        return receptor;
    }

    public void setReceptor(Conta receptor) {
        this.receptor = receptor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Calendar getDataTransferecia() {
        return dataTransferecia;
    }

}
