package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Thiago Cortat on 03/07/15.
 */
public class ApiUpdateProduto implements Serializable{


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

    public class Produto implements Serializable{
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

        public class Gondola implements Serializable {
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

        public class Estoque implements Serializable {
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
