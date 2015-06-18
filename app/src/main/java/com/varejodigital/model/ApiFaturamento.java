package com.varejodigital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagocortat on 6/10/15.
 */
public class ApiFaturamento implements Serializable {


    /**
     * faturamento : {"porDia":[],"acumulado":"128.38","ano":2015,"medio":"11.67","porMes":[{"mes":1,"acumulado":"122.37","medio":"13.60","porSemana":[{"semana":4,"acumulado":"122.37","medio":"13.60"}]},{"mes":2,"acumulado":"6.01","medio":"3.01","porSemana":[{"semana":5,"acumulado":"6.01","medio":"3.01"}]}]}
     */
    @SerializedName("faturamento")
    private Faturamento faturamento;

    public void setFaturamento(Faturamento faturamento) {
        this.faturamento = faturamento;
    }

    public Faturamento getFaturamento() {
        return faturamento;
    }

    public class Faturamento implements Serializable {
        /**
         * porDia : []
         * acumulado : 128.38
         * ano : 2015
         * medio : 11.67
         * porMes : [{"mes":1,"acumulado":"122.37","medio":"13.60","porSemana":[{"semana":4,"acumulado":"122.37","medio":"13.60"}]},{"mes":2,"acumulado":"6.01","medio":"3.01","porSemana":[{"semana":5,"acumulado":"6.01","medio":"3.01"}]}]
         */
        @SerializedName("porDia")
        private List<PorDia> porDia;
        @SerializedName("acumulado")
        private String acumulado;
        @SerializedName("ano")
        private int ano;
        @SerializedName("medio")
        private String medio;
        @SerializedName("porMes")
        private List<PorMes> porMes;

        public void setPorDia(List<PorDia> porDia) {
            this.porDia = porDia;
        }

        public void setAcumulado(String acumulado) {
            this.acumulado = acumulado;
        }

        public void setAno(int ano) {
            this.ano = ano;
        }

        public void setMedio(String medio) {
            this.medio = medio;
        }

        public void setPorMes(List<PorMes> porMes) {
            this.porMes = porMes;
        }

        public List<PorDia> getPorDia() {
            return porDia;
        }

        public String getAcumulado() {
            return acumulado;
        }

        public int getAno() {
            return ano;
        }

        public String getMedio() {
            return medio;
        }

        public List<PorMes> getPorMes() {
            return porMes;
        }

        public class PorMes implements Serializable {
            /**
             * mes : 1
             * acumulado : 122.37
             * medio : 13.60
             * porSemana : [{"semana":4,"acumulado":"122.37","medio":"13.60"}]
             */
            @SerializedName("mes")
            private int mes;
            @SerializedName("acumulado")
            private String acumulado;
            @SerializedName("medio")
            private String medio;
            @SerializedName("porSemana")
            private List<PorSemana> porSemana;

            public void setMes(int mes) {
                this.mes = mes;
            }

            public void setAcumulado(String acumulado) {
                this.acumulado = acumulado;
            }

            public void setMedio(String medio) {
                this.medio = medio;
            }

            public void setPorSemana(List<PorSemana> porSemana) {
                this.porSemana = porSemana;
            }

            public int getMes() {
                return mes;
            }

            public String getAcumulado() {
                return acumulado;
            }

            public String getMedio() {
                return medio;
            }

            public List<PorSemana> getPorSemana() {
                return porSemana;
            }

            public class PorSemana implements Serializable {
                /**
                 * semana : 4
                 * acumulado : 122.37
                 * medio : 13.60
                 */
                @SerializedName("semana")
                private int semana;
                @SerializedName("acumulado")
                private String acumulado;
                @SerializedName("medio")
                private String medio;

                public void setSemana(int semana) {
                    this.semana = semana;
                }

                public void setAcumulado(String acumulado) {
                    this.acumulado = acumulado;
                }

                public void setMedio(String medio) {
                    this.medio = medio;
                }

                public int getSemana() {
                    return semana;
                }

                public String getAcumulado() {
                    return acumulado;
                }

                public String getMedio() {
                    return medio;
                }
            }
        }

        public class PorDia implements Serializable {
            /**
             * dia : "2015-01-25"
             * acumulado : 122.37
             * medio : 13.60
             */
            @SerializedName("dia")
            private String dia;
            @SerializedName("acumulado")
            private String acumulado;
            @SerializedName("medio")
            private String medio;

            public void setDia(String dia) {
                this.dia = dia;
            }

            public void setAcumulado(String acumulado) {
                this.acumulado = acumulado;
            }

            public void setMedio(String medio) {
                this.medio = medio;
            }

            public String getDia() {
                return dia;
            }

            public String getAcumulado() {
                return acumulado;
            }

            public String getMedio() {
                return medio;
            }
        }
    }
}
