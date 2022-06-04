package com.example.levv.model.bo.usuario;

import com.example.levv.model.bo.meioDeTransporte.Veiculo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

public class Transportador extends Pessoa {

    private boolean transportadorAprovado;
    private String numeroRegistroDeDocumento;
    private Veiculo veiculo;
    private DocumentReference referenciaVeiculo;
    private Arquivo documentoDeIdentificacao;
    private GeoPoint geoPoint;

    public Transportador() {
        super();
    }

    public boolean isTransportadorAprovado() {
        return transportadorAprovado;
    }

    public void setTransportadorAprovado(boolean transportadorAprovado) {
        this.transportadorAprovado = transportadorAprovado;
    }

    public String getNumeroRegistroDeDocumento() {
        return numeroRegistroDeDocumento;
    }

    public void setNumeroRegistroDeDocumento(String numeroRegistroDeDocumento) {
        this.numeroRegistroDeDocumento = numeroRegistroDeDocumento;
    }

    @Exclude
    public Veiculo getVeiculo() {
        return veiculo;
    }

    @Exclude
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public DocumentReference getReferenciaVeiculo() {
        return referenciaVeiculo;
    }

    public void setReferenciaVeiculo(DocumentReference referenciaVeiculo) {
        this.referenciaVeiculo = referenciaVeiculo;
    }

    @Exclude
    public Arquivo getDocumentoDeIdentificacao() {
        return documentoDeIdentificacao;
    }

    @Exclude
    public void setDocumentoDeIdentificacao(Arquivo documentoDeIdentificacao) {
        this.documentoDeIdentificacao = documentoDeIdentificacao;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
