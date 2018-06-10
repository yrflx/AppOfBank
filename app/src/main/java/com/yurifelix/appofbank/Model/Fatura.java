package com.yurifelix.appofbank.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yurif on 25/04/2018.
 */

public class Fatura {

    private Calendar vencimento;
    private Calendar fechamento;
    private ArrayList<Pagamento> pagamentos;
    private String status; //aberta - fechada - vencida - paga

    public Fatura(Calendar fechamento) {
        this.fechamento = fechamento;
        this.vencimento = Calendar.getInstance();

        vencimento.set(Calendar.DAY_OF_MONTH, fechamento.get(Calendar.DAY_OF_MONTH));
        vencimento.set(Calendar.MONTH, fechamento.get(Calendar.MONTH));
        vencimento.set(Calendar.YEAR, fechamento.get(Calendar.YEAR));
        vencimento.set(Calendar.DAY_OF_MONTH, vencimento.get(Calendar.DAY_OF_MONTH ) + 10);


    }

    public Calendar getVencimento() {
        return vencimento;
    }

    public void setVencimento(Calendar vencimento) {
        this.vencimento = vencimento;
    }

    public Calendar getFechamento() {
        return fechamento;
    }

    public void setFechamento(Calendar fechamento) {
        this.fechamento = fechamento;
    }

    public ArrayList<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(ArrayList<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
