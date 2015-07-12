package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Thiago Cortat on 03/07/15.
 */
public class ApiUpdateProduto implements Serializable{

    public ApiUpdateProduto(Produto produto) {
        this.produto = produto;
    }

    public ApiUpdateProduto(int idproduto) {
        this.produto = new Produto(idproduto);
    }

    public ApiUpdateProduto(int idproduto, double qtdEstoque) {
        this.produto = new Produto(idproduto, qtdEstoque);
    }

    public ApiUpdateProduto(int idproduto, double qtdEstoque, double qtdGondola) {
        this.produto = new Produto(idproduto, qtdEstoque, qtdGondola);
    }

    /**
     * produto : {"id":70598,"gondola":{"disponivel":10},"estoque":{"disponivel":10}}
     */
    @SerializedName("produto")
    private Produto produto;

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public static class Produto implements Serializable{

        public Produto(int idproduto) {
            this.id = idproduto;
        }

        public Produto(int idproduto, double qtdEstoque) {
            this.id = idproduto;
            this.estoque = new Estoque(qtdEstoque);
        }

        public Produto(int idproduto, double qtdEstoque, double qtdGondola) {
            this.id = idproduto;
            this.estoque = new Estoque(qtdEstoque);
            this.gondola = new Gondola(qtdGondola);
        }
        /**
         * id : 70598
         * gondola : {"disponivel":10}
         * estoque : {"disponivel":10}
         */
        @SerializedName("id")
        private int id;
        @SerializedName("gondola")
        private Gondola gondola;
        @SerializedName("estoque")
        private Estoque estoque;

        public void setId(int id) {
            this.id = id;
        }

        public void setGondola(Gondola gondola) {
            this.gondola = gondola;
        }

        public void setEstoque(Estoque estoque) {
            this.estoque = estoque;
        }

        public int getId() {
            return id;
        }

        public Gondola getGondola() {
            return gondola;
        }

        public Estoque getEstoque() {
            return estoque;
        }

        public static class Gondola implements Serializable {

            public Gondola(double disponivel) {
                this.disponivel = disponivel;
            }

            /**
             * disponivel : 10.0
             */
            @SerializedName("disponivel")
            private double disponivel;

            public void setDisponivel(double disponivel) {
                this.disponivel = disponivel;
            }

            public double getDisponivel() {
                return disponivel;
            }
        }

        public static class Estoque implements Serializable {

            public Estoque(double disponivel) {
                this.disponivel = disponivel;
            }

            /**
             * disponivel : 10.0
             */
            @SerializedName("disponivel")
            private double disponivel;

            public void setDisponivel(double disponivel) {
                this.disponivel = disponivel;
            }

            public double getDisponivel() {
                return disponivel;
            }
        }
    }
}
