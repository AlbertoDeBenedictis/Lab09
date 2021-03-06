package it.polito.tdp.borders.model;

public class Country {

	private int codice;
	private String sigla;
	private String nome;

	public Country() {

	}

	public Country(int codice, String sigla, String nome) {
		this.codice = codice;
		this.sigla = sigla;
		this.nome = nome;

	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
