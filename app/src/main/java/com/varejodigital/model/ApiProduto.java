package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagocortat on 6/10/15.
 */
public class ApiProduto implements Serializable{

    /**
     * produto : {"id":133,"gondola":{"prontoReposicao":0},"preco":"8.06","custo":"7.66","loja":{"id":1,"nome":"PROJETANDOO"},"atributos":[],"codigoBarra":"","estoque":{"maximo":0,"disponivel":0,"pontoRessuprimento":0,"minimo":0},"imagens":[{"id":155,"url":"http://store.allinshopp.com.br/155/155.jpg"}],"nome":"BATOM VULT Nº 08","codigoInterno":0,"codigoExterno":0}
     */
    @SerializedName("produto")
    private Produto produto;

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public static class Produto implements Serializable {
        /**
         * id : 133
         * gondola : {"prontoReposicao":0}
         * preco : 8.06
         * custo : 7.66
         * loja : {"id":1,"nome":"PROJETANDOO"}
         * atributos : []
         * codigoBarra :
         * estoque : {"maximo":0,"disponivel":0,"pontoRessuprimento":0,"minimo":0}
         * imagens : [{"id":155,"url":"http://store.allinshopp.com.br/155/155.jpg"}]
         * nome : BATOM VULT Nº 08
         * codigoInterno : 0
         * codigoExterno : 0
         */
        @SerializedName("id")
        private int id;
        @SerializedName("gondola")
        private Gondola gondola;
        @SerializedName("preco")
        private String preco;
        @SerializedName("custo")
        private String custo;
        @SerializedName("loja")
        private Loja loja;
        @SerializedName("atributos")
        private List<?> atributos;
        @SerializedName("codigoBarra")
        private String codigoBarra;
        @SerializedName("estoque")
        private Estoque estoque;
        @SerializedName("imagens")
        private List<Imagens> imagens;
        @SerializedName("fornecedores")
        private List<Fornecedor> fornecedores;
        @SerializedName("nome")
        private String nome;
        @SerializedName("codigoInterno")
        private int codigoInterno;
        @SerializedName("codigoExterno")
        private int codigoExterno;
        @SerializedName("margem")
        private String margem;

        @SerializedName("vendasPorDia")
        private VendaDia vendaDia;
        @SerializedName("vendasPorSemana")
        private VendaSemana vendaSemana;
        @SerializedName("vendasPorMes")
        private VendaMes vendaMes;

        public void setId(int id) {
            this.id = id;
        }

        public void setGondola(Gondola gondola) {
            this.gondola = gondola;
        }

        public void setPreco(String preco) {
            this.preco = preco;
        }

        public void setCusto(String custo) {
            this.custo = custo;
        }

        public void setLoja(Loja loja) {
            this.loja = loja;
        }

        public void setAtributos(List<?> atributos) {
            this.atributos = atributos;
        }

        public void setCodigoBarra(String codigoBarra) {
            this.codigoBarra = codigoBarra;
        }

        public void setEstoque(Estoque estoque) {
            this.estoque = estoque;
        }

        public void setImagens(List<Imagens> imagens) {
            this.imagens = imagens;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setCodigoInterno(int codigoInterno) {
            this.codigoInterno = codigoInterno;
        }

        public void setCodigoExterno(int codigoExterno) {
            this.codigoExterno = codigoExterno;
        }

        public int getId() {
            return id;
        }

        public Gondola getGondola() {
            return gondola;
        }

        public String getPreco() {
            return preco;
        }

        public String getCusto() {
            return custo;
        }

        public Loja getLoja() {
            return loja;
        }

        public List<?> getAtributos() {
            return atributos;
        }

        public String getCodigoBarra() {
            return codigoBarra;
        }

        public Estoque getEstoque() {
            return estoque;
        }

        public List<Imagens> getImagens() {
            return imagens;
        }

        public String getNome() {
            return nome;
        }

        public int getCodigoInterno() {
            return codigoInterno;
        }

        public int getCodigoExterno() {
            return codigoExterno;
        }

        public String getMargem() {
            return margem;
        }

        public void setMargem(String margem) {
            this.margem = margem;
        }

        public List<Fornecedor> getFornecedores() {
            return fornecedores;
        }

        public void setFornecedores(List<Fornecedor> fornecedores) {
            this.fornecedores = fornecedores;
        }

        public VendaDia getVendaDia() {
            return vendaDia;
        }

        public void setVendaDia(VendaDia vendaDia) {
            this.vendaDia = vendaDia;
        }

        public VendaSemana getVendaSemana() {
            return vendaSemana;
        }

        public void setVendaSemana(VendaSemana vendaSemana) {
            this.vendaSemana = vendaSemana;
        }

        public VendaMes getVendaMes() {
            return vendaMes;
        }

        public void setVendaMes(VendaMes vendaMes) {
            this.vendaMes = vendaMes;
        }

        public static class Gondola implements Serializable {

            @SerializedName("maximo")
            private int maximo;
            @SerializedName("disponivel")
            private int disponivel;
            @SerializedName("minimo")
            private int minimo;
            @SerializedName("prontoReposicao")
            private int prontoReposicao;

            public int getMaximo() {
                return maximo;
            }

            public void setMaximo(int maximo) {
                this.maximo = maximo;
            }

            public int getDisponivel() {
                return disponivel;
            }

            public void setDisponivel(int disponivel) {
                this.disponivel = disponivel;
            }

            public int getMinimo() {
                return minimo;
            }

            public void setMinimo(int minimo) {
                this.minimo = minimo;
            }

            public void setProntoReposicao(int prontoReposicao) {
                this.prontoReposicao = prontoReposicao;
            }

            public int getProntoReposicao() {
                return prontoReposicao;
            }
        }

        public static class Loja implements Serializable  {
            /**
             * id : 1
             * nome : PROJETANDOO
             * canal: "CARIOCAO_SANTA_TEREZA",
             * codigoInterno: 15
             */
            @SerializedName("id")
            private int id;
            @SerializedName("nome")
            private String nome;
            @SerializedName("canal")
            private String canal;
            @SerializedName("codigoInterno")
            private int codigoInterno;

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

            public String getCanal() {
                return canal;
            }

            public void setCanal(String canal) {
                this.canal = canal;
            }

            public int getCodigoInterno() {
                return codigoInterno;
            }

            public void setCodigoInterno(int codigoInterno) {
                this.codigoInterno = codigoInterno;
            }
        }

        public static class Estoque implements Serializable  {
            /**
             * maximo : 0
             * disponivel : 0
             * pontoRessuprimento : 0
             * minimo : 0
             */
            @SerializedName("maximo")
            private int maximo;
            @SerializedName("disponivel")
            private int disponivel;
            @SerializedName("pontoRessuprimento")
            private int pontoRessuprimento;
            @SerializedName("minimo")
            private int minimo;

            public void setMaximo(int maximo) {
                this.maximo = maximo;
            }

            public void setDisponivel(int disponivel) {
                this.disponivel = disponivel;
            }

            public void setPontoRessuprimento(int pontoRessuprimento) {
                this.pontoRessuprimento = pontoRessuprimento;
            }

            public void setMinimo(int minimo) {
                this.minimo = minimo;
            }

            public int getMaximo() {
                return maximo;
            }

            public int getDisponivel() {
                return disponivel;
            }

            public int getPontoRessuprimento() {
                return pontoRessuprimento;
            }

            public int getMinimo() {
                return minimo;
            }
        }

        public static class Imagens  implements Serializable {
            /**
             * id : 155
             * url : http://store.allinshopp.com.br/155/155.jpg
             */
            @SerializedName("id")
            private int id;
            @SerializedName("url")
            private String url;

            public void setId(int id) {
                this.id = id;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class Fornecedor implements Serializable  {
            /**
             * id : 1
             * nome : PROJETANDOO
             * codigoInterno: 15
             */
            @SerializedName("id")
            private int id;
            @SerializedName("nome")
            private String nome;
            @SerializedName("codigoInterno")
            private int codigoInterno;

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

            public int getCodigoInterno() {
                return codigoInterno;
            }

            public void setCodigoInterno(int codigoInterno) {
                this.codigoInterno = codigoInterno;
            }
         }

        public static class VendaDia implements Serializable  {

            @SerializedName("quantidade")

            private int quantidade;

            public int getQuantidade() {
                return quantidade;
            }

            public void setQuantidade(int quantidade) {
                this.quantidade = quantidade;
            }
        }

        public static class VendaSemana implements Serializable  {

            @SerializedName("quantidade")

            private int quantidade;

            public int getQuantidade() {
                return quantidade;
            }

            public void setQuantidade(int quantidade) {
                this.quantidade = quantidade;
            }
        }

        public static class VendaMes implements Serializable  {

            @SerializedName("quantidade")

            private int quantidade;

            public int getQuantidade() {
                return quantidade;
            }

            public void setQuantidade(int quantidade) {
                this.quantidade = quantidade;
            }
        }
    }
}
