package com.yurifelix.appofbank.Model;

import java.util.Date;

/**
 * Created by yurif on 25/04/2018.
 */

public class Cliente {


    private String nome;
    private Banco[] bancos;
    private Conta[] contas;

    private String email;
    private String senha;

    private Date dataNascimento;
    private Date dataCadastro;

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(String nome, String email, String senha, Date dataNascimento, Date dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Conta[] getContas() {
        return contas;
    }

    public void setContas(Conta[] contas) {
        this.contas = contas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Banco[] getBancos() {
        return bancos;
    }

    public void setBancos(Banco[] bancos) {
        this.bancos = bancos;
    }
}
