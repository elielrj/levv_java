package com.example.levv.model.bo.pedido;

public class Item {

	private int quantidade;
	private int peso;
	private int volume;

	public Item(int quantidade, int peso, int volume) {
		this.quantidade = quantidade;
		this.peso = peso;
		this.volume = volume;
	}

	public Item(ItemBuilder itemBuilder) {
		this.quantidade = itemBuilder.quantidade;
		this.peso = itemBuilder.peso;
		this.volume = itemBuilder.volume;
	}

	public Item() {
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public static class ItemBuilder{

		private int quantidade;
		private int peso;
		private int volume;

		public ItemBuilder setQuantidade(int quantidade) {
			this.quantidade = quantidade;
			return this;
		}

		public ItemBuilder setPeso(int peso) {
			this.peso = peso;
			return this;
		}

		public ItemBuilder setVolume(int volume) {
			this.volume = volume;
			return this;
		}

		public Item create() {
			return new Item(this);
		}
	}
}
