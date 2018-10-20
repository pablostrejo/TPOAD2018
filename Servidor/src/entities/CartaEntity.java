package entities;

import javax.persistence.*;

import dtos.CartaDTO;

import enums.Palo;

/**
 * El atributo posicionValor, que sirve para el caso de tirar una carta ya sea
 * jugando normal o en truco. Ya que por ejemplo el ancho de espada va a tener
 * posicion 1, y en el caso que haya valores iguales como por ejemplo el numero
 * "3", se le asigna la misma posicion a la carta.
 **/

@Entity
@Table(name = "Cartas")
public class CartaEntity {
	@Id
	@Column(name = "id_carta", nullable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "tinyint")
	private Palo palo;

	@Column
	private int numero;

	@Column(name = "posicion_valor")
	private int posicionValor;


	public CartaEntity() {
		
	}

	public CartaEntity(Palo palo, int numero, int posicionValor) {
		this.palo = palo;
		this.numero = numero;
		this.posicionValor = posicionValor;
	}

	public CartaDTO toDTO() {
		CartaDTO dto = new CartaDTO();
		dto.setId(this.id);
		dto.setNumero(this.numero);
		dto.setPalo(this.palo);
		dto.setPosicionValor(this.posicionValor);
		return dto;
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

	public boolean sosCartaNegra() {
		return (this.numero == 10 || this.numero == 11 || this.numero == 12);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + numero;
		result = prime * result + ((palo == null) ? 0 : palo.hashCode());
		result = prime * result + posicionValor;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaEntity other = (CartaEntity) obj;
		if (id != other.id)
			return false;
		if (numero != other.numero)
			return false;
		if (palo != other.palo)
			return false;
		if (posicionValor != other.posicionValor)
			return false;
		return true;
	}

}
