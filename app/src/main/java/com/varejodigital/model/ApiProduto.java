package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thiagocortat on 6/10/15.
 */
public class ApiProduto {

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

    public class Produto {
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
        @SerializedName("nome")
        private String nome;
        @SerializedName("codigoInterno")
        private int codigoInterno;
        @SerializedName("codigoExterno")
        private int codigoExterno;

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

        public class Gondola {
            /**
             * prontoReposicao : 0
             */
            @SerializedName("prontoReposicao")
            private int prontoReposicao;

            public void setProntoReposicao(int prontoReposicao) {
                this.prontoReposicao = prontoReposicao;
            }

            public int getProntoReposicao() {
                return prontoReposicao;
            }
        }

        public class Loja {
            /**
             * id : 1
             * nome : PROJETANDOO
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
        }

        public class Estoque {
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

        public class Imagens {
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
    }
}
