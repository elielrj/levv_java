package com.example.levv.model.bo.meioDeTransporte;

import com.example.levv.model.bo.usuario.Arquivo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;


public class Veiculo {


    private String modelo;
    private String marca;
    private String placa;
    private String renavam;
    private boolean status;
    private MeioDeTransporte meioDeTransporte;
    private Arquivo documentoDeCrlv;
    private DocumentReference referenciaMeioDeTransporte;


    public Veiculo() {
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public DocumentReference getReferenciaMeioDeTransporte() {
        return referenciaMeioDeTransporte;
    }

    public void setReferenciaMeioDeTransporte(DocumentReference referenciaMeioDeTransporte) {
        this.referenciaMeioDeTransporte = referenciaMeioDeTransporte;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Exclude
    public MeioDeTransporte getMeioDeTransporte() {
        return meioDeTransporte;
    }

    @Exclude
    public void setMeioDeTransporte(MeioDeTransporte meioDeTransporte) {
        this.meioDeTransporte = meioDeTransporte;
    }

    @Exclude
    public Arquivo getDocumentoDeCrlv() {
        return documentoDeCrlv;
    }

    @Exclude
    public void setDocumentoDeCrlv(Arquivo documentoDeCrlv) {
        this.documentoDeCrlv = documentoDeCrlv;
    }

    @Override
    public String toString() {
        return
                meioDeTransporte.toString() + ": " +
                        modelo + "/" +
                        marca + " - " +
                        placa;
    }


}