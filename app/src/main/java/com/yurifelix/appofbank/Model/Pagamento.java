package com.yurifelix.appofbank.Model;

import java.util.Calendar;

/**
 * Created by yurif on 25/04/2018.
 */

public class Pagamento {

    private String local;
    private String codigo;
    private Double valor;
    private Calendar dataPagamento;

    public Pagamento(String local, String codigo , Double valor) {
        this.local = local;
        this.valor = valor;
        this.codigo = codigo;
        this.dataPagamento = Calendar.getInstance();
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
