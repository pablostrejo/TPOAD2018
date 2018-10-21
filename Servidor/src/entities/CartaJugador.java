package entities;

import javax.persistence.*;

import dtos.CartaJugadorDTO;

@Entity
@Table(name = "CartasJugador")
public class CartaJugador {

	@Id
	@Column(name = "id_cartaJugador", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jugador")
	private Jugador jugador;

	@ManyToOne /* fetch = FetchType.EAGER) */
	@JoinColumn(name = "id_carta")
	private Carta carta;

	@Column(columnDefinition = "bit")
	private boolean tirada;

	public CartaJugador(Jugador jugador, Carta carta, boolean tirada) {
		this.jugador = jugador;
		this.carta = carta;
		this.tirada = tirada;
	}

	public CartaJugador() {

	}

	public CartaJugadorDTO toDTO() {
		CartaJugadorDTO dto = new CartaJugadorDTO();

		dto.setCarta(this.carta.toDTO());
		dto.setId(this.id);
		dto.setJugador(this.jugador.toDTO());
		dto.setTirada(this.tirada);

		return dto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public boolean isTirada() {
		return tirada;
	}

	public void setTirada(boolean tirada) {
		this.tirada = tirada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carta == null) ? 0 : carta.hashCode());
		result = prime * result + id;
		result = prime * result + ((jugador == null) ? 0 : jugador.hashCode());
		result = prime * result + (tirada ? 1231 : 1237);
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

		CartaJugador other = (CartaJugador) obj;
		if (carta == null) {
			if (other.carta != null)
				return false;
		} else if (!carta.equals(other.carta))
			return false;

		if (id != other.id)
			return false;

		if (jugador == null) {
			if (other.jugador != null)
				return false;
		} else if (!jugador.equals(other.jugador))
			return false;
		if (tirada != other.tirada)
			return false;

		return true;
	}

}
