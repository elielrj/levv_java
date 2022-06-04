package com.example.levv.model.bo.meioDeTransporte;

public class MeioDeTransporte {

    private String descricao;
    private int limiteDePeso;
    private int limiteDeVolume;
    private boolean status;

    public MeioDeTransporte(String descricao, boolean status, int limiteDeVolume, int limiteDePeso) {
        this.descricao = descricao;
        this.status = status;
        this.limiteDeVolume = limiteDeVolume;
        this.limiteDePeso = limiteDePeso;
    }

    public MeioDeTransporte() {
    }

    public MeioDeTransporte(MeioDeTransporteBuilder meioDeTransporteBuilder) {
        this.descricao = meioDeTransporteBuilder.descricao;
        this.status = meioDeTransporteBuilder.status;
        this.limiteDeVolume = meioDeTransporteBuilder.limiteDeVolume;
        this.limiteDePeso = meioDeTransporteBuilder.limiteDePeso;
    }

    @Override
    public String toString() {
        return descricao;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getLimiteDeVolume() {
        return limiteDeVolume;
    }

    public void setLimiteDeVolume(int limiteDeVolume) {
        this.limiteDeVolume = limiteDeVolume;
    }

    public int getLimiteDePeso() {
        return limiteDePeso;
    }

    public void setLimiteDePeso(int limiteDePeso) {
        this.limiteDePeso = limiteDePeso;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class MeioDeTransporteBuilder {


        private String descricao;
        private int limiteDePeso;
        private int limiteDeVolume;
        private boolean status;

        public MeioDeTransporteBuilder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public MeioDeTransporteBuilder setLimiteDePeso(int limiteDePeso) {
            this.limiteDePeso = limiteDePeso;
            return this;
        }

        public MeioDeTransporteBuilder setLimiteDeVolume(int limiteDeVolume) {
            this.limiteDeVolume = limiteDeVolume;
            return this;
        }

        public MeioDeTransporteBuilder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public MeioDeTransporte create() {
            return new MeioDeTransporte(this);
        }
    }
}
