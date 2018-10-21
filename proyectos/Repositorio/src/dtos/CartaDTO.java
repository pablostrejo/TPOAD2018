package dtos; 

import java.io.Serializable;

import enums.Palo;

public class CartaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Palo palo;
	private int numero;
	private int posicionValor;

	public CartaDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPosicionValor() {
		return posicionValor;
	}

	public void setPosicionValor(int posicionValor) {
		this.posicionValor = posicionValor;
	}

	public String getNombreImagen() {
		return palo.name().toUpperCase() + numero + ".PNG";
	}

	@Override
	public String toString() {
		return numero + " de " + palo.name();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaDTO other = (CartaDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
