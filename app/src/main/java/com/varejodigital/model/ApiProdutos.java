package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagocortat on 6/10/15.
 */
public class ApiProdutos implements Serializable {

    @SerializedName("list")
    private List<Produto> list;

    public void setList(List<Produto> list) {
        this.list = list;
    }

    public List<Produto> getList() {
        return list;
    }

    public class Produto implements Serializable {
        /**
         * id : 132
         * nome : BATOM VULT Nº 01
         */
        @SerializedName("id")
        private int id;
        @SerializedName("nome")
        private String nome;

        public void setId(int id) {
            this.id = id;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        @Override
        public String toString() {
            return "" + nome;
        }
    }

}
