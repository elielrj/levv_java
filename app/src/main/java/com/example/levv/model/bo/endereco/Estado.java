package com.example.levv.model.bo.endereco;

import androidx.annotation.NonNull;

public class Estado {


	private String nome;
	private String sigla;
	private boolean status;


	public Estado(String nome) {
		this.nome = nome;
		this.sigla = buscarSigla(nome);
	}

	public Estado() {
	}

	public Estado(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public Estado( String nome, String sigla, boolean status) {
		this(nome, sigla);
		this.status = status;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public String buscarSigla(@NonNull String estado) {

		switch (estado) {
			case "Acre":
				return "AC";
			case "Alagoas":
				return "AL";
			case "Amapá":
				return "AP";
			case "Amazonas":
				return "AM";
			case "Bahia":
				return "BA";
			case "Ceará":
				return "CE";
			case "Espírito Santo":
				return "ES";
			case "Goiás":
				return "GO";
			case "Maranhão":
				return "MA";
			case "Mato Grosso":
				return "MT";
			case "Mato Grosso do Sul":
				return "MS";
			case "Minas Gerais":
				return "MG";
			case "Pará":
				return "PA";
			case "Paraíba":
				return "PB";
			case "Paraná":
				return "PR";
			case "Pernambuco":
				return "PE";
			case "Piauí":
				return "PI";
			case "Rio de Janeiro":
				return "RJ";
			case "Rio Grande do Norte":
				return "RN";
			case "Rio Grande do Sul":
				return "RS";
			case "Rondônia":
				return "RO";
			case "Roraima":
				return "RR";
			case "Santa Catarina":
				return "SC";
			case "São Paulo":
				return "SP";
			case "Sergipe":
				return "SE";
			case "Tocantins":
				return "TO";
			case "Distrito Federal":
				return "DF";
			default:
				return "erro";
		}
	}

	@Override
	public String toString() {
		return nome+ "/" + sigla;
	}
}
