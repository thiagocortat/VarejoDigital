package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by thiagocortat on 3/24/15.
 */
//@Parcel
public class Funcionario {

    protected String id;
    protected String nome;
    protected double salario;
    @SerializedName("data_admissao")
    protected String dataAdmissao;
    protected String cargo;
    protected boolean activo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
