package br.ufc.quixada.dsdm.trabalho3.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Carro implements Serializable {

    @Expose
    private Integer id;

    @Expose
    private String modelo;

    @Expose
    private String marca;

    @Expose
    private String cor;

    @Expose
    private String anoDeLancamento;

    public Carro(){}

    public Carro(Integer id, String modelo, String marca, String cor, String anoDeLancamento) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.anoDeLancamento = anoDeLancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public void setAnoDeLancamento(String anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carro carro = (Carro) o;

        return getId().equals(carro.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ID: " + id + ",\tMODELO: " + modelo + "\nMARCA: " + marca + ",\tCOR:" + cor + ",\tANO:" + anoDeLancamento;
    }
}
