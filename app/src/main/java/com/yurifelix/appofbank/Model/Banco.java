package com.yurifelix.appofbank.Model;

/**
 * Created by yurif on 25/04/2018.
 */


public class Banco {
    private static final long serialVersionUID = 1L;

    private String nome;

    private int limiteMaximo;
    private int limiteMinimo;

    private Agencia[] agencias;


    public Banco(String nome) {
        this.nome = nome;
        this.limiteMaximo = limiteMaximo;
        this.limiteMinimo = limiteMinimo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLimiteMaximo() {
        return limiteMaximo;
    }

    public void setLimiteMaximo(int limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }

    public int getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(int limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    public Agencia[] getAgencias() {
        return agencias;
    }

    public void setAgencias(Agencia[] agencias) {
        this.agencias = agencias;
    }
}
