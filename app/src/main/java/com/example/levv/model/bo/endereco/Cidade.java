package com.example.levv.model.bo.endereco;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

public class Cidade {

	private String nome;
	private boolean status;
	private Estado estado;
	private DocumentReference referencia;


	public Cidade() {
	}

	public Cidade(String nome, Estado estado) {
		this.nome = nome;
		this.estado = estado;
		this.status = false;
	}

	public DocumentReference getReferencia() {
		return referencia;
	}

	public void setReferencia(DocumentReference referencia) {
		this.referencia = referencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Exclude
	public Estado getEstado() {
		return estado;
	}

	@Exclude
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return nome + ", " + estado.toString();
	}
}
