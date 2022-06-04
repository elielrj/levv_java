package com.example.levv.model.bo.endereco;

import com.example.levv.support.AppUtil;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

public class Endereco {

	private String logradouro;
	private String numero;
	private String complemento;
	private String tipoDeMoradia;
	private String cep;
	private boolean status;
	private DocumentReference referencia;
	private Bairro bairro;
	private GeoPoint LatLng;

	public GeoPoint getLatLng() {
		return LatLng;
	}

	public void setLatLng(GeoPoint latLng) {
		LatLng = latLng;
	}

	public void setReferencia(DocumentReference referencia) {
		this.referencia = referencia;
	}

	public Endereco() {
	}

	public Endereco(EnderecoBuilder enderecoBuilder) {
		this.logradouro = enderecoBuilder.logradouro;
		this.numero = enderecoBuilder.numero;
		this.complemento = enderecoBuilder.complemento;
		this.tipoDeMoradia = enderecoBuilder.tipoDeMoradia;
		this.cep = enderecoBuilder.cep;
		this.bairro = enderecoBuilder.bairro;
		this.status = enderecoBuilder.status;
		this.LatLng = enderecoBuilder.LatLng;
	}

	public DocumentReference getReferencia() {
		return referencia;
	}

	public static class EnderecoBuilder{
		private String logradouro;
		private String numero;
		private String complemento;
		private String tipoDeMoradia;
		private String cep;
		private boolean status;
		private Bairro bairro;
		private GeoPoint LatLng;


		public EnderecoBuilder setLatLng(GeoPoint latLng) {
			LatLng = latLng;
			return this;
		}

		public EnderecoBuilder setLogradouro(String logradouro) {
			this.logradouro = logradouro;
			return this;
		}

		public EnderecoBuilder setNumero(String numero) {
			this.numero = numero;
			return this;
		}

		public EnderecoBuilder setComplemento(String complemento) {
			this.complemento = complemento;
			return this;
		}

		public EnderecoBuilder setTipoDeMoradia(String tipoDeMoradia) {
			this.tipoDeMoradia = tipoDeMoradia;
			return this;
		}

		public EnderecoBuilder setCep(String cep) {
			this.cep = AppUtil.removerMascaraCep(cep);
			return this;
		}

		public EnderecoBuilder setStatus(boolean status) {
			this.status = status;
			return this;
		}

		public EnderecoBuilder setBairro(Bairro bairro) {
			this.bairro = bairro;
			return this;
		}


		public Endereco createEndereco(){
			return new Endereco(this);
		}
	}

	public Endereco(String logradouro, String numero, String complemento, String tipoDeMoradia, String cep, Bairro bairro) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.tipoDeMoradia = tipoDeMoradia;
		this.cep = AppUtil.removerMascaraCep(cep);
		this.status = false;
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTipoDeMoradia() {
		return tipoDeMoradia;
	}

	public void setTipoDeMoradia(String tipoDeMoradia) {
		this.tipoDeMoradia = tipoDeMoradia;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = AppUtil.removerMascaraCep(cep);
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	@Exclude
	public Bairro getBairro() {
		return bairro;
	}


	@Exclude
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Override
	public String toString() {
		return logradouro +
				", Nr "+ numero  +
				isComplemento() +
				" - " + tipoDeMoradia +
				", " + bairro.toString();
	}

	private String isComplemento(){
		if(complemento.isEmpty())
			return ", Comp" + complemento;
		else
			return "";
	}
}
