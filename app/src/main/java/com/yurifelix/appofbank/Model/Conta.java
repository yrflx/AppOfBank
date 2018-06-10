package com.yurifelix.appofbank.Model;

/**
 * Created by yurif on 25/04/2018.
 */

public class Conta {

    private String numero;
    private Agencia agencia;
    private Banco banco;

    private Double limite;
    private Double saldo;

    private Pagamento[] historicoPagamentos;
    private Tranferencia[] historicoTransferencia;
    private Fatura[] faturas;
    private Saque[] saques;


    public Conta(String numero, Agencia agencia, Banco banco, Double limite) {
        this.numero = numero;
        this.agencia = agencia;
        this.banco = banco;
        this.limite = limite;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Pagamento[] getHistoricoPagamentos() {
        return historicoPagamentos;
    }

    public void setHistoricoPagamentos(Pagamento[] historicoPagamentos) {
        this.historicoPagamentos = historicoPagamentos;
    }

    public Tranferencia[] getHistoricoTransferencia() {
        return historicoTransferencia;
    }

    public void setHistoricoTransferencia(Tranferencia[] historicoTransferencia) {
        this.historicoTransferencia = historicoTransferencia;
    }

    public Fatura[] getFaturas() {
        return faturas;
    }

    public void setFaturas(Fatura[] faturas) {
        this.faturas = faturas;
    }
}
